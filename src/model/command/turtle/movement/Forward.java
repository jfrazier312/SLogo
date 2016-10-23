package model.command.turtle.movement;

import model.animal.Animal;
import model.command.Parameter;

public class Forward extends TurtleMovement {

	private final double numParams = 2;
	
	public Forward() {
		super();
	}

	/**
	 * Moves the animal forward in its current heading by a specified number of pixels
	 * @param params - array of parameters
	 * @return the number of pixels
	 */
	@Override
	public double run(Parameter[] params) {
		Animal turtle = params[0].getAnimal();
		double pixels = params[1].getValue();
		return move(turtle, pixels);
	}
}