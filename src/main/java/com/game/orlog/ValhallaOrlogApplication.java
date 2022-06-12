package com.game.orlog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class ValhallaOrlogApplication extends Application {
	private static Stage stage;
	
	@Override
	public void start(Stage stage) throws IOException {
		// TODO proper start
		FXMLLoader fxmlLoader = new FXMLLoader(getClass()
				.getResource("ValhallaOrlogMain.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("Game game gaming.");
		stage.setScene(scene);
		ValhallaOrlogApplication.stage = stage;
		stage.show();
	}
	
	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		launch();
	}
}
