module com.game.orlog {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.game.orlog to javafx.fxml;
    exports com.game.orlog;
}