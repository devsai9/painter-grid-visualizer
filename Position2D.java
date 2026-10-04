import java.util.Objects;

enum Direction {
    NORTH("↑"),
    EAST("→"),
    SOUTH("↓"),
    WEST("←");

    private final String emoji;

    Direction(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() { return emoji; }
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

