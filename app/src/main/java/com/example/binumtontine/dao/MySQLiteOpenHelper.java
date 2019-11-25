package com.example.binumtontine.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //propriétés
    private String creation = "create table organefaitier ("
        +"datemesure TEXT PRIMARY KEY,"
        +"poids INTEGER NOT NULL,"
        +"taille INTEGER NOT NULL,"
        +"age INTEGER NOT NULL,"
        +"sexe INTEGER NOT NULL);";
    /**
     * Constructeur
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MySQLiteOpenHelper(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Si changement de BD
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creation);

    }

    /**
     * Si changement de version de la BD
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
