package com.thoughtworks.scmviz.sparkline;

public class DataPoint {

    private final Integer value;
    private final boolean isTick;

    public DataPoint(Integer value, boolean tick) {
        this.value = value;
        isTick = tick;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isTick() {
        return isTick;
    }
}
