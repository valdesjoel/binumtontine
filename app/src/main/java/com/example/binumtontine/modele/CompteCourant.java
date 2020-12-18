package com.example.binumtontine.modele;

/**
 * Create a class file named Category.java
 * This model class will be useful to convert json data into objects.
 */
public class CompteCourant {

    private int CcNumero;
    private String CcLibelle;
    private String CcIsDecouvOn;
    private Double CcMtMaxDecouv;
    private String CcIsMaxDecouvNeg;

    public CompteCourant(int ccNumero, String ccLibelle, String ccIsDecouvOn, Double ccMtMaxDecouv, String ccIsMaxDecouvNeg) {
        CcNumero = ccNumero;
        CcLibelle = ccLibelle;
        CcIsDecouvOn = ccIsDecouvOn;
        CcMtMaxDecouv = ccMtMaxDecouv;
        CcIsMaxDecouvNeg = ccIsMaxDecouvNeg;
    }

    public int getCcNumero() {
        return CcNumero;
    }

    public void setCcNumero(int ccNumero) {
        CcNumero = ccNumero;
    }

    public String getCcLibelle() {
        return CcLibelle;
    }

    public void setCcLibelle(String ccLibelle) {
        CcLibelle = ccLibelle;
    }

    public String getCcIsDecouvOn() {
        return CcIsDecouvOn;
    }

    public void setCcIsDecouvOn(String ccIsDecouvOn) {
        CcIsDecouvOn = ccIsDecouvOn;
    }

    public Double getCcMtMaxDecouv() {
        return CcMtMaxDecouv;
    }

    public void setCcMtMaxDecouv(Double ccMtMaxDecouv) {
        CcMtMaxDecouv = ccMtMaxDecouv;
    }

    public String getCcIsMaxDecouvNeg() {
        return CcIsMaxDecouvNeg;
    }

