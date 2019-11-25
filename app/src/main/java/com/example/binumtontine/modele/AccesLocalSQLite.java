package com.example.binumtontine.modele;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.binumtontine.dao.MySQLiteOpenHelper;

import java.util.Date;

/**
 * cette classe permet l'accès à SQLite
 */
public class AccesLocalSQLite {

    //propriétés
    private String nomBaseDonnee = "bdBinumTontine.sqlite";
    private Integer versionBD = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;

    /**
     * Constructeur
     * @param context
     */
    public AccesLocalSQLite (Context context){
        accesBD = new MySQLiteOpenHelper(context,nomBaseDonnee,null,versionBD);
    }

    /**
     * Ajout d'un organe faitier dans le BD
     * @param organeFaitier_modele
     */
    public void ajout(OrganeFaitier_modele organeFaitier_modele){
        bd= accesBD.getWritableDatabase();
        String req = "insert into organefaitier (datemesure, poids, taille, age, sexe) values ";
        req += "(\""+organeFaitier_modele.getSigle()+"\","+organeFaitier_modele.getLibelle()+","+organeFaitier_modele.getNum_agrement()+","+organeFaitier_modele.getDate_agrement()+","+organeFaitier_modele.getBoite_postale()+")";
        bd.execSQL(req);
    }

    public OrganeFaitier_modele recupDernierOF_In_BD(){
        bd = accesBD.getReadableDatabase();
        OrganeFaitier_modele organeFaitier_modele = null;
        String req = "select * from organefaitier";
        Cursor curseur = bd.rawQuery(req,null);
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            Date date = new Date();
            String sigle = curseur.getString(0);
            organeFaitier_modele = new OrganeFaitier_modele(sigle,null,null,null,null,null,null,null,null,null,null,null,null);
        }
        curseur.close();
        return organeFaitier_modele;
    }
}
