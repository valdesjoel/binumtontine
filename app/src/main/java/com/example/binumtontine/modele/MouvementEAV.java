package com.example.binumtontine.modele;

public class MouvementEAV {
    //Attributs
    private String MvDateDeValeur;
    private String MvMontant;
    private String MvSensMvm;
    private String MvSoldeMvm;

    /**
     * Constructor of MouvementEAV class
     * @param mvDateDeValeur
     * @param mvMontant
     * @param mvSensMvm
     */
    public MouvementEAV(String mvSensMvm, String mvDateDeValeur, String mvMontant,  String mvSoldeMvm) {
        MvDateDeValeur = mvDateDeValeur;
        MvMontant = mvMontant;
        MvSensMvm = mvSensMvm;
        MvSoldeMvm = mvSoldeMvm;
    }

    //Getters and setters
    public String getMvDateDeValeur() {
        return MvDateDeValeur;
    }

    public void setMvDateDeValeur(String mvDateDeValeur) {
        MvDateDeValeur = mvDateDeValeur;
    }

    public String getMvMontant() {
        return MvMontant;
    }

    public void setMvMontant(String mvMontant) {
        MvMontant = mvMontant;
    }

    public String getMvSensMvm() {
        return MvSensMvm;
    }

    public void setMvSensMvm(String mvSensMvm) {
        MvSensMvm = mvSensMvm;
    }

    public String getMvSoldeMvm() {
        return MvSoldeMvm;
    }

    public void setMvSoldeMvm(String mvSoldeMvm) {
        MvSoldeMvm = mvSoldeMvm;
    }
}
