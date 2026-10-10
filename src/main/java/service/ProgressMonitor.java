package service;

public class ProgressMonitor implements Runnable {

    private int progress = 0;

    @Override
    public void run() {

        for (int i = 1; i <= 5; i++) {

            synchronized (this) {
                progress += 20;
                System.out.println(
                    Thread.currentThread().getName()
                    + " - Project Progress: " + progress + "%"
                );
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Progress monitoring interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }
}