/**
 * This is the class for the COSINE command
 * 
 * @author Aninda Manocha
 */

package model.command.math.trigonometry;

import Parsing.expression.ConstantExpression;
import Parsing.expression.ExpressionTree;
import Parsing.expression.VariableExpression;
import model.animal.Animal;
import model.command.Parameter;

public class Cosine extends MathTrigonometry {
	private final double paramCount;
	
	public Cosine() {
		super();
		numParams = 2;
		paramCount = 1;
	}

	/**
	 * Determines the cosine of a value in degrees
	 * @param params - array of parameters
	 * @return the cosine value
	 */
	@Override
	public double run(Parameter[] params) {
		Animal turtle = params[0].getAnimal();
		double degrees = ExpressionTree.getInstance().process(turtle, params[1].getNode());
		return cos(degrees);
	}
}