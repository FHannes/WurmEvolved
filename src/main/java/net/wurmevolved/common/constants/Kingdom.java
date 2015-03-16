package net.wurmevolved.common.constants;

public enum Kingdom {

    NO_KINGDOM("No kingdom", "free"),
    JENN_KELLON("Jenn Kellon", "jenn"),
    MOL_REHAN("Mol Rehan", "molr"),
    HOTS("Horde of the Summoned", "hots"),
    FREEDOM("Freedom", "free");

    private String name;
    private String resName;

    Kingdom(String name, String resName) {
        this.name = name;
        this.resName = resName;
    }

    public String getName() {
        return name;
    }

    public String getResName() {
        return resName;
    }

}
