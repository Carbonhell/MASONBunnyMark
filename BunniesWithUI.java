import sim.engine.*;
import sim.display.*;

import sim.portrayal.continuous.*;
import sim.portrayal.simple.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import sim.portrayal.*;
import java.util.Locale;

public class BunniesWithUI extends GUIState
{
	public Display2D display;
	public JFrame displayFrame;
	ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
	public Console console;


	public static void main(String[] args)
	{
		Locale.setDefault(new Locale("en", "US"));
		BunniesWithUI vid = new BunniesWithUI();
		vid.console = new Console(vid);
		vid.console.setVisible(true);
	}

	public BunniesWithUI() { super(new Bunnies(System.currentTimeMillis()));}
	public BunniesWithUI(SimState state) { super(state); }
	public static String getName() { return "BunnyMark"; }

	public void start()
	{
		super.start();
		setupPortrayals();
	}

	public void load(SimState state)
	{
		super.load(state);
		setupPortrayals();
	}

	public void setupPortrayals()
	{
		Bunnies bunnies = (Bunnies) state;
		bunnies.console = console;
		yardPortrayal.setField(bunnies.yard);
		yardPortrayal.setPortrayalForAll(new ImagePortrayal2D(BunniesWithUI.class, "icon.png", 64.0));

		display.reset();
		display.setBackdrop(Color.white);
		display.repaint();
	}

	public void init(Controller c)
	{
		super.init(c);
		display = new Display2D(1024,600,this);
		this.scheduleRepeatingImmediatelyAfter(new RateAdjuster(60.0));
		display.setClipping(false);

		displayFrame = display.createFrame();
		displayFrame.setTitle("BunnyMark");
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		display.attach(yardPortrayal, "Yard");
	}

	public void quit()
	{
		super.quit();
		if (displayFrame != null) displayFrame.dispose();
		displayFrame = null;
		display = null;
	}

	public Object getSimulationInspectedObject() { return state; }
	
	public Inspector getInspector()
	{
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}
}