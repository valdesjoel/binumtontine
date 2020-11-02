package com.example.binumtontine.activity.adherent;

public class Brouillard {
    public String heure;
    public String nomAdherent;
    public String libelle;
    public String compte;
    public String montantOperation;
    public String soldeCaisse;

    public Brouillard() {
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getNomAdherent() {
        return nomAdherent;
    }

    public void setNomAdherent(String nomAdherent) {
        this.nomAdherent = nomAdherent;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getMontantOperation() {
        return montantOperation;
    }

    public void setMontantOperation(String montantOperation) {
        this.montantOperation = montantOperation;
    }

    public String getSoldeCaisse() {
        return soldeCaisse;
    }

    public void setSoldeCaisse(String soldeCaisse) {
        this.soldeCaisse = soldeCaisse;
    }

    public Brouillard(String heure, String nomAdherent, String libelle, String compte, String montantOperation, String soldeCaisse) {
        this.heure = heure;
        this.nomAdherent = nomAdherent;
        this.libelle = libelle;
        this.compte = compte;
        this.montantOperation = montantOperation;
        this.soldeCaisse = soldeCaisse;



    }
}
