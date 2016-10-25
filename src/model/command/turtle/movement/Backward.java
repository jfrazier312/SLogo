package model.command.turtle.movement;

import model.animal.Animal;
import model.command.Parameter;

public class Backward extends TurtleMovement {
	private final double paramCount;
	
	public Backward() {
		super();
		numParams = 2;
		paramCount = 1;
	}

	/**
	 * Moves the animal backward in its current heading by a specified number of pixels
	 * @param params - array of parameters
	 * @return the number of pixels
	 */
	@Override
	public double run(Parameter[] params) {
		Animal turtle = params[0].getAnimal();
		double pixels = params[1].getValue();
		return move(turtle, pixels * -1)*-1;
	}
}