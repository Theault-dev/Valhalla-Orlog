module com.game.orlog {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires org.kordamp.bootstrapfx.core;
	requires json.simple;
	requires org.json;

    opens com.game.orlog to javafx.fxml;
    exports com.game.orlog;
    exports com.game.orlog.enumClass;
}