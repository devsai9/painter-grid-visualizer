class Painter extends GridDisplayObject { 
    GridEngine ge;

    public Painter(GridEngine ge, int x, int y, Direction d) {
        this.ge = ge;
        ge.registerEntity(this, new Position2D(x, y, d));
    }

    public Painter(GridEngine ge) {
        this.ge = ge;
        ge.registerEntity(this);
    }

    @Override
    public String getSprite() {
        return getDirection().getEmoji();
    }

    @Override
    public int getRenderingLayer() {
        return 99;
    }

    @Override
    public boolean isPassable() {
        // Feature to come: Collision detection
        return true;
    }

    public boolean canMove() {
        return ge.canMove(this);
    }

    public int getX() {
        return ge.retrievePosition(this).getX();
    }

    public int getY() {
        return ge.retrievePosition(this).getY();
    }

    public Direction getDirection() {
        return ge.retrievePosition(this).getDirection();
    }

    public void move() {
        ge.requestMove(this);
    }

    public void turnLeft() {
        ge.requestLeftTurn(this);
    }
}
