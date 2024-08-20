package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class UserInterfaceController implements Initializable{
	
	@FXML private Slider difficultySlider;
	@FXML private Label difficultyLabel;
	private static int currentDifficulty;
	@FXML private AnchorPane application;
	private Stage newGameStage;
	private Parent root;
	private Scene scene;
	private Stage closeStage;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentDifficulty = (int) difficultySlider.getValue();
		difficultyLabel.setText("AI Difficulty: " + Integer.toString(currentDifficulty));
		
		difficultySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				currentDifficulty = (int) difficultySlider.getValue();
				difficultyLabel.setText("AI Difficulty: " + Integer.toString(currentDifficulty));
			}
		});
	}
	
	@FXML private void newGame(ActionEvent e) throws IOException {
		System.out.println("new game");
		root = FXMLLoader.load(getClass().getResource("Game.fxml"));
		newGameStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		newGameStage.setScene(scene);
		newGameStage.show();
	}
	
	@FXML private void exitApp(ActionEvent e) {
		Alert exitConfirm = new Alert(AlertType.CONFIRMATION);
		exitConfirm.setTitle("Tic Tac Big Toe");
		exitConfirm.setHeaderText("Why are you leaving :(");
		exitConfirm.setContentText("Close application?");
		
		if(exitConfirm.showAndWait().get() == ButtonType.OK) {
			closeStage = (Stage) application.getScene().getWindow();
			closeStage.close();
			System.out.println("Application closed");			
		}
	}
	
	static public int getCurrentDifficulty() {
		return currentDifficulty;
	}
}
