package com.example.binumtontine.modele;

/**
 *  A supprimer car inutilisée
 */
public class InscripAdherentFrais {

    private String libelleFrais;
    private String montantFrais;
    private String nombrePart;

    public InscripAdherentFrais() {
    }

    public String getLibelleFrais() {
        return libelleFrais;
    }

    public void setLibelleFrais(String libelleFrais) {
        this.libelleFrais = libelleFrais;
    }

    public String getMontantFrais() {
        return montantFrais;
    }

    public void setMontantFrais(String montantFrais) {
        this.montantFrais = montantFrais;
    }

    public String getNombrePart() {
        return nombrePart;
    }

    public void setNombrePart(String nombrePart) {
        this.nombrePart = nombrePart;
    }
}
