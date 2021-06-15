package com.example.binumtontine.modele;

public class ProduitEAP {
    //Fields's class
    private int EpNumero;
    private String EpLibelle;
    private String EpValTxInter;
    private String EpMtMinMisePer;
    private String EpDureeMin;
    private String EpIsTxIntNeg;

    private String EpTypTxInter;
    private String EpBaseTxInter;
    private String EpNatureRupAn;
    private String EpValTxMtRupture;
    private String EpBaseTxPenal;
    private String EpIsPenalNRespMise;
    private String EpNbEchPenalOn;
    private String EpIsEchPenalSucces;
    private String EpIsPenalRupAnt;
    private String EpNaturePenal;
    private String EpValTxMtPenalite;
    private String EpBaseTxMtPenal;


    //Constructor


    public ProduitEAP() {
    }

    public ProduitEAP(int epNumero, String epLibelle, String epValTxInter,  String epDureeMin, String epMtMinMisePer, String epIsTxIntNeg) {
        EpNumero = epNumero;
        EpLibelle = epLibelle;
        EpValTxInter = epValTxInter;
        EpMtMinMisePer = epMtMinMisePer;
        EpDureeMin = epDureeMin;
        EpIsTxIntNeg = epIsTxIntNeg;

    }

    //Getters and Setters

    public int getEpNumero() {
        return EpNumero;
    }

    public void setEpNumero(int epNumero) {
        EpNumero = epNumero;
    }

    public String getEpLibelle() {
        return EpLibelle;
    }

    public void setEpLibelle(String epLibelle) {
        EpLibelle = epLibelle;
    }

    public String getEpValTxInter() {
        return EpValTxInter;
    }

    public void setEpValTxInter(String epValTxInter) {
        EpValTxInter = epValTxInter;
    }

    public String getEpMtMinMisePer() {
        return EpMtMinMisePer;
    }

    public void setEpMtMinMisePer(String epMtMinMisePer) {
        EpMtMinMisePer = epMtMinMisePer;
    }

    public String getEpDureeMin() {
        return EpDureeMin;
    }

    public void setEpDureeMin(String epDureeMin) {
        EpDureeMin = epDureeMin;
    }

    public String getEpIsTxIntNeg() {
        return EpIsTxIntNeg;
    }

    public void setEpIsTxIntNeg(String epIsTxIntNeg) {
        EpIsTxIntNeg = epIsTxIntNeg;
    }

    public String getEpTypTxInter() {
        return EpTypTxInter;
    }

    public void setEpTypTxInter(String epTypTxInter) {
        EpTypTxInter = epTypTxInter;
    }

    public String getEpBaseTxInter() {
        return EpBaseTxInter;
    }

    public void setEpBaseTxInter(String epBaseTxInter) {
        EpBaseTxInter = epBaseTxInter;
    }

    public String getEpNatureRupAn() {
        return EpNatureRupAn;
    }

    public void setEpNatureRupAn(String epNatureRupAn) {
        EpNatureRupAn = epNatureRupAn;
    }

    public String getEpValTxMtRupture() {
        return EpValTxMtRupture;
    }

    public void setEpValTxMtRupture(String epValTxMtRupture) {
        EpValTxMtRupture = epValTxMtRupture;
    }

    public String getEpBaseTxPenal() {
        return EpBaseTxPenal;
    }

    public void setEpBaseTxPenal(String epBaseTxPenal) {
        EpBaseTxPenal = epBaseTxPenal;
    }

    public String getEpIsPenalNRespMise() {
        return EpIsPenalNRespMise;
    }

    public void setEpIsPenalNRespMise(String epIsPenalNRespMise) {
        EpIsPenalNRespMise = epIsPenalNRespMise;
    }

    public String getEpNbEchPenalOn() {
        return EpNbEchPenalOn;
    }

    public void setEpNbEchPenalOn(String epNbEchPenalOn) {
        EpNbEchPenalOn = epNbEchPenalOn;
    }

    public String getEpIsEchPenalSucces() {
        return EpIsEchPenalSucces;
    }

    public void setEpIsEchPenalSucces(String epIsEchPenalSucces) {
        EpIsEchPenalSucces = epIsEchPenalSucces;
    }

    public String getEpIsPenalRupAnt() {
        return EpIsPenalRupAnt;
    }

    public void setEpIsPenalRupAnt(String epIsPenalRupAnt) {
        EpIsPenalRupAnt = epIsPenalRupAnt;
    }

