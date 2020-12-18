package com.example.binumtontine.modele;

public class ProduitEAT {
    //Fields's class
    private int EtNumero;
    private String EtLibelle;
    private String EtValTxInter;
    private String EtMtMinMise;
    private String EtMtMaxMise;

    //Constructor


    /**
     * Constructor use when create adherent when to open new account EAT
     * @param etNumero ID of produit EAAT
     * @param etLibelle Libelle produit EAT
     * @param etMtMinMise Montant minimum for subscribe to new EAT
     * @param etMtMaxMise Montant maximum for subscribe to new EAT
     */
    public ProduitEAT(int etNumero, String etLibelle,String etValTxInter, String etMtMinMise, String etMtMaxMise) {
        EtNumero = etNumero;
        EtLibelle = etLibelle;
        EtValTxInter = etValTxInter;
        EtMtMinMise = etMtMinMise;
        EtMtMaxMise = etMtMaxMise;
    }

    //Getters and Setters


    public int getEtNumero() {
        return EtNumero;
    }


    public String getEtLibelle() {
        return EtLibelle;
    }

    public void setEtLibelle(String etLibelle) {
        EtLibelle = etLibelle;
    }

    public String getEtValTxInter() {
        return EtValTxInter;
    }

    public void setEtValTxInter(String etValTxInter) {
        EtValTxInter = etValTxInter;
    }

    public String getEtMtMinMise() {
        return EtMtMinMise;
    }

    public void setEtMtMinMise(String etMtMinMise) {
        EtMtMinMise = etMtMinMise;
    }

    public String getEtMtMaxMise() {
        return EtMtMaxMise;
    }

    public void setEtMtMaxMise(String etMtMaxMise) {
        EtMtMaxMise = etMtMaxMise;
    }

    /**
     * Permet d'encoder les bases EtBaseTxInter sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEtBaseTxInter(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital déposé en début de période")){
            base_encode = "TI1";
        }else if (base_decode.equals("2- Solde du contrat de souscription")){
            base_encode = "TI2";
        }else if (base_decode.equals("3- Capital déposé + Intérêt")){
            base_encode = "TI3";
        }else if (base_decode.equals("4- Solde du compte")){
            base_encode = "TI4";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EtBaseTxInter de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEtBaseTxInter(String base_encode){
        String base_decode ="";
        if (base_encode.equals("TI1")){
            base_decode = "1- Capital déposé en début de période";
        }else if (base_encode.equals("TI2")){
            base_decode = "2- Solde du contrat de souscription";
        }else if (base_encode.equals("TI3")){
            base_decode = "3- Capital déposé + Intérêt";
        }else if (base_encode.equals("TI4")){
            base_decode = "4- Solde du compte";
        }
        return base_decode;
    }
    /**
     * Permet d'encoder les bases EtBaseTxPenal sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEtBaseTxPenal(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital déposé en début de période")){
            base_encode = "TP1";
        }else if (base_decode.equals("2- Solde du contrat de souscription")){
            base_encode = "TP2";
        }else if (base_decode.equals("3- Capital déposé + Intérêt")){
            base_encode = "TP3";
        }else if (base_decode.equals("4- Solde du compte")){
            base_encode = "TP4";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EtBaseTxPenal de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEtBaseTxPenal(String base_encode){
        String base_decode ="";
        if (base_encode.equals("TP1")){
            base_decode = "1- Capital déposé en début de période";
        }else if (base_encode.equals("TP2")){
            base_decode = "2- Solde du contrat de souscription";
        }else if (base_encode.equals("TP3")){
            base_decode = "3- Capital déposé + Intérêt";
        }else if (base_encode.equals("TP4")){
            base_decode = "4- Solde du compte";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases EtBaseTxIntPenal sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEtBaseTxIntPenal(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital déposé en début de période")){
            base_encode = "TR1";
        }else if (base_decode.equals("2- Solde du contrat de souscription")){
            base_encode = "TR2";
        }else if (base_decode.equals("3- Capital déposé + Intérêt")){
            base_encode = "TR3";
        }else if (base_decode.equals("4- Solde du compte")){
            base_encode = "TR4";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EtBaseTxIntPenal de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEtBaseTxIntPenal(String base_encode){
        String base_decode ="";
        if (base_encode.equals("TR1")){
            base_decode = "1- Capital déposé en début de période";
        }else if (base_encode.equals("TR2")){
            base_decode = "2- Solde du contrat de souscription";
        }else if (base_encode.equals("TR3")){
            base_decode = "3- Capital déposé + Intérêt";
        }else if (base_encode.equals("TR4")){
            base_decode = "4- Solde du compte";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases EtBaseTxMtPenalDeblocage sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEtBaseTxMtPenalDeblocage(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital déposé en début de période")){
            base_encode = "TD1";
        }else if (base_decode.equals("2- Solde du contrat de souscription")){
            base_encode = "TD2";
        }else if (base_decode.equals("3- Capital déposé + Intérêt")){
            base_encode = "TD3";
        }else if (base_decode.equals("4- Solde du compte")){
            base_encode = "TD4";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EtBaseTxMtPenalDeblocage de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEtBaseTxMtPenalDeblocage(String base_encode){
        String base_decode ="";
        if (base_encode.equals("TD1")){
            base_decode = "1- Capital déposé en début de période";
        }else if (base_encode.equals("TD2")){
            base_decode = "2- Solde du contrat de souscription";
        }else if (base_encode.equals("TD3")){
            base_decode = "3- Capital déposé + Intérêt";
        }else if (base_encode.equals("TD4")){
            base_decode = "4- Solde du compte";
        }
        return base_decode;
    }

}
