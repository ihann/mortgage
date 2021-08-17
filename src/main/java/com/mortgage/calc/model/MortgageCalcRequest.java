package com.mortgage.calc.model;

import com.mortgage.calc.constant.ScheduleType;

import javax.validation.constraints.NotNull;

public class MortgageCalcRequest {

    @NotNull
    private double price;
    @NotNull
    private double down;
    @NotNull
    private double rate;
    @NotNull
    private int amortization;
    @NotNull
    private ScheduleType scheduleType;

    public MortgageCalcRequest() {
    }

    public MortgageCalcRequest(Double price, Double down, Double rate, Integer amortization, ScheduleType scheduleType) {
        this.price = price;
        this.down = down;
        this.rate = rate;
        this.amortization = amortization;
        this.scheduleType = scheduleType;
    }

    public double getPrice() {
        return price;
    }

    public double getDown() {
        return down;
    }

    public double getRate() {
        return rate;
    }

    public int getAmortization() {
        return amortization;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }
}
