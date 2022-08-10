package fundamentals.multithreading;

public class Lock {
    public static void main(String[] args) {
        var lock1 = new Object();
        var lock2 = new Object();

        var thread1 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + ": synchronized on lock2!");
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + ": synchronized on lock1!");
                }
            }
        });

        var thread2 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + ": synchronized on lock1!");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + ": synchronized on lock2!");
                }
            }
        });

        var threadPrinter = new Thread(() -> {
            try {
                while (true) {
                    System.out.println(thread1.getName() + ": " + thread1.getState());
                    System.out.println(thread2.getName() + ": " + thread2.getState());
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        threadPrinter.start();
        thread1.start();
        thread2.start();
    }
}
