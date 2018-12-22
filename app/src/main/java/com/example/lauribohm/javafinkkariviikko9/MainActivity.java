package com.example.lauribohm.javafinkkariviikko9;

import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    EditText valittuPv;
    EditText alkuA;
    EditText loppuA;

    TextView tuloste;
    Button button;
    Spinner spinner;

    String vallittuT = null;

    String ValittuPV = null;
    String alku;
    String loppu;

    String valittuT;

    readXMLFile RXF = readXMLFile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        valittuPv = (EditText) findViewById(R.id.editText);
        alkuA = (EditText) findViewById(R.id.editText3);
        loppuA = (EditText) findViewById(R.id.editText2);

        tuloste = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);


        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, RXF.teatterit());
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                valittuT = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                alku = null;
                loppu = null;

                ValittuPV = valittuPv.getText().toString();

                if (!alkuA.getText().toString().equals("")) {
                    alku = alkuA.getText().toString();
                }

                if (!loppuA.getText().toString().equals("")) {
                    loppu = loppuA.getText().toString();
                }

                RXF.findSchedule(ValittuPV, tuloste, valittuT, alku, loppu);

            }
        });
    }
}
