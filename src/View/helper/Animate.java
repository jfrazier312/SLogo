package View.helper;

import java.util.List;

import View.AnimalPaneGUI;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.animal.Animal;

/**
 * Handles animation
 */
/**
 * @author Lucy Zhang
 *
 */
public class Animate {

	private List<Coordinate> coordinatePairs;
	private AnimalPaneGUI animalPaneGUI;
	private Pen pen;
	private int counter;

	// decrease this to speed up, increase to slow down
	private double SPEED = 1;

	public void beginAnimation(AnimalPaneGUI animalPaneGUI) {
		this.animalPaneGUI = animalPaneGUI;
		coordinatePairs = animalPaneGUI.getAnimalPane().getCoordinateMap();
		counter = 0;

		// TODO: will this work with multiple turtles
		for (Animal animal : animalPaneGUI.getAnimalPane().getMyAnimalList()) {
			pen = animal.getActualPen();
			translateAnimation(coordinatePairs.get(counter), animal);
		}
	}

	private void translateAnimation(Coordinate coordinate, Animal animal) {
		ImageView animalImage = animal.getImageView();
		bindPenToAnimal(animalImage, coordinate);
		changeAnimalVisibility(animalImage, coordinate);
		handleRotation(coordinate, animalImage);
		handleMovement(coordinate, animal, animalImage);
	}
	
	public void changeAnimalVisibility(ImageView animalImage, Coordinate coordinate) {
		animalImage.setVisible(coordinate.getShowing() != 0);
//		if(coordinate.getShowing() == 0) {
//			animalImage.setVisible(false);
//		} else {
//			animalImage.setVisible(true);
//		}
	}

	private void handleMovement(Coordinate point, Animal animal, ImageView animalImage) {
		TranslateTransition translation = new TranslateTransition(Duration.millis(getTranslateTime(point, animalImage)),
				animalImage);
		// System.out.println("trans X : " + animalImage.getTranslateX());
		// System.out.println("trans Y : " + animalImage.getTranslateY());

		inputTranslationCoordinates(point, animalImage, translation);

		translation.setOnFinished(e -> {
			// System.out.println("Finished, setting animal x and y" +
			// animalImage.getTranslateX() + ", "
			// + animalImage.getTranslateY());
			updateAnimalCoordinates(point, animal);
			unbindPen(point);
			incrementCounters(point);
			if (counter < coordinatePairs.size()) {
				translateAnimation(coordinatePairs.get(counter), animal);
			}
		});
		translation.play();
	}

	private void inputTranslationCoordinates(Coordinate point, ImageView animalImage, TranslateTransition translation) {
		translation.setFromX(animalImage.getTranslateX());
		translation.setFromY(animalImage.getTranslateY());
		translation.setToX(point.getX());
		translation.setToY(point.getY());
	}

	private void incrementCounters(Coordinate coordinate) {
		if (coordinate.getPen() == 1) {
			pen.incrementCounter();
		}
		counter++;
	}

	private void updateAnimalCoordinates(Coordinate point, Animal animal) {
		animal.setX(point.getX());
		animal.setY(point.getY());
	}

	private void unbindPen(Coordinate coordinate) {
		if (coordinate.getPen() == 1) {
			pen.getLineList().get(pen.getCounter()).endXProperty().unbind();
			pen.getLineList().get(pen.getCounter()).endYProperty().unbind();
		}
	}

	// TODO: Jordan - rotation change when passed in heading
	private void handleRotation(Coordinate coordinate, ImageView animalImage) {
		animalImage.setRotate(coordinate.getHeading());
	}

	private void bindPenToAnimal(ImageView animalImage, Coordinate coordinate) {
		if (coordinate.getPen() == 1) {
			pen.createLine(animalImage.getTranslateX(), animalImage.getTranslateY());
			animalPaneGUI.getMyContainer().getChildren().add(pen.getLineList().get(pen.getCounter()));
			pen.getLineList().get(pen.getCounter()).endXProperty().bind(animalImage.translateXProperty());
			pen.getLineList().get(pen.getCounter()).endYProperty().bind(animalImage.translateYProperty());
		}
	}

	private Double getTranslateTime(Coordinate point, ImageView turtle) {
		double oldx = turtle.getTranslateX();
		double oldy = turtle.getTranslateY();
		double newx = point.getX();
		double newy = point.getY();

		double distance = Math.sqrt(Math.pow((int) newx - oldx, 2) + Math.pow((int) newy - oldy, 2));
		Double time = distance * SPEED;
		return time;
	}
}
