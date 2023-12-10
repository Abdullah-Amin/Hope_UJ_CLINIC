package com.example.hope_uj_clinic.my_flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.databinding.ActivityConditionClassifierBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConditionClassifierActivity extends AppCompatActivity {

    private ActivityConditionClassifierBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConditionClassifierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void classify(View view) {

        if ((binding.HREt.getText().toString().isEmpty() || !isNumeric(binding.HREt.getText().toString()))) {
            binding.HREt.setError("Please enter a valid number");
            return;
        }
        if ((binding.oxygenEt.getText().toString().isEmpty() || !isNumeric(binding.oxygenEt.getText().toString()))) {
            binding.oxygenEt.setError("Please enter a valid number");
            return;
        }
        if ((binding.respEt.getText().toString().isEmpty() || !isNumeric(binding.respEt.getText().toString()))) {
            binding.respEt.setError("Please enter a valid number");
            return;
        }
        if ((binding.tempEt.getText().toString().isEmpty() || !isNumeric(binding.tempEt.getText().toString()))) {
            binding.tempEt.setError("Please enter a valid number");
            return;
        }

        int hr = Integer.parseInt(binding.HREt.getText().toString());
        int resp = Integer.parseInt(binding.respEt.getText().toString());
        int spo2 = Integer.parseInt(binding.oxygenEt.getText().toString());
        int temp = Integer.parseInt(binding.tempEt.getText().toString());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ml-model-production.up.railway.app/")
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ConditionService conditionService =
                retrofit.create(ConditionService.class);

        conditionService.getPrediction(new ConditionData(hr, resp, spo2, temp))
                        .enqueue(new Callback<PredictionResponse>() {
                            @Override
                            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                                if (response.isSuccessful()){
                                    Log.i("abdo", "onResponse: " + response.body().prediction);
                                    Intent intent = new Intent(ConditionClassifierActivity.this, ConditionActivity.class);
                                    intent.putExtra("prediction", response.body().prediction);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(ConditionClassifierActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PredictionResponse> call, Throwable t) {
                                Toast.makeText(ConditionClassifierActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}