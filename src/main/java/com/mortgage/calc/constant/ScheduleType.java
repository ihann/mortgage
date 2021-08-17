package com.mortgage.calc.constant;

public enum ScheduleType {
    ACC_BI_WEEKLY(24), BI_WEEKLY(26), MONTHLY(12);

    int value;

    ScheduleType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
