/**
 * This is the class for the PENDOWN command
 * 
 * @author Aninda Manocha
 */

package model.command.turtle.pen;

import model.animal.Animal;
import model.command.Parameter;

public class PenDown extends TurtlePen {
	private final double paramCount;
	
	public PenDown() {
		super();
		numParams = 1;
		paramCount = 0;
	}

	/**
	 * Puts pen down so that the animal leaves a trail when it moves
	 * @param params - array of parameters
	 * @return 1
	 */
	@Override
	public double run(Parameter[] params) {
		Animal turtle = params[0].getAnimal();
		return penDown(turtle, 1);
	}
}