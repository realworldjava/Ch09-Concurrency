package com.wiley.realworldjava.concurrency;

public class Synchronizing {

    public static void main(String[] args) throws InterruptedException {
       // waitAndNotify();

    }

    private static void waitAndNotify() throws InterruptedException {
        final Object lock = new Object(); // can be any class type
        synchronized(lock) {
            lock.wait();
        }
        synchronized(lock){
            lock.notify();
        }
    }

    public synchronized void someMethod(){
        //      ... do stuff ...
    }

    private final Object LOCK = new Object();
    public void someMethod2(){
//      ... do stuff ...
        synchronized(LOCK){
            //      ... do more stuff, this block is protected

        }
    }




}
