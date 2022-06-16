package com.game.orlog;

import com.game.orlog.menu.AvailableMenu;
import com.game.orlog.menu.HomePage;
import com.game.orlog.menu.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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

    public static void main(String[] args) {
        launch();
    }
}
