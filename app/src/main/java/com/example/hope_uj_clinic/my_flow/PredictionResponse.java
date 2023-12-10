package com.example.hope_uj_clinic.my_flow;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PredictionResponse implements Parcelable {
    String prediction;

    protected PredictionResponse(Parcel in) {
        prediction = in.readString();
    }

    public static final Creator<PredictionResponse> CREATOR = new Creator<PredictionResponse>() {
        @Override
        public PredictionResponse createFromParcel(Parcel in) {
            return new PredictionResponse(in);
        }

        @Override
        public PredictionResponse[] newArray(int size) {
            return new PredictionResponse[size];
        }
    };

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    @Override
    public String toString() {
        return "PredictionResponse{" +
                "prediction='" + prediction + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(prediction);
    }
}
