package Main;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameOver extends Pane {
	public static void show(String message) {
		Stage window = new Stage();
		Group rootNode = new Group();
		Scene scene = new Scene(rootNode,Main.getBackgroundColor());
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Game Over!");
		window.initStyle(StageStyle.UNDECORATED);
		window.setWidth(300);
		window.setHeight(300);
		
		Rectangle background = new Rectangle(300,300);
		background.setFill(Main.getBackgroundColor());
		background.setStrokeWidth(2);
		background.setStroke(Color.BLACK);
		
		Label label = new Label();
		label.setText(message);
		label.setTextFill(Color.WHITE);
		
		Button playAgainButton = new Button("Play Again");
		playAgainButton.setOnAction(e -> {
			window.close();
			Main.resetBoard();
			TrashTalk.changeMessage("");
		});
		
		Button quitGameButton = new Button("Quit (LOSER)");
		quitGameButton.setOnAction(e -> {
			window.close();
			Main.quitGame();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,playAgainButton,quitGameButton);
		layout.setAlignment(Pos.CENTER);
		layout.setMinWidth(300);
		layout.setMinHeight(300);
		rootNode.getChildren().addAll(background,layout);
		
		window.setScene(scene);
		window.showAndWait();
	}
}
