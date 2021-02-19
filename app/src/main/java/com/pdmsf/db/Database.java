package com.pdmsf.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pdmsf.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DATABASE_NAME = "memory_game";

    private static final int DATABASE_ACCESS = 0;
    private static final String SQL_STRUCT = "CREATE TABLE IF NOT EXISTS player(id_ INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, score INTEGER NOT NULL DEFAULT 0, errors INTEGER NOT NULL DEFAULT 0); ";
    private static final String SQL_INSERT = "INSERT INTO player (nome, score, errors) VALUES ('%s', '%d', '%d');";
    private static final String SQL_SELECT_ALL = "SELECT * FROM player ORDER BY score DESC, errors ASC LIMIT 15;";
    private static final String SQL_CLEAR = "DROP TABLE IF EXISTS player;";
    private SQLiteDatabase database;
    private Cursor cursor;
    private int indexID, indexNome, indexScore, indexErrors;

    public Database(Context context) {
        database = context.openOrCreateDatabase(DATABASE_NAME, DATABASE_ACCESS, null);
        database.execSQL(SQL_STRUCT);
    }

    public void clear() {
        database.execSQL(SQL_CLEAR);
    }

    public void close() {
        database.close();
    }

    public void insert(Player player) {
        String query = String.format(SQL_INSERT, player.getNome(), player.getScore(), player.getErrors());
        database.execSQL(query);
    }

    public List<Player> all() {
        List<Player> players = new ArrayList<Player>();
        Player player;

        cursor = database.rawQuery(SQL_SELECT_ALL, null);

        if(cursor.moveToFirst()) {
            indexID = cursor.getColumnIndex("id_");
            indexNome = cursor.getColumnIndex("nome");
            indexScore = cursor.getColumnIndex("score");
            indexErrors = cursor.getColumnIndex("errors");

            do {
                player = new Player(cursor.getString(indexNome), cursor.getInt(indexScore), cursor.getInt(indexErrors));
                players.add(player);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return players;
    }
}
