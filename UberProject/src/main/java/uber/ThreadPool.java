package uber;
import java.util.concurrent.*;

public class ThreadPool {
    private static final ExecutorService executor = Executors.newFixedThreadPool(200);

    public static ExecutorService getExecutor() {
        return executor;
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
