public class Main {
    public static void main(String[] args) {
        GridEngine ge = new GridEngine();

        PainterPlus p = new PainterPlus(ge);

        ge.updateDisplay();

        p.moveTo(9, 9);

        ge.updateDisplay();

        while (!ge.rendererIsDone()) {}
        ge.stopRenderer();
    }
}
