public class Main {
    public static void main(String[] args) {
        GridEngine ge = GridEngine.getInstance();

        PainterPlus p = new PainterPlus(ge);
        PainterPlus p2 = new PainterPlus(ge, 2, 2, Direction.NORTH);

        ge.updateDisplay();

        p.moveTo(9, 9);
        p2.moveTo(6, 7);

        ge.updateDisplay();

        while (!ge.rendererIsDone()) {}
        ge.stopRenderer();
    }
}
