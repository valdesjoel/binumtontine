package com.example.binumtontine.activity.adherent;

import java.io.Serializable;

/**
 * Create a class file named Adherent.java
 * This model class will be useful to convert json data into objects.
 */
public class Adherent implements Serializable {

    private String AdNumero;
    private String AdCode;
    private String AdNumManuel;
    private String AdNom;
    private String AdPrenom;
    private String AdDateNaiss;
    private String AdLieuNaiss;
    private String AdSexe;
    private String AdNationalite;
    private String AdSitFam;
    private String AdNbreEnfACh;
    private String AdTel1;
    private String AdTel2;
    private String AdTel3;
    private String AdEMail;
    private String AdProfess;
    private String AdDomicile;
    private String AdLieuTrav;
    private String AdActivitePr;
    private String AdTypCarteID;
    private String AdNumCarteID;
    private String AdValideDu;
    private String AdValideAu;
    private String AdTypHabite;
    private String AdEstParti;
    private String AdPartiLe;
    private String AdRemplacePar;
    private int AdGuichet;
    private String  AdNbreCompte;

    //for manage accounts's adherent
    private String libelle_produit="";
    private String numero_dossier="";
    private String dateHCree="";
    private String montant_solde="";


    public String getLibelleProduit() {
        return libelle_produit;
    }

    public void setLibelleProduit(String libelle_produit) {
        this.libelle_produit = libelle_produit;
    }

    public String getNumeroDossier() {
        return numero_dossier;
    }

    public void setNumeroDossier(String numero_dossier) {
        this.numero_dossier = numero_dossier;
    }

    public String getDateHCree() {
        return dateHCree;
    }

    public void setDateHCree(String dateHCree) {
        this.dateHCree = dateHCree;
    }

    public String getMontantSolde() {
        return montant_solde;
    }

    public void setMontantSolde(String montant_solde) {
        this.montant_solde = montant_solde;
    }

    public Adherent(){}

    public Adherent(String adNumero, String adCode, String adNumManuel, String adNom, String adPrenom,
                    String adDateNaiss, String adLieuNaiss, String adSexe, String adNationalite,
                    String adSitFam, String adNbreEnfACh, String adTel1, String adTel2, String adTel3,
                    String adEMail, String adProfess, String adDomicile, String adLieuTrav,
                    String adActivitePr, String adTypCarteID, String adNumCarteID,
                    String adValideDu, String adValideAu, String adTypHabite, String adEstParti,
                    String adPartiLe, String adRemplacePar, int adGuichet) {
        this.AdNumero = adNumero;
        this.AdCode = adCode;
        this.AdNumManuel = adNumManuel;
        this.AdNom = adNom;
        this.AdPrenom = adPrenom;
        this.AdDateNaiss = adDateNaiss;
        this.AdLieuNaiss = adLieuNaiss;
        this.AdSexe = adSexe;
        this.AdNationalite = adNationalite;
        this.AdSitFam = adSitFam;
        this.AdNbreEnfACh = adNbreEnfACh;
        this.AdTel1 = adTel1;
        this.AdTel2 = adTel2;
        this.AdTel3 = adTel3;
        this.AdEMail = adEMail;
        this.AdProfess = adProfess;
        this.AdDomicile = adDomicile;
        this.AdLieuTrav = adLieuTrav;
        this.AdActivitePr = adActivitePr;
        this.AdTypCarteID = adTypCarteID;
        this.AdNumCarteID = adNumCarteID;
        this.AdValideDu = adValideDu;
        this.AdValideAu = adValideAu;
        this.AdTypHabite = adTypHabite;
        this.AdEstParti = adEstParti;
        this.AdPartiLe = adPartiLe;
        this.AdRemplacePar = adRemplacePar;
        this.AdGuichet = adGuichet;
    }
    public Adherent(String adCode, String adNumManuel, String adNom, String adPrenom,
                    String adDateNaiss, String adLieuNaiss, String adSexe, String adNationalite,
                    String adSitFam, String adNbreEnfACh, String adTel1, String adTel2, String adTel3,
                    String adEMail, String adProfess, String adDomicile, String adLieuTrav,
                    String adActivitePr, String adTypCarteID, String adNumCarteID,
                    String adValideDu, String adValideAu, String adTypHabite, String adEstParti,
                    String adPartiLe, String adRemplacePar, int adGuichet) {
        this.AdCode = adCode;
        this.AdNumManuel = adNumManuel;
        this.AdNom = adNom;
        this.AdPrenom = adPrenom;
        this.AdDateNaiss = adDateNaiss;
        this.AdLieuNaiss = adLieuNaiss;
        this.AdSexe = adSexe;
        this.AdNationalite = adNationalite;
        this.AdSitFam = adSitFam;
        this.AdNbreEnfACh = adNbreEnfACh;
        this.AdTel1 = adTel1;
        this.AdTel2 = adTel2;
        this.AdTel3 = adTel3;
        this.AdEMail = adEMail;
        this.AdProfess = adProfess;
        this.AdDomicile = adDomicile;
        this.AdLieuTrav = adLieuTrav;
        this.AdActivitePr = adActivitePr;
        this.AdTypCarteID = adTypCarteID;
        this.AdNumCarteID = adNumCarteID;
        this.AdValideDu = adValideDu;
        this.AdValideAu = adValideAu;
        this.AdTypHabite = adTypHabite;
        this.AdEstParti = adEstParti;
        this.AdPartiLe = adPartiLe;
        this.AdRemplacePar = adRemplacePar;
        this.AdGuichet = adGuichet;
    }

