import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class GridEngine {
    public static final int GRID_WIDTH = 10;
    
    private static GridEngine _instance = null;

    private static HashMap<UUID, Position2D> positions;
    private static GridDisplay display;

    public GridEngine() {
        if (_instance != null) throw new RuntimeException("Cannot instantiate multiple GridEngines");
        else _instance = this;

        positions = new HashMap<>();
        display = new GridDisplay();
        Thread t = new Thread(display);
        t.start();

        System.out.println("Initialized GridEngine");
    }

    // May do nothing if n.getUUID() is already on the Grid.
    public void registerEntity(GridDisplayObject n) {
        positions.putIfAbsent(n.getUUID(), new Position2D(0, 0, Direction.EAST));
    }

    public void registerEntity(GridDisplayObject n, Position2D pos) {
        positions.putIfAbsent(n.getUUID(), pos);
    }

    // May do nothing if n.getUUID() is not on the grid.
    public void unregisterEntity(GridDisplayObject n) {
        positions.remove(n.getUUID());
    }

    public Position2D retrievePosition(GridDisplayObject n) {
        UUID uuid = n.getUUID();
        Position2D curr = positions.get(uuid);

        return curr;
    }

    public boolean canMove(GridDisplayObject n) {
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

    public void requestMove(GridDisplayObject n) {
        if (!canMove(n)) throw new RuntimeException("Cannot move UUID " + n.getUUID() + " in current position");
        
        Position2D received = retrievePosition(n);
        if (received == null) throw new RuntimeException("Invalid UUID");

        Direction d = received.getDirection();
        
        if      (d == Direction.NORTH) received.setY(received.getY() - 1);
        else if (d == Direction.SOUTH) received.setY(received.getY() + 1);
        else if (d == Direction.EAST)  received.setX(received.getX() + 1);
        else if (d == Direction.WEST)  received.setX(received.getX() - 1);

        updateDisplay();
    }

    public void requestLeftTurn(GridDisplayObject n) {
        Position2D received = retrievePosition(n);
        if (received == null) throw new RuntimeException("Invalid UUID");

        Direction d = received.getDirection();

        if      (d == Direction.NORTH) received.setDirection(Direction.WEST);
        else if (d == Direction.SOUTH) received.setDirection(Direction.EAST);
        else if (d == Direction.EAST)  received.setDirection(Direction.NORTH);
        else if (d == Direction.WEST)  received.setDirection(Direction.SOUTH);

        updateDisplay();
    }

    public String[] gridToStringArr() {
        UUID[] u = positions.keySet().toArray(new UUID[0]); 
        String[] ret = new String[GRID_WIDTH * GRID_WIDTH];

        for (int i = 0; i < u.length; i++) {
            Position2D p = positions.get(u[i]);

            ret[p.getY() * GRID_WIDTH + p.getX()] = p.getDirection().getEmoji();
        }

        return ret;
    }

    public void updateDisplay() {
        display.submitFrame(gridToStringArr(), GRID_WIDTH);
    }

    public boolean rendererIsDone() {
        return display.bufferEmpty();
    }

    public void stopRenderer() {
        display.stop();
    }
}

abstract class GridDisplayObject {
    private final UUID uuid = UUID.randomUUID();

    public UUID getUUID() {
        return uuid;
    }
}
