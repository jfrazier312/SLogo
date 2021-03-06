package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import View.helper.Coordinate;
import View.helper.PenContainer;
import model.animal.Animal;
import model.animal.Turtle;

public class AnimalPane extends Observable implements Observer {

	private Map<Integer, Animal> myAnimalMap = new HashMap<>();
	private List<Animal> myAnimalList;
	private List<String> myCommandHistory;

	// <string = name of variable, string/other? = value / expression>
	private Map<String, String> myVariables;

	// Map of string ID to List of CoordinatePair<double x, double y>
	private Map<Integer, List<Coordinate>> coordinateMap;
	private List<Coordinate> coordinateList;

	private PenContainer penColor;

	private int animalID;

	public AnimalPane() {
		// this increments when adding new animals
		animalID = 0;

		// map of AnimalPane ID to Animal (for multiple animals on same pane)
		myAnimalMap = new HashMap<Integer, Animal>();
		myAnimalList = new ArrayList<Animal>();

		// Map of variable names and expressions
		myVariables = new HashMap<String, String>();

		// list of all executed commands
		myCommandHistory = new ArrayList<String>();

		// list of Animal ID and Coordinate Lists for translation rendering
		coordinateList = new ArrayList<Coordinate>();
		coordinateMap = new HashMap<Integer, List<Coordinate>>();

		penColor = new PenContainer();

	}

	public Animal addAnimal() {
		animalID++;
		Animal animal = new Turtle(penColor);

		penColor.addObserver(animal.getActualPen());

		myAnimalMap.put(animalID, animal);
		myAnimalList.add(animal);

		animal.setId(animalID);
		animal.addObserver(this);
		return animal;
	}

	/**
	 * Adds animal and notifies view that a new animal needs to be displayed
	 * 
	 * @param animal
	 */
	public void addAnimal(Animal animal) {

		System.out.println("Added animal in backend AnimalPane");
		animalID++;

		penColor.addObserver(animal.getActualPen());

		myAnimalMap.put(animalID, animal);
		myAnimalList.add(animal);

		animal.setId(animalID);
		animal.addObserver(this);

	}

	// for removing non active animals
	public void removeAnimal(Animal animal) {
		myAnimalMap.remove(animal.getId());
		myAnimalList.remove(animal);
	}

	@Deprecated
	@Override
	public void update(Observable o, Object arg) {
		// updates values of animals, object arg is the value that is changing
		// or just update everything why not with a complete re renderingR

		if (o instanceof Animal) {
			Animal cur = (Animal) o;
			for (int animalId : myAnimalMap.keySet()) {
				if (animalId == cur.getId()) { // I think this will hit them all
												// no matter what
					// TODO: update animalPane.

					if (cur.getSelected()) {
						// now update this guy
					}

				}
			}
		}
	}

	public Map<Integer, Animal> getMyAnimalMap() {
		return myAnimalMap;
	}

	public List<String> getMyCommandHistory() {
		return myCommandHistory;
	}

	public Map<String, String> getMyVariables() {
		return myVariables;
	}

	public Map<Integer, List<Coordinate>> getCoordinateMap() {
		return coordinateMap;
	}

	public List<Coordinate> getCoordinateList() {
		return coordinateList;
	}

	public int getAnimalID() {
		return animalID;
	}

	public void setMyAnimalMap(Map<Integer, Animal> myAnimalMap) {
		this.myAnimalMap = myAnimalMap;
	}

	public void setMyCommandHistory(List<String> myCommandHistory) {
		this.myCommandHistory = myCommandHistory;
	}

	public void setMyVariables(Map<String, String> myVariables) {
		this.myVariables = myVariables;
	}

	public void setCoordinateMap(Map<Integer, List<Coordinate>> coordinateMap) {
		for (Integer id : coordinateMap.keySet()) {
			this.coordinateMap.put(id, coordinateMap.get(id));
		}
	}

	public void signalAnimation() {
		setChanged();
		notifyObservers();
	}

	public void clearAnimals() {
		myAnimalList.clear();
		myAnimalMap.clear();
	}

	public void setAnimalID(int animalID) {
		this.animalID = animalID;
	}

	public List<Animal> getMyAnimalList() {
		return myAnimalList;
	}

	public void setMyAnimalList(List<Animal> myAnimalList) {
		this.myAnimalList = myAnimalList;
	}

	public PenContainer getPenContainer() {
		return penColor;
	}

	public void setPenContainer(PenContainer penColor) {
		this.penColor = penColor;
	}
}
