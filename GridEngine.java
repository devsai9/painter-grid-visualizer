import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class GridEngine {
    public static final int GRID_WIDTH = 10;
    
    private static GridEngine instance = null;

    private static HashMap<UUID, Position2D> positions;
    private static HashMap<UUID, GridDisplayObject> gridObjects;
    private static GridDisplay display;

    private GridEngine() {
        positions = new HashMap<>();
        gridObjects = new HashMap<>();

        display = new GridDisplay();
        Thread t = new Thread(display);
        t.start();

        System.out.println("Initialized GridEngine");
    }

    public static GridEngine getInstance() {
        if (instance == null) instance = new GridEngine();

        return instance;
    }

    // May do nothing if n.getUUID() is already on the Grid.
    public static void registerEntity(GridDisplayObject n) {
        positions.putIfAbsent(n.getUUID(), new Position2D(0, 0, Direction.EAST));
        gridObjects.putIfAbsent(n.getUUID(), n);
    }

    public static void registerEntity(GridDisplayObject n, Position2D pos) {
        positions.putIfAbsent(n.getUUID(), pos);
        gridObjects.putIfAbsent(n.getUUID(), n);
    }

    // May do nothing if n.getUUID() is not on the grid.
    public static void unregisterEntity(GridDisplayObject n) {
        positions.remove(n.getUUID());
        gridObjects.remove(n.getUUID());
    }

    public static Position2D retrievePosition(GridDisplayObject n) {
        UUID uuid = n.getUUID();
        Position2D curr = positions.get(uuid);

        return curr;
    }

    public static boolean canMove(GridDisplayObject n) {
        Position2D received = retrievePosition(n);
        if (received == null) throw new RuntimeException("Invalid UUID");

        int xLocation = received.getX();
        int yLocation = received.getY();
        Direction d = received.getDirection();

        if (d == Direction.NORTH) return yLocation > 0;
        if (d == Direction.SOUTH) return yLocation < GRID_WIDTH - 1;
        if (d == Direction.EAST)  return xLocation < GRID_WIDTH - 1;
        if (d == Direction.WEST)  return xLocation > 0;
    
        return false;
    }

    public static void requestMove(GridDisplayObject n) {
        if (!canMove(n)) throw new RuntimeException("Cannot move UUID " + n.getUUID() + " in current position");
        
        Position2D received = retrievePosition(n);
        if (received == null) throw new RuntimeException("Invalid UUID");

        Direction d = received.getDirection();
        
        received.setX(received.getX() + d.getDx());
        received.setY(received.getY() + d.getDy());

        updateDisplay();
    }

    public static void requestLeftTurn(GridDisplayObject n) {
        Position2D received = retrievePosition(n);
        if (received == null) throw new RuntimeException("Invalid UUID");

        received.setDirection(received.getDirection().turnLeft());

        updateDisplay();
    }

    public static String[] gridToStringArr() {
        UUID[] uuids = positions.keySet().toArray(new UUID[0]);

        UUID[] newGridPositions = new UUID[GRID_WIDTH * GRID_WIDTH];
        String[] ret = new String[GRID_WIDTH * GRID_WIDTH];

        for (int i = 0; i < uuids.length; i++) {
            Position2D p = positions.get(uuids[i]);
            GridDisplayObject o = gridObjects.get(uuids[i]);

            int idx = p.getY() * GRID_WIDTH + p.getX();

            if (newGridPositions[idx] == null) {
                ret[idx] = o.getSprite();
                newGridPositions[idx] = uuids[i];
            }
            else {
                GridDisplayObject existing = gridObjects.get(newGridPositions[idx]);

                if (existing.getRenderingLayer() < o.getRenderingLayer()) {
                    ret[idx] = o.getSprite();
                    newGridPositions[idx] = uuids[i];
                }
            }
        }

        return ret;
    }

    public static void updateDisplay() {
        display.submitFrame(gridToStringArr(), GRID_WIDTH);
    }

    public static boolean rendererIsDone() {
        return display.bufferEmpty();
    }

    public static void stopRenderer() {
        display.stop();
    }
}

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
