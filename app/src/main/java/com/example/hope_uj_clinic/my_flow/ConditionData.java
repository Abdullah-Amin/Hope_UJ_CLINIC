package com.example.hope_uj_clinic.my_flow;

public class ConditionData {

    private double HR;
    private double RESP;
    private double SpO2;
    private double TEMP;

    public ConditionData(double HR, double RESP, double spO2, double TEMP) {
        this.HR = HR;
        this.RESP = RESP;
        SpO2 = spO2;
        this.TEMP = TEMP;
    }

    public double getHR() {
        return HR;
    }

    public void setHR(double HR) {
        this.HR = HR;
    }

    public double getRESP() {
        return RESP;
    }

    public void setRESP(double RESP) {
        this.RESP = RESP;
    }

    public double getSpO2() {
        return SpO2;
    }

    public void setSpO2(double spO2) {
        SpO2 = spO2;
    }

    public double getTEMP() {
        return TEMP;
    }

    public void setTEMP(double TEMP) {
        this.TEMP = TEMP;
    }

    @Override
    public String toString() {
        return "ConditionData{" +
                "HR='" + HR + '\'' +
                ", RESP='" + RESP + '\'' +
                ", SpO2='" + SpO2 + '\'' +
                ", TEMP='" + TEMP + '\'' +
                '}';
    }
}
