import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import ec.util.*;

public class Bunny implements Steppable
{
	public Double2D lastd = new Double2D(0,0);
	public Double2D loc = new Double2D(0,0);

	public Double2D momentum()
    {
        return lastd;
	}

	public Double2D randomness(MersenneTwisterFast r)
    {
        double x = r.nextDouble() * 2 - 1.0;
        double y = r.nextDouble() * 2 - 1.0;
        double l = Math.sqrt(x * x + y * y);
        return new Double2D(0.05*x/l,0.05*y/l);
    }

	public void step(SimState state)
	{
		Bunnies bunnies = (Bunnies) state;
		Continuous2D yard = bunnies.yard;
		loc = yard.getObjectLocation(this);
		Double2D rand = randomness(bunnies.random);
		Double2D mome = momentum();

		double dx = rand.x + mome.x;
        double dy = rand.y + mome.y;

		// renormalize to the given step size
        double dis = Math.sqrt(dx*dx+dy*dy);
        if (dis>0)
        {
            dx = dx / dis * 0.7;
            dy = dy / dis * 0.7;
        }
        
        lastd = new Double2D(dx,dy);
        loc = new Double2D(yard.stx(loc.x + dx), yard.sty(loc.y + dy));
        yard.setObjectLocation(this, loc);
	}
}