package com.pdmsf.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final List<String> listMemory = new ArrayList();
    private final List<Button> matchButton = new ArrayList();
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetMemory();
    }

    private void showMatchButtons() {
        for (Button button : matchButton) button.setVisibility(View.VISIBLE);
    }

    private void resetMemory() {
        showMatchButtons();
        listMemory.clear();
        matchButton.clear();

        String[] numberArray = this.getResources().getStringArray(R.array.number_array);
        listMemory.addAll(Arrays.asList(numberArray));
        Collections.shuffle(listMemory);
        index = 0;
    }

    public void selectNumber(View view) {
        Button button = (Button)view;

        if(button.getText().equals(listMemory.get(index))) {
            matchButton.add(button);
            button.setVisibility(View.INVISIBLE);
            index += 1;
        } else {
            index = 0;
            showMatchButtons();
        }
    }

    public void resetGame(View view) {
        resetMemory();
    }
}