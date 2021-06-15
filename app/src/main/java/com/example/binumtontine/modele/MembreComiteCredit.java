package com.example.binumtontine.modele;

public class MembreComiteCredit {
    public static final String KEY_McNumero = "McNumero";
    public static final String KEY_McNom = "McNom";
    public static final String KEY_McPrenom = "McPrenom";
    public static final String KEY_McTypMembre = "McTypMembre";
    public static final String KEY_McComiteCredit = "McComiteCredit";
    public static final String KEY_McUser = "McUser";
    public static final String KEY_McDatHCree= "McDatHCree";
    public static final String KEY_McUserCree = "McUserCree";
    public static final String KEY_McIsOn = "McIsOn";
    public static final String KEY_McDatHOff = "McDatHOff";
    public static final String KEY_McUserOff = "McUserOff";

    private String McNumero; //champ clé
    private String McNom; //McNom VARCHAR(40) NOT NULL COMMENT 'Nom du membre'
    private String McPrenom; //McPrenom VARCHAR(40) NULL COMMENT 'Prénom du membre',
    private String McTypMembre; //McTypMembre ENUM('PR','ME', 'GC')  'Type du membre du Comité de Crédit, qui sera :
    //PR: Président; ME:  Membre; GC: Gestionnaire de compte',
    private int McComiteCredit; //McComiteCredit INT(11)  NULL COMMENT 'pour le champ clé du comité de crédit concerné' REFERENCES comite_credit (CmNumero)
    private int McUser; //pour le sélectionner au besoin dans les utilisateurs de la caisse, quand le CC est de type Conseil d_Administration, ou de Direction, sinon dans les utilisateurs du Guichet quand le CC est de type Agence, cf. Table Comité de Crédit.
   // Il est obligatoire de le sélectionner, seulement pour les types "Membre", qu_il n_est pas obligatoire'
    private String McDatHCree; //McDatHCree timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'pour la date et heure de création du membre',
    private int McUserCree; //McUserCree INT(11)  NULL  COMMENT 'pour la clé de l_utilisateur l_ayant créé' REFERENCES user (ux_numero),
    private String McIsOn; //McIsOn ENUM('Y','N') NOT NULL COMMENT 'pour indiquer si ce membre est actif ou non',
    private String McDatHOff; //McDatHOff timestamp NULL COMMENT 'pour la date et heure de sa désactivation' ,
    private int McUserOff; //McUserOff INT(11)   NULL  COMMENT 'pour la clé de l_utilisateur l_ayant désactivé' REFERENCES user (ux_numero)

    public MembreComiteCredit() {
    }

    public String getMcNumero() {
        return McNumero;
    }

    public void setMcNumero(String mcNumero) {
        McNumero = mcNumero;
    }

    public String getMcNom() {
        return McNom;
    }

    public void setMcNom(String mcNom) {
        McNom = mcNom;
    }

    public String getMcPrenom() {
        return McPrenom;
    }

    public void setMcPrenom(String mcPrenom) {
        McPrenom = mcPrenom;
    }

    public String getMcTypMembre() {
        return McTypMembre;
    }

    public void setMcTypMembre(String mcTypMembre) {
        McTypMembre = mcTypMembre;
    }

    public int getMcComiteCredit() {
        return McComiteCredit;
    }

    public void setMcComiteCredit(int mcComiteCredit) {
        McComiteCredit = mcComiteCredit;
    }

    public int getMcUser() {
        return McUser;
    }

    public void setMcUser(int mcUser) {
        McUser = mcUser;
    }

    public String getMcDatHCree() {
        return McDatHCree;
    }

    public void setMcDatHCree(String mcDatHCree) {
        McDatHCree = mcDatHCree;
    }

    public int getMcUserCree() {
        return McUserCree;
    }

    public void setMcUserCree(int mcUserCree) {
        McUserCree = mcUserCree;
    }

    public String getMcIsOn() {
        return McIsOn;
    }

    public void setMcIsOn(String mcIsOn) {
        McIsOn = mcIsOn;
    }

    public String getMcDatHOff() {
        return McDatHOff;
    }

    public void setMcDatHOff(String mcDatHOff) {
        McDatHOff = mcDatHOff;
    }

    public int getMcUserOff() {
        return McUserOff;
    }

    public void setMcUserOff(int mcUserOff) {
        McUserOff = mcUserOff;
    }
    public String getMcNomAndMcPrenom(){

        return this.McNom.concat(" ").concat(this.McPrenom);
    }
    public static String decodeMcTypMembre(String encode){
        String decode ="";
        if (encode==null){
            return decode;
        }
        //McTypMembre ENUM('PR','ME', 'GC')  'Type du membre du Comité de Crédit, qui sera :
        //PR: Président; ME:  Membre; GC: Gestionnaire de compte',
        if (encode.equals("PR")){
            decode = "Président";
        }else if (encode.equals("ME")){
            decode = "Membre";
        }else if (encode.equals("GC")){
            decode = "Gestionnaire de compte";
        }

        return decode;
    }
}
