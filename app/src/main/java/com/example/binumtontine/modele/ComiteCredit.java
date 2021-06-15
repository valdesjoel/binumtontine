package com.example.binumtontine.modele;

public class ComiteCredit {
    public static final String KEY_CmNumero = "CmNumero";
    public static final String KEY_CmNom = "CmNom";
    public static final String KEY_CmType = "CmType";
    public static final String KEY_CmNbMembre = "CmNbMembre";
    public static final String KEY_CmCaisse = "CmCaisse";
    public static final String KEY_CmGuichet = "CmGuichet";
    public static final String KEY_CmDateHCree = "CmDateHCree";
    public static final String KEY_CmUserCree = "CmUserCree";
    public static final String KEY_CmIsOn = "CmIsOn";
    private String CmNumero; //champ clé
    private String CmNom; //VARCHAR(50) Nom ou appellation du Comité de Crédit
    private String CmType; //ENUM('CA','DI', 'AG', 'IN') NOT NULL COMMENT 'Type du Comité de Crédit, qui sera
    private int CmNbMembre; //pour le nombre de membres du Comité de Crédit
    private int CmCaisse; //pour le champ clé de la caisse concernée' REFERENCES caisse (cx_numero)
    private int CmGuichet; //pour le champ clé du guichet concerné, pour ceux de l_agence' REFERENCES guichet (gx_numero)
    private String CmDateHCree; //timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'pour la date et heure de création du Comité'
    private int CmUserCree; //pour la clé de l_utilisateur l_ayant créé' REFERENCES user (ux_numero),
    private String CmIsOn; //ENUM('Y','N') NOT NULL COMMENT 'pour indiquer si ce comité de Crédit est actif ou non',
    private String CmDateHOff; //timestamp NULL COMMENT 'pour la date et heure de sa désactivation' ,
    private int CmUserOff; //pour la clé de l_utilisateur l_ayant désactivé' REFERENCES user (ux_numero),

    public ComiteCredit() {
    }

    public String getCmNumero() {
        return CmNumero;
    }

    public void setCmNumero(String cmNumero) {
        CmNumero = cmNumero;
    }

    public String getCmNom() {
        return CmNom;
    }

    public void setCmNom(String cmNom) {
        CmNom = cmNom;
    }

    public String getCmType() {
        return CmType;
    }

    public void setCmType(String cmType) {
        CmType = cmType;
    }

    public int getCmNbMembre() {
        return CmNbMembre;
    }

    public void setCmNbMembre(int cmNbMembre) {
        CmNbMembre = cmNbMembre;
    }

    public int getCmCaisse() {
        return CmCaisse;
    }

    public void setCmCaisse(int cmCaisse) {
        CmCaisse = cmCaisse;
    }

    public int getCmGuichet() {
        return CmGuichet;
    }

    public void setCmGuichet(int cmGuichet) {
        CmGuichet = cmGuichet;
    }

    public String getCmDateHCree() {
        return CmDateHCree;
    }

    public void setCmDateHCree(String cmDateHCree) {
        CmDateHCree = cmDateHCree;
    }

    public int getCmUserCree() {
        return CmUserCree;
    }

    public void setCmUserCree(int cmUserCree) {
        CmUserCree = cmUserCree;
    }

    public String getCmIsOn() {
        return CmIsOn;
    }

    public void setCmIsOn(String cmIsOn) {
        CmIsOn = cmIsOn;
    }

    public String getCmDateHOff() {
        return CmDateHOff;
    }

    public void setCmDateHOff(String cmDateHOff) {
        CmDateHOff = cmDateHOff;
    }

    public int getCmUserOff() {
        return CmUserOff;
    }

    public void setCmUserOff(int cmUserOff) {
        CmUserOff = cmUserOff;
    }

    @Override
    public String toString() {
        return "ComiteCredit{" +
                "CmNumero='" + CmNumero + '\'' +
                ", CmNom='" + CmNom + '\'' +
                ", CmType='" + CmType + '\'' +
                ", CmNbMembre=" + CmNbMembre +
                ", CmCaisse=" + CmCaisse +
                ", CmGuichet=" + CmGuichet +
                ", CmDateHCree='" + CmDateHCree + '\'' +
                ", CmUserCree=" + CmUserCree +
                ", CmIsOn='" + CmIsOn + '\'' +
                ", CmDateHOff='" + CmDateHOff + '\'' +
                ", CmUserOff=" + CmUserOff +
                '}';
    }
}
