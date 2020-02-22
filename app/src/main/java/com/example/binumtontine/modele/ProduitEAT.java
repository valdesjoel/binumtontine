package com.example.binumtontine.modele;

public class ProduitEAT {
    //Fields's class
    private int EtNumero;
    private String EtLibelle;
    private String EtValTxInter;
    private String EtMtMinMise;
    private String EtMtMaxMise;

    //Constructor


    /**
     * Constructor use when create adherent when to open new account EAT
     * @param etNumero ID of produit EAAT
     * @param etLibelle Libelle produit EAT
     * @param etMtMinMise Montant minimum for subscribe to new EAT
     * @param etMtMaxMise Montant maximum for subscribe to new EAT
     */
    public ProduitEAT(int etNumero, String etLibelle,String etValTxInter, String etMtMinMise, String etMtMaxMise) {
        EtNumero = etNumero;
        EtLibelle = etLibelle;
        EtValTxInter = etValTxInter;
        EtMtMinMise = etMtMinMise;
        EtMtMaxMise = etMtMaxMise;
    }

    //Getters and Setters


    public int getEtNumero() {
        return EtNumero;
    }


    public String getEtLibelle() {
        return EtLibelle;
    }

    public void setEtLibelle(String etLibelle) {
        EtLibelle = etLibelle;
    }

    public String getEtValTxInter() {
        return EtValTxInter;
    }

    public void setEtValTxInter(String etValTxInter) {
        EtValTxInter = etValTxInter;
    }

    public String getEtMtMinMise() {
        return EtMtMinMise;
    }

    public void setEtMtMinMise(String etMtMinMise) {
        EtMtMinMise = etMtMinMise;
    }

    public String getEtMtMaxMise() {
        return EtMtMaxMise;
    }

    public void setEtMtMaxMise(String etMtMaxMise) {
        EtMtMaxMise = etMtMaxMise;
    }
}