    public void setCcIsMaxDecouvNeg(String ccIsMaxDecouvNeg) {
        CcIsMaxDecouvNeg = ccIsMaxDecouvNeg;
    }
    /**
     * Permet d'encoder les bases CcBaseTxIntDecouv sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCcBaseTxIntDecouv(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital restant dû")){
            base_encode = "CA1";
        }else if (base_decode.equals("2- Montant restant dû (Capital restant dû + Intérêt échu)")){
            base_encode = "CA2";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases CcBaseTxIntDecouv de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeCcBaseTxIntDecouv(String base_encode){
        String base_decode ="";
        if (base_encode.equals("CA1")){
            base_decode = "1- Capital restant dû";
        }else if (base_encode.equals("CA2")){
            base_decode = "2- Montant restant dû (Capital restant dû + Intérêt échu)";
        }
        return base_decode;
    }
    /**
     * Permet d'encoder les bases CcBaseTxIntPenRetard sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCcBaseTxIntPenRetard(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital restant dû")){
            base_encode = "CB1";
        }else if (base_decode.equals("2- Mt restant dû 1 (Capital restant dû + Intérêt échu)")){
            base_encode = "CB2";
        }else if (base_decode.equals("3- Mt restant dû 2 (Cap. Rest. Dû + Int. échu + Int. Retard)")){
            base_encode = "CB3";
        }else if (base_decode.equals("4- Mt restant dû 3 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard)")){
            base_encode = "CB4";
        }else if (base_decode.equals("5- Mt restant dû 4 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard + Com. Ten. + Compte + Com échue)")){
            base_encode = "CB5";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases CcBaseTxIntDecouv de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeCcBaseTxIntPenRetard(String base_encode){
        String base_decode ="";
        if (base_encode.equals("CB1")){
            base_decode = "1- Capital restant dû";
        }else if (base_encode.equals("CB2")){
            base_decode = "2- Mt restant dû 1 (Capital restant dû + Intérêt échu)";
        }else if (base_encode.equals("CB3")){
            base_decode = "3- Mt restant dû 2 (Cap. Rest. Dû + Int. échu + Int. Retard)";
        }else if (base_encode.equals("CB4")){
            base_decode = "4- Mt restant dû 3 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard)";
        }else if (base_encode.equals("CB5")){
            base_decode = "5- Mt restant dû 4 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard + Com. Ten. + Compte + Com échue)";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases CcBasexInt_IntRetDecouv sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCcBasexInt_IntRetDecouv(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital restant dû")){
            base_encode = "CC1";
        }else if (base_decode.equals("2- Mt restant dû 1 (Capital restant dû + Intérêt échu)")){
            base_encode = "CC2";
        }else if (base_decode.equals("3- Mt restant dû 2 (Cap. Rest. Dû + Int. échu + Int. Retard)")){
            base_encode = "CC3";
        }else if (base_decode.equals("4- Mt restant dû 3 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard)")){
            base_encode = "CC4";
        }else if (base_decode.equals("5- Mt restant dû 4 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard + Com. Ten. + Compte + Com échue)")){
            base_encode = "CC5";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases CcBasexInt_IntRetDecouv de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeCcBasexInt_IntRetDecouv(String base_encode){
        String base_decode ="";
        if (base_encode.equals("CC1")){
            base_decode = "1- Capital restant dû";
        }else if (base_encode.equals("CC2")){
            base_decode = "2- Mt restant dû 1 (Capital restant dû + Intérêt échu)";
        }else if (base_encode.equals("CC3")){
            base_decode = "3- Mt restant dû 2 (Cap. Rest. Dû + Int. échu + Int. Retard)";
        }else if (base_encode.equals("CC4")){
            base_decode = "4- Mt restant dû 3 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard)";
        }else if (base_encode.equals("CC5")){
            base_decode = "5- Mt restant dû 4 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard + Com. Ten. + Compte + Com échue)";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases CcNatBaseAgio sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCcNatBaseAgio(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Cumul des versements du mois")){
            base_encode = "CD1";
        }else if (base_decode.equals("2- Cumul des retraits du mois")){
            base_encode = "CD2";
        }else if (base_decode.equals("3- Solde Minimum en Compte")){
            base_encode = "CD3";
        }else if (base_decode.equals("4- Solde du Compte")){
            base_encode = "CD4";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases CcNatBaseAgio de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeCcNatBaseAgio(String base_encode){
        String base_decode ="";
        if (base_encode.equals("CD1")){
            base_decode = "1- Cumul des versements du mois";
        }else if (base_encode.equals("CD2")){
            base_decode = "2- Cumul des retraits du mois";
        }else if (base_encode.equals("CD3")){
            base_decode = "3- Solde Minimum en Compte";
        }else if (base_encode.equals("CD4")){
            base_decode = "4- Solde du Compte";
        }
        return base_decode;
    }
    /**
     * Permet d'encoder les bases CcBaseTxCommMvm sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCcBaseTxCommMvm(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Nombre de mouvements sur la période")){
            base_encode = "CE1";
        }else if (base_decode.equals("2- Mouvements de débit effectué")){
            base_encode = "CE2";
        }else if (base_decode.equals("3- Mouvements de débit avant date de valeur")){
            base_encode = "CE3";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases CcBaseTxCommMvm de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeCcBaseTxCommMvm(String base_encode){
        String base_decode ="";
        if (base_encode.equals("CE1")){
            base_decode = "1- Nombre de mouvements sur la période";
        }else if (base_encode.equals("CE2")){
            base_decode = "2- Mouvements de débit effectué";
        }else if (base_encode.equals("CE3")){
            base_decode = "3- Mouvements de débit avant date de valeur";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases CcBaseTxIntAvceSpec sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCcBaseTxIntAvceSpec(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital restant dû")){
            base_encode = "CF1";
        }else if (base_decode.equals("2- Montant restant dû (Capital restant dû + Intérêt échu)")){
            base_encode = "CF2";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases CcBaseTxIntAvceSpec de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeCcBaseTxIntAvceSpec(String base_encode){
        String base_decode ="";
        if (base_encode.equals("CF1")){
            base_decode = "1- Capital restant dû";
        }else if (base_encode.equals("CF2")){
            base_decode = "2- Montant restant dû (Capital restant dû + Intérêt échu)";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases CcBaseTxIntPenAvceSpec sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCcBaseTxIntPenAvceSpec(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital restant dû")){
            base_encode = "CG1";
        }else if (base_decode.equals("2- Mt restant dû 1 (Capital restant dû + Intérêt échu)")){
            base_encode = "CG2";
        }else if (base_decode.equals("3- Mt restant dû 2 (Cap. Rest. Dû + Int. échu + Int. Retard)")){
            base_encode = "CG3";
        }else if (base_decode.equals("4- Mt restant dû 3 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard)")){
            base_encode = "CG4";
        }else if (base_decode.equals("5- Mt restant dû 4 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard + Com. Ten. + Compte + Com échue)")){
            base_encode = "CG5";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases CcBaseTxIntPenAvceSpec de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeCcBaseTxIntPenAvceSpec(String base_encode){
        String base_decode ="";
        if (base_encode.equals("CG1")){
            base_decode = "1- Capital restant dû";
        }else if (base_encode.equals("CG2")){
            base_decode = "2- Mt restant dû 1 (Capital restant dû + Intérêt échu)";
        }else if (base_encode.equals("CG3")){
            base_decode = "3- Mt restant dû 2 (Cap. Rest. Dû + Int. échu + Int. Retard)";
        }else if (base_encode.equals("CG4")){
            base_decode = "4- Mt restant dû 3 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard)";
        }else if (base_encode.equals("CG5")){
            base_decode = "5- Mt restant dû 4 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard + Com. Ten. + Compte + Com échue)";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases CcBasexInt_IntRetAvceSpec sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeCcBasexInt_IntRetAvceSpec(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Capital restant dû")){
            base_encode = "CH1";
        }else if (base_decode.equals("2- Mt restant dû 1 (Capital restant dû + Intérêt échu)")){
            base_encode = "CH2";
        }else if (base_decode.equals("3- Mt restant dû 2 (Cap. Rest. Dû + Int. échu + Int. Retard)")){
            base_encode = "CH3";
        }else if (base_decode.equals("4- Mt restant dû 3 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard)")){
            base_encode = "CH4";
        }else if (base_decode.equals("5- Mt restant dû 4 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard + Com. Ten. + Compte + Com échue)")){
            base_encode = "CH5";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases CcBasexInt_IntRetAvceSpec de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeCcBasexInt_IntRetAvceSpec(String base_encode){
        String base_decode ="";
        if (base_encode.equals("CH1")){
            base_decode = "1- Capital restant dû";
        }else if (base_encode.equals("CH2")){
            base_decode = "2- Mt restant dû 1 (Capital restant dû + Intérêt échu)";
        }else if (base_encode.equals("CH3")){
            base_decode = "3- Mt restant dû 2 (Cap. Rest. Dû + Int. échu + Int. Retard)";
        }else if (base_encode.equals("CH4")){
            base_decode = "4- Mt restant dû 3 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard)";
        }else if (base_encode.equals("CH5")){
            base_decode = "5- Mt restant dû 4 (Cap. Rest. Dû + Int. échu + Int. Retard + Pen. Retard + Com. Ten. + Compte + Com échue)";
        }
        return base_decode;
    }

}
