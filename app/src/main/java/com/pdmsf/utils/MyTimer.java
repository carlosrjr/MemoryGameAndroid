package com.pdmsf.utils;

import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pdmsf.memorygame.MainActivity;

import org.w3c.dom.Text;

import java.util.List;

public class MyTimer extends CountDownTimer {
    private long now;
    private ProgressBar progressBar;
    private MainActivity mainActivity;
    private TextView scoreView;

    public MyTimer(long millisInFuture, long countDownInterval, ProgressBar progressBar, MainActivity mainActivity, TextView scoreView) {
        super(millisInFuture, countDownInterval);
        now = millisInFuture/100;
        this.progressBar = progressBar;
        this.mainActivity = mainActivity;
        this.scoreView = scoreView;
    }

    public long getScore() {return now;};

    @Override
    public void onTick(long millisUntilFinished) {
        //textView.setText(String.format("%s", millisUntilFinished));
        now = millisUntilFinished/50;
        scoreView.setText(String.valueOf(millisUntilFinished));
        progressBar.setProgress((int)(millisUntilFinished/1000));
    }

    @Override
    public void onFinish() {
        now = 1;
        progressBar.setProgress(0);
        mainActivity.setWinner(true);
    }
}
