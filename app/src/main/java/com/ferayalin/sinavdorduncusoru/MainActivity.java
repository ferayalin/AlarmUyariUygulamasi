package com.ferayalin.sinavdorduncusoru;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private int alarmSaati, alarmDakika;
    private EditText etAlarmSaati, etAlarmAdi;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Calendar calendar;
    private RadioGroup rgUyari;
    private RadioGroup rgAralik;
    RadioButton rA, rU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etAlarmAdi = findViewById(R.id.etAd);
        etAlarmSaati = findViewById(R.id.etAlarmSaati);

        rgUyari = findViewById(R.id.rgUyari);
        rgAralik = findViewById(R.id.rgAralik);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("alarmlar");
        final Calendar c;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onClickSaatSec(View view) {
        calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay,
                                      int minute) {
                    alarmSaati = hourOfDay;
                    alarmDakika = minute;
                    etAlarmSaati.setText(hourOfDay + ":" + minute);
                }

            }, alarmSaati, alarmDakika, true);
        timePickerDialog.show();
    }

    public void alarmKur(View view) {
        String secilenAralik, secilenUyari;
        Alarm alarm;

        //hangisi seçiliyse Uyarı grubu
        int rgUyariID = rgUyari.getCheckedRadioButtonId();
        View radioButtonU = rgUyari.findViewById(rgUyariID);
        int idxU = rgUyari.indexOfChild(radioButtonU);

        //içideki Uyarı yazısını al
        rU = (RadioButton) rgUyari.getChildAt(idxU);
        secilenUyari = rU.getText().toString();

        //hangisi seçiliyse Aralık grubu
        int rgAralikID = rgAralik.getCheckedRadioButtonId();
        View radioButtonA = rgAralik.findViewById(rgAralikID);
        int idxA = rgAralik.indexOfChild(radioButtonA);

        //içideki Aralık yazısını al
        rA = (RadioButton) rgAralik.getChildAt(idxA);
        secilenAralik = rA.getText().toString();


        NotificationPublisher np = new NotificationPublisher();
        Context context = this.getApplicationContext();
        if(np != null){
            alarm = new Alarm(etAlarmAdi.getText().toString(), alarmSaati, alarmDakika, secilenAralik, secilenUyari);
            myRef.child(UUID.randomUUID().toString()).setValue(alarm);
            np.alarmKur(context,alarm);

            //this.getApplicationContext()
            Toast.makeText(context, "Alarm kuruldu :).", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(MainActivity.this, AlarmListesi.class);
        startActivity(intent);

    }

}
