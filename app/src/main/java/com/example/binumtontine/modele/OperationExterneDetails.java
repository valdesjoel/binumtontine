package com.example.binumtontine.modele;

public class OperationExterneDetails {
    public static final String KEY_OdNumero = "OdNumero";
    public static final String KEY_OdOperExterne = "OdOperExterne";
    public static final String KEY_OdLibelle = "OdLibelle";
    public static final String KEY_OdMontant = "OdMontant";
    public static final String KEY_OdSensOper = "OdSensOper";
    public static final String KEY_OdUserCree = "OdUserCree";
    public static final String KEY_OdDatHCree = "OdDatHCree";
    public static final String KEY_OdStatut = "OdStatut";
    public static final String KEY_OdGuichet = "OdGuichet";

    private int OdNumero;
    private int OdOperExterne;
    private String OdLibelle;
    private String OdMontant;
    private String OdSensOper;
    private String OdUserCree;
    private String OdDatHCree;
    private String OdStatut;
    private String OdGuichet;

    public OperationExterneDetails() {

    }

    public int getOdNumero() {
        return OdNumero;
    }

    public void setOdNumero(int odNumero) {
        OdNumero = odNumero;
    }

    public int getOdOperExterne() {
        return OdOperExterne;
    }

    public void setOdOperExterne(int odOperExterne) {
        OdOperExterne = odOperExterne;
    }

    public String getOdLibelle() {
        return OdLibelle;
    }

    public void setOdLibelle(String odLibelle) {
        OdLibelle = odLibelle;
    }

    public String getOdMontant() {
        return OdMontant;
    }

    public void setOdMontant(String odMontant) {
        OdMontant = odMontant;
    }

    public String getOdSensOper() {
        return OdSensOper;
    }

    public void setOdSensOper(String odSensOper) {
        OdSensOper = odSensOper;
    }

    public String getOdUserCree() {
        return OdUserCree;
    }

    public void setOdUserCree(String odUserCree) {
        OdUserCree = odUserCree;
    }

    public String getOdDatHCree() {
        return OdDatHCree;
    }

    public void setOdDatHCree(String odDatHCree) {
        OdDatHCree = odDatHCree;
    }

    public String getOdStatut() {
        return OdStatut;
    }

    public void setOdStatut(String odStatut) {
        OdStatut = odStatut;
    }

    public String getOdGuichet() {
        return OdGuichet;
    }

    public void setOdGuichet(String odGuichet) {
        OdGuichet = odGuichet;
    }

    @Override
    public String toString() {
        return "OperationExterneDetails{" +
                "OdNumero=" + OdNumero +
                ", OdOperExterne=" + OdOperExterne +
                ", OdLibelle='" + OdLibelle + '\'' +
                ", OdMontant='" + OdMontant + '\'' +
                ", OdSensOper='" + OdSensOper + '\'' +
                ", OdUserCree='" + OdUserCree + '\'' +
                ", OdDatHCree='" + OdDatHCree + '\'' +
                ", OdStatut='" + OdStatut + '\'' +
                ", OdGuichet='" + OdGuichet + '\'' +
                '}';
    }
}
