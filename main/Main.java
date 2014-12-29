package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("kaduvil.fxml"));
		stage.setTitle("JavaFX Welcome");
		Scene scene = new Scene(loader.load(), 640, 800);
		stage.setScene(scene);
		stage.show();
	}
}