package View.helper;

import java.util.Map;
import java.util.Observable;

import Controller.Controller;
import Controller.DataSetup.DataOutput;
import View.SlogoView;
import View.Workspace;
import View.helpscreen.HelpScreen;
import View.tabs.GenericPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Handles buttons
 */
/**
 * @author Jordan Frazier
 * @author Lucy Zhang
 *
 */

public class Buttons extends Observable {
	private Graphics graphic = new Graphics();
	private HelpScreen helpPage = new HelpScreen();
	private Controller myController;
	
	private String currentCommand;
 	
	public Buttons(Controller controller) {
		myController = controller;
		currentCommand = "";
	}

	public VBox createConsoleInputButtons(Console console, GenericPane<String> pane, SlogoView slogoView) {
		VBox container = new VBox(5);
		Button run = createRunButton(console, pane);
		Button clear = createClearButton(console, slogoView);
		Button help = createHTMLHelpButton();
		Button wkspc = createNewWorkspaceButton(slogoView);
		Button saveWkspc = createSaveWorkspaceButton(slogoView);
		container.getChildren().addAll(run, clear, help, wkspc, saveWkspc);
		return container;
	}

	private Button createRunButton(Console console, GenericPane<String> pane) {
		Button run = graphic.createButton("Run");
		run.setPrefWidth(Workspace.BUTTON_WIDTH);
		run.setOnAction(e -> {
			String input = console.getInput();
			// TODO: add more checks for empty input
			if (input.isEmpty()) {
				return;
			}

			System.out.println(input);
			// Add command to history, move this to only after its been checked
			// for errors
//			addCommandToHistory(pane, input);

			myController.writeInputToFile(input);
			myController.handleInput();
//			myController.checkForPrintCommand("print", console); // testing the print
			// myController.checkForPrintCommand("print", console); // testing
			// the print
			// command
			
			// Updating Command History Pane with command
			updateObservers(input);
		});
		return run;
	}

	private void updateObservers(String input) {
		currentCommand = input;
		setChanged();
		notifyObservers();
	}

	private Button createClearButton(Console console, SlogoView slogoView) {
		Button clear = graphic.createButton("Clear");
		clear.setPrefWidth(Workspace.BUTTON_WIDTH);
		clear.setOnAction(e -> {
			console.clearConsole();
			Workspace pane=slogoView.getCurrentWorkspaceLeftPane();
			pane.resetLeftPane();
			
		});
		return clear;
	}

	private Button createHTMLHelpButton() {
		Button help = graphic.createButton("Help");
		help.setPrefWidth(Workspace.BUTTON_WIDTH);
		help.setOnAction(e -> {
			helpPage.displayHelp();
		});
		return help;
	}

	private Button createNewWorkspaceButton(SlogoView slogoView) {
		Button wkspc = graphic.createButton("New Workspace");
		wkspc.setPrefWidth(Workspace.BUTTON_WIDTH);
		wkspc.setOnAction(e -> {
			// System.out.println("main.getSlogoView(): "+main.getSlogoView());
			slogoView.createNewWorkSpace();
		});
		return wkspc;
	}
	
	private Button createSaveWorkspaceButton(SlogoView slogoView){
		Button wkspc = graphic.createButton("Save Workspace");
		wkspc.setPrefWidth(Workspace.BUTTON_WIDTH);
		wkspc.setOnAction(e -> {
			Map<String,String> data = slogoView.parseWorkspaceData();
			DataOutput dataOutput = new DataOutput(data.get("title")+"_out.xml",data);
		});
		return wkspc;
	}

	@Deprecated
	private void addCommandToHistory(final GenericPane<String> pane, String input) {
		pane.getAllItems().add(input);
	}
	
	public String getCurrentCommand() {
		return currentCommand;
	}
}
