package com.example.hope_uj_clinic.Employee.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hope_uj_clinic.DatabaseHelper;
import com.example.hope_uj_clinic.R;
import com.example.hope_uj_clinic.Patient.Booking;
import com.example.hope_uj_clinic.Patient.Detilse;
import com.example.hope_uj_clinic.Patient.ModelVitalSigns;
import com.example.hope_uj_clinic.Patient.UserHome;
import com.example.hope_uj_clinic.Patient.VitalSigns;
import com.example.hope_uj_clinic.databinding.ActivityAddDataInCliencBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDataInClienc extends AppCompatActivity {
    String key;
    String name;
    String userid ;
    Calendar myCalendar;
    private int  mHour, mMinute;
    ActivityAddDataInCliencBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_data_in_clienc);
        key=getIntent().getStringExtra("key");
        name=getIntent().getStringExtra("name");
        userid=getIntent().getStringExtra("userid");
        //binding.name.setText(userid.substring(0,7)+"\n"+name);

        binding.TheWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(AddDataInClienc.this, ShowViscalEmployee.class);
                showDetilse.putExtra("type","The Weight");
                showDetilse.putExtra("userid",userid);
                startActivity(showDetilse);

//             String thewe="TheWeight";
//
//                Open(thewe,key,userid);
            }
        });
        binding.TheTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(AddDataInClienc.this,ShowViscalEmployee.class);
                showDetilse.putExtra("type","The Temperature");
                showDetilse.putExtra("userid",userid);
                startActivity(showDetilse);
//               String thete="TheTemperature";
//              Open(thete,key,userid);

            }
        });

        binding.RespiratoryRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(AddDataInClienc.this,ShowViscalEmployee.class);
                showDetilse.putExtra("type","Respiratory Rate");
                showDetilse.putExtra("userid",userid);
                startActivity(showDetilse);
//                Open(res,key,userid);
            }
        });


        binding.Blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(AddDataInClienc.this,ShowViscalEmployee.class);
                showDetilse.putExtra("type","Blood");
                showDetilse.putExtra("userid",userid);
//                Open(bool,key,userid);
                startActivity(showDetilse);
            }
        });
        binding.Pulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(AddDataInClienc.this,ShowViscalEmployee.class);
                showDetilse.putExtra("type","Pulse");
                showDetilse.putExtra("userid",userid);
                startActivity(showDetilse);
//                Open(pluse,key,userid);
            }
        });
        binding.Oxysgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetilse= new Intent(AddDataInClienc.this,ShowViscalEmployee.class);
                showDetilse.putExtra("type","Oxygen");
                showDetilse.putExtra("userid",userid);
                startActivity(showDetilse);
//                Open(ox,key,userid);
            }
        });


    }
    public void Open(String type,String key,String useid){
        final Dialog dialog = new Dialog(AddDataInClienc.this, R.style.WideDialog);
        dialog.setContentView(R.layout.itemsendvitar);
        ColorDrawable dialogColor = new ColorDrawable(Color.WHITE);
        dialogColor.setAlpha(50);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        TextView names=dialog.findViewById(R.id.name);
        names.setText(""+useid);
        EditText vitar=dialog.findViewById(R.id.vitar);
        TextView datee=dialog.findViewById(R.id.date);
        TextView timee=dialog.findViewById(R.id.time);
        Button ok=dialog.findViewById(R.id.ok);
        myCalendar = Calendar.getInstance();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vit =vitar.getText().toString().trim();
                String dat=datee.getText().toString();
                String time=timee.getText().toString();
                if (vit.isEmpty()){
                    vitar.setError("is Empty");
                    return;
                }
                if (dat.isEmpty()){
                    datee.setError("is Empty");
                    return;
                }
                if (time.isEmpty()){
                    timee.setError("is Empty");
                    return;
                }
                else {
                    ModelVitalSigns signs = new ModelVitalSigns();
                    signs.setDate(dat);
                    signs.setRate(vit);
                    signs.setTime(time);
                    signs.setType(type);

                    DatabaseHelper dbHelper = new DatabaseHelper(AddDataInClienc.this);
                    // Pass the type value to the addVitalSigns method
                    long insertedId = dbHelper.addVitalSigns(signs, userid, type);
                    if (insertedId != -1) {
                        Toast.makeText(AddDataInClienc.this, "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddDataInClienc.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                datee.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        datee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddDataInClienc.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        timee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddDataInClienc.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timee.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        dialog.show();
    }

}
