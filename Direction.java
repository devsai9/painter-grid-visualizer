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

