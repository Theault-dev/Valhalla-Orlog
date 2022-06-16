package com.game.orlog.menu;

import com.game.orlog.ValhallaOrlogApplication;
import javafx.scene.Node;

public abstract class Menu {
    private Node content;

    public void show() {
        content.setVisible(true);
    }

    public void hide() {
        content.setVisible(false);
    }

    protected void setContent(Node content) {
        this.content = content;
    }

    public Node getContent() {
        return content;
    }
}
