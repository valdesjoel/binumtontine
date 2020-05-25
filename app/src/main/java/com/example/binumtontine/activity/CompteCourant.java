package com.example.binumtontine.activity;

/**
 * Create a class file named Category.java
 * This model class will be useful to convert json data into objects.
 */
public class CompteCourant {

    private int CcNumero;
    private String CcLibelle;
    private String CcIsDecouvOn;
    private Double CcMtMaxDecouv;
    private String CcIsMaxDecouvNeg;

    public CompteCourant(int ccNumero, String ccLibelle, String ccIsDecouvOn, Double ccMtMaxDecouv, String ccIsMaxDecouvNeg) {
        CcNumero = ccNumero;
        CcLibelle = ccLibelle;
        CcIsDecouvOn = ccIsDecouvOn;
        CcMtMaxDecouv = ccMtMaxDecouv;
        CcIsMaxDecouvNeg = ccIsMaxDecouvNeg;
    }

    public int getCcNumero() {
        return CcNumero;
    }

    public void setCcNumero(int ccNumero) {
        CcNumero = ccNumero;
    }

    public String getCcLibelle() {
        return CcLibelle;
    }

    public void setCcLibelle(String ccLibelle) {
        CcLibelle = ccLibelle;
    }

    public String getCcIsDecouvOn() {
        return CcIsDecouvOn;
    }

    public void setCcIsDecouvOn(String ccIsDecouvOn) {
        CcIsDecouvOn = ccIsDecouvOn;
    }

    public Double getCcMtMaxDecouv() {
        return CcMtMaxDecouv;
    }

    public void setCcMtMaxDecouv(Double ccMtMaxDecouv) {
        CcMtMaxDecouv = ccMtMaxDecouv;
    }

    public String getCcIsMaxDecouvNeg() {
        return CcIsMaxDecouvNeg;
    }

    public void setCcIsMaxDecouvNeg(String ccIsMaxDecouvNeg) {
        CcIsMaxDecouvNeg = ccIsMaxDecouvNeg;
    }
}
