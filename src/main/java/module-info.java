module com.game.orlog {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires org.kordamp.bootstrapfx.core;
	requires json.simple;
	requires org.json;

    exports com.game.orlog;
    exports com.game.orlog.model.enumClass;
    exports com.game.orlog.controller;
    
    opens com.game.orlog to javafx.fxml;
    opens com.game.orlog.controller to javafx.fxml;
}