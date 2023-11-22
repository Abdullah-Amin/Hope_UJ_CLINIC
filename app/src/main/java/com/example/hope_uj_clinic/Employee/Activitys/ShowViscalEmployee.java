package com.example.hope_uj_clinic.Employee.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import com.example.hope_uj_clinic.Patient.Adapters.AdapterVitalSigns;
import com.example.hope_uj_clinic.Patient.ModelVitalSigns;
import com.example.hope_uj_clinic.databinding.ActivityShowViscalEmployeeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ShowViscalEmployee extends AppCompatActivity {
    ActivityShowViscalEmployeeBinding binding;
    String key;
    String userid;
    String type;
    AdapterVitalSigns vitalSigns;
    Calendar myCalendar;
    private int mHour, mMinute;

    ArrayList<ModelVitalSigns> list = new ArrayList<>();
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_viscal_employee);
        //key = getIntent().getStringExtra("key");
        userid = getIntent().getStringExtra("userid");
        type = getIntent().getStringExtra("type");

        binding.textAccount.setText(type);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(type, key, userid);
            }
        });

        vitalSigns = new AdapterVitalSigns();
        binding.rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.rec.setAdapter(vitalSigns);
        databaseHelper = new DatabaseHelper(this);
        getData();
    }

    public void openDialog(String type, String key, String userid) {
        final Dialog dialog = new Dialog(ShowViscalEmployee.this, R.style.WideDialog);
        dialog.setContentView(R.layout.itemsendvitar);
        ColorDrawable dialogColor = new ColorDrawable(Color.WHITE);
        dialogColor.setAlpha(50);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        TextView names = dialog.findViewById(R.id.name);
        names.setText("MRN - " + userid.subSequence(0, 7));
        EditText vitar = dialog.findViewById(R.id.vitar);
        TextView datee = dialog.findViewById(R.id.date);
        TextView timee = dialog.findViewById(R.id.time);
        Button ok = dialog.findViewById(R.id.ok);
        Button cancel = dialog.findViewById(R.id.cancel);
        myCalendar = Calendar.getInstance();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vit = vitar.getText().toString().trim();
                String dat = datee.getText().toString();
                String time = timee.getText().toString();
                if (vit.isEmpty()) {
                    vitar.setError("is Empty");
                    return;
                }
                if (dat.isEmpty()) {
                    datee.setError("is Empty");
                    return;
                }
                if (time.isEmpty()) {
                    timee.setError("is Empty");
                    return;
                } else {
                    ModelVitalSigns signs = new ModelVitalSigns();
                    signs.setDate(dat);
                    //signs.setKey(key);
                    signs.setRate(vit);
                    signs.setTime(time);
                    signs.setType(type);

                    long result = databaseHelper.addVitalSigns(signs, userid,type);
                    if (result != -1) {
                        Toast.makeText(ShowViscalEmployee.this, "Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getData();
                    } else {
                        Toast.makeText(ShowViscalEmployee.this, "Failed to add vital signs", Toast.LENGTH_SHORT).show();
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
                new DatePickerDialog(ShowViscalEmployee.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(ShowViscalEmployee.this,
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

    private void getData() {
        list.clear();
        list = databaseHelper.getAllVitalSigns(type);
        if (list.size() > 0) {
            vitalSigns.setList(list);
            vitalSigns.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
    }


}