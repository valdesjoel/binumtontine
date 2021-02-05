package com.example.binumtontine.activity.adherent;

import java.io.Serializable;

public class ComptesAdherent implements Serializable {


    //for manage accounts's adherent
    private int numero_compte;
    private String libelle_produit;
    private String numero_dossier;
    private String dateHCree;
    private String montant_solde;
    private String type_compte;
    private String taux_compte;
    private String nomAffilie;
    private String montantAccorde;
    private String dateFin;
    private String nbUnites;
    private String period;


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

    public int getNumero_compte() {
        return numero_compte;
    }

    public void setNumero_compte(int numero_compte) {
        this.numero_compte = numero_compte;
    }

    public String getType_compte() {
        return type_compte;
    }

    public void setType_compte(String type_compte) {
        this.type_compte = type_compte;
    }

    public String getTaux_compte() {
        return taux_compte;
    }

    public void setTaux_compte(String taux_compte) {
        this.taux_compte = taux_compte;
    }

    public String getNomAffilie() {
        return nomAffilie;
    }

    public void setNomAffilie(String nomAffilie) {
        this.nomAffilie = nomAffilie;
    }

    public String getMontantAccorde() {
        return montantAccorde;
    }

    public void setMontantAccorde(String montantAccorde) {
        this.montantAccorde = montantAccorde;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getNbUnites() {
        return nbUnites;
    }

    public void setNbUnites(String nbUnites) {
        this.nbUnites = nbUnites;
    }

    public String getPeriod() {
        if (this.period.equals("J")){
            return "Jours";
        }else if (this.period.equals("M")){
            return "Mois";
        }else if (this.period.equals("T")){
            return "Trimestres";
        }else if (this.period.equals("S")){
            return "Semestres";
        }else {
            return period;
        }

    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public ComptesAdherent() {
    }

    public ComptesAdherent(String libelle_produit, String numero_dossier, String dateHCree, String montant_solde) {
        this.libelle_produit = libelle_produit;
        this.numero_dossier = numero_dossier;
        this.dateHCree = dateHCree;
        this.montant_solde = montant_solde;
    }

    public ComptesAdherent(int numero_compte, String libelle_produit, String numero_dossier,
                           String dateHCree, String montant_solde, String type_compte, String taux_compte) {
        this.numero_compte = numero_compte;
        this.libelle_produit = libelle_produit;
        this.numero_dossier = numero_dossier;
        this.dateHCree = dateHCree;
        this.montant_solde = montant_solde;
        this.type_compte = type_compte;
        this.taux_compte = taux_compte;
    }
    public ComptesAdherent(int numero_compte, String libelle_produit, String numero_dossier,
                           String dateHCree, String montant_solde, String type_compte,
                           String taux_compte, String nomAffilie, String montantAccorde) {
        this.numero_compte = numero_compte;
        this.libelle_produit = libelle_produit;
        this.numero_dossier = numero_dossier;
        this.dateHCree = dateHCree;
        this.montant_solde = montant_solde;
        this.type_compte = type_compte;
        this.taux_compte = taux_compte;
        this.nomAffilie = nomAffilie;
        this.montantAccorde = montantAccorde;
    }
}
