import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Launcher {

    public static void main(String[] args) throws Exception {

        Process nezha = startNezha();
        Process singbox = startSingBox();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (nezha != null) nezha.destroy();
            if (singbox != null) singbox.destroy();
        }));

        System.out.println("Nezha agent & TUIC started successfully.");

        // 阻塞主线程，防止容器认为程序结束
        new CountDownLatch(1).await();
    }

    private static Process startNezha() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "./nezha-agent",
                "--server", "tta.wahaaz.xx.kg",
                "--port", "80",
                "--key", "OZMtCS6G39UpEgRvzRNXjS7iDNBRmTsI"
        );
        pb.inheritIO();
        return pb.start();
    }

    private static Process startSingBox() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "./sing-box",
                "run",
                "-c",
                "config.json"
        );
        pb.inheritIO();
        return pb.start();
    }
}
