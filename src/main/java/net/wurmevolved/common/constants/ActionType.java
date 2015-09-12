package net.wurmevolved.common.constants;

public enum ActionType {

    UNKNOWN(0, "Unknown"),

    EXAMINE(1, "Examine"),

    DROP(145, "Drop"),
    TAKE(145, "Take");

    private static final ActionType[] actions = new ActionType[65536];
    private short id;
    private String name;

    ActionType(int id, String name) {
        this.id = (short) id;
        this.name = name;
    }

    public short getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ActionType get(short id) {
        ActionType actionType = actions[id & 0xFFFF];
        return actionType != null ? actionType : UNKNOWN;
    }

    static {
        for (ActionType actionType : values()) {
            actions[actionType.getId() & 0xFFFF] = actionType;
        }
    }

}
