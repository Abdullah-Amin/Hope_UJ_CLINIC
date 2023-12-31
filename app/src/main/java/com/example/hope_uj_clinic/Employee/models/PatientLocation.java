package com.example.hope_uj_clinic.Employee.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PatientLocation implements Parcelable {

    private String patientId;
    private String orderId;
    private String personType;
    private String userName;
    private String name;
    private String note;
    private String orderState;
    private String latitude;
    private String longitude;

    private String user;

    public PatientLocation(String patientId, String orderId, String userName,
                           String name, String note, String personType,
                           String orderState, String latitude, String longitude) {
        this.patientId = patientId;
        this.orderId = orderId;
        this.userName = userName;
        this.name = name;
        this.note = note;
        this.personType = personType;
        this.orderState = orderState;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected PatientLocation(Parcel in) {
        patientId = in.readString();
        orderId = in.readString();
        userName = in.readString();
        name = in.readString();
        note = in.readString();
        personType = in.readString();
        orderState = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }



    public static final Creator<PatientLocation> CREATOR = new Creator<PatientLocation>() {
        @Override
        public PatientLocation createFromParcel(Parcel in) {
            return new PatientLocation(in);
        }

        @Override
        public PatientLocation[] newArray(int size) {
            return new PatientLocation[size];
        }
    };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(patientId);
        parcel.writeString(orderId);
        parcel.writeString(userName);
        parcel.writeString(name);
        parcel.writeString(note);
        parcel.writeString(personType);
        parcel.writeString(orderState);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
    }

    @Override
    public String toString() {
        return "PatientLocation{" +
                "patientId='" + patientId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", personType='" + personType + '\'' +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", orderState='" + orderState + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
