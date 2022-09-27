package Main;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane {
	
	//comment out which section you dont want (cats and dogs) or (x's and o's)
		//final Image O_IMAGE = new Image(getClass().getResourceAsStream("Images/O.png"));
		//final Image X_IMAGE = new Image(getClass().getResourceAsStream("Images/X.png"));
		
		final Image O_IMAGE = new Image(getClass().getResourceAsStream("Images/Cat.png"));
		final Image X_IMAGE = new Image(getClass().getResourceAsStream("Images/Dog.png"));
	
	final Image BLANK_IMAGE = new Image(getClass().getResourceAsStream("Images/Blank.png"));
	ImageView imageView;
	char moveType = Main.BLANK;
	
	public Tile() {
		Image tileImage = new Image(getClass().getResourceAsStream("Images/Blank.png"));
		this.imageView = new ImageView(tileImage);
		setAlignment(Pos.CENTER);
		getChildren().addAll(imageView);
		
		setOnMouseClicked(event -> {
			if (moveType == Main.BLANK) {
				set(Main.playerMove);
				Main.botTurn();
			}
		});
	}
	
	public char get() {
		return moveType;
	}
	
	public void set(char moveType) {
		this.moveType = moveType;
		Image tileImage = BLANK_IMAGE;
		switch (this.moveType) {
		case 'o':
			tileImage = O_IMAGE;
			break;
		case 'x':
			tileImage = X_IMAGE;
			break;
		}
		this.imageView.setImage(tileImage);
	}
}
