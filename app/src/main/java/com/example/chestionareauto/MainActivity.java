package com.example.chestionareauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
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
    Button btnGo;
<<<<<<< HEAD
    Button btnA;
    Button btnB;
    Button btnC;
    TextView timerTextView;
    TextView questionTextView;
    Random rand;
    int questionCoutner = 1;
    int[] buttonStatus = { 0, 0, 0 };
   // ArrayList<Integer> correctAnswers = new ArrayList<Integer>();
    int[] correctAnswers = { 0, 0, 0 };
    int counterCorrectAnswers = 0;

    public void chooseAnswer(View view) {

       int tag = Integer.parseInt(view.getTag().toString());
       if(buttonStatus[tag] == 0 ) {

           //Daca butonul nu a mai fost apasat
           view.setBackgroundResource(R.color.light_blue);
           buttonStatus[tag] = 1;
       } else {

           //Daca butonul a mai fost apasat, il "deselectam" si schimbam culoarea de fundal inapoi in gri
           view.setBackgroundResource(R.color.light_gray);
           buttonStatus[tag] = 0;
       }
    }

    public void next(View view) {

        boolean answerStatus = true;

        //Comparam fiecare element din vectorul correctAnswers ( sablonul cu raspunsuri corecte pt intrebarea curenta ) cu fiecare element din buttinStatus ( raspunsurile alese de iutilizator )

        for( int i = 0; i < correctAnswers.length; i++)
        {
            if(correctAnswers[i] != buttonStatus[i])
                answerStatus = false;
        }
        if(answerStatus == true)
        {
            Log.i("info", "Raspuns correct");
            counterCorrectAnswers++;
        }
        else Log.i("info", "Raspuns gresit");
    }
=======
//    ArrayList<Object> questionsAndAnswers = new ArrayList<Object>();
    JSONArray jsonArray;
    String correctAnswer;
    String chosedAnswer;

    Integer candidateScore = 0;
>>>>>>> 989764c69fe2108a01a87f6eb93ccfdedbe5326e

    public void start(View view) {
        layoutGame.setVisibility(View.VISIBLE);
        btnGo.setVisibility(View.INVISIBLE);
        get_json();
<<<<<<< HEAD

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

        btnGo.setEnabled(false);
    }

    public void updateTimer( int secondsLeft ) {

        //transformam secundele in minute : secunde
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondsString = Integer.toString(seconds);

        if(seconds <= 9) {
            secondsString = "0" + secondsString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondsString);
=======
        getRandomQuestion();
>>>>>>> 989764c69fe2108a01a87f6eb93ccfdedbe5326e
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutGame = findViewById(R.id.layoutGame);
        btnGo = findViewById(R.id.btnGo);
<<<<<<< HEAD
        timerTextView = findViewById(R.id.timerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
=======
>>>>>>> 989764c69fe2108a01a87f6eb93ccfdedbe5326e

        layoutGame.setVisibility(View.INVISIBLE);
        btnGo.setVisibility(View.VISIBLE);

    }

    public void get_json() {
        String json;

        try {

            rand = new Random();
            int a = rand.nextInt(6);

            InputStream is = getAssets().open("intrebari_raspunsuri2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
<<<<<<< HEAD

            JSONArray jsonArray = new JSONArray(json);

            JSONObject obj = jsonArray.getJSONObject(a);

            JSONArray secondArray = obj.getJSONArray("answers").getJSONArray(0);

            // obj.getJSONArray("answers").get(1).toString()
            //   Log.i("info", secondArray.get(0).toString());

            //get questions and answers

            for( int i = 0; i < 3; i++)
            {
                // daca in json valoarea raspunsului e false, scriem valoarea 0 in vectorul correctAnswers, daca nu scriem valoarea 1 ( true )
                if(obj.getJSONArray("answers").getJSONArray(i).get(1).toString() == "false") {
                    correctAnswers[i] = 0;
=======
            jsonArray = new JSONArray(json);
//            JSONObject obj11 = jsonArray.getJSONObject(a);
//            JSONArray secondArray = obj.getJSONArray("answers").getJSONArray(0);
            // obj.getJSONArray("answers").get(1).toString()
//            Log.i("Intra aici?", jsonArray.toString());
//            for(int i = 0; i < jsonArray.length(); i++) {
//                JSONObject obj = jsonArray.getJSONObject(i);
//                Log.i("inside iterator: ", obj.toString());
//                questionsAndAnswers.add(obj);
//            }

//            Log.i("final array: ", questionsAndAnswers.get(0).toString());
/*
                for(int i = 0; i < jsonArray.length(); i++ )
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                     if (obj.getString("question").equals("2. Which of the following statements about blind spots is true?")) {
                        numberlist.add(obj.getString("question"));
                    }
                numberlist.add(obj.toString());
>>>>>>> 989764c69fe2108a01a87f6eb93ccfdedbe5326e
                }
                else {
                    correctAnswers[i] = 1;
                }
            }

            questionTextView.setText(Integer.toString(questionCoutner) + ". " + obj.getString("question"));

            btnA.setText("A) " + secondArray.get(0).toString());

            btnB.setText("B) " + obj.getJSONArray("answers").getJSONArray(1).get(0).toString());

            btnC.setText("C) " + obj.getJSONArray("answers").getJSONArray(2).get(0).toString());

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void getRandomQuestion() {
        int rand = new Random().nextInt(7);
        try {
            JSONObject object1 = jsonArray.getJSONObject(rand);
            TextView questionText = (TextView) findViewById(R.id.questionTextView);
            questionText.setText(object1.getString("question"));

            TextView answer1 = (TextView) findViewById(R.id.btnA);
            answer1.setText(object1.getString("answer1"));

            TextView answer2 = (TextView) findViewById(R.id.btnB);
            answer2.setText(object1.getString("answer2"));

            TextView answer3 = (TextView) findViewById(R.id.btnC);
            answer3.setText(object1.getString("answer3"));

            correctAnswer = object1.getString("correctAnswer");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onAnswer1Click(View v) {
        chosedAnswer = ((Button)v).getText().toString();
        // TODO: Set the background color of the button after it's selected
//        Button button = (Button) findViewById(R.id.btnA);
//        button.setBackgroundColor(Color.DKGRAY);
    }

    public void onAnswer2Click(View v) {
        chosedAnswer = ((Button)v).getText().toString();
    }

    public void onAnswer3Click(View v) {
        chosedAnswer = ((Button)v).getText().toString();
    }

    public void nextQuestion() {
        // Keep track of the candidate score
        if (chosedAnswer.equals(correctAnswer)) {
            candidateScore++;
        }
    }

}
