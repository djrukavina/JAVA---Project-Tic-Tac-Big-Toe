package application;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SubGameController implements Initializable {

	@FXML private Stage closeStage;
	@FXML private AnchorPane subGame;
	private int currentDifficulty;
	@FXML private Button topLeftButton;
	@FXML private Button topCenterButton;
	@FXML private Button topRightButton;
	@FXML private Button midLeftButton;
	@FXML private Button midCenterButton;
	@FXML private Button midRightButton;
	@FXML private Button botLeftButton;
	@FXML private Button botCenterButton;
	@FXML private Button botRightButton;
	private Image imageX;
	private Image imageO;
	Random random = new Random();
	private boolean currentMovePlayer;
	private GameLogic gameState;
	@FXML private TextFlow textBox;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentDifficulty = UserInterfaceController.getCurrentDifficulty();
		System.out.println(currentDifficulty);
		
		//determine random player start depending on difficulty
		double randomValue = random.nextDouble() * 100;
		currentMovePlayer = (randomValue >= currentDifficulty);
		System.out.println(randomValue);
		System.out.println(currentMovePlayer);
		
		imageX = new Image(getClass().getResourceAsStream("/images/TTBT_X.png"));
		imageO = new Image(getClass().getResourceAsStream("/images/TTBT_O.png"));
		
		gameState = new GameLogic();
		if (!currentMovePlayer) gameState.aiMove();
		updateBoard();
	}
	
	private void updateBoard() {
		for (int row=0; row<3; ++row) {
			for (int col=0; col<3; ++col) {
				if (gameState.board[row][col] != ' ') createVisual(row, col, gameState.board[row][col]);
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
		imageView.setLayoutX(60 + 100 * row);
		imageView.setLayoutY(140 + 100 * col);
		imageView.setPickOnBounds(true);
		subGame.getChildren().add(imageView);
		System.out.println(row);
		System.out.println(col);
	}
	
	private void checkEnd() {
		if (gameState.isGameOver()) {
			if (gameState.getAvailableMoves().isEmpty()) {
				System.out.println("draw");
				Text text = new Text("It's a draw!");
				textBox.getChildren().add(text);
				//GameController.setGameState(' ');
				disableButtons();
				GameLogic.mainBoard[GameLogic.lastPlayedSpot[0]][GameLogic.lastPlayedSpot[1]] = ' ';
			}
			if (gameState.isWinning('X') ) {
				System.out.println("AI won");
				Text text = new Text("AI won this subgame!");
				textBox.getChildren().add(text);
				//GameController.setBoardState('X');
				disableButtons();
				GameLogic.mainBoard[GameLogic.lastPlayedSpot[0]][GameLogic.lastPlayedSpot[1]] = 'X';
			}
			if (gameState.isWinning('O') ) {
				System.out.println("Player won");
				Text text = new Text("Player won this subgame!");
				textBox.getChildren().add(text);
				disableButtons();
				GameLogic.mainBoard[GameLogic.lastPlayedSpot[0]][GameLogic.lastPlayedSpot[1]] = 'O';
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
	
	@FXML private void quitSubGame(ActionEvent e) {
		System.out.println("quit sub game");
		GameController.setAnyGameStarted(false);
		closeStage = (Stage) subGame.getScene().getWindow();
		closeStage.close();
	}
	
	@FXML private void topLeft(ActionEvent e) {
		topLeftButton.setDisable(true);
		gameState.playerMove(0, 0);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
	@FXML private void topCenter(ActionEvent e) {
		topCenterButton.setDisable(true);
		gameState.playerMove(1, 0);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
	@FXML private void topRight(ActionEvent e) {
		topRightButton.setDisable(true);
		gameState.playerMove(2, 0);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
	@FXML private void midLeft(ActionEvent e) {
		midLeftButton.setDisable(true);
		gameState.playerMove(0, 1);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
	@FXML private void midCenter(ActionEvent e) {
		midCenterButton.setDisable(true);
		gameState.playerMove(1, 1);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
	@FXML private void midRight(ActionEvent e) {
		midRightButton.setDisable(true);
		gameState.playerMove(2, 1);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
	@FXML private void botLeft(ActionEvent e) {
		botLeftButton.setDisable(true);
		gameState.playerMove(0, 2);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
	@FXML private void botCenter(ActionEvent e) {
		botCenterButton.setDisable(true);
		gameState.playerMove(1, 2);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
	@FXML private void botRight(ActionEvent e) {
		botRightButton.setDisable(true);
		gameState.playerMove(2, 2);
		updateBoard();
		checkEnd();
		if (!gameState.isGameOver()) {
			gameState.aiMove();
			updateBoard();
			checkEnd();			
		}
	}
}
