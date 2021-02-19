package com.pdmsf.memorygame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pdmsf.db.Database;
import com.pdmsf.model.Player;
import com.pdmsf.utils.MyTimer;
import com.pdmsf.widgets.HighscoreActivity;

import org.w3c.dom.Text;

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
    private MyTimer myTimer;
    private TextView scoreView;
    private EditText playerName;
    private int errors = 0;
    private GridLayout gridCongratulations;
    private GridLayout gridButtons;
    private Button btn_save_score;
    private TextView total_errors;


    private static final int TIME_DEFAULT = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        bg = (ConstraintLayout) findViewById(R.id.bgApp);
        gridProgress = (GridLayout) findViewById(R.id.grid_progress);
        scoreView = (TextView) findViewById(R.id.score);
        playerName = (EditText) findViewById(R.id.player_name);
        gridCongratulations = (GridLayout) findViewById(R.id.grid_congratulations);
        gridButtons = (GridLayout) findViewById(R.id.grid_buttons);
        btn_save_score = (Button) findViewById(R.id.btn_save_score);
        total_errors = (TextView) findViewById(R.id.total_errors);
        myTimer = new MyTimer(TIME_DEFAULT*1000, 1000, progressBar, this);
        progressBar.setMax(TIME_DEFAULT);

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
        scoreView.setText("0");
        gridProgress.setVisibility(View.VISIBLE);
        bg.setBackgroundColor(getResources().getColor(R.color.white));
        progressBar.setProgress(TIME_DEFAULT);
        String[] numberArray = this.getResources().getStringArray(R.array.number_array);
        listMemory.addAll(Arrays.asList(numberArray));
        Collections.shuffle(listMemory);
        playerName.setVisibility(View.VISIBLE);
        btn_save_score.setVisibility(View.VISIBLE);
        index = 0;
        errors = 0;
        myTimer.start();
    }

    public void setWinner(boolean value) {
        TextView title = (TextView) findViewById(R.id.congratulations_title);
        total_errors.setText(String.format("Erros: %d", errors));

        if(value) {
            gridButtons.setVisibility(View.GONE);
            gridCongratulations.setVisibility(View.VISIBLE);
            playerName.setVisibility(View.VISIBLE);
        } else {
            gridCongratulations.setVisibility(View.GONE);
            gridButtons.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void selectNumber(View view) {
        Button button = (Button)view;

        if(button.getText().equals(listMemory.get(index))) {
            int color = button.getBackgroundTintList().getDefaultColor();

            bg.setBackgroundColor(color);
            scoreView.setText(String.format("%s", Long.valueOf(String.valueOf(scoreView.getText())) + myTimer.getScore()));
            matchButton.add(button);
            button.setVisibility(View.INVISIBLE);
            index += 1;
            if(index == 6) {
                gridProgress.setVisibility(View.INVISIBLE);
                setWinner(true);
            }
        } else {
            bg.setBackgroundColor(getResources().getColor(R.color.white));
            index = 0;
            calcError();
            showMatchButtons();
        }
    }

    public void resetGame(View view) {
        resetMemory();
    }

    public void saveHighscore(View view) {
        String name = playerName.getText().toString().trim().length() == 0 ? "Não informado" : playerName.getText().toString().trim().substring(0,15);
        Player p = new Player();
        p.setNome(name);
        p.setScore(scoreView.getText().toString());
        p.setErrors(errors);

        playerName.setText("");
        playerName.setVisibility(View.INVISIBLE);
        btn_save_score.setVisibility(View.INVISIBLE);

        Database db = new Database(this);
        db.insert(p);
        db.close();

        showHighscore();
    }

    public void scoreView(View view) {
        showHighscore();
    }

    private void showHighscore() {
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }

    public void calcError() {
        long points = Long.valueOf(String.valueOf(scoreView.getText()));
        scoreView.setText(String.format("%s", (int)(points * (0.5))));
        errors++;
    }
}