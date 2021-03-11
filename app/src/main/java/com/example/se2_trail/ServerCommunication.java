package com.example.se2_trail;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class ServerCommunication {



    @RequiresApi(api = Build.VERSION_CODES.O) // appeard after adding String.join
    public static String changeEverySecondNumber(int[] matNr){
        String[] result = new String[matNr.length];
        for (int i = 0; i <matNr.length ; i= i+2) {
            result[i]=  Integer.toString(matNr[i]);
        }

        for (int i = 1; i <matNr.length ; i= i+2) {
            int asciiNr = matNr[i]+64;
            char asciiCharacter = (char) asciiNr;
            result[i] = Character.toString(asciiCharacter);
        }
        String resultMatNr = String.join(",", result);
        return resultMatNr;
    }
}

