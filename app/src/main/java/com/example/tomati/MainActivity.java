package com.example.tomati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private AppCompatButton myButton;
    private ImageButton stateBtn;
    private ImageButton restartBtn;
    private CountDownTimer timer;

    private ProgressBar progressBar;
    private TextView progressBarText;

    private int totalTimeInMillis = 5000; // 10 seconds
    private int numSteps = 100; // Number of steps to reach full progress
    private int stepDuration = totalTimeInMillis / numSteps; // Duration for each step
    private long timeLeft = (long) totalTimeInMillis;

    private boolean statePause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton = findViewById(R.id.btn_start);
        stateBtn = findViewById(R.id.btn_pause);
        restartBtn = findViewById(R.id.btn_restart);
        statePause = false;

        progressBar = findViewById(R.id.progress_bar);
        progressBarText = findViewById(R.id.progress_bar_text);

        stateBtn.setVisibility(View.INVISIBLE);
        restartBtn.setVisibility(View.INVISIBLE);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myButton.setEnabled(false);
                stateBtn.setVisibility(View.VISIBLE);
                restartBtn.setVisibility(View.VISIBLE);
                updateProgressBar();
            }
        });

        stateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statePause) {
                    stateBtn.setImageResource(R.drawable.play);
                    timer.cancel();
                } else {
                    stateBtn.setImageResource(R.drawable.pause);
                    updateProgressBar();
                }
                statePause = !statePause;
            }
        });

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                progressBar.setProgress(progressBar.getMax());
                timeLeft = totalTimeInMillis;
                // Reset progress bar
                progressBar.setProgress(100);
                progressBarText.setText(String.valueOf(totalTimeInMillis / 1000));
                myButton.setEnabled(true);
                stateBtn.setVisibility(View.INVISIBLE);
                restartBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void updateProgressBar() {
        timer = new CountDownTimer(timeLeft, stepDuration) {
            int currentProgress = 100;

            public void onTick(long millisUntilFinished) {
                // Update the progress bar on each tick
                timeLeft = millisUntilFinished;
                currentProgress = (int) (timeLeft * 100 / totalTimeInMillis);
                progressBar.setProgress((currentProgress));
                // Update the TextView to display current progress
                progressBarText.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                // Ensure progress bar reaches full progress
                progressBar.setProgress(progressBar.getMax());
                timeLeft = totalTimeInMillis;
                // Reset progress bar
                progressBar.setProgress(100);
                progressBarText.setText(String.valueOf(totalTimeInMillis / 1000));
                myButton.setEnabled(true);
                stateBtn.setVisibility(View.INVISIBLE);
                restartBtn.setVisibility(View.INVISIBLE);
            }
        }.start();
    }
}