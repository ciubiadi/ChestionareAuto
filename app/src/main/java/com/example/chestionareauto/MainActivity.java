package com.example.chestionareauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout layoutGame;
    ConstraintLayout finishLayout;
    Button btnGo;
    ArrayList<String> numberlist = new ArrayList<String>();
    TextView timerTextView;

    public void start(View view) {
        layoutGame.setVisibility(View.VISIBLE);
        btnGo.setVisibility(View.INVISIBLE);
        get_json();

        new CountDownTimer(1800100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                layoutGame.setVisibility(View.INVISIBLE);
                finishLayout.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void updateTimer( int secondsLeft ) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondsString = Integer.toString(seconds);

        if(secondsString.equals("0")) {
            secondsString = "00";
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutGame = findViewById(R.id.layoutGame);
        finishLayout = findViewById(R.id.finishLayout);
        btnGo = findViewById(R.id.btnGo);
        timerTextView = findViewById(R.id.timerTextView);

        layoutGame.setVisibility(View.INVISIBLE);
        finishLayout.setVisibility(View.INVISIBLE);
        btnGo.setVisibility(View.VISIBLE);

    }

    public void get_json() {
        String json;

        try {

            Random rand = new Random();
            int a = rand.nextInt(7);

            InputStream is = getAssets().open("intrebari_raspunsuri2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);

            JSONObject obj = jsonArray.getJSONObject(0);

            JSONArray secondArray = obj.getJSONArray("answers").getJSONArray(0);

            // obj.getJSONArray("answers").get(1).toString()
            Log.i("info", secondArray.get(1).toString());



/*
                for(int i = 0; i < jsonArray.length(); i++ )
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                     if (obj.getString("question").equals("2. Which of the following statements about blind spots is true?")) {
                        numberlist.add(obj.getString("question"));
                    }
                numberlist.add(obj.toString());
                }
*/
               // Toast.makeText(getApplicationContext(), numberlist.toString(), Toast.LENGTH_LONG).show();
/*
            JSONObject obj = jsonArray.getJSONObject(0);
            JSONArray secondArray = obj.getJSONArray("answers");
      //      numberlist.add(obj.getString("answers"));
            numberlist.add(obj.get("question").toString());
            numberlist.add(secondArray.getString(0));
                Log.i("info", numberlist.toString());
*/

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
