package com.example.chestionareauto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    ConstraintLayout layoutGame;
    ConstraintLayout finishLayout;
    ConstraintLayout startLayout;
    Button btnGo;
    Button btnA;
    Button btnB;
    Button btnC;
    Button btnNext;
    Button btnNextQuestion;
    Button btnAnswerLater;
    Button btnSemnaleLuminoase;
    Button btnDepasirea;
    Button btnVitezaDistanta;
    Button btnCirculatiaAutostrazi;
    TextView timerTextView;
    TextView questionTextView;
    TextView answersTextView;
    TextView resultTextView;
    Random rand;
    int questionCounter = 1;
    //    int answeredQuestionsCounter = 0;
    int[] buttonStatus = { 0, 0, 0 };
    // ArrayList<Integer> correctAnswers = new ArrayList<Integer>();
    int[] correctAnswers = { 0, 0, 0 };
    int counterCorrectAnswers = 0;
    boolean isSendAnswerEnabled = true;
    ArrayList<Integer> arrayOfQuestions = new ArrayList<>();

    JSONArray jsonArray;
    JSONObject obj;

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
        isSendAnswerButtonEnabled();
    }

    public void next(View view) throws JSONException {
        btnNextQuestion.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        btnAnswerLater.setVisibility(View.INVISIBLE);

        Log.i("info", "correct answers: " + correctAnswers[0]);
        if (correctAnswers[0] == 1) {
            btnA.setBackgroundResource(R.color.light_green);
        } else { btnA.setBackgroundResource(R.color.light_red); }

        if (correctAnswers[1] == 1) {
            btnB.setBackgroundResource(R.color.light_green);
        } else { btnB.setBackgroundResource(R.color.light_red); }

        if (correctAnswers[2] == 1) {
            btnC.setBackgroundResource(R.color.light_green);
        } else { btnC.setBackgroundResource(R.color.light_red); }
    }

    public void start(View view) throws JSONException {
        layoutGame.setVisibility(View.VISIBLE);
        btnGo.setVisibility(View.INVISIBLE);
        btnNextQuestion.setVisibility(View.INVISIBLE);
        btnSemnaleLuminoase.setVisibility(View.INVISIBLE);
        btnDepasirea.setVisibility(View.INVISIBLE);
        btnVitezaDistanta.setVisibility(View.INVISIBLE);
        btnCirculatiaAutostrazi.setVisibility(View.INVISIBLE);


        int tagjsoon = Integer.parseInt(view.getTag().toString());
        get_json(tagjsoon);
        getQuestion();
        isSendAnswerButtonEnabled();

        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
            countDownTimer.start();
        }else {
            countDownTimer = new CountDownTimer(1800100, 1000) {

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
//        getRandomQuestion();
    }

    public void backToMenu(View view) {
        finishLayout.setVisibility(View.INVISIBLE);
        startLayout.setVisibility(View.VISIBLE);
        btnSemnaleLuminoase.setVisibility(View.VISIBLE);
        btnDepasirea.setVisibility(View.VISIBLE);
        btnVitezaDistanta.setVisibility(View.VISIBLE);
        btnCirculatiaAutostrazi.setVisibility(View.VISIBLE);
        questionCounter = 1;
//    int answeredQuestionsCounter = 0;

        // ArrayList<Integer> correctAnswers = new ArrayList<Integer>();
        arrayOfQuestions.clear();
        resetAnswersButtonState();
        questionCounter = 1;
        answersTextView.setText(Integer.toString(questionCounter) + "/26 ");
        counterCorrectAnswers = 0;
        isSendAnswerEnabled = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutGame = findViewById(R.id.layoutGame);
        finishLayout = findViewById(R.id.finishLayout);
        startLayout = findViewById(R.id.startLayout);
        btnGo = findViewById(R.id.btnGo);
        timerTextView = findViewById(R.id.timerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        answersTextView = findViewById(R.id.answersTextView);
        resultTextView = findViewById(R.id.rezultatTextView);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnNext = findViewById(R.id.btnNext);
        btnNextQuestion = findViewById(R.id.buttonNextQuestion);
        btnAnswerLater = findViewById(R.id.btnAnswerLater);
        btnSemnaleLuminoase = findViewById(R.id.btnSemnaleLuminoase);
        btnDepasirea = findViewById(R.id.btnDepasirea);
        btnVitezaDistanta = findViewById(R.id.btnVitezaDistanta);
        btnCirculatiaAutostrazi = findViewById(R.id.btnCirculatiaAutostrazi);

        layoutGame.setVisibility(View.INVISIBLE);
        finishLayout.setVisibility(View.INVISIBLE);
        btnGo.setVisibility(View.INVISIBLE);

    }

    public void get_json(int tag) {
        String json;
        InputStream is = null;
        try {

            rand = new Random();
            int a = rand.nextInt(6);
            if(tag==0)
            {
                is = getAssets().open("semnale_luminoase.json");
            }
            else if(tag==1)
            {
                is = getAssets().open("depasirea.json");
            }
            else if(tag==2)
            {
                is = getAssets().open("distanta_intre_vehicule.json");
            }
            else if(tag==3)
            {
                is = getAssets().open("circulatia_autostrazi.json");
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            jsonArray = new JSONArray(json);
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void getQuestion() throws JSONException {
        int randomNumber = -1;

        randomNumber = rand.nextInt(6);
        while (arrayOfQuestions.contains(randomNumber)) {
            randomNumber = rand.nextInt(26);
        }

        arrayOfQuestions.add(randomNumber);
        obj = jsonArray.getJSONObject(randomNumber);

        for( int i = 0; i < 3; i++)
        {
            // daca in json valoarea raspunsului e false, scriem valoarea 0 in vectorul correctAnswers, daca nu, scriem valoarea 1 ( true )
            if(obj.getJSONArray("answers").getJSONArray(i).get(1).toString() == "false") {
                correctAnswers[i] = 0;
//                    jsonArray = new JSONArray(json);
            }
            else {
                correctAnswers[i] = 1;
            }
        }

        questionTextView.setText(Integer.toString(questionCounter) + ". " + obj.getString("question"));

        btnA.setText("A) " + obj.getJSONArray("answers").getJSONArray(0).get(0).toString());

        btnB.setText("B) " + obj.getJSONArray("answers").getJSONArray(1).get(0).toString());

        btnC.setText("C) " + obj.getJSONArray("answers").getJSONArray(2).get(0).toString());

        Log.i("info", obj.toString());
    }

    public void resetAnswersButtonState() {
        btnA.setBackgroundResource(R.color.light_gray);
        btnB.setBackgroundResource(R.color.light_gray);
        btnC.setBackgroundResource(R.color.light_gray);

        buttonStatus[0] = 0; buttonStatus[1] = 0; buttonStatus[2] = 0;
    }

    public void isSendAnswerButtonEnabled() {
        if (buttonStatus[0] == 0 && buttonStatus[1] == 0 && buttonStatus[2] == 0) {
            btnNext.setEnabled(false);
            btnNext.setBackgroundResource(R.color.light_gray);
        } else {
            btnNext.setEnabled(true);
            btnNext.setBackgroundResource(R.color.light_green);
        }
    }

    public void answerLater(View view) throws JSONException {
        arrayOfQuestions.remove(arrayOfQuestions.size() - 1);
        getQuestion();
    }

    public void goToNextQuestion(View view) throws JSONException {
        btnNext.setVisibility(View.VISIBLE);
        btnAnswerLater.setVisibility(View.VISIBLE);
        btnNextQuestion.setVisibility(View.INVISIBLE);

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

//        answeredQuestionsCounter++;
        questionCounter++;
        Log.i("info", "Raspunsuri date: " + questionCounter);
        Log.i("info", "Raspunsuri corecte: " + counterCorrectAnswers);

        if (questionCounter == 27) {
            layoutGame.setVisibility(View.INVISIBLE);
            finishLayout.setVisibility(View.VISIBLE);
            if (counterCorrectAnswers > 22) {
                resultTextView.setText("Ai raspuns corect la " + counterCorrectAnswers + " intrebari. Felicitari, ai trecut!");
            } else {
                resultTextView.setText("Ai raspuns corect la " + counterCorrectAnswers + " intrebari. Din pacate, nu ai trecut. Mai incearca!");
            }
            return;
        }
        getQuestion();
        resetAnswersButtonState();
        answersTextView.setText(Integer.toString(questionCounter) + "/26 ");
        isSendAnswerButtonEnabled();
    }

}
