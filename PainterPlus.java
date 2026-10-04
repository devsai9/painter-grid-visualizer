class PainterPlus extends Painter {
    public PainterPlus(GridEngine ge, int x, int y, Direction direction) {
        super(ge, x, y, direction);
    }

    public PainterPlus(GridEngine ge) {
        super(ge);
    }

    public void moveTo(int x, int y) {
        // Move on x-axis
        if (getX() < x) {
            // You want to go to the right (east)
            while (getDirection() != Direction.EAST) turnLeft();
        } else if (getX() > x) {
            // You want to go to the left (west)
            while (getDirection() != Direction.WEST) turnLeft();
        }

        while (getX() != x) {
            if (!canMove()) break;
            move();
        }

        // Move on y-axis
        if (getY() < y) {
            // You want to go down (south)
            while (getDirection() != Direction.SOUTH) turnLeft();
        } else if (getY() > y) {
            // You want to go up (north)
            while (getDirection() != Direction.NORTH) turnLeft();
        }

        while (getY() != y) {
            if (!canMove()) break;
            move();
        }
    }
}

