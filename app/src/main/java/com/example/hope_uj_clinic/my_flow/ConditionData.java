package com.example.hope_uj_clinic.my_flow;

public class ConditionData {

    private int HR;
    private int RESP;
    private int SpO2;
    private int TEMP;

    public ConditionData(int HR, int RESP, int spO2, int TEMP) {
        this.HR = HR;
        this.RESP = RESP;
        SpO2 = spO2;
        this.TEMP = TEMP;
    }

    public int getHR() {
        return HR;
    }

    public void setHR(int HR) {
        this.HR = HR;
    }

    public int getRESP() {
        return RESP;
    }

    public void setRESP(int RESP) {
        this.RESP = RESP;
    }

    public int getSpO2() {
        return SpO2;
    }

    public void setSpO2(int spO2) {
        SpO2 = spO2;
    }

    public int getTEMP() {
        return TEMP;
    }

    public void setTEMP(int TEMP) {
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
