package com.example.chestionareauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    Button btnGo;
    ArrayList<String> numberlist = new ArrayList<String>();

    public void start(View view) {
        layoutGame.setVisibility(View.VISIBLE);
        btnGo.setVisibility(View.INVISIBLE);
        get_json();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutGame = findViewById(R.id.layoutGame);
        btnGo = findViewById(R.id.btnGo);

        layoutGame.setVisibility(View.INVISIBLE);
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
