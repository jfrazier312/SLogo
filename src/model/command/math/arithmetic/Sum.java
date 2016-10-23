package model.command.math.arithmetic;

import model.command.Parameter;

public class Sum extends MathArithmetic {

	private final double numParams = 2;
	
	public Sum() {
		super();
	}

	/**
	 * Determines the sum of two values
	 * @param params - array of parameters
	 * @return the sum
	 */
	public double run(Parameter[] params) {
		double expression1 = params[0].getValue();
		double expression2 = params[1].getValue();
		return sum(expression1, expression2);
	}
}