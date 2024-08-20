package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;


public class Main extends Application {
	private Image icon = new Image(getClass().getResourceAsStream("/images/TTBT_X.png"));
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Tic Tac BIG Toe");
			primaryStage.getIcons().add(icon);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(event -> {
				event.consume();
				exitApp(primaryStage);
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void exitApp(Stage stage) {
		Alert exitConfirm = new Alert(AlertType.CONFIRMATION);
		exitConfirm.setTitle("Tic Tac Big Toe");
		exitConfirm.setHeaderText("Why are you leaving :(");
		exitConfirm.setContentText("Close application?");
		
		if(exitConfirm.showAndWait().get() == ButtonType.OK) {
			stage.close();
			System.out.println("Application closed");			
		}
	}
}
