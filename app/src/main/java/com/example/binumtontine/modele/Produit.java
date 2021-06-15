package com.example.binumtontine.modele;

public class Produit {
    //Constants fields
    public static final String KEY_PdNumero = "PdNumero";
    public static final String KEY_PdNom = "PdNom";
    public static final String KEY_PdPrixUnit = "PdPrixUnit";
    public static final String KEY_PdQteStock = "PdQteStock";
    public static final String KEY_PdStockMin = "PdStockMin";
    public static final String KEY_PdGuichet = "PdGuichet";
    public static final String KEY_PdUserCree = "PdUserCree";
    public static final String KEY_PdIsOn = "PdIsOn";
    public static final String KEY_PdPrixVtePubl = "PdPrixVtePubl";
    //Attributes's class
    private String PdNumero;            //PdNumero /I+. **  Champ clé autoincrementable.
    private String PdNom;               //PdNom. /A70. **  Nom ou désignation du produit
    private String PdPrixUnit;          //PdPrixUnit /R **  Prix unitaire du produit
    private String PdQteStock;          //PdQteStock / I. **  Quantité en stock
    private String PdGuichet;           //PdGuichet / I. **  Numéro de Guichet
    private String PdStockMin;           //PdStockMin /I. ** Pour saisir le stock minimum du Produit sur le Guichet
    private String PdUserCree;           //PdUserCree /I. ** Pourindiquer le user aynt effectué l'opération
    private String PdIsOn;           //PdIsOn /I. ** Pour indiquer si le produit est actif ou pas
    private String image;
    private String PdPrixVtePubl; // PdPrixVtePubl  / R  **  pour y renseigner le prix de vente Public de chaque produit,
    // lors de sa création, dans l'Admin. Caisse, et réutilisable en exploitation par l'Agent de Guichet,
    // en cas de vente au Public, via une fonctions propre tel "Divers/ Vente Produits", dans le menu Principal


    // Constructor

    public Produit() {
    }

    //Getters and Setters

    public String getPdNumero() {
        return PdNumero;
    }

    public void setPdNumero(String pdNumero) {
        PdNumero = pdNumero;
    }

    public String getPdNom() {
        return PdNom;
    }

    public void setPdNom(String pdNom) {
        PdNom = pdNom;
    }

    public String getPdPrixUnit() {
        return PdPrixUnit;
    }

    public void setPdPrixUnit(String pdPrixUnit) {
        PdPrixUnit = pdPrixUnit;
    }

    public String getPdQteStock() {
        return PdQteStock;
    }

    public void setPdQteStock(String pdQteStock) {
        PdQteStock = pdQteStock;
    }

    public String getPdStockMin() {
        return PdStockMin;
    }

    public void setPdStockMin(String pdStockMin) {
        PdStockMin = pdStockMin;
    }

    public String getPdGuichet() {
        return PdGuichet;
    }

    public void setPdGuichet(String pdGuichet) {
        PdGuichet = pdGuichet;
    }

    public String getPdUserCree() {
        return PdUserCree;
    }

    public void setPdUserCree(String pdUserCree) {
        PdUserCree = pdUserCree;
    }

    public String getPdIsOn() {
        return PdIsOn;
    }

    public void setPdIsOn(String pdIsOn) {
        PdIsOn = pdIsOn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPdPrixVtePubl() {
        return PdPrixVtePubl;
    }

    public void setPdPrixVtePubl(String pdPrixVtePubl) {
        PdPrixVtePubl = pdPrixVtePubl;
    }

    // Functions

    @Override
    public String toString() {
        return "Produit{" +
                "PdNumero='" + PdNumero + '\'' +
                ", PdNom='" + PdNom + '\'' +
                ", PdPrixUnit='" + PdPrixUnit + '\'' +
                ", PdQteStock='" + PdQteStock + '\'' +
                ", PdGuichet='" + PdGuichet + '\'' +
                ", PdStockMin='" + PdStockMin + '\'' +
                ", PdUserCree='" + PdUserCree + '\'' +
                ", PdIsOn='" + PdIsOn + '\'' +
                '}';
    }
}
