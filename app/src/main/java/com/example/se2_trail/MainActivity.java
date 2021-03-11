package com.example.se2_trail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    Button sendButton;
    EditText inputNumber;
    TextView  txtViewEingabeMatrikelNr, txtViewAntwort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = (Button)findViewById(R.id.button);
        inputNumber = (EditText)findViewById(R.id.editTextNumber);
        txtViewEingabeMatrikelNr = (TextView)findViewById(R.id.textView);
        txtViewAntwort = (TextView)findViewById((R.id.textView2));
        sendButton.setOnClickListener(XXX);



    }
}