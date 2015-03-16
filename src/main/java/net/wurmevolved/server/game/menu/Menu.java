package net.wurmevolved.server.game.menu;

public class Menu extends SubMenu {

    private String wiki;

    Menu(String wiki) {
        super("");
        this.wiki = wiki;
    }

    public String getWiki() {
        return wiki;
    }

}
