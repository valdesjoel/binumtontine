package com.example.binumtontine.modele;

import com.example.binumtontine.activity.convertisseur.DateTools;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Echeancier {
    public String EcNumero;
    public DemandeCreditModele EcCredit;
    public int EcNumVersion;
    public Date EcDateRemb;
    public double EcMtCapital;
    public double EcMtInteret;
    public double EcTotalCapRemb;
    public double EcTotalIntRemb;
    public double EcMtPenalite;
    public double EcTotalPenaliteRemb;
    public double EcMtPenalRetard;
    public double EcTotalPenalRetardRemd;
    public String EcIsSolde;
    public String EcDateHSolde;
    public String EcDetails;

    public Echeancier() {
    }

    public String getEcNumero() {
        return EcNumero;
    }

    public void setEcNumero(String ecNumero) {
        EcNumero = ecNumero;
    }

    public DemandeCreditModele getEcCredit() {
        return EcCredit;
    }

    public void setEcCredit(DemandeCreditModele ecCredit) {
        EcCredit = ecCredit;
    }

    public int getEcNumVersion() {
        return EcNumVersion;
    }

    public void setEcNumVersion(int ecNumVersion) {
        EcNumVersion = ecNumVersion;
    }

    public Date getEcDateRemb() {
        return EcDateRemb;
    }

    public void setEcDateRemb(Date ecDateRemb) {
        EcDateRemb = ecDateRemb;
    }

    public double getEcMtCapital() {
        return EcMtCapital;
    }

    public void setEcMtCapital(double ecMtCapital) {
        EcMtCapital = ecMtCapital;
    }

    public double getEcMtInteret() {
        return EcMtInteret;
    }

    public void setEcMtInteret(double ecMtInteret) {
        EcMtInteret = ecMtInteret;
    }

    public double getEcTotalCapRemb() {
        return EcTotalCapRemb;
    }

    public void setEcTotalCapRemb(double ecTotalCapRemb) {
        EcTotalCapRemb = ecTotalCapRemb;
    }

    public double getEcTotalIntRemb() {
        return EcTotalIntRemb;
    }

    public void setEcTotalIntRemb(double ecTotalIntRemb) {
        EcTotalIntRemb = ecTotalIntRemb;
    }

    public double getEcMtPenalite() {
        return EcMtPenalite;
    }

    public void setEcMtPenalite(double ecMtPenalite) {
        EcMtPenalite = ecMtPenalite;
    }

    public double getEcTotalPenaliteRemb() {
        return EcTotalPenaliteRemb;
    }

    public void setEcTotalPenaliteRemb(double ecTotalPenaliteRemb) {
        EcTotalPenaliteRemb = ecTotalPenaliteRemb;
    }

    public double getEcMtPenalRetard() {
        return EcMtPenalRetard;
    }

    public void setEcMtPenalRetard(double ecMtPenalRetard) {
        EcMtPenalRetard = ecMtPenalRetard;
    }

    public double getEcTotalPenalRetardRemd() {
        return EcTotalPenalRetardRemd;
    }

    public void setEcTotalPenalRetardRemd(double ecTotalPenalRetardRemd) {
        EcTotalPenalRetardRemd = ecTotalPenalRetardRemd;
    }

    public String getEcIsSolde() {
        return EcIsSolde;
    }

    public void setEcIsSolde(String ecIsSolde) {
        EcIsSolde = ecIsSolde;
    }

    public String getEcDateHSolde() {
        return EcDateHSolde;
    }

    public void setEcDateHSolde(String ecDateHSolde) {
        EcDateHSolde = ecDateHSolde;
    }

    public String getEcDetails() {
        return EcDetails;
    }

    public void setEcDetails(String ecDetails) {
        EcDetails = ecDetails;
    }

    @Override
    public String toString() {
        return "Echeancier{" +
                "EcNumero='" + EcNumero + '\'' +
                ", EcCredit=" + EcCredit +
                ", EcNumVersion=" + EcNumVersion +
                ", EcDateRemb='" + EcDateRemb + '\'' +
                ", EcMtCapital=" + EcMtCapital +
                ", EcMtInteret=" + EcMtInteret +
                ", EcTotalCapRemb=" + EcTotalCapRemb +
                ", EcTotalIntRemb=" + EcTotalIntRemb +
                ", EcMtPenalite=" + EcMtPenalite +
                ", EcTotalPenaliteRemb=" + EcTotalPenaliteRemb +
                ", EcMtPenalRetard=" + EcMtPenalRetard +
                ", EcTotalPenalRetardRemd=" + EcTotalPenalRetardRemd +
                ", EcIsSolde='" + EcIsSolde + '\'' +
                ", EcDateHSolde='" + EcDateHSolde + '\'' +
                ", EcDetails='" + EcDetails + '\'' +
                '}';
    }

//    private void generateEcheance() throws Exception {
//        double montantCredit = Double.parseDouble(this.EcCredit.getDcMtAccorde());
//        double montantInteret = 0.0;
//
//        double taux = 0.0;
//        int nbreEcheance = 1;
//
//        nbreEcheance = Integer.parseInt(this.EcCredit.getDcDureeRemb());
//        montantInteret = calculTauxInteret(montantCredit, taux);
//
//        Date dateDebutEcheance = new SimpleDateFormat("dd-MM-yyyy").parse(this.EcCredit.getDcDatePremEch());
//        int mois_suivant = 0;
//        Double sommeCapital = 0.0;
//        Double sommeInteret = 0.0;
//
//        Echeancier echeancier;
//     /*   this.setDcMtAccorde(String.valueOf(montantCredit));
////        this.setDcTauxInt(Float.parseFloat(taux+""));
//        this.setDcDatePremEch(DateTools.formatterDateToString(dateDebutEcheance));
////        this.setDcDureeRemb(String.valueOf(nbreEcheance));
//*/
//        for(int i=0; i<nbreEcheance; i++){
//            echeancier = new Echeancier();
//            echeancier.setEcNumVersion(i+1);
//            if(i+1==nbreEcheance){
//                echeancier.setEcMtCapital((montantCredit-sommeCapital));
//                echeancier.setEcTotalCapRemb(0);
//                echeancier.setEcTotalIntRemb(0);
//                echeancier.setEcMtInteret(montantInteret - sommeInteret);
//
//            }else{
//                echeancier.setEcMtCapital(Math.round(montantCredit/nbreEcheance));
//                echeancier.setEcMtInteret(Math.round(montantInteret/nbreEcheance));
//
//                sommeCapital += echeancier.getEcMtCapital();
//                sommeInteret += echeancier.getEcMtInteret();
//
//                echeancier.setEcTotalCapRemb(montantCredit-sommeCapital);
//                echeancier.setEcTotalIntRemb(montantInteret-sommeInteret);
//
//            }
//
//            echeancier.setEcDateRemb(DateUtils.addMonths(dateDebutEcheance, i+1));
//
//            echeancier.setEcCredit(this.demandeCredit);
//            /*echeancier.setEcStatut(0);
//            echeancier.setEcMontantCapitalPaye(0);*/
//
//            listEcheanciers.add(echeancier);
//
//        }
//
//
//
//
//
//    }
//    private double calculTauxInteret (Double capital, double taux){
//
//        return (capital*taux)/100 ;
//    }
}
