module com.game.orlog {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
	requires json.simple;

    opens com.game.orlog to javafx.fxml;
    exports com.game.orlog;
}