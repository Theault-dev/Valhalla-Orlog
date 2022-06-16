package com.game.orlog;

import com.game.orlog.menu.AvailableMenu;
import com.game.orlog.menu.HomePage;
import com.game.orlog.menu.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

<<<<<<< HEAD
public class ValhallaOrlogApplication extends Application {
    private VBox holder;
    private Menu current;
    private HomePage homePage;
    private static ValhallaOrlogApplication application;

    public static ValhallaOrlogApplication getApplication() {
        return application;
    }
    @Override
    public void start(Stage stage) throws IOException {
        holder = new VBox();

        Scene scene = new Scene(holder);

        stage.setTitle("Valhalla Orlog");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        application = this;
        homePage = new HomePage();

        displayMenu(AvailableMenu.HOMEPAGE);
    }

    public void displayMenu(AvailableMenu menu) {
        if (current != null) {
            current.hide();
            switch (menu) {
                case HOMEPAGE -> {
                    current = homePage;
                }
                case GAMEBOARD -> {
                    // TODO
                }
                case SETTINGS -> {
                    // TODO
                }
                case RULE -> {
                    // TODO
                }
                case SYSTEM -> {
                    // TODO
                }
                case OPTION -> {
                    // TODO
                }
            }
        } else {
            current = homePage;
        }
        current.show();
    }
=======
>>>>>>> Theault

public class ValhallaOrlogApplication extends Application {
	private static Stage stage;
	
	@Override
	public void start(Stage stage) throws IOException {
		ValhallaOrlogApplication.stage = stage;
		// TODO proper start
		FXMLLoader fxmlLoader = new FXMLLoader(getClass()
				.getResource("ValhallaOrlogMain.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("Game game gaming.");
		stage.setScene(scene);
		stage.show();
	}
	
	public static Stage getStage() {
		return stage;
	}
	public static Parent getRoot() {
		return stage.getScene().getRoot();
	}

	public static void main(String[] args) {
		launch();
	}
}
