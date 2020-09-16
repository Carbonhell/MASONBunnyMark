import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.display.Console;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Bunnies extends SimState
{
	public Continuous2D yard = new Continuous2D(1.0,1024,600);
	public int numBunnies = 400;
	public int allBunnies = 0;
	public Console console; // Set by GUIState to fetch the fps count
	private PrintWriter fp;

	public Bunnies(long seed)
	{
		super(seed);
		try{
			fp = new PrintWriter(new FileWriter("output.txt"));
		} catch(IOException e) {}
	}

	public int getAllBunnies() {
		return allBunnies;
	}

	public double getFPS() {
		return console.getStepsPerSecond();
	}

	public void start()
	{
		super.start();
		yard.clear();

		Steppable steppable = new Steppable() {
			public void step(SimState state) {
				for(int i=0;i<numBunnies;i++)
				{
					Bunny bunny = new Bunny();
					yard.setObjectLocation(bunny, new Double2D(random.nextDouble()*1024, random.nextDouble() * 600));
					schedule.scheduleRepeating(bunny);
				}
				Bunnies bunnies = (Bunnies) state;
				bunnies.allBunnies += numBunnies;
				double fps = console.getStepsPerSecond();
				fp.printf("%d,%f\n", bunnies.allBunnies, fps);
				if(fps < 20.0 && fps != -1) {
					fp.flush();
					System.exit(0);
				}
			}
		};

		schedule.scheduleRepeating(steppable, 200);
	}

	public void finish()
	{
		fp.close();
		super.finish();
	}

	public static void main(String[] args)
	{
		doLoop(Bunnies.class, args);
		System.exit(0);
		
	}
}