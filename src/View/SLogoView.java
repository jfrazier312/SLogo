package View;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.animal.Animal;
import Model.animal.Turtle;
import View.tabs.CommandHistoryPane;
import View.tabs.ExampleCommandsPane;
import View.tabs.GenericPane;
import View.tabs.OptionsPane;
import View.tabs.VariablesPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Main SLogo interface
 *
 * @author Lucy Zhang
 * @author Jordan Frazier
 *
 */
public class SLogoView implements AbstractSLogoView {
	private Scene myScene;
	private Graphics graphics;
	
	
	static final int SCENE_WIDTH = 1200;
	static final int SCENE_HEIGHT = 700;
	static final int LEFT_PANE_WIDTH = SCENE_WIDTH - SCENE_WIDTH / 3;
	static final int RIGHT_PANE_WIDTH = SCENE_WIDTH / 3 - 30;
	private static final int TURTLE_HEIGHT = 15;
	private static final int TURTLE_WIDTH = 15;
	private static final String EN_RESRC_PATH = "resources/languages/English";
	private static final String CHI_RESRC_PATH = "resources/languages/Chinese";
	
	private BorderPane myRoot;
	private Pane myAnimalPane;
	private List<Animal> myAnimalList;
	private int NUM_ANIMALS = 1;
	private Buttons buttons;
	private Console console;
	private Animate animation;
	private ResourceBundle myResources;
	private final GenericPane<String> historyPane = new CommandHistoryPane();
	
	public SLogoView() {
		graphics = new Graphics();
		buttons = new Buttons();
		animation = new Animate();
		myAnimalList = new ArrayList<>();
		myResources = ResourceBundle.getBundle(EN_RESRC_PATH);
	}

	public Scene init() {
		myRoot = new BorderPane();
		myScene = new Scene(myRoot, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
		populateTopPane();
		populateLeftPane();
		populateRightPane();
		return myScene;
	}

	private void populateLeftPane() {
		VBox leftPane = graphics.createVBoxPane(LEFT_PANE_WIDTH, SCENE_HEIGHT);	
		leftPane.getStyleClass().add("left-pane");
		HBox container = createConsole();
		createAnimalGrid();
		populateGridWithAnimals();
		leftPane.getChildren().addAll(myAnimalPane, container);
		myRoot.setLeft(leftPane);
	}

	private void populateRightPane() {
		// TODO: Jordan - inset magic numbers
		VBox rightPane = graphics.createVBoxPane(RIGHT_PANE_WIDTH, SCENE_HEIGHT);
		rightPane.getStyleClass().add("right-pane");
		
		TabPane informationTabPane = createTabInfoPane();
		informationTabPane.getStyleClass().add("tab-pane");
		
		rightPane.getChildren().addAll(informationTabPane);
		myRoot.setRight(rightPane);
	}
	
	private TabPane createTabInfoPane() {
		TabPane tabPane = new TabPane();
		Tab examples = createExampleCommandsTab();
		Tab variables = createVariablesTab();
		Tab history = createHistoryTab();
		Tab options = createOptionsTab();
		tabPane.getTabs().addAll(history, examples, variables, options);
		return tabPane;
	}
	
	private <T> Tab createTab(GenericPane<T> pane) {
		Tab tab = new Tab();
		tab.setContent(pane.getTabContent());
		tab.setText(pane.getTabName());
		tab.setClosable(false);
		return tab;
	}
	
	private Tab createExampleCommandsTab() {
		GenericPane<String> pane = new ExampleCommandsPane();
		Tab tab = createTab(pane);
		return tab;
	}
	
	private Tab createVariablesTab() {
		GenericPane<String> pane = new VariablesPane();
		Tab tab = createTab(pane);
		return tab;
	}
	
	private Tab createHistoryTab() {
		Tab tab = createTab(historyPane);
		return tab;
	}
	
	private Tab createOptionsTab() {
		// TODO: Jordan - only getting first animal now. eventually will possible have to ID each animal 
		// and have different options for each animal ID
		GenericPane<HBox> pane = new OptionsPane(myAnimalList.get(0));
		Tab tab = createTab(pane);
		return tab;
	}
	
	private void populateTopPane() {
		HBox container = new HBox(20);
		container.getStyleClass().add("top-pane");
		
		Text title = new Text(myResources.getString("SLogo"));
		title.getStyleClass().add("slogo-title");
		
		ComboBox<String> languageComboBox = createLanguageChooser();
		languageComboBox.getStyleClass().add("language-button");

		container.getChildren().addAll(title, languageComboBox);
		myRoot.setTop(container);
	}

	@Override
	public void createAnimalGrid() {
		myAnimalPane = new Pane();
		myAnimalPane.setPrefWidth(LEFT_PANE_WIDTH);
		myAnimalPane.setPrefHeight(SCENE_HEIGHT - SCENE_HEIGHT / 4);
		myAnimalPane.getStyleClass().add("animal-pane");
	}

	@Override
	public HBox createConsole() {
		TextArea consoleArea = createConsoleArea();
		VBox buttons = createButtons();
		HBox consoleContainer = new HBox(5);
		consoleContainer.getChildren().addAll(consoleArea, buttons);
		return consoleContainer;
	}

	@Override
	public TextArea createConsoleArea() {
		// TODO: Jordan - input correct width / height (doesn't matter)
		TextArea consoleArea = graphics.createConsoleTextArea(LEFT_PANE_WIDTH - 100, SCENE_HEIGHT / 6);
		console = new Console(consoleArea);
		console.initConsole();
		return consoleArea;
	}

	private VBox createButtons() {
		VBox container = buttons.createConsoleInputButtons(console, historyPane);
		return container;
	}

	// Maybe specific animal buttons that call this, which adds to animallist,
	// then the list is completely rendered by calling populateGridWithAnimals()
	private void addAnimal(Animal animal) {
		myAnimalList.add(animal);
		renderAnimalGrid();
	}

	// This method needs to change, discuss if/how we would let a user define
	// what animals they want, or how many they want
	private void fillAnimalList(int numAnimals) {
		for (int i = 0; i < numAnimals; i++) {
			Animal turtle = new Turtle(TURTLE_WIDTH, TURTLE_HEIGHT, (myAnimalPane.getPrefWidth() - myAnimalPane.getLayoutX() - 15) / 2,
					(myAnimalPane.getPrefHeight() - myAnimalPane.getLayoutY()) / 2);
			myAnimalList.add(turtle);
		}
	}

	@Override
	public void populateGridWithAnimals() {
		fillAnimalList(NUM_ANIMALS);
		renderAnimalGrid();
	}

	@Override
	public void renderAnimalGrid() {
		for (Animal animal : myAnimalList) {
			renderAnimal(animal);
		}
	}

	private void renderAnimal(Animal animal) {
		if (isValidLocation(animal.getX(), animal.getY())) {
			addAnimalToGrid(animal);
		} else {
			System.out.println("NOT INSIDE ANIMAL PANE");
		}
	}

	private void addAnimalToGrid(Animal animal) {
		Rectangle s = graphics.createRectCell(animal.getX(), animal.getY(), animal.getWidth(), animal.getHeight(),
				Color.WHITE, Color.WHITE);
		ImagePattern turtlePattern = new ImagePattern(animal.getImage());
		s.setFill(turtlePattern);
		myAnimalPane.getChildren().add(s);
	}

	// are we going to let turtle go off of the screen?
	private boolean isValidLocation(double x, double y) {
		return (x > myAnimalPane.getLayoutX()) && (y > myAnimalPane.getLayoutY()) && (x < myAnimalPane.getPrefWidth()) && (y < myAnimalPane.getPrefHeight());
	}

	@Override
	public ComboBox<String> createLanguageChooser() {
		String[] languages = { "English", "Chinese", "French", "German", "Italian", "Portuguese", "Russian",
				"Spanish" };
		ComboBox<String> languageSelector = graphics.createComboBox(languages);
		languageSelector.setValue(languages[0]);
		languageSelector.valueProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("combobox value is: " + newValue);
				if (newValue.equals("Chinese")) {
					myResources = ResourceBundle.getBundle(CHI_RESRC_PATH);
				} else if (newValue.equals("English")) {
					myResources = ResourceBundle.getBundle(EN_RESRC_PATH);
				}
				// etc
				// Loop through a list of all Text Values that should be updated and update them? 
//				currentText.setText(myResources.getString(currentText.getText()));
			}
		});
		return languageSelector;
	}

}
