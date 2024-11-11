package com.wiley.realworldjava.concurrency;

public class UsingVolatile {

    volatile double value = 10.0;
    volatile double count = value + 1;

    public void updateValue(double score) {
        double total = value + score;
        value = total / count;
    }

}
