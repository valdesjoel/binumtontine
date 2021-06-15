package com.example.binumtontine.modele;

import java.io.Serializable;

public class ProduitDmdeCredit implements Serializable {
    //Constants fields
    public static final String KEY_PcNumero = "PcNumero";
    public static final String KEY_PcDmdeCredit = "PcDmdeCredit";
    public static final String KEY_PcProduit = "PcProduit";
    public static final String KEY_PcQuantite = "PcQuantite";
    public static final String KEY_PcDesign = "PcDesign";
    public static final String KEY_PcPrixUnit = "PcPrixUnit";
    public static final String KEY_PcMtTotal = "PcMtTotal";
    public static final String KEY_PcQteTotLivre = "PcQteTotLivre";

    //Attributes's class
    private String PcNumero;            //PcNumero /I+. **  Champ clé autoincrementable.
    private String PcDmdeCredit;
    private String PcProduit;
    private String PcQuantite;
    private String PcDesign;
    private String PcPrixUnit;
    private String PcMtTotal;
    private String PcQteTotLivre;

    public ProduitDmdeCredit() {
    }

    public String getPcNumero() {
        return PcNumero;
    }

    public void setPcNumero(String pcNumero) {
        PcNumero = pcNumero;
    }

    public String getPcDmdeCredit() {
        return PcDmdeCredit;
    }

    public void setPcDmdeCredit(String pcDmdeCredit) {
        PcDmdeCredit = pcDmdeCredit;
    }

    public String getPcProduit() {
        return PcProduit;
    }

    public void setPcProduit(String pcProduit) {
        PcProduit = pcProduit;
    }

    public String getPcQuantite() {
        return PcQuantite;
    }

    public void setPcQuantite(String pcQuantite) {
        PcQuantite = pcQuantite;
    }

    public String getPcDesign() {
        return PcDesign;
    }

    public void setPcDesign(String pcDesign) {
        PcDesign = pcDesign;
    }

    public String getPcPrixUnit() {
        return PcPrixUnit;
    }

    public void setPcPrixUnit(String pcPrixUnit) {
        PcPrixUnit = pcPrixUnit;
    }

    public String getPcMtTotal() {
        return PcMtTotal;
    }

    public void setPcMtTotal(String pcMtTotal) {
        PcMtTotal = pcMtTotal;
    }

    public String getPcQteTotLivre() {
        return PcQteTotLivre;
    }

    public void setPcQteTotLivre(String pcQteTotLivre) {
        PcQteTotLivre = pcQteTotLivre;
    }
}
