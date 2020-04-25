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

            InputStream is = getAssets().open("intrebari_raspunsuri2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i < jsonArray.length(); i++ )
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("question").equals("2. Which of the following statements about blind spots is true?")) {
                    numberlist.add(obj.getString("question"));
                }
            }

            Toast.makeText(getApplicationContext(), numberlist.toString(), Toast.LENGTH_LONG).show();


        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
