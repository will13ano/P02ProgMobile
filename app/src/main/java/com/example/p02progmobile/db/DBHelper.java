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
            + COLUMN_ID_TIME + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOME + " TEXT NOT NULL );";

    private static final String CREATE_TABLE_JOGADOR = "CREATE TABLE " + JOGADOR_TABLE_NAME + "("
            + COLUMN_ID_JOGADOR + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NOME + " TEXT NOT NULL, "
            + COLUMN_CPF + " TEXT NOT NULL, "
            + COLUMN_BIRTH + " INTEGER, "
            + COLUMN_ID_TIME + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_ID_TIME + ") REFERENCES " + TIME_TABLE_NAME
            + " ("+ COLUMN_ID_TIME +") );";

    private SQLiteDatabase sqLiteDatabase;

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

    public long inserirTime(Time time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, time.getNome());

        long id = db.insert(TIME_TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public boolean updateTime(Time time) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, time.getNome());

        try {
            sqLiteDatabase.update(TIME_TABLE_NAME, values, "idTime=?", new String[]{String.valueOf(time.getIdTime())});
            sqLiteDatabase.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Time getTime(int id) {
        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TIME_TABLE_NAME,
                new String[]{"*"},
                "idTime=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        cursor.moveToFirst();
        return new Time(cursor.getInt(0), cursor.getString(1));
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

        long id =  sqLiteDatabase.delete(TIME_TABLE_NAME,
                COLUMN_ID_TIME + "=?", new String[]{String.valueOf(time.getIdTime())});
        sqLiteDatabase.close();

        return id;
    }

    public long inserirJogador(Jogador jogador) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NOME, jogador.getNome());
        values.put(COLUMN_CPF, jogador.getCpf());
        values.put(COLUMN_BIRTH, jogador.getAnoNascimento());
        values.put(COLUMN_ID_TIME, jogador.getIdTime());

        long id = sqLiteDatabase.insert(JOGADOR_TABLE_NAME, null, values);
        sqLiteDatabase.close();

        return id;
    }

    public boolean updateJogador(Jogador jogador) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID_TIME, jogador.getIdTime());
        values.put(COLUMN_NOME, jogador.getNome());
        values.put(COLUMN_CPF, jogador.getCpf());
        values.put(COLUMN_BIRTH, jogador.getAnoNascimento());

        try {
            sqLiteDatabase.update(JOGADOR_TABLE_NAME, values,
                    COLUMN_ID_JOGADOR+"=?",
                    new String[]{String.valueOf(jogador.getIdJogador())});
            sqLiteDatabase.close();
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    public ArrayList<Jogador> selectAllJogadores() {
        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(JOGADOR_TABLE_NAME,
                new String[]{"*"},
                null, null, null, null, null, null);

        return this.converteCursorEmListaDeJogadores(cursor);
    }

    public long deleteJogador(Jogador jogador) {
        sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.delete(JOGADOR_TABLE_NAME,
                COLUMN_ID_JOGADOR+"=?",
                new String[]{String.valueOf(jogador.getIdJogador())});
    }

    public ArrayList<Jogador> selectJogadoresPorTime(Time time) {
        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(JOGADOR_TABLE_NAME,
                new String[]{"*"},
                "idTime=?", new String[]{String.valueOf(time.getIdTime())},
                null, null, null, null);

        return this.converteCursorEmListaDeJogadores(cursor);
    };

    private ArrayList<Jogador> converteCursorEmListaDeJogadores(Cursor cursor) {
        ArrayList<Jogador> jogadoresList = new ArrayList<Jogador>();

        while (cursor.moveToNext()) {
            jogadoresList.add(
                    this.converteCursorEmJogador(cursor)
            );
        }

        return jogadoresList;
    }

    private Jogador converteCursorEmJogador(Cursor cursor) {
        int idTimeIndex = cursor.getColumnIndex("idTime");
        int idJogadorIndex = cursor.getColumnIndex("idJogador");
        int anoNascimentoIndex = cursor.getColumnIndex("anoNascimento");
        int cpfIndex = cursor.getColumnIndex("cpf");
        int nomeIndex = cursor.getColumnIndex("nome");

        return new Jogador(
                cursor.getInt(idTimeIndex),
                cursor.getInt(idJogadorIndex),
                cursor.getString(nomeIndex),
                cursor.getString(cpfIndex),
                cursor.getInt(anoNascimentoIndex)
        );
    }
}