    public Adherent(String adNumero, String adCode, String adNumManuel, String adNom, String adPrenom,
                    String adDateNaiss, String adLieuNaiss, String adSexe, String adNationalite,
                    String adSitFam, String adNbreEnfACh, String adTel1, String adTel2, String adTel3,
                    String adEMail, String adProfess, String adDomicile, String adLieuTrav,
                    String adActivitePr, String adTypCarteID, String adNumCarteID,
                    String adValideDu, String adValideAu, String adTypHabite, String adEstParti,
                    String adPartiLe, String adRemplacePar, int adGuichet,String  adNbreCompte) {
        this.AdNumero = adNumero;
        this.AdCode = adCode;
        this.AdNumManuel = adNumManuel;
        this.AdNom = adNom;
        this.AdPrenom = adPrenom;
        this.AdDateNaiss = adDateNaiss;
        this.AdLieuNaiss = adLieuNaiss;
        this.AdSexe = adSexe;
        this.AdNationalite = adNationalite;
        this.AdSitFam = adSitFam;
        this.AdNbreEnfACh = adNbreEnfACh;
        this.AdTel1 = adTel1;
        this.AdTel2 = adTel2;
        this.AdTel3 = adTel3;
        this.AdEMail = adEMail;
        this.AdProfess = adProfess;
        this.AdDomicile = adDomicile;
        this.AdLieuTrav = adLieuTrav;
        this.AdActivitePr = adActivitePr;
        this.AdTypCarteID = adTypCarteID;
        this.AdNumCarteID = adNumCarteID;
        this.AdValideDu = adValideDu;
        this.AdValideAu = adValideAu;
        this.AdTypHabite = adTypHabite;
        this.AdEstParti = adEstParti;
        this.AdPartiLe = adPartiLe;
        this.AdRemplacePar = adRemplacePar;
        this.AdGuichet = adGuichet;
        this.AdNbreCompte = adNbreCompte;
    }

    public String getAdNumero() {
        return this.AdNumero;
    }
/*
    public void setAdNumero(int adNumero) {
        AdNumero = adNumero;
    }
    */

    public String getAdCode() {
        return this.AdCode;
    }

    public void setAdCode(String adCode) {
        this.AdCode = adCode;
    }

    public String getAdNumManuel() {
        return this.AdNumManuel;
    }

    public void setAdNumManuel(String adNumManuel) {
        this.AdNumManuel = adNumManuel;
    }

    public String getAdNom() {
        return this.AdNom;
    }

    public void setAdNom(String adNom) {
        this.AdNom = adNom;
    }

    public String getAdPrenom() {
        return this.AdPrenom;
    }

    public void setAdPrenom(String adPrenom) {
        this.AdPrenom = adPrenom;
    }

    public String getAdDateNaiss() {
        return this.AdDateNaiss;
    }

