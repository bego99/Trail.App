package com.example.se2_trail;


import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ServerSocket serverSocket;
    Handler UIHandler;
    Thread thread1 = null;
    private final int port = 53212;

    Button sendButton;
    Button berechnungButton;
    Button clearSendButton;

    EditText inputNumber;
    TextView txtViewEingabeMatrikelNr;
    TextView txtViewAntwort;
    TextView txtViewErrorMessage;
    TextView txtViewMatNr;
    String matNr;
    String serverResponds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define activity Main Objjects
        sendButton = (Button) findViewById(R.id.button);

        clearSendButton = (Button)findViewById(R.id.button5);
        berechnungButton = (Button) findViewById(R.id.button2);
        inputNumber = (EditText) findViewById(R.id.editTextNumber);
        txtViewEingabeMatrikelNr = (TextView) findViewById(R.id.textView);
        txtViewAntwort = (TextView) findViewById(R.id.textView2);
        txtViewMatNr = (TextView) findViewById(R.id.textView4);
        txtViewErrorMessage = (TextView) findViewById(R.id.textView3);

        // set invisible
        txtViewMatNr.setVisibility(View.INVISIBLE);
        txtViewAntwort.setVisibility(View.INVISIBLE);
        clearSendButton.setVisibility(View.INVISIBLE);


        //Error Message nicht anzeigen;
        txtViewErrorMessage.setText("");

        // Send number to Server
        sendButton.setOnClickListener(this::onClick);
        // sort MatNr
        berechnungButton.setOnClickListener(this::onClick2);

        clearSendButton.setOnClickListener(this::onClickClearSendButtons);

    }

    // 1: Ich uebergebe server, 2: progress speichern, 3:was bekomme ich vom Async zurueck
    private class ServerComunication extends AsyncTask<String, String, String> {
        // preproces, doinBackground, postproces

        // onPrexcecute


        Socket socket = null;

        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null; // oder Inputstream reagiert aber nicht auf zeilenumbruch
        String message;
        private final String adress = "se2-isys.aau.at";
        private final int port = 53212;

        @Override
        protected String doInBackground(String... params) { //keinen Zugriff auf UI muss async parameter 1 ist


            message = params[0];
            String serverResponds = null;
            try {
                socket = new Socket(adress, port);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                bufferedReader = new BufferedReader(new InputStreamReader((socket.getInputStream())));

                dataOutputStream.writeBytes(message + "\n");
                serverResponds = bufferedReader.readLine();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return serverResponds;
        }
        @Override
        protected void onPostExecute(String serverResponds){


            txtViewAntwort.setText(serverResponds);
            txtViewAntwort.setVisibility(View.VISIBLE);
            sendButton.setVisibility(View.INVISIBLE);
            clearSendButton.setVisibility(View.VISIBLE);
        }
    }





        @Override
        public void onClick(View v) {

            // Safe input MatNr
            matNr = inputNumber.getText().toString();
            txtViewErrorMessage.setText("");

            if (matNr.length() == 8) {

                try {

                    ServerComunication serverComunication = new ServerComunication();
                    serverComunication.execute(matNr);
                    serverComunication = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                txtViewErrorMessage.setText("Bitte geben Sie eine Oesterreichische Matrikelnummer ein");
                inputNumber.getText().clear();
            }
        }


        public void onClick2(View v) {
            // obtaining number from input
            String sortMat = inputNumber.getText().toString();
            // creating String array to convert it to int Array
            // geht auch mit charAt(i)
            String[] sortmatNrArray = sortMat.split("");
            Log.d("lenght", String.valueOf(sortmatNrArray.length));

            if (sortmatNrArray.length == 8) {
                txtViewErrorMessage.setText("");
                sendButton.setVisibility(View.INVISIBLE);
                clearSendButton.setVisibility(View.VISIBLE);
                txtViewMatNr.setVisibility(View.VISIBLE);
                txtViewMatNr.setText("Deine Matrikel Nummer lautet: " + sortMat);
                String sorted = changeEverySecondNumber(sortmatNrArray);
                inputNumber.setText(sorted);

            } else {
                txtViewErrorMessage.setText("Eine Oesterreichische Matrikelnummer hat 8 Ziffern");
                inputNumber.getText().clear();
            }
        }


        public static String changeEverySecondNumber(String[] matNr) {
            String[] result = new String[matNr.length];
            for (int i = 0; i < matNr.length; i = i + 2) {
                result[i] = (matNr[i]);
            }

            for (int i = 1; i < matNr.length; i = i + 2) {
                int stringtToInt = Integer.parseInt(matNr[i]);
                int asciiNr = stringtToInt + 64;
                char asciiCharacter = (char) asciiNr;
                result[i] = Character.toString(asciiCharacter);
            }
            String resultMatNr = Arrays.toString(result).replace("[", "").replace("]", "");
            ;
            Log.d("string", resultMatNr);
            return resultMatNr;
        }


        public void onClickClearSendButtons(View v){
        sendButton.setVisibility(View.VISIBLE);
        clearSendButton.setVisibility(View.INVISIBLE);
        txtViewMatNr.setText("");
        txtViewAntwort.setText("");
        inputNumber.setText("");
        }





    }
