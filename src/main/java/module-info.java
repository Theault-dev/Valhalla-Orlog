module com.game.orlog {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens com.game.orlog to javafx.fxml;
    exports com.game.orlog;
}