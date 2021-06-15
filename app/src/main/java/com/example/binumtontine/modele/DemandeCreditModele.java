package com.example.binumtontine.modele;

import com.example.binumtontine.activity.convertisseur.DateTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DemandeCreditModele {
    //Attributes Start
    private String DcNumero;
    private String DcMembre;
    private String DcDate;
    private String DcCredit;
    private String DcObjet;
    private String DcNumDossier;
    private String DcCpteEAVPrinc;
    private String DcMtSoldeEAV;
    private String DcMtEpargnOblig;
    private String DcMtPartSocial;
    private String DcActivitePrinc;
    private String DcNbAnneeExper;
    private String DcAutresActivite;
    private String DcIsEmpruntPasse;
    private String DcEmpruntPasseOu;
    private String DcIsEchRembResp;
    private String DcPourqRembSiNon;
    private String DcPretEnCours;
    private String DcPretInstit;
    private String DcNaturePret;
    private String DcMtPrincPret;
    private String DcMtRestARemb;
    private String DcMtPretSollicite;
    private String DcDescriptPret;
    private String DcDureeRemb;
    private String DcDatePremEch;
    private String DcPeriodRemb;
    private String DcNbJrDiffere;
    private String DcGarantiesProp;
    private String DcIsAvaliste1;
    private String DcNumAvaliste1;
    private String DcMtSoldAvalist1;
    private String DcIsAvaliste2;
    private String DcNumAvaliste2;
    private String DcMtSoldAvalist2;
    private String DcIsAvaliste3;
    private String DcNumAvaliste3;
    private String DcMtSoldAvalist3;
    private String DcIsBesoinsAFin;
    private String DcMtTotalBesoins;
    private String DcMotivationPret;
    private String DcIsPhotocopieCNI;
    private String DcIsCpteExploit;
    private String DcIsDmdeManuscr;
    private String DcIsPceGarantie;
    private String DcIsAutresDocum;
    private String DcAutresDocum;
    private String DcIsSignatAdh;
    private String DcDateSignat;
    private String DcAgentCredit;
    private String DcObservAgCred;
    private String DcAvisAgCredit;
    private String DcDateDecisiion;
    private String DcDecisionComGes;
    private String DcMtAccorde;
    private String DcDureeAccord;
    private String DcMotivations;
    private String DcEtapeCredit;
    private String DcDateHCree;
    private String DcDetails;
    private String DcMtDetteHorsBilan;
    private String DcTotPayeDetteHorsBilan;
    private String DcUserCree;
    private String DcGuichet;
    private String DcCodeDebl;
    private String DcDetailsDebl;
    private String DcCmNumero;


    //Attributes END
    //JSON Parameters START
    public static final String KEY_DcNumero ="DcNumero";
    public static final String KEY_DcMembre ="DcMembre";
    public static final String KEY_DcDate ="DcDate";
    public static final String KEY_DcCredit ="DcCredit";
    public static final String KEY_DcObjet ="DcObjet";
    public static final String KEY_DcNumDossier ="DcNumDossier";
    public static final String KEY_DcCpteEAVPrinc ="DcCpteEAVPrinc";
    public static final String KEY_DcMtSoldeEAV ="DcMtSoldeEAV";
    public static final String KEY_DcMtEpargnOblig ="DcMtEpargnOblig";
    public static final String KEY_DcMtPartSocial ="DcMtPartSocial";
    public static final String KEY_DcActivitePrinc ="DcActivitePrinc";
    public static final String KEY_DcNbAnneeExper ="DcNbAnneeExper";
    public static final String KEY_DcAutresActivite ="DcAutresActivite";
    public static final String KEY_DcIsEmpruntPasse ="DcIsEmpruntPasse";
    public static final String KEY_DcEmpruntPasseOu ="DcEmpruntPasseOu";
    public static final String KEY_DcIsEchRembResp ="DcIsEchRembResp";
    public static final String KEY_DcPourqRembSiNon ="DcPourqRembSiNon";
    public static final String KEY_DcPretEnCours ="DcPretEnCours";
    public static final String KEY_DcPretInstit ="DcPretInstit";
    public static final String KEY_DcNaturePret ="DcNaturePret";
    public static final String KEY_DcMtPrincPret ="DcMtPrincPret";
    public static final String KEY_DcMtRestARemb ="DcMtRestARemb";
    public static final String KEY_DcMtPretSollicite ="DcMtPretSollicite";
    public static final String KEY_DcDescriptPret ="DcDescriptPret";
    public static final String KEY_DcDureeRemb ="DcDureeRemb";
    public static final String KEY_DcDatePremEch ="DcDatePremEch";
    public static final String KEY_DcPeriodRemb ="DcPeriodRemb";
    public static final String KEY_DcNbJrDiffere ="DcNbJrDiffere";
    public static final String KEY_DcGarantiesProp ="DcGarantiesProp";
    public static final String KEY_DcIsAvaliste1 ="DcIsAvaliste1";
    public static final String KEY_DcNumAvaliste1 ="DcNumAvaliste1";
    public static final String KEY_DcMtSoldAvalist1 ="DcMtSoldAvalist1";
    public static final String KEY_DcIsAvaliste2 ="DcIsAvaliste2";
    public static final String KEY_DcNumAvaliste2 ="DcNumAvaliste2";
    public static final String KEY_DcMtSoldAvalist2 ="DcMtSoldAvalist2";
    public static final String KEY_DcIsAvaliste3 ="DcIsAvaliste3";
    public static final String KEY_DcNumAvaliste3 ="DcNumAvaliste3";
    public static final String KEY_DcMtSoldAvalist3 ="DcMtSoldAvalist3";
    public static final String KEY_DcIsBesoinsAFin ="DcIsBesoinsAFin";
    public static final String KEY_DcMtTotalBesoins ="DcMtTotalBesoins";
    public static final String KEY_DcMotivationPret ="DcMotivationPret";
    public static final String KEY_DcIsPhotocopieCNI ="DcIsPhotocopieCNI";
    public static final String KEY_DcIsCpteExploit ="DcIsCpteExploit";
    public static final String KEY_DcIsDmdeManuscr ="DcIsDmdeManuscr";
    public static final String KEY_DcIsPceGarantie ="DcIsPceGarantie";
    public static final String KEY_DcIsAutresDocum ="DcIsAutresDocum";
    public static final String KEY_DcAutresDocum ="DcAutresDocum";
    public static final String KEY_DcIsSignatAdh ="DcIsSignatAdh";
    public static final String KEY_DcDateSignat ="DcDateSignat";
    public static final String KEY_DcAgentCredit ="DcAgentCredit";
    public static final String KEY_DcObservAgCred ="DcObservAgCred";
    public static final String KEY_DcAvisAgCredit ="DcAvisAgCredit";
    public static final String KEY_DcDateDecisiion ="DcDateDecisiion";
    public static final String KEY_DcDecisionComGes ="DcDecisionComGes";
    public static final String KEY_DcMtAccorde ="DcMtAccorde";
    public static final String KEY_DcDureeAccord ="DcDureeAccord";
    public static final String KEY_DcMotivations ="DcMotivations";
    public static final String KEY_DcEtapeCredit ="DcEtapeCredit";
    public static final String KEY_DcDateHCree ="DcDateHCree";
    public static final String KEY_DcDetails ="DcDetails";
    public static final String KEY_DcMtDetteHorsBilan ="DcMtDetteHorsBilan";
    public static final String KEY_DcTotPayeDetteHorsBilan ="DcTotPayeDetteHorsBilan";
    public static final String KEY_DcUserCree ="DcUserCree";
    public static final String KEY_DcGuichet ="DcGuichet";
    public static final String KEY_DcCodeDebl ="DcCodeDebl";
    public static final String KEY_DcDetailsDebl ="DcDetailsDebl";
    public static final String KEY_DcCmNumero ="DcCmNumero";

// JSON Parameters END


    //Constructor
//    public DemandeCreditModele() {
//    }



    //Getters and setters


    public String getDcNumero() {
        return DcNumero;
    }

    public void setDcNumero(String dcNumero) {
        DcNumero = dcNumero;
    }

    public String getDcMembre() {
        return DcMembre;
    }

    public void setDcMembre(String dcMembre) {
        DcMembre = dcMembre;
    }

    public String getDcDate() {
        return DcDate;
    }

    public void setDcDate(String dcDate) {
        DcDate = dcDate;
    }

    public String getDcCredit() {
        return DcCredit;
    }

    public void setDcCredit(String dcCredit) {
        DcCredit = dcCredit;
    }

    public String getDcObjet() {
        return DcObjet;
    }

    public void setDcObjet(String dcObjet) {
        DcObjet = dcObjet;
    }

    public String getDcNumDossier() {
        return DcNumDossier;
    }

    public void setDcNumDossier(String dcNumDossier) {
        DcNumDossier = dcNumDossier;
    }

    public String getDcCpteEAVPrinc() {
        return DcCpteEAVPrinc;
    }

    public void setDcCpteEAVPrinc(String dcCpteEAVPrinc) {
        DcCpteEAVPrinc = dcCpteEAVPrinc;
    }

    public String getDcMtSoldeEAV() {
        return DcMtSoldeEAV;
    }

    public void setDcMtSoldeEAV(String dcMtSoldeEAV) {
        DcMtSoldeEAV = dcMtSoldeEAV;
    }

    public String getDcMtEpargnOblig() {
        return DcMtEpargnOblig;
    }

    public void setDcMtEpargnOblig(String dcMtEpargnOblig) {
        DcMtEpargnOblig = dcMtEpargnOblig;
    }

    public String getDcMtPartSocial() {
        return DcMtPartSocial;
    }

    public void setDcMtPartSocial(String dcMtPartSocial) {
        DcMtPartSocial = dcMtPartSocial;
    }

    public String getDcActivitePrinc() {
        return DcActivitePrinc;
    }

    public void setDcActivitePrinc(String dcActivitePrinc) {
        DcActivitePrinc = dcActivitePrinc;
    }

    public String getDcNbAnneeExper() {
        return DcNbAnneeExper;
    }

    public void setDcNbAnneeExper(String dcNbAnneeExper) {
        DcNbAnneeExper = dcNbAnneeExper;
    }

    public String getDcAutresActivite() {
        return DcAutresActivite;
    }

    public void setDcAutresActivite(String dcAutresActivite) {
        DcAutresActivite = dcAutresActivite;
    }

    public String getDcIsEmpruntPasse() {
        return DcIsEmpruntPasse;
    }

    public void setDcIsEmpruntPasse(String dcIsEmpruntPasse) {
        DcIsEmpruntPasse = dcIsEmpruntPasse;
    }

    public String getDcEmpruntPasseOu() {
        return DcEmpruntPasseOu;
    }

    public void setDcEmpruntPasseOu(String dcEmpruntPasseOu) {
        DcEmpruntPasseOu = dcEmpruntPasseOu;
    }

    public String getDcIsEchRembResp() {
        return DcIsEchRembResp;
    }

    public void setDcIsEchRembResp(String dcIsEchRembResp) {
        DcIsEchRembResp = dcIsEchRembResp;
    }

    public String getDcPourqRembSiNon() {
        return DcPourqRembSiNon;
    }

    public void setDcPourqRembSiNon(String dcPourqRembSiNon) {
        DcPourqRembSiNon = dcPourqRembSiNon;
    }

    public String getDcPretEnCours() {
        return DcPretEnCours;
    }

    public void setDcPretEnCours(String dcPretEnCours) {
        DcPretEnCours = dcPretEnCours;
    }

    public String getDcPretInstit() {
        return DcPretInstit;
    }

    public void setDcPretInstit(String dcPretInstit) {
        DcPretInstit = dcPretInstit;
    }

    public String getDcNaturePret() {
        return DcNaturePret;
    }

    public void setDcNaturePret(String dcNaturePret) {
        DcNaturePret = dcNaturePret;
    }

    public String getDcMtPrincPret() {
        return DcMtPrincPret;
    }

    public void setDcMtPrincPret(String dcMtPrincPret) {
        DcMtPrincPret = dcMtPrincPret;
    }

    public String getDcMtRestARemb() {
        return DcMtRestARemb;
    }

    public void setDcMtRestARemb(String dcMtRestARemb) {
        DcMtRestARemb = dcMtRestARemb;
    }

    public String getDcMtPretSollicite() {
        return DcMtPretSollicite;
    }

    public void setDcMtPretSollicite(String dcMtPretSollicite) {
        DcMtPretSollicite = dcMtPretSollicite;
    }

    public String getDcDescriptPret() {
        return DcDescriptPret;
    }

    public void setDcDescriptPret(String dcDescriptPret) {
        DcDescriptPret = dcDescriptPret;
    }

    public String getDcDureeRemb() {
        return DcDureeRemb;
    }

    public void setDcDureeRemb(String dcDureeRemb) {
        DcDureeRemb = dcDureeRemb;
    }

    public String getDcDatePremEch() {
        return DcDatePremEch;
    }

    public void setDcDatePremEch(String dcDatePremEch) {
        DcDatePremEch = dcDatePremEch;
    }

    public String getDcPeriodRemb() {
        return DcPeriodRemb;
    }

    public void setDcPeriodRemb(String dcPeriodRemb) {
        DcPeriodRemb = dcPeriodRemb;
    }

    public String getDcNbJrDiffere() {
        return DcNbJrDiffere;
    }

    public void setDcNbJrDiffere(String dcNbJrDiffere) {
        DcNbJrDiffere = dcNbJrDiffere;
    }

    public String getDcGarantiesProp() {
        return DcGarantiesProp;
    }

    public void setDcGarantiesProp(String dcGarantiesProp) {
        DcGarantiesProp = dcGarantiesProp;
    }

    public String getDcIsAvaliste1() {
        return DcIsAvaliste1;
    }

    public void setDcIsAvaliste1(String dcIsAvaliste1) {
        DcIsAvaliste1 = dcIsAvaliste1;
    }

    public String getDcMtSoldAvalist1() {
        return DcMtSoldAvalist1;
    }

    public void setDcMtSoldAvalist1(String dcMtSoldAvalist1) {
        DcMtSoldAvalist1 = dcMtSoldAvalist1;
    }

    public String getDcIsAvaliste2() {
        return DcIsAvaliste2;
    }

    public void setDcIsAvaliste2(String dcIsAvaliste2) {
        DcIsAvaliste2 = dcIsAvaliste2;
    }

    public String getDcMtSoldAvalist2() {
        return DcMtSoldAvalist2;
    }

    public void setDcMtSoldAvalist2(String dcMtSoldAvalist2) {
        DcMtSoldAvalist2 = dcMtSoldAvalist2;
    }

    public String getDcIsAvaliste3() {
        return DcIsAvaliste3;
    }

    public void setDcIsAvaliste3(String dcIsAvaliste3) {
        DcIsAvaliste3 = dcIsAvaliste3;
    }

    public String getDcMtSoldAvalist3() {
        return DcMtSoldAvalist3;
    }

    public void setDcMtSoldAvalist3(String dcMtSoldAvalist3) {
        DcMtSoldAvalist3 = dcMtSoldAvalist3;
    }

    public String getDcIsBesoinsAFin() {
        return DcIsBesoinsAFin;
    }

    public void setDcIsBesoinsAFin(String dcIsBesoinsAFin) {
        DcIsBesoinsAFin = dcIsBesoinsAFin;
    }

    public String getDcMtTotalBesoins() {
        return DcMtTotalBesoins;
    }

    public void setDcMtTotalBesoins(String dcMtTotalBesoins) {
        DcMtTotalBesoins = dcMtTotalBesoins;
    }

    public String getDcMotivationPret() {
        return DcMotivationPret;
    }

    public void setDcMotivationPret(String dcMotivationPret) {
        DcMotivationPret = dcMotivationPret;
    }

    public String getDcIsPhotocopieCNI() {
        return DcIsPhotocopieCNI;
    }

    public void setDcIsPhotocopieCNI(String dcIsPhotocopieCNI) {
        DcIsPhotocopieCNI = dcIsPhotocopieCNI;
    }

    public String getDcIsCpteExploit() {
        return DcIsCpteExploit;
    }

    public void setDcIsCpteExploit(String dcIsCpteExploit) {
        DcIsCpteExploit = dcIsCpteExploit;
    }

    public String getDcIsDmdeManuscr() {
        return DcIsDmdeManuscr;
    }

    public void setDcIsDmdeManuscr(String dcIsDmdeManuscr) {
        DcIsDmdeManuscr = dcIsDmdeManuscr;
    }

    public String getDcIsPceGarantie() {
        return DcIsPceGarantie;
    }

    public void setDcIsPceGarantie(String dcIsPceGarantie) {
        DcIsPceGarantie = dcIsPceGarantie;
    }

    public String getDcIsAutresDocum() {
        return DcIsAutresDocum;
    }

    public void setDcIsAutresDocum(String dcIsAutresDocum) {
        DcIsAutresDocum = dcIsAutresDocum;
    }

    public String getDcAutresDocum() {
        return DcAutresDocum;
    }

    public void setDcAutresDocum(String dcAutresDocum) {
        DcAutresDocum = dcAutresDocum;
    }

    public String getDcIsSignatAdh() {
        return DcIsSignatAdh;
    }

    public void setDcIsSignatAdh(String dcIsSignatAdh) {
        DcIsSignatAdh = dcIsSignatAdh;
    }

    public String getDcDateSignat() {
        return DcDateSignat;
    }

    public void setDcDateSignat(String dcDateSignat) {
        DcDateSignat = dcDateSignat;
    }

    public String getDcAgentCredit() {
        return DcAgentCredit;
    }

    public void setDcAgentCredit(String dcAgentCredit) {
        DcAgentCredit = dcAgentCredit;
    }

    public String getDcObservAgCred() {
        return DcObservAgCred;
    }

    public void setDcObservAgCred(String dcObservAgCred) {
        DcObservAgCred = dcObservAgCred;
    }

    public String getDcAvisAgCredit() {
        return DcAvisAgCredit;
    }

    public void setDcAvisAgCredit(String dcAvisAgCredit) {
        DcAvisAgCredit = dcAvisAgCredit;
    }

    public String getDcDateDecisiion() {
        return DcDateDecisiion;
    }

    public void setDcDateDecisiion(String dcDateDecisiion) {
        DcDateDecisiion = dcDateDecisiion;
    }

    public String getDcDecisionComGes() {
        return DcDecisionComGes;
    }

    public void setDcDecisionComGes(String dcDecisionComGes) {
        DcDecisionComGes = dcDecisionComGes;
    }

    public String getDcMtAccorde() {
        return DcMtAccorde;
    }

    public void setDcMtAccorde(String dcMtAccorde) {
        DcMtAccorde = dcMtAccorde;
    }

    public String getDcDureeAccord() {
        return DcDureeAccord;
    }

    public void setDcDureeAccord(String dcDureeAccord) {
        DcDureeAccord = dcDureeAccord;
    }

    public String getDcMotivations() {
        return DcMotivations;
    }

    public void setDcMotivations(String dcMotivations) {
        DcMotivations = dcMotivations;
    }

    public String getDcEtapeCredit() {
        return DcEtapeCredit;
    }

    public void setDcEtapeCredit(String dcEtapeCredit) {
        DcEtapeCredit = dcEtapeCredit;
    }

    public String getDcDateHCree() {
        return DcDateHCree;
    }

    public void setDcDateHCree(String dcDateHCree) {
        DcDateHCree = dcDateHCree;
    }

    public String getDcDetails() {
        return DcDetails;
    }

    public void setDcDetails(String dcDetails) {
        DcDetails = dcDetails;
    }

    public String getDcMtDetteHorsBilan() {
        return DcMtDetteHorsBilan;
    }

    public void setDcMtDetteHorsBilan(String dcMtDetteHorsBilan) {
        DcMtDetteHorsBilan = dcMtDetteHorsBilan;
    }

    public String getDcTotPayeDetteHorsBilan() {
        return DcTotPayeDetteHorsBilan;
    }

    public void setDcTotPayeDetteHorsBilan(String dcTotPayeDetteHorsBilan) {
        DcTotPayeDetteHorsBilan = dcTotPayeDetteHorsBilan;
    }

    public String getDcUserCree() {
        return DcUserCree;
    }

    public void setDcUserCree(String dcUserCree) {
        DcUserCree = dcUserCree;
    }

    public String getDcGuichet() {
        return DcGuichet;
    }

    public void setDcGuichet(String dcGuichet) {
        DcGuichet = dcGuichet;
    }

    public String getDcNumAvaliste1() {
        return DcNumAvaliste1;
    }

    public void setDcNumAvaliste1(String dcNumAvaliste1) {
        DcNumAvaliste1 = dcNumAvaliste1;
    }

    public String getDcNumAvaliste2() {
        return DcNumAvaliste2;
    }

    public void setDcNumAvaliste2(String dcNumAvaliste2) {
        DcNumAvaliste2 = dcNumAvaliste2;
    }

    public String getDcNumAvaliste3() {
        return DcNumAvaliste3;
    }

    public void setDcNumAvaliste3(String dcNumAvaliste3) {
        DcNumAvaliste3 = dcNumAvaliste3;
    }

    public String getDcCodeDebl() {
        return DcCodeDebl;
    }

    public void setDcCodeDebl(String dcCodeDebl) {
        DcCodeDebl = dcCodeDebl;
    }

    public String getDcDetailsDebl() {
        return DcDetailsDebl;
    }

    public void setDcDetailsDebl(String dcDetailsDebl) {
        DcDetailsDebl = dcDetailsDebl;
    }

    public String getDcCmNumero() {
        return DcCmNumero;
    }

    public void setDcCmNumero(String dcCmNumero) {
        DcCmNumero = dcCmNumero;
    }
}
