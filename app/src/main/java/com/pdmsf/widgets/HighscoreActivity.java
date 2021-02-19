package com.pdmsf.widgets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pdmsf.db.Database;
import com.pdmsf.memorygame.R;
import com.pdmsf.model.Player;

import java.util.List;

public class HighscoreActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        listView = (ListView) findViewById(R.id.lista);

        Database db = new Database(this);

        List<Player> players = db.all();
        String [] array = new String[players.size()];

        for(int i = 0; i < players.size(); i++) {
            array[i] = String.format("%dº - %s - Pontuação: %s - Erros: %d", i+1, players.get(i).getNome(), players.get(i).getScore(), players.get(i).getErrors());
        }

        if(array.length == 0) array = new String [] {"Nenhum resultado"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);

        listView.setAdapter(adapter);

        db.close();
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void clearHighscore(View view) {
        Database db = new Database(this);
        db.clear();
        listView.setAdapter(null);
        db.close();
    }
}