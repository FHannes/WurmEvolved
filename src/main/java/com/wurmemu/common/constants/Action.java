package com.wurmemu.common.constants;

public enum Action {

    UNKNOWN(0, "Unknown"),

    TAKE(1000, "Take");

    private static final Action[] actions = new Action[65535];
    private short id;
    private String name;

    Action(int id, String name) {
        this.id = (short) id;
        this.name = name;
    }

    public short getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Action get(short id) {
        Action action = actions[id & 0xFFFF];
        return action != null ? action : UNKNOWN;
    }

    static {
        for (Action action : values()) {
            actions[action.getId() & 0xFFFF] = action;
        }
    }

}
