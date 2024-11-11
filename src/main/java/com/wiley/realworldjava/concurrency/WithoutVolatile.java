package com.wiley.realworldjava.concurrency;

public class WithoutVolatile {

    double value = 10.0;
    double count = value + 1;

    public void updateValue(double score) {
        double total = value + score;
        value = total / count;
    }

}
