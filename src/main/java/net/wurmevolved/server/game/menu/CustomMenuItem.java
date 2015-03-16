package net.wurmevolved.server.game.menu;

public class CustomMenuItem extends MenuItem {

    private short id;
    private String caption;

    public CustomMenuItem(short id, String caption) {
        this.id = id;
        this.caption = caption;
    }

    @Override
    public short getId() {
        return id;
    }

    @Override
    public String getCaption() {
        return caption;
    }

}