    public String getEpNaturePenal() {
        return EpNaturePenal;
    }

    public void setEpNaturePenal(String epNaturePenal) {
        EpNaturePenal = epNaturePenal;
    }

    public String getEpValTxMtPenalite() {
        return EpValTxMtPenalite;
    }

    public void setEpValTxMtPenalite(String epValTxMtPenalite) {
        EpValTxMtPenalite = epValTxMtPenalite;
    }

    public String getEpBaseTxMtPenal() {
        return EpBaseTxMtPenal;
    }

    public void setEpBaseTxMtPenal(String epBaseTxMtPenal) {
        EpBaseTxMtPenal = epBaseTxMtPenal;
    }

    /**
     * Permet d'encoder les bases EpBaseTxInter sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEpBaseTxInter(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Montant de la mise périodique")){
            base_encode = "PI1";
        }else if (base_decode.equals("2- Total de la mise périodique")){
            base_encode = "PI2";
        }else if (base_decode.equals("3- Mise périodique + Intérêt")){
            base_encode = "PI3";
        }else if (base_decode.equals("4- Cumul des mises effectuées")){
            base_encode = "PI4";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EpBaseTxInter de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEpBaseTxInter(String base_encode){
        String base_decode ="";
        if (base_encode.equals("PI1")){
            base_decode = "1- Montant de la mise périodique";
        }else if (base_encode.equals("PI2")){
            base_decode = "2- Total de la mise périodique";
        }else if (base_encode.equals("PI3")){
            base_decode = "3- Mise périodique + Intérêt";
        }else if (base_encode.equals("PI4")){
            base_decode = "4- Cumul des mises effectuées";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases EpBaseTxPenal sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEpBaseTxPenal(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Total de la mise périodique")){
            base_encode = "PP1";
        }else if (base_decode.equals("2- Mise périodique + Intérêt")){
            base_encode = "PP2";
        }else if (base_decode.equals("3- Cumul des mises effectuées + Intérêt")){
            base_encode = "PP3";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EpBaseTxPenal de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEpBaseTxPenal(String base_encode){
        String base_decode ="";
        if (base_encode.equals("PP1")){
            base_decode = "1- Total de la mise périodique";
        }else if (base_encode.equals("PP2")){
            base_decode = "2- Mise périodique + Intérêt";
        }else if (base_encode.equals("PP3")){
            base_decode = "3- Cumul des mises effectuées + Intérêt";
        }
        return base_decode;
    }
    /**
     * Permet d'encoder les bases EpBaseTxIntRupant sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEpBaseTxIntRupant(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Cumul des mises effectuées")){
            base_encode = "PR1";
        }else if (base_decode.equals("2- Total de la mise périodique")){
            base_encode = "PR2";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EpBaseTxIntRupant de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEpBaseTxIntRupant(String base_encode){
        String base_decode ="";
        if (base_encode.equals("PR1")){
            base_decode = "1- Cumul des mises effectuées";
        }else if (base_encode.equals("PR2")){
            base_decode = "2- Total de la mise périodique";
        }
        return base_decode;
    }
    /**
     * Permet d'encoder les bases EpBaseTxMtPenal sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEpBaseTxMtPenal(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Total de la mise périodique")){
            base_encode = "PD1";
        }else if (base_decode.equals("2- Mise périodique + Intérêt")){
            base_encode = "PD2";
        }else if (base_decode.equals("3- Cumul des mises effectuées + Intérêt")){
            base_encode = "PD3";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EpBaseTxMtPenal de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEpBaseTxMtPenal(String base_encode){
        String base_decode ="";
        if (base_encode.equals("PD1")){
            base_decode = "1- Total de la mise périodique";
        }else if (base_encode.equals("PD2")){
            base_decode = "2- Mise périodique + Intérêt";
        }else if (base_encode.equals("PD3")){
            base_decode = "3- Cumul des mises effectuées + Intérêt";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les périodicités CpPeriod sur 1 caractère avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCpPeriod(String base_decode){
        String base_encode ="";
        if (base_decode.equals("JOURNALIERE")){
            base_encode = "J";
        }else if (base_decode.equals("HEBDOMADAIRE")){
            base_encode = "H";
        }else if (base_decode.equals("MENSUELLE")){
            base_encode = "M";
        }else if (base_decode.equals("ANNUELLE")){
            base_encode = "A";
        }
        return base_encode;
    }

}
