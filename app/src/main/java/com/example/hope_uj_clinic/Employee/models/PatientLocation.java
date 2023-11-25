package com.example.hope_uj_clinic.Employee.models;

public class PatientLocation {

    private String patientId;

    private String personType;
    private Double latitude;

    private Double longitude;

    public PatientLocation(String patientId, String personType, Double latitude, Double longitude) {
        this.patientId = patientId;
        this.personType = personType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "PatientLocation{" +
                "patientId='" + patientId + '\'' +
                ", personType='" + personType + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
