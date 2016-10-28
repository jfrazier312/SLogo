package Parsing;

import model.animal.Animal;
import model.command.Command;
import model.command.Parameter;
import model.command.turtle.TurtleCommand;

public class MethodExpression extends Expression{

    private Class<Command> method;
    
    public MethodExpression (String name, Class<Command> method) {
        super(name);
        this.method = method;
    }
    
    @Override
    public double run(Animal turtle, TreeNode node) {
    	double value = 0;
    	Object obj = null;
		try {
			obj = method.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Command command = (Command)obj;
		Parameter[] parameters = new Parameter[(int)command.getNumParams()];
		int paramIndex = 0;
		int endIndex = node.getChildren().size();
		
		if (command instanceof TurtleCommand) {
			parameters[0] = new Parameter(turtle);
			paramIndex++;
		} 
		for (int c = 0; c < endIndex; c++) {
			System.out.println(node.getChildren().get(c));
			parameters[paramIndex] = new Parameter(ExpressionTree.getInstance().process(turtle, node.getChildren().get(c)));
			paramIndex++;
		}
		
		value = command.run(parameters);
		return value;
    }
}