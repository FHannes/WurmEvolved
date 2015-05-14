package net.wurmevolved.common.constants;

public enum Layer {

    SURFACE(0, 50),
    CAVE(-1, 20),
    NONE(-100, 0);

    private static final Layer[] layers = new Layer[256];
    private byte id;
    private short local;

    Layer(int id, int local) {
        this.id = (byte) id;
        this.local = (short) local;
    }

    public short getId() {
        return id;
    }

    public short getLocal() {
        return local;
    }

    public static Layer get(short id) {
        Layer material = layers[id & 0xFF];
        return material != null ? material : NONE;
    }

    static {
        for (Layer layer : values()) {
            layers[layer.getId() & 0xFF] = layer;
        }
    }

}
