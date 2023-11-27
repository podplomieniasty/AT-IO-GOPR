package org.atar.simulator.clock;

public enum ClockParam {

    // -------- AVAILABLE TYPES -------- //
    ONE_SECOND(1000),
    TEN_SECONDS(10 * 1000);


    private long value;
    private ClockParam(long _value) {
        this.value = _value;
    }
    public long getValue() { return this.value; }
    public long getValueAsSeconds() { return this.value / 1000; }
}
