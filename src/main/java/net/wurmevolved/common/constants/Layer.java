package net.wurmevolved.common.constants;

public enum Layer {

    SURFACE(0),
    CAVE(-1),
    NONE(-100);

    private static final Layer[] layers = new Layer[256];
    private byte id;

    Layer(int id) {
        this.id = (byte) id;
    }

    public short getId() {
        return id;
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
