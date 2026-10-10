import service.ProgressMonitor;

public class ProgressMonitorTest {

    public static void main(String[] args) {

        ProgressMonitor monitor = new ProgressMonitor();

        Thread thread1 = new Thread(monitor, "Progress Thread 1");
        Thread thread2 = new Thread(monitor, "Progress Thread 2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
            Thread.currentThread().interrupt();
        }

        System.out.println("Progress monitoring completed.");
    }
}