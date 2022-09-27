package Main;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class BackgroundColorPicker extends ChoiceBox<String> {
	
	static Color[] colors = {
			Color.BLACK,
			Color.GRAY,
			Color.DARKGRAY,
			Color.LIME,
			Color.BLUE,
			Color.RED,
			Color.AQUA,
			Color.PURPLE
	};
	
	public BackgroundColorPicker() {
		getItems().add("Black");
		getItems().add("Gray");
		getItems().add("Dark Gray");
		getItems().add("Lime");
		getItems().add("Blue");
		getItems().add("Red");
		getItems().add("Aqua");
		getItems().add("Purple");
		
		Label descriptionLabel = new Label();
		descriptionLabel.setText("Background Color:");
		descriptionLabel.setTextFill(Color.WHITE);
		Main.rootNode.getChildren().add(descriptionLabel);
		
		setOnAction((event) -> {
			Color color = getColor(getSelectionModel().getSelectedIndex());
			Main.changeBackgroundColor(color);
		});
	}
	
	private static Color getColor(int colorIndex) {
		return colors[colorIndex];
	}
}
