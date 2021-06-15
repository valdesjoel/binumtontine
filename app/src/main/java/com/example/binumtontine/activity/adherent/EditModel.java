package com.example.binumtontine.activity.adherent;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valdès FOTSO on 16-Dec-19.
 */
public class EditModel {

    private String fraisID;
    private String fcValeur;
    private String fcNbrePartMinJ;
    private String fcNbrePartMinF;
    private String fcNbrePartMinH;
    private String fraisLibelle;
    private String fraisFonction;
    private String fraisNature;
    private String fraisBase;
    private String fcNbrePartSouhaiter="1";
    private Adherent adherent;




    public String getFcValeur() {
        return fcValeur;
    }
    public String getFcNbrePartMinH() {
        return fcNbrePartMinH;
    }

    public void setFcValeur(String fcValeur) {
        this.fcValeur = fcValeur;
    }
    public void setFcNbrePartMinH(String fcNbrePartMinH) {
        this.fcNbrePartMinH = fcNbrePartMinH;
    }

    public String getFcNbrePartMinJ() {
        return fcNbrePartMinJ;
    }

    public void setFcNbrePartMinJ(String fcNbrePartMinJ) {
        this.fcNbrePartMinJ = fcNbrePartMinJ;
    }

    public String getFcNbrePartMinF() {
        return fcNbrePartMinF;
    }

    public void setFcNbrePartMinF(String fcNbrePartMinF) {
        this.fcNbrePartMinF = fcNbrePartMinF;
    }

    public String getFraisID() {
        return fraisID;
    }

    public void setFraisID(String fraisID) {
        this.fraisID = fraisID;
    }

    public String getFraisLibelle() {
        return fraisLibelle;
    }

    public void setFraisLibelle(String fraisLibelle) {
        this.fraisLibelle = fraisLibelle;
    }

    public String getFraisFonction() {
        return fraisFonction;
    }

    public void setFraisFonction(String fraisFonction) {
        this.fraisFonction = fraisFonction;
    }

    public String getFraisNature() {
        return fraisNature;
    }

    public void setFraisNature(String fraisNature) {
        this.fraisNature = fraisNature;
    }

    public String getFraisBase() {
        return (fraisBase==null?"":fraisBase);
    }

    public void setFraisBase(String fraisBase) {
        this.fraisBase = fraisBase;
    }

    public String getFcNbrePartSouhaiter() {
        return fcNbrePartSouhaiter;
    }

    public void setFcNbrePartSouhaiter(String fcNbrePartSouhaiter) {
        this.fcNbrePartSouhaiter = fcNbrePartSouhaiter;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public String calculTotalPartSociales(){
        double total = 0.0;
        if (this.fcNbrePartSouhaiter ==null || this.fcNbrePartSouhaiter.equals("") ){
            this.fcNbrePartSouhaiter ="1";
        }
        try {
            total = Double.parseDouble(this.getFcNbrePartSocialeAdherentByAge()+"")*Double.parseDouble(this.fcValeur);
//            total = Double.parseDouble(this.getFcNbrePartSocialeAdherentByAge())*Double.parseDouble(this.fcValeur);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return total+"";
    }

    /**
     * Fonction permettant de déterminer le nombre de part sociale de l'adhérent concerné
     * @return le nombre de part sociale en fonction de l'âge de l'dhérent
     */
    public int getFcNbrePartSocialeAdherentByAge(){
        int nbrePartSociale = 1;
        if (this.getFraisFonction().equals("P")){
            if (this.adherent.getAge()<21){
                nbrePartSociale =  Integer.parseInt(this.getFcNbrePartMinJ());
            }else if (this.adherent.getAge()>=21 && this.adherent.getAdSexe().equals("F")){
                nbrePartSociale = Integer.parseInt(this.getFcNbrePartMinF());
            }else{ //sexe = M
                nbrePartSociale =  Integer.parseInt(this.getFcNbrePartMinH());
            }
        }
        return nbrePartSociale;
    }

    public void getMontantbase (ArrayList < EditModel> editModels){
            for(int i=0; i<editModels.size();i++){
                Log.e("Param fetch_all_frais_by_guichet: ", ">Avant " + this.toString());
                if (this.fraisNature.equals("T") && this.fraisBase.equals(editModels.get(i).fraisID)){
                    this.fcValeur = Double.parseDouble(editModels.get(i).fcNbrePartSouhaiter)*Double.parseDouble(this.fcValeur)+"";
                    Log.e("Param fetch_all_frais_by_guichet: ", ">if " + this.toString());
                    break;
                }else{
                    Log.e("Param fetch_all_frais_by_guichet: ", ">else " + this.toString());
                }
        }
    }
    public String  getMontantbaseValeur (ArrayList < EditModel> editModels){
        String valeur ="";
            for(EditModel editModel: editModels){
                if (this.fraisNature.equals("T") && this.fraisBase.equals(editModel.fraisID)){
                    this.fcValeur = Double.parseDouble(editModel.fcNbrePartSouhaiter)*Double.parseDouble(this.fcValeur)+"";
                    valeur= this.fcValeur;
                    break;
                }else{
                    valeur= this.fcValeur;
                }
        }
            return valeur;
    }

    @Override
    public String toString() {
        return "EditModel{" +
                "fraisID='" + fraisID + '\'' +
                ", fcValeur='" + fcValeur + '\'' +
                ", fcNbrePartMinJ='" + fcNbrePartMinJ + '\'' +
                ", fcNbrePartMinF='" + fcNbrePartMinF + '\'' +
                ", fcNbrePartMinH='" + fcNbrePartMinH + '\'' +
                ", fraisLibelle='" + fraisLibelle + '\'' +
                ", fraisFonction='" + fraisFonction + '\'' +
                ", fraisNature='" + fraisNature + '\'' +
                ", fraisBase='" + fraisBase + '\'' +
                ", fcNbrePartSouhaiter='" + fcNbrePartSouhaiter + '\'' +
                ", adherent=" + adherent +
                '}';
    }
}
