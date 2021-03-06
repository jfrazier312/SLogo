/**
 * This is the VariableExpression class, which extends the expression class and is used to process variables that are entered as
 * parameters
 * 
 * @author Teddy Franceschi
 * @author Aninda Manocha
 */

package Parsing.expression;

import Controller.Data;
import Parsing.TreeNode;
import model.animal.Animal;
import model.variable.Variable;

public class VariableExpression extends Expression{
    private String name;
    private boolean local;
    
	public VariableExpression (String name) {
        super(name);
        this.name = name;
    }
    
    public String getName() {
    	return name;
    }
    
    @Override
    public double run(Animal turtle, TreeNode node) {
    	System.out.println("variable name = " + name);
    	String commandName = ExpressionTree.getInstance().getCurrentCommand();
    	System.out.println("current command = " + commandName);
    	Variable variable;
    	if (Data.getInstance().containsCommand(commandName) && Data.getInstance().containsLocalVariable(commandName, name)) { 
    		//running user-defined command
    		System.out.println("LOCAL VARIABLE");
    		variable = Data.getInstance().getLocalVariable(commandName, name);
    		return variable.getValue();
    	} else {
    		variable = Data.getInstance().getVariable(name);
    		return variable.getValue();
    	}
    }
}
