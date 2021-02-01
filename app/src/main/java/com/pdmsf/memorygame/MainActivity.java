package com.pdmsf.memorygame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<String> listMemory = new ArrayList();
    private final List<Button> matchButton = new ArrayList();
    private int index;
    private ProgressBar progressBar;
    private ConstraintLayout bg;
    private GridLayout gridProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        bg = (ConstraintLayout) findViewById(R.id.bgApp);
        gridProgress = (GridLayout) findViewById(R.id.grid_progress);
        progressBar.setMax(6);

        //setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        resetMemory();
    }

    private void showMatchButtons() {
        for (Button button : matchButton) button.setVisibility(View.VISIBLE);
    }

    private void resetMemory() {
        showMatchButtons();
        listMemory.clear();
        matchButton.clear();
        setWinner(false);
        gridProgress.setVisibility(View.VISIBLE);
        bg.setBackgroundColor(getResources().getColor(R.color.white));
        progressBar.setProgress(0);
        String[] numberArray = this.getResources().getStringArray(R.array.number_array);
        listMemory.addAll(Arrays.asList(numberArray));
        Collections.shuffle(listMemory);
        index = 0;
    }

    private void setWinner(boolean value) {
        TextView title = (TextView) findViewById(R.id.congratulations_title);
        TextView subtitle = (TextView) findViewById(R.id.congratulations_subtitle);

        if(value) {
            title.setVisibility(View.VISIBLE);
            subtitle.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
            subtitle.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void selectNumber(View view) {
        Button button = (Button)view;

        if(button.getText().equals(listMemory.get(index))) {
            int color = button.getBackgroundTintList().getDefaultColor();

            bg.setBackgroundColor(color);

            matchButton.add(button);
            button.setVisibility(View.INVISIBLE);
            index += 1;
            progressBar.setProgress(index);
            if(index == 6) {
                gridProgress.setVisibility(View.INVISIBLE);
                setWinner(true);
            }
        } else {
            bg.setBackgroundColor(getResources().getColor(R.color.white));
            index = 0;
            progressBar.setProgress(0);
            showMatchButtons();
        }
    }

    public void resetGame(View view) {
        resetMemory();
    }
}