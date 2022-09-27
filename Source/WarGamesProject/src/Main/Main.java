package Main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
	static Group rootNode;
	static Label winsLabel;
	static Label lossesLabel;
	static Label tiesLabel;
	
	//move types
	static final char X = 'x';
	static final char O = 'o';
	static final char BLANK = ' ';
	
	//win types
	static final byte PLAYER = 2;
	static final byte BOT = 1;
	static final byte TIE = 0;
	
	//what move types the player and bot are
	static char playerMove = X;
	static char botMove = O;
	
	//setting the board
	static Tile[][] board = new Tile[3][3];
	
	//game stats
	static int wins = 0;
	static int losses = 0;
	static int ties = 0;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		rootNode = new Group();
		Scene scene = new Scene(rootNode,Color.BLACK);
		
		//create window, set window title, and set icon of program
		Image icon = new Image(getClass().getResourceAsStream("Icon.png"));
		stage.getIcons().add(icon);
		stage.setTitle("W A R - G A M E S");
		stage.setWidth(816);
		stage.setHeight(700);
		stage.setResizable(false);
		
		//add trash-talk section to board
		TrashTalk trashTalk = new TrashTalk();
		trashTalk.setTranslateY(613);
		trashTalk.setMinWidth(616);
		rootNode.getChildren().add(trashTalk);
		
		//add tiles to board
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				Tile tile = new Tile();
				tile.setTranslateX(x*200);
				tile.setTranslateY(y*200);
				rootNode.getChildren().add(tile);
				board[x][y] = tile;
			}
		}
		
		//add win/loss/tie tracker labels
		winsLabel = new Label();
		winsLabel.setTranslateX(630);
		winsLabel.setTranslateY(20);
		winsLabel.setScaleY(2);
		winsLabel.setScaleX(2);
		winsLabel.setTextFill(Color.WHITE);
		winsLabel.setText("Wins: "+wins);
		rootNode.getChildren().add(winsLabel);
		
		lossesLabel = new Label();
		lossesLabel.setTranslateX(630);
		lossesLabel.setTranslateY(50);
		lossesLabel.setScaleY(2);
		lossesLabel.setScaleX(2);
		lossesLabel.setTextFill(Color.WHITE);
		lossesLabel.setText("Losses: "+losses);
		rootNode.getChildren().add(lossesLabel);
		
		tiesLabel = new Label();
		tiesLabel.setTranslateX(630);
		tiesLabel.setTranslateY(80);
		tiesLabel.setScaleY(2);
		tiesLabel.setScaleX(2);
		tiesLabel.setTextFill(Color.WHITE);
		tiesLabel.setText("Ties: "+ties);
		rootNode.getChildren().add(tiesLabel);
		
		//add background color picker
		BackgroundColorPicker colorPicker = new BackgroundColorPicker();
		colorPicker.setTranslateY(20);
		rootNode.getChildren().add(colorPicker);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void botTurn() {
		if (checkWinner() != PLAYER) {
			int[] bestMove = bestMove();
			if (bestMove[0] != -1 && bestMove[1] != -1) {
				int x = bestMove[0];
				int y = bestMove[1];
				board[x][y].set(botMove);
			}
		}
		TrashTalk.trashTalk();
		checkGameEnd();
	}
	
	//chooses best move for bot to take based on which move has the best score
	//scores are either -1 is the player wins, 0 if tie, and 1 if bot wins
	//there will be multiple winning paths, so it will be slow. By the end, there will be few to none left.
	//if there are no possible winning scenarios, the bot will try to tie
	public static int[] bestMove() {
		int bestScore = Integer.MIN_VALUE;
		int[] bestMove = {-1,-1};
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (board[x][y].get() == BLANK) {
					board[x][y].set(botMove);
					int score = minimax(board,false);
					board[x][y].set(BLANK);
					
					if (score > bestScore) {
						bestScore = score;
						bestMove[0] = x;
						bestMove[1] = y;
					}
				}
			}
		}
		return bestMove;
	}
	
	public static int minimax(Tile[][] board, boolean botTurn) {
		byte winner = checkWinner();
		
		if (winner != -1) {
			if (winner == PLAYER) {
				return -1;
			}
			else if (winner == BOT) {
				return 1;
			}
			else if (winner == TIE) {
				return 0;
			}
		}
		
		if (botTurn) {
			int bestScore = Integer.MIN_VALUE;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (board[x][y].get() == BLANK) {
						board[x][y].set(botMove);
						int score = minimax(board,false);
						board[x][y].set(BLANK);
						bestScore = Math.max(score, bestScore);
					}
				}
			}
			return bestScore;
		}
		else {
			int bestScore = Integer.MAX_VALUE;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (board[x][y].get() == BLANK) {
						board[x][y].set(playerMove);
						int score = minimax(board,true);
						board[x][y].set(BLANK);
						bestScore = Math.min(score, bestScore);
					}
				}
			}
			return bestScore;
		}
	}
	
	public static void resetBoard() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				board[x][y].set(BLANK);
			}
		}
	}
	
	public void setPlayerMove(char type) {
		playerMove = type;
		if (type == O) {
			botMove = X;
		}
		else {
			botMove = O;
		}
	}
	
	public static byte checkWinner() {
		boolean playerWin = false;
		boolean botWin = false;
		boolean isTie = true;
		
		char topLeft = board[0][0].get();
		char topCenter = board[1][0].get();
		char topRight = board[2][0].get();
		char centerLeft = board[0][1].get();
		char center = board[1][1].get();
		char centerRight = board[2][1].get();
		char bottomLeft = board[0][2].get();
		char bottomCenter = board[1][2].get();
		char bottomRight = board[2][2].get();
		
		char checkValue = playerMove;
		for (int i = 0; i < 2; i++) {
			//first row
			if (topLeft == topCenter && topLeft == topRight) {
				if (topLeft == checkValue) {
					if (checkValue == playerMove) {
						playerWin = true;
					}
					else if (checkValue == botMove) {
						botWin = true;
					}
				}
			}
			
			//second row
			if (centerLeft == center && centerLeft == centerRight) {
				if (centerLeft == checkValue) {
					if (checkValue == playerMove) {
						playerWin = true;
					}
					else if (checkValue == botMove) {
						botWin = true;
					}
				}
			}
			
			//third row
			if (bottomLeft == bottomCenter && bottomLeft == bottomRight) {
				if (bottomLeft == checkValue) {
					if (checkValue == playerMove) {
						playerWin = true;
					}
					else if (checkValue == botMove) {
						botWin = true;
					}
				}
			}
			
			//first column
			if (topLeft == centerLeft && topLeft == bottomLeft) {
				if (topLeft == checkValue) {
					if (checkValue == playerMove) {
						playerWin = true;
					}
					else if (checkValue == botMove) {
						botWin = true;
					}
				}
			}
			
			//second column
			if (topCenter == center && topCenter == bottomCenter) {
				if (topCenter == checkValue) {
					if (checkValue == playerMove) {
						playerWin = true;
					}
					else if (checkValue == botMove) {
						botWin = true;
					}
				}
			}
			
			//third column
			if (topRight == centerRight && topRight == bottomRight) {
				if (topRight == checkValue) {
					if (checkValue == playerMove) {
						playerWin = true;
					}
					else if (checkValue == botMove) {
						botWin = true;
					}
				}
			}
			
			//diagonal bottom-left -> top-right
			if (bottomLeft == center && bottomLeft == topRight) {
				if (bottomLeft == checkValue) {
					if (checkValue == playerMove) {
						playerWin = true;
					}
					else if (checkValue == botMove) {
						botWin = true;
					}
				}
			}
			
			//diagonal top-left -> bottom-right
			if (topLeft == center && topLeft == bottomRight) {
				if (topLeft == checkValue) {
					if (checkValue == playerMove) {
						playerWin = true;
					}
					else if (checkValue == botMove) {
						botWin = true;
					}
				}
			}
			checkValue = botMove;
		}
		
		//since nobody has won yet, is the game a tie?
		if (!playerWin && !botWin) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (board[x][y].get() == BLANK) {
						isTie = false;
					}
				}
			}
		}
		
		if (playerWin) {
			return PLAYER;
		}
		else if (botWin) {
			return BOT;
		}
		else if (isTie) {
			return TIE;
		}
		else {
			return -1;
		}
	}
	
	public static void checkGameEnd() {
		byte winner = checkWinner();
		switch (winner) {
		case PLAYER:
			wins += 1;
			winsLabel.setText("Wins: "+wins);
			GameOver.show("You cheated! You cannot get to this result without cheating!");
			break;
		case BOT:
			losses += 1;
			lossesLabel.setText("Losses: "+losses);
			GameOver.show("You are a loser. I WIN; YOU LOSE!");
			break;
		case TIE:
			ties += 1;
			tiesLabel.setText("Ties: "+ties);
			GameOver.show("We may have tied, but I will win!");
			break;
		}
	}
	
	public static void quitGame() {
		((Stage)(rootNode.getScene().getWindow())).close();
	}
	
	public static int irandom(int max) {
		int rand = (int)Math.floor(Math.random()*(double)max);
		return rand;
	}
	
	public static void changeBackgroundColor(Color col) {
		rootNode.getScene().setFill(col);
	}
	
	public static Color getBackgroundColor() {
		return (Color) rootNode.getScene().getFill();
	}
}
