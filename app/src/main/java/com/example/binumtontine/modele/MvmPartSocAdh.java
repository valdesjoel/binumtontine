package com.example.binumtontine.modele;

import com.example.binumtontine.activity.adherent.Adherent;

public class MvmPartSocAdh {
    public static final String KEY_MpNumero = "MpNumero";
    public static final String KEY_MpAdherent = "MpAdherent";
    public static final String KEY_MpNbPartsSoc = "MpNbPartsSoc";
    public static final String KEY_MpMtPartsSoc = "MpMtPartsSoc";
    public static final String KEY_MpTypOper = "MpTypOper";
    public static final String KEY_MpNewNbPartsSoc = "MpNewNbPartsSoc";
    public static final String KEY_MpUser= "MpUser";
    public static final String KEY_MpDatHOper = "MpDatHOper";
    public static final String KEY_MpDetails = "MpDetails";
    public static final String KEY_MpModePaiement = "MpModePaiement";

    private int mpNumero; //PRIMARY KEY AUTO_INCREMENT
    private int mpAdherent; //Pour l_id du membre ou Adhérent concerné' REFERENCES adherent (AdNumero)
    private int mpNbPartsSoc; //'Nombre de parts sociales concernées par l_opération',
    private double mpMtPartsSoc; //Montant équivalent du nb des parts sociales
    private String mpTypOper; //ENUM('I','A','R','C') NOT NULL COMMENT 'Type d_opérations effectue par le mvm(Initialisation/I, Augmentation/A, Réduction/R, Cession/C)',
    private double MpNewNbPartsSoc; //Nouvelle valeur des Parts sociales du membre après opération.'
    private int mpUser; //Utilisateur ou Agent ayant effectué l_opération' REFERENCES user (ux_numero) ,
    private String MpDatHOper; //Date et heure à laquelle cette opération a été faite.',
    private String mpDetails; //Détails ou commentaires sur cette opération sur les Parts sociales'
    private String mpModePaiement; //Détails ou commentaires sur cette opération sur les Parts sociales'

    public int getMpNumero() {
        return mpNumero;
    }

    public int getMpAdherent() {
        return mpAdherent;
    }

    public void setMpAdherent(int mpAdherent) {
        this.mpAdherent = mpAdherent;
    }

    public int getMpNbPartsSoc() {
        return mpNbPartsSoc;
    }

    public void setMpNbPartsSoc(int mpNbPartsSoc) {
        this.mpNbPartsSoc = mpNbPartsSoc;
    }

    public double getMpMtPartsSoc() {
        return mpMtPartsSoc;
    }

    public void setMpMtPartsSoc(double mpMtPartsSoc) {
        this.mpMtPartsSoc = mpMtPartsSoc;
    }

    public String getMpTypOper() {
        return mpTypOper;
    }

    public void setMpTypOper(String mpTypOper) {
        this.mpTypOper = mpTypOper;
    }

    public double getMpNewNbPartsSoc() {
        return MpNewNbPartsSoc;
    }

    public void setMpNewNbPartsSoc(double mpNewNbPartsSoc) {
        MpNewNbPartsSoc = mpNewNbPartsSoc;
    }

    public int getMpUser() {
        return mpUser;
    }

    public void setMpUser(int mpUser) {
        this.mpUser = mpUser;
    }

    public String getMpDatHOper() {
        return MpDatHOper;
    }

    public void setMpDatHOper(String mpDatHOper) {
        MpDatHOper = mpDatHOper;
    }

    public String getMpDetails() {
        return mpDetails;
    }

    public void setMpDetails(String mpDetails) {
        this.mpDetails = mpDetails;
    }

    public String getMpModePaiement() {
        return mpModePaiement;
    }

    public void setMpModePaiement(String mpModePaiement) {
        this.mpModePaiement = mpModePaiement;
    }
}
