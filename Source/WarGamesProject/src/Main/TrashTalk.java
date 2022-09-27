package Main;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class TrashTalk extends StackPane {
	static String message = "";
	static Label text = new Label();
	
	public TrashTalk() {
		text.setTextAlignment(TextAlignment.CENTER);
		text.setTextFill(Color.WHITE);
		text.setScaleX(2);
		text.setScaleY(1.5);
		getChildren().add(text);
	}
	
	public static void changeMessage(String message) {
		text.setText(message);
	}
	
	public static String getMessage() {
		return text.getText();
	}
	
	//choose a random trash-talk message to display
	//due to the 'while' loop, the trash-talk message will never be the same as the previous
	public static void trashTalk() {
		String[] possibleMessages = {
				"You're bad at this game.",
				"You will never win.",
				"How are you this bad?",
				"I knew you'd be bad, but not this bad.",
				"This is pitiful",
				"Have I ever mentioned how ugly you are?",
				"Your performance explains why your\nparents put you up for adoption.",
				"You really chose to wear something that ugly today?",
				"Interesting move. You've already lost.",
				"...you really must have double-digit IQ."
				};
		String message = possibleMessages[Main.irandom(possibleMessages.length)];
		while (message.equals(getMessage())) {
			message = possibleMessages[Main.irandom(possibleMessages.length)];
		}
		changeMessage(message);
	}
}
