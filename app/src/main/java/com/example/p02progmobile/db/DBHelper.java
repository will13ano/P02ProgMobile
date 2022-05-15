package com.example.p02progmobile.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "jogos.db";

    // DEFININDO TABELA TIME
    private static final String TIME_TABLE_NAME = "times";
    private static final String COLUMN_ID_TIME = "idTime";
    private static final String COLUMN_NOME = "nome";

    // DEFININDO TABELA JOGADOR
    private static final String JOGADOR_TABLE_NAME = "jogadores";
    private static final String COLUMN_ID_JOGADOR = "idJogador";
    private static final String COLUMN_CPF = "cpf";
    private static final String COLUMN_BIRTH = "anoNascimento";

    private static final String CREATE_TABLE_TIME = "CREATE TABLE " + TIME_TABLE_NAME + "("
            + COLUMN_ID_TIME + "INT PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOME + " TEXT NOT NULL );";

    private static final String CREATE_TABLE_JOGADOR = "CREATE TABLE " + JOGADOR_TABLE_NAME + "("
            + COLUMN_ID_JOGADOR + "INT NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOME + "TEXT NOT NULL,"
            + COLUMN_CPF + "TEXT NOT NULL,"
            + COLUMN_BIRTH + "INT,"
            + COLUMN_ID_TIME + "INT,"
            + "FOREIGN KEY (" + COLUMN_ID_TIME + ") REFERENCES " + TIME_TABLE_NAME
            + "("+ COLUMN_ID_TIME +") );";

    SQLiteDatabase sqLiteDatabase;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TIME);
        sqLiteDatabase.execSQL(CREATE_TABLE_JOGADOR);
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void inserirTime(Time time) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_TIME, time.getIdTime());
        values.put(COLUMN_NOME, time.getNome());

        sqLiteDatabase.insert(TIME_TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public void updateTime(Time time) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, time.getNome());

        sqLiteDatabase.update(TIME_TABLE_NAME, values, "idTime=?", new String[]{String.valueOf(time.getIdTime())});
        sqLiteDatabase.close();
    }

    public ArrayList<Time> selectAllTimes() {
        Cursor cursor = getReadableDatabase().query(TIME_TABLE_NAME, new String[]{"*"}, null, null, null, null, null);

        ArrayList<Time> listaTimes = new ArrayList<Time>();

        while (cursor.moveToNext()) {
            listaTimes.add(new Time(cursor.getInt(0), cursor.getString(1)));
        }

        return listaTimes;
    }

    public long deleteTime(Time time) {
        sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TIME_TABLE_NAME,
                COLUMN_ID_TIME + "=?", new String[]{String.valueOf(time.getIdTime())});
    }
}
