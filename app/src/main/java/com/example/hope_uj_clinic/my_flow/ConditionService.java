package com.example.hope_uj_clinic.my_flow;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ConditionService {

    //https://ml-model-production.up.railway.app/predictApi
    @POST("predictApi")
    @Headers("Content-Type: application/json")
    Call<PredictionResponse> getPrediction(@Body ConditionData conditionData);
}
