package com.game.orlog.menu;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class HomePage extends Menu {
    public HomePage() throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("ValhallaOrlogMain.fxml"));
        setContent(fxmlLoader.load());
    }
}
