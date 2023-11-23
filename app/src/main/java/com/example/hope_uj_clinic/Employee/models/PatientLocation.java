package com.example.hope_uj_clinic.Employee.models;

public class PatientLocation {

    private Double latitude;

    private Double longitude;

    public PatientLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
