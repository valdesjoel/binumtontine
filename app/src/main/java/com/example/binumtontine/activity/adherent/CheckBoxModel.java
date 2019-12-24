package com.example.binumtontine.activity.adherent;

/**
 * Created by Valdès on 29/11/19.
 */
public class CheckBoxModel {

    private boolean isSelected;
    private String animal;
    private String pieceID;

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getPieceID() {
        return pieceID;
    }

    public void setPieceID(String pieceID) {
        this.pieceID = pieceID;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
