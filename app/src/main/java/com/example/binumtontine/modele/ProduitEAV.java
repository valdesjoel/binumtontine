package com.example.binumtontine.modele;

public class ProduitEAV {
    /**
     * Permet d'encoder les bases ev_base_tx_inter_an sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEvBaseTxInterAn(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Solde minimum mensuel")){
            base_encode = "VI1";
        }else if (base_decode.equals("2- Solde moyen mensuel")){
            base_encode = "VI2";
        }else if (base_decode.equals("3- Solde minimum trimestriel")){
            base_encode = "VI3";
        }else if (base_decode.equals("4- Solde moyen trimestriel")){
            base_encode = "VI4";
        }else if (base_decode.equals("5- Solde minimum bimensuel")){
            base_encode = "VI5";
        }else if (base_decode.equals("6- Solde moyen bimensuel")){
            base_encode = "VI6";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases ev_base_tx_inter_an de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEvBaseTxInterAn(String base_encode){
        String base_decode ="";
        if (base_encode.equals("VI1")){
            base_decode = "1- Solde minimum mensuel";
        }else if (base_encode.equals("VI2")){
            base_decode = "2- Solde moyen mensuel";
        }else if (base_encode.equals("VI3")){
            base_decode = "3- Solde minimum trimestriel";
        }else if (base_encode.equals("VI4")){
            base_decode = "4- Solde moyen trimestriel";
        }else if (base_encode.equals("VI5")){
            base_decode = "5- Solde minimum bimensuel";
        }else if (base_encode.equals("VI6")){
            base_decode = "6- Solde moyen bimensuel";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases ev_base_tx_agios_prelev sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEvBaseTxAgiosPrelev(String base_decode){
        String base_codifier ="";
        if (base_decode.equals("1- Somme des versements du mois")){
            base_codifier = "VF1";
        }else if (base_decode.equals("2- Cumul des mouvements créditeurs du mois")){
            base_codifier = "VF2";
        }else if (base_decode.equals("3- Solde des versements de la période")){
            base_codifier = "VF3";
        }else if (base_decode.equals("4- Valeur du minimum en compte")){
            base_codifier = "VF4";
        }
        return base_codifier;
    }

    /**
     * Permet de décoder les bases ev_base_tx_agios_prelev de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEvBaseTxAgiosPrelev(String base_encode){
        String base_decode ="";
        if (base_encode.equals("VF1")){
            base_decode = "1- Somme des versements du mois";
        }else if (base_encode.equals("VF2")){
            base_decode = "2- Cumul des mouvements créditeurs du mois";
        }else if (base_encode.equals("VF3")){
            base_decode = "3- Solde des versements de la période";
        }else if (base_encode.equals("VF4")){
            base_decode = "4- Valeur du minimum en compte";
        }
        return base_decode;
    }

    /**
     * Permet d'encoder les bases EvBaseFraisCloture sur 3 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeEvBaseFraisCloture(String base_decode){
        String base_encode ="";
        if (base_decode.equals("1- Minimum en compte")){
            base_encode = "VC1";
        }else if (base_decode.equals("2- Solde en compte")){
            base_encode = "VC2";
        }
        return base_encode;
    }

    /**
     * Permet de décoder les bases EvBaseFraisClotur de 3 caractères venant de la base de données
     * @param base_encode chaine de 3 caractères
     * @return
     */
    public static String decodeEvBaseFraisCloture(String base_encode){
        String base_decode ="";
        if (base_encode.equals("VC1")){
            base_decode = "1- Minimum en compte";
        }else if (base_encode.equals("VC2")){
            base_decode = "2- Solde en compte";
        }
        return base_decode;
    }
}
