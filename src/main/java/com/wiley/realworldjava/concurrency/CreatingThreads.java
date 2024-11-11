package com.wiley.realworldjava.concurrency;

public class CreatingThreads {

    public static void main(String[] args) {
        anonymous();
        runnable();
        lambda();

        fluentApi();

        addMetadata();

        unstarted();

        virtual();

    }

    private static void virtual() {
        Thread thread = Thread.ofVirtual()
                .name("Some meaningful name")
                .start(() -> doSomeWork());
    }

    private static void unstarted() {
        Thread thread = Thread.ofPlatform()
                .name("Some meaningful name")
                .priority(Thread.NORM_PRIORITY+1)
                .group(new ThreadGroup("Some thread group"))
                .daemon(false)
                .unstarted(() -> doSomeWork());
        thread.start();
    }

    private static void addMetadata() {
        Thread thread = Thread.ofPlatform()
                .name("Some meaningful name")
                .priority(Thread.NORM_PRIORITY + 1)
                .group(new ThreadGroup("Some thread group"))
                .daemon(false)
                .start(() -> doSomeWork());
    }

    private static void fluentApi() {
        Thread thread = Thread
                .ofPlatform()
                .start(() -> doSomeWork());
    }

    private static void lambda() {
        new Thread(() -> doSomeWork()).start();
    }

    private static void runnable() {
        // Create a Runnable that defines the work to be done:
        Runnable someRunnable = new Runnable() {
            @Override
            public void run() {
                doSomeWork();
            }
        };
// assign the Runnable to a thread and start it running:
        new Thread(someRunnable).start();
    }

    private static void anonymous() {
        new Thread() {
            @Override
            public void run() {
                doSomeWork();
            }
        }.start();
    }

    private static void doSomeWork() {
    }
}
