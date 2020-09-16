# What is this?
This is a bunnymark benchmark test for the MASON framework. It's about displaying as many agents/sprites as possible before the framerate drops below a certain threshold (currently hardcoded at 20 FPS).

I've done it quickly to get some data to compare it with another bunnymark benchmark test, for the Amethyst framework (https://github.com/Carbonhell/amethyst-bunnymark), thus there are probably a lot of ways this can be improved.

# How to run it?

Add the MASON jar to your classpath (https://cs.gmu.edu/~eclab/projects/mason/) and compile all the java files. Then run 'java BunniesWithUI' and start the simulation. Once the framework drops below the threshold, the simulation will exit and the data will be displayed into the newly generated output.txt (format: numOfBunnies,currentFrameRate)