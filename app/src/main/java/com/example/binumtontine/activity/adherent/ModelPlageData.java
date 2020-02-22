package com.example.binumtontine.activity.adherent;

import java.io.Serializable;

/**
 * Create a class file named Adherent.java
 * This model class will be useful to convert json data into objects.
 */
public class ModelPlageData implements Serializable {

    private String PdNumero;
    private String PdTypeData;
    private String PdNature;
    private String PdValTaux;
    private String PdValDe;
    private String PdValA;
    private String PdBase;
    private String PdProduit;


    public ModelPlageData(){}

    public ModelPlageData(String pdTypeData, String pdNature, String pdValTaux, String pdValDe, String pdValA,
                          String pdBase, String pdProduit) {
        this.PdTypeData = pdTypeData;
        this.PdNature = pdNature;
        this.PdValTaux = pdValTaux;
        this.PdValDe = pdValDe;
        this.PdValA = pdValA;
        this.PdBase = pdBase;
        this.PdProduit = pdProduit;

    }
    public ModelPlageData(String pdNumero,String pdTypeData, String pdNature, String pdValTaux, String pdValDe, String pdValA,
                          String pdBase, String pdProduit) {
        this.PdNumero = pdNumero;
        this.PdTypeData = pdTypeData;
        this.PdNature = pdNature;
        this.PdValTaux = pdValTaux;
        this.PdValDe = pdValDe;
        this.PdValA = pdValA;
        this.PdBase = pdBase;
        this.PdProduit = pdProduit;

    }



    public String getPdTypeData() {
        return this.PdTypeData;
    }
    public String getPdNumero() {
        return this.PdNumero;
    }
/*
    public void setAdNumero(int adNumero) {
        PdTypeData = adNumero;
    }
    */

    public String getPdNature() {
        return this.PdNature;
    }

    public void setPdNature(String pdNature) {
        this.PdNature = pdNature;
    }

    public String getPdValTaux() {
        return this.PdValTaux;
    }

    public void setPdValTaux(String pdValTaux) {
        this.PdValTaux = pdValTaux;
    }

    public String getPdValDe() {
        return this.PdValDe;
    }

    public void setPdValDe(String pdValDe) {
        this.PdValDe = pdValDe;
    }

    public String getPdValA() {
        return this.PdValA;
    }

    public void setPdValA(String pdValA) {
        this.PdValA = pdValA;
    }

    public String getPdBase() {
        return this.PdBase;
    }

    public void setPdBase(String pdBase) {
        this.PdBase = pdBase;
    }

    public String getPdProduit() {
        return this.PdProduit;
    }

    public void setPdProduit(String pdProduit) {
        this.PdProduit = pdProduit;
    }


}
