import java.util.Objects;

enum Direction {
    NORTH("↑", 0, -1),
    EAST("→", 1, 0),
    SOUTH("↓", 0, 1),
    WEST("←", -1, 0);

    private final String emoji;
    private final int    dx;
    private final int    dy;

    Direction(String emoji, int dx, int dy) {
        this.emoji = emoji;
        this.dx    = dx;
        this.dy    = dy;
    }

    public String getEmoji() { return emoji; }
    public int    getDx()    { return dx; }
    public int    getDy()    { return dy; }

    public Direction turnLeft() {
        return switch(this) {
            case NORTH -> WEST ;
            case WEST  -> SOUTH;
            case SOUTH -> EAST ;
            case EAST  -> NORTH;
        };
    }
}

public class Position2D {
    int x, y;
    Direction direction;

    public Position2D(int x, int y, Direction d) {
        this.x = x;
        this.y = y;
        this.direction = d;
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setDirection(Direction d) { this.direction = d; }

    public int getX() { return x; }
    public int getY() { return y; }
    public Direction getDirection() { return direction; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || o.getClass() != this.getClass()) return false;

        Position2D p = (Position2D) o;
        return this.getX() == p.getX() && this.getY() == p.getY() && this.getDirection() == p.getDirection();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ") @ " + direction;
    }
}

