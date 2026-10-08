import java.util.UUID;

abstract class GridDisplayObject {
    private final UUID uuid = UUID.randomUUID();

    public UUID getUUID() {
        return uuid;
    }

    public abstract String getSprite();

    public int getRenderingLayer() {
        return 0;
    }

    public boolean isPassable() {
        return true;
    }
}
