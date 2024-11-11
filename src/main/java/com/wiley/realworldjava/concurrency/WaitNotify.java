package com.wiley.realworldjava.concurrency;

public class WaitNotify {

    final  Object  lock = new Object(); // can be any class type

    void callWait() throws InterruptedException {
        synchronized(lock) {
            lock.wait();
        }
    }

    void callNotify() {
        synchronized(lock){
            lock.notify();
        }
    }

    void callNotifyAll() {
        synchronized(lock){
            lock.notifyAll();
        }
    }
}
