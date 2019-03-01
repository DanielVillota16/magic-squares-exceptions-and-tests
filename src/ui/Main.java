package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is in charge of initialize the entire program.
 * There are just two methods, the necessary ones to initialize a javafx program.
 * @author Jesus Daniel Villota
 * Date: 12/02/2019
 */

public class Main extends Application{

	/**
	 * Main method: it allows to the program to get executed.
	 * @param args the necessary argument that the main method needs to get initialized.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Start method: it allows to the user interface to get executed and showed on screen.
	 * @param stage the root Node where the all the other ones will get into.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("MagicSquare.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Magic Squares");
		stage.setScene(scene);
		stage.show();
		
	}

}