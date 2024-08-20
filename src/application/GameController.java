package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class GameController implements Initializable {
	
	@FXML private AnchorPane game;
	@FXML private AnchorPane subGame;
	private Parent root;
	private Stage mainStage;
	private Stage closeStage;
	private Scene scene;
	private int currentDifficulty;
	private static boolean anyGameStarted;
	private Image imageX;
	private Image imageO;
	public GameLogic gameState;
	@FXML private Button topLeftButton;
	@FXML private Button topCenterButton;
	@FXML private Button topRightButton;
	@FXML private Button midLeftButton;
	@FXML private Button midCenterButton;
	@FXML private Button midRightButton;
	@FXML private Button botLeftButton;
	@FXML private Button botCenterButton;
	@FXML private Button botRightButton;
	@FXML private TextFlow textBox;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentDifficulty = UserInterfaceController.getCurrentDifficulty();
		System.out.println(currentDifficulty);
		resetGameState();
		
		imageX = new Image(getClass().getResourceAsStream("/images/TTBT_X.png"));
		imageO = new Image(getClass().getResourceAsStream("/images/TTBT_O.png"));
		
		gameState = new GameLogic();
	}
	
	private void resetGameState() {
		GameLogic.resetGame();
		setAnyGameStarted(false);
	}
	
	@FXML private void newSubGame(ActionEvent e) {
		try {
			Image icon = new Image(getClass().getResourceAsStream("/images/TTBT_O.png"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SubGame.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Tic Tac BIG Toe");
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	@FXML private void updateBoardFXML() {
		updateBoard();
		checkEnd();
	}
	
	private void updateBoard() {
		for (int row=0; row<3; ++row) {
			for (int col=0; col<3; ++col) {
				if (gameState.mainBoard[row][col] != ' ') createVisual(row, col, gameState.mainBoard[row][col]);
			}
		}
		System.out.println("update done");
	}
	
	private void createVisual(int row, int col, char player) {
		ImageView imageView = new ImageView();
		imageView.setFitHeight(80);
		imageView.setFitWidth(80);
		if (player == 'X') {
			imageView.setImage(imageX);			
		} else {
			imageView.setImage(imageO);
		}
		imageView.setLayoutX(180 + 100 * row);
		imageView.setLayoutY(140 + 100 * col);
		imageView.setPickOnBounds(true);
		game.getChildren().add(imageView);
		System.out.println(row);
		System.out.println(col);
	}
	
	@FXML private void quitGame(ActionEvent e) throws IOException {
		System.out.println("quit game");
		root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		mainStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	@FXML private void quitSubGame(ActionEvent e) {
		System.out.println("quit sub game");
		setAnyGameStarted(false);
		closeStage = (Stage) subGame.getScene().getWindow();
		closeStage.close();
	}
	
	@FXML private void exitApp(ActionEvent e) {
		Alert exitConfirm = new Alert(AlertType.CONFIRMATION);
		exitConfirm.setTitle("Tic Tac Big Toe");
		exitConfirm.setHeaderText("Why are you leaving :(");
		exitConfirm.setContentText("Close application?");
		
		if(exitConfirm.showAndWait().get() == ButtonType.OK) {
			closeStage = (Stage) game.getScene().getWindow();
			closeStage.close();
			System.out.println("Application closed");			
		}
	}
	
	public boolean getAnyGameStarted() {
		return anyGameStarted;
	}

	public static void setAnyGameStarted(boolean bool) {
		anyGameStarted = bool;
	}
	
	private void checkEnd() {
		if (gameState.isGameOverMain()) {
			if (gameState.getAvailableMovesMain().isEmpty()) {
				System.out.println("draw");
				Text text = new Text("It's a draw!");
				textBox.getChildren().add(text);
				//GameController.setGameState(' ');
				disableButtons();
			}
			if (gameState.isWinningMain('X') ) {
				System.out.println("AI won");
				Text text = new Text("AI won this game!");
				textBox.getChildren().add(text);
				//GameController.setBoardState('X');
				disableButtons();
			}
			if (gameState.isWinningMain('O') ) {
				System.out.println("Player won");
				Text text = new Text("Player won this game!");
				textBox.getChildren().add(text);
				disableButtons();
			}
		}
	}
	
	private void disableButtons() {
		topLeftButton.setDisable(true);
		topCenterButton.setDisable(true);
		topRightButton.setDisable(true);
		midLeftButton.setDisable(true);
		midCenterButton.setDisable(true);
		midRightButton.setDisable(true);
		botLeftButton.setDisable(true);
		botCenterButton.setDisable(true);
		botRightButton.setDisable(true);
	}

	@FXML private void button1(ActionEvent e) {
		if (gameState.mainBoard[0][0]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {0, 0};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
	@FXML private void button2(ActionEvent e) {
		if (gameState.mainBoard[1][0]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {1, 0};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
	@FXML private void button3(ActionEvent e) {
		if (gameState.mainBoard[2][0]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {2, 0};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
	@FXML private void button4(ActionEvent e) {
		if (gameState.mainBoard[0][1]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {0, 1};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
	@FXML private void button5(ActionEvent e) {
		if (gameState.mainBoard[1][1]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {1, 1};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
	@FXML private void button6(ActionEvent e) {
		if (gameState.mainBoard[2][1]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {2, 1};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
	@FXML private void button7(ActionEvent e) {
		if (gameState.mainBoard[0][2]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {0, 2};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
	@FXML private void button8(ActionEvent e) {
		if (gameState.mainBoard[1][2]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {1, 2};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
	@FXML private void button9(ActionEvent e) {
		if (gameState.mainBoard[2][2]==' ' && !getAnyGameStarted()) {
			gameState.lastPlayedSpot = new int[] {2, 2};
			setAnyGameStarted(true);
			newSubGame(e);
			updateBoard();
		} else {
			updateBoard();
			System.out.println("igra je vec pokrenuta ili završena");
		}
	}
}
