package com.example.binumtontine.activity.adherent;

/**
 * Created by Valdès FOTSO on 16-Dec-19.
 */
public class EditModel {

    private String editTextValue;
    private String ET_nbre_part_socialeValue;
    private String fraisID;
    private String fraisLibelle;
    private String fraisFonction;

    public String getEditTextValue() {
        return editTextValue;
    }
    public String getET_nbre_part_socialeValue() {
        return ET_nbre_part_socialeValue;
    }

    public void setEditTextValue(String editTextValue) {
        this.editTextValue = editTextValue;
    }
    public void setET_nbre_part_socialeValue(String ET_nbre_part_socialeValue) {
        this.ET_nbre_part_socialeValue =ET_nbre_part_socialeValue;
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

}
