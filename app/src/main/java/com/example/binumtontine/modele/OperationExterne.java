package com.example.binumtontine.modele;

public class OperationExterne {
    public static final String KEY_OeNumero = "OeNumero";
    public static final String KEY_OeCode = "OeCode";
    public static final String KEY_OeLibelle = "OeLibelle";
    public static final String KEY_OeLibelle2 = "OeLibelle2";
    public static final String KEY_OeType = "OeType";
    public static final String KEY_OeCaisse = "OeCaisse";
    public static final String KEY_OeIsOn = "OeIsOn";
    public static final String KEY_OeUserCree = "OeUserCree";
    public static final String KEY_OeDateHCree = "OeDateHCree";

    public static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to push intent.extra

    private int OeNumero;
    private String OeCode;
    private String OeLibelle;
    private String OeLibelle2;
    private String OeType;
    private String OeCaisse;
    private String OeIsOn;
    private int OeUserCree;
    private String OeDateHCree;

    public OperationExterne() {
    }

    public int getOeNumero() {
        return OeNumero;
    }

    public void setOeNumero(int oeNumero) {
        OeNumero = oeNumero;
    }

    public String getOeCode() {
        return OeCode;
    }

    public void setOeCode(String oeCode) {
        OeCode = oeCode;
    }

    public String getOeLibelle() {
        return OeLibelle;
    }

    public void setOeLibelle(String oeLibelle) {
        OeLibelle = oeLibelle;
    }

    public String getOeLibelle2() {
        return OeLibelle2;
    }

    public void setOeLibelle2(String oeLibelle2) {
        OeLibelle2 = oeLibelle2;
    }

    public String getOeType() {
        return OeType;
    }

    public void setOeType(String oeType) {
        OeType = oeType;
    }

    public String getOeCaisse() {
        return OeCaisse;
    }

    public void setOeCaisse(String oeCaisse) {
        OeCaisse = oeCaisse;
    }

    public String getOeIsOn() {
        return OeIsOn;
    }

    public void setOeIsOn(String oeIsOn) {
        OeIsOn = oeIsOn;
    }

    public int getOeUserCree() {
        return OeUserCree;
    }

    public void setOeUserCree(int oeUserCree) {
        OeUserCree = oeUserCree;
    }

    public String getOeDateHCree() {
        return OeDateHCree;
    }

    public void setOeDateHCree(String oeDateHCree) {
        OeDateHCree = oeDateHCree;
    }
}