    public void setAdDateNaiss(String adDateNaiss) {
        this.AdDateNaiss = adDateNaiss;
    }

    public String getAdLieuNaiss() {
        return this.AdLieuNaiss;
    }

    public void setAdLieuNaiss(String adLieuNaiss) {
        this.AdLieuNaiss = adLieuNaiss;
    }

    public String getAdSexe() {
        return this.AdSexe;
    }

    public void setAdSexe(String adSexe) {
        this.AdSexe = adSexe;
    }

    public String getAdNationalite() {
        return this.AdNationalite;
    }

    public void setAdNationalite(String adNationalite) {
        this.AdNationalite = adNationalite;
    }

    public String getAdSitFam() {
        return this.AdSitFam;
    }

    public void setAdSitFam(String adSitFam) {
        this.AdSitFam = adSitFam;
    }

    public String getAdNbreEnfACh() {
        return this.AdNbreEnfACh;
    }

    public void setAdNbreEnfACh(String adNbreEnfACh) {
        this.AdNbreEnfACh = adNbreEnfACh;
    }

    public String getAdTel1() {
        return this.AdTel1;
    }

    public void setAdTel1(String adTel1) {
        this.AdTel1 = adTel1;
    }

    public String getAdTel2() {
        return this.AdTel2;
    }

    public void setAdTel2(String adTel2) {
        this.AdTel2 = adTel2;
    }

    public String getAdTel3() {
        return this.AdTel3;
    }

    public void setAdTel3(String adTel3) {
        this.AdTel3 = adTel3;
    }

    public String getAdEMail() {
        return this.AdEMail;
    }

    public void setAdEMail(String adEMail) {
        this.AdEMail = adEMail;
    }

    public String getAdProfess() {
        return this.AdProfess;
    }

    public void setAdProfess(String adProfess) {
        this.AdProfess = adProfess;
    }

    public String getAdDomicile() {
        return this.AdDomicile;
    }

    public void setAdDomicile(String adDomicile) {
        this.AdDomicile = adDomicile;
    }

    public String getAdLieuTrav() {
        return this.AdLieuTrav;
    }

    public void setAdLieuTrav(String adLieuTrav) {
        this.AdLieuTrav = adLieuTrav;
    }

    public String getAdActivitePr() {
        return this.AdActivitePr;
    }

    public void setAdActivitePr(String adActivitePr) {
        this.AdActivitePr = adActivitePr;
    }

    public String getAdTypCarteID() {
        return this.AdTypCarteID;
    }

    public void setAdTypCarteID(String adTypCarteID) {
        this.AdTypCarteID = adTypCarteID;
    }

    public String getAdNumCarteID() {
        return this.AdNumCarteID;
    }

    public void setAdNumCarteID(String adNumCarteID) {
        this.AdNumCarteID = adNumCarteID;
    }

    public String getAdValideDu() {
        return this.AdValideDu;
    }

    public void setAdValideDu(String adValideDu) {
        this.AdValideDu = adValideDu;
    }

    public String getAdValideAu() {
        return this.AdValideAu;
    }

    public void setAdValideAu(String adValideAu) {
        this.AdValideAu = adValideAu;
    }

    public String getAdTypHabite() {
        return this.AdTypHabite;
    }

    public void setAdTypHabite(String adTypHabite) {
        this.AdTypHabite = adTypHabite;
    }

    public String getAdEstParti() {
        return this.AdEstParti;
    }

    public void setAdEstParti(String adEstParti) {
        this.AdEstParti = adEstParti;
    }

    public String getAdPartiLe() {
        return this.AdPartiLe;
    }

    public void setAdPartiLe(String adPartiLe) {
        this.AdPartiLe = adPartiLe;
    }

    public String getAdRemplacePar() {
        return this.AdRemplacePar;
    }

    public void setAdRemplacePar(String adRemplacePar) {
        this.AdRemplacePar = adRemplacePar;
    }

    public int getAdGuichet() {
        return AdGuichet;
    }

    public void setAdGuichet(int adGuichet) {
        this.AdGuichet = adGuichet;
    }



    public String getAdNbreCompte() {
        return this.AdNbreCompte;
    }

    public void setAdNbreCompte(String adNbreCompte) {
        this.AdNbreCompte = adNbreCompte;
    }
}
