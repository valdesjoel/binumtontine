package com.example.binumtontine.modele;

public class SituationGuichetModel {
    private String jour_ouvre;
    private String caisse;
    private String guichet;
    private String montant_demarrage;
    private String depot;
    private String retrait;
    private String remboursement_credit;
    private String frais_inscription;
    private String commission;
    private String recuperation_credit;
    private String sortie_transfert;
    private String entree_transfert;
    private String cotisation_annuelle;
    private String charges;
    private String produits;

    public SituationGuichetModel() {
        this.jour_ouvre = "";
        this.caisse = "";
        this.guichet = "";
        this.montant_demarrage = "";
        this.depot = "";
        this.retrait = "";
        this.remboursement_credit = "";
        this.frais_inscription = "";
        this.commission = "";
        this.recuperation_credit = "";
        this.sortie_transfert = "";
        this.entree_transfert = "";
        this.cotisation_annuelle = "";
        this.charges = "";
        this.produits = "";
    }

    public SituationGuichetModel(String jour_ouvre,
                                 String caisse,
                                 String guichet,
                                 String montant_demarrage,
                                 String depot,
                                 String retrait,
                                 String remboursement_credit,
                                 String frais_inscription,
                                 String commission,
                                 String recuperation_credit,
                                 String sortie_transfert,
                                 String entree_transfert,
                                 String cotisation_annuelle,
                                 String charges,
                                 String produits) {
        this.jour_ouvre = jour_ouvre;
        this.caisse = caisse;
        this.guichet = guichet;
        this.montant_demarrage = montant_demarrage;
        this.depot = depot;
        this.retrait = retrait;
        this.remboursement_credit = remboursement_credit;
        this.frais_inscription = frais_inscription;
        this.commission = commission;
        this.recuperation_credit = recuperation_credit;
        this.sortie_transfert = sortie_transfert;
        this.entree_transfert = entree_transfert;
        this.cotisation_annuelle = cotisation_annuelle;
        this.charges = charges;
        this.produits = produits;
    }

    public String getJour_ouvre() {
        return jour_ouvre;
    }

    public void setJour_ouvre(String jour_ouvre) {
        this.jour_ouvre = jour_ouvre;
    }

    public String getCaisse() {
        return caisse;
    }

    public void setCaisse(String caisse) {
        this.caisse = caisse;
    }

    public String getGuichet() {
        return guichet;
    }

    public void setGuichet(String guichet) {
        this.guichet = guichet;
    }

    public String getMontant_demarrage() {
        return montant_demarrage;
    }

    public void setMontant_demarrage(String montant_demarrage) {
        this.montant_demarrage = montant_demarrage;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getRetrait() {
        return retrait;
    }

    public void setRetrait(String retrait) {
        this.retrait = retrait;
    }

    public String getRemboursement_credit() {
        return remboursement_credit;
    }

    public void setRemboursement_credit(String remboursement_credit) {
        this.remboursement_credit = remboursement_credit;
    }

    public String getFrais_inscription() {
        return frais_inscription;
    }

    public void setFrais_inscription(String frais_inscription) {
        this.frais_inscription = frais_inscription;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getRecuperation_credit() {
        return recuperation_credit;
    }

    public void setRecuperation_credit(String recuperation_credit) {
        this.recuperation_credit = recuperation_credit;
    }

    public String getSortie_transfert() {
        return sortie_transfert;
    }

    public void setSortie_transfert(String sortie_transfert) {
        this.sortie_transfert = sortie_transfert;
    }

    public String getEntree_transfert() {
        return entree_transfert;
    }

    public void setEntree_transfert(String entree_transfert) {
        this.entree_transfert = entree_transfert;
    }

    public String getCotisation_annuelle() {
        return cotisation_annuelle;
    }

    public void setCotisation_annuelle(String cotisation_annuelle) {
        this.cotisation_annuelle = cotisation_annuelle;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getProduits() {
        return produits;
    }

    public void setProduits(String produits) {
        this.produits = produits;
    }
}
