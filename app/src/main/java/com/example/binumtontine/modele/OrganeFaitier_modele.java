package com.example.binumtontine.modele;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Valdes
 */
public class OrganeFaitier_modele implements Serializable {

    //propriétés
    private Integer numero;
    private String sigle;
    private String libelle;
    private String num_agrement;
    private String date_agrement;
    private String OfNumAgremCobac;
    private String OfDatAgremCobac;
    private String OfNumAgremCNC;
    private String OfDatAgremCNC;
    private Integer boite_postale;
    private String ville_of;
    private String pays_of;
    private String adresse_of;
    private String telephone1_of;
    private String telephone2_of;
    private String telephone3_of;
    private String siege_of;
    private String nom_pca_of;
    private String nom_vpca_of;
    private String nom_dg_of;

    public OrganeFaitier_modele(String sigle, String libelle, String num_agrement,
                                String date_agrement, String OfNumAgremCobac, String OfDatAgremCobac,String OfNumAgremCNC,String OfDatAgremCNC, Integer boite_postale, String ville_of,
                                String pays_of, String adresse_of, String telephone1_of,String telephone2_of,String telephone3_of,
                                String siege_of, String nom_pca_of, String nom_vpca_of, String nom_dg_of) {
        this.sigle = sigle;
        this.libelle = libelle;
        this.num_agrement = num_agrement;
        this.date_agrement = date_agrement;

        this.OfNumAgremCobac = OfNumAgremCobac;
        this.OfDatAgremCobac = OfDatAgremCobac;
        this.OfNumAgremCNC = OfNumAgremCNC;
        this.OfDatAgremCNC = OfDatAgremCNC;

        this.boite_postale = boite_postale;
        this.ville_of = ville_of;
        this.pays_of = pays_of;
        this.adresse_of = adresse_of;
        this.telephone1_of = telephone1_of;
        this.telephone2_of = telephone2_of;
        this.telephone3_of = telephone3_of;
        this.siege_of = siege_of;
        this.nom_pca_of = nom_pca_of;
        this.nom_vpca_of = nom_vpca_of;
        this.nom_dg_of = nom_dg_of;
    }

    public OrganeFaitier_modele(Integer numero_of, String sigle, String libelle, String num_agrement,
                                String date_agrement, String OfNumAgremCobac, String OfDatAgremCobac,String OfNumAgremCNC,String OfDatAgremCNC, Integer boite_postale, String ville_of,
                                String pays_of, String adresse_of, String telephone1_of,String telephone2_of,String telephone3_of,
                                String siege_of, String nom_pca_of, String nom_vpca_of, String nom_dg_of) {
        this.numero = numero_of;
        this.sigle = sigle;
        this.libelle = libelle;
        this.num_agrement = num_agrement;
        this.date_agrement = date_agrement;

        this.OfNumAgremCobac = OfNumAgremCobac;
        this.OfDatAgremCobac = OfDatAgremCobac;
        this.OfNumAgremCNC = OfNumAgremCNC;
        this.OfDatAgremCNC = OfDatAgremCNC;

        this.boite_postale = boite_postale;
        this.ville_of = ville_of;
        this.pays_of = pays_of;
        this.adresse_of = adresse_of;
        this.telephone1_of = telephone1_of;
        this.telephone2_of = telephone2_of;
        this.telephone3_of = telephone3_of;
        this.siege_of = siege_of;
        this.nom_pca_of = nom_pca_of;
        this.nom_vpca_of = nom_vpca_of;
        this.nom_dg_of = nom_dg_of;
    }

    public Integer getNumero() {
        return numero;
    }
    public String getSigle() {
        return sigle;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getNum_agrement() {
        return num_agrement;
    }

    public String getDate_agrement() {
        return date_agrement;
    }

    public String getOfNumAgremCobac() {
        return OfNumAgremCobac;
    }

    public String getOfDatAgremCobac() {
        return OfDatAgremCobac;
    }

    public String getOfNumAgremCNC() {
        return OfNumAgremCNC;
    }

    public String getOfDatAgremCNC() {
        return OfDatAgremCNC;
    }

    public Integer getBoite_postale() {
        return boite_postale;
    }


    public String getVille_of() {
        return ville_of;
    }

    public String getPays_of() {
        return pays_of;
    }

    public String getAdresse_of() {
        return adresse_of;
    }

    public String getTelephone1_of() {
        return telephone1_of;
    }

    public String getTelephone2_of() {
        return telephone2_of;
    }

    public String getTelephone3_of() {
        return telephone3_of;
    }

    public String getSiege_of() {
        return siege_of;
    }

    public String getNom_pca_of() {
        return nom_pca_of;
    }

    public String getNom_vpca_of() {
        return nom_vpca_of;
    }

    public String getNom_dg_of() {
        return nom_dg_of;
    }

    /**
     * conversion d''un organe faitier au format JSONArray
     * @return
     */
    public JSONArray convertTOJSONArray(){
        List laListe = new ArrayList();



        laListe.add(sigle);
        laListe.add(libelle);
        laListe.add(num_agrement);
        laListe.add(date_agrement);
        laListe.add(boite_postale);
        laListe.add(ville_of);
        laListe.add(pays_of);
        laListe.add(adresse_of);
        laListe.add(telephone1_of);
        laListe.add(telephone2_of);
        laListe.add(telephone3_of);
        laListe.add(siege_of);
        laListe.add(nom_pca_of);
        laListe.add(nom_vpca_of);
        laListe.add(nom_dg_of);
        laListe.add(numero);

        laListe.add(OfNumAgremCobac);
        laListe.add(OfDatAgremCobac);
        laListe.add(OfNumAgremCNC);
        laListe.add(OfDatAgremCNC);

        return new JSONArray(laListe) ;

    }
}
