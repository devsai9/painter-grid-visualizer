import java.util.concurrent.ArrayBlockingQueue;
import java.io.PrintWriter;

class GridDisplay implements Runnable {
    public static record Frame(String[] positions, int gridWidth) {}

    private final ArrayBlockingQueue<Frame> queue = new ArrayBlockingQueue<>(50);
    private boolean needReset = false;
    private volatile boolean running = true;

    private final int frameDelayMs = 100;

    // Avoid auto-flushing to prevent flickering
    private static final PrintWriter writer = new PrintWriter(System.out, false);

    @Override
    public void run() {
        System.out.println("Running rendering thread at " + (1000 / frameDelayMs) + " fps.");

        while (running) {
            try {
                Frame f = queue.take();
                updateDisplay(f.positions, f.gridWidth);

                Thread.sleep(frameDelayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
        // System.out.println("Halting renderer.");
    }

    public void submitFrame(String[] positions, int gridWidth) {
        if (!queue.offer(new Frame(positions, gridWidth))) {
            queue.poll();
            queue.offer(new Frame(positions, gridWidth));
        }
    }

    public boolean bufferEmpty() {
        return queue.size() == 0;
    }

    private void moveUp(int l) {
        if (l < 0) return;
        writer.print("\033[" + l + "A\r");
    }

    private void displayFrame(String[] positions, int gridWidth) {
        for (int i = 0; i < positions.length; i++) {
            if (i != 0 && i % gridWidth == 0) writer.print("\n");
            
            String c = positions[i];
            writer.print(c == null || c.isBlank() ? " • " : " " + c + " ");
        }
        writer.println();
        writer.flush();
    }

    private void updateDisplay(String[] positions, int gridWidth) {
        int gridHeight = positions.length / gridWidth;
        
        if (needReset) moveUp(gridHeight);
        displayFrame(positions, gridHeight);
        needReset = true;
    }
}
