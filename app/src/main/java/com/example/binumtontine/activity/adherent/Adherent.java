package com.example.binumtontine.activity.adherent;

import com.example.binumtontine.modele.InscripAdherentFrais;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Create a class file named Adherent.java
 * This model class will be useful to convert json data into objects.
 */
public class Adherent implements Serializable {


    public static final String KEY_AD_AdNumero = "AdNumero";
    public static final String KEY_AD_AdCode = "AdCode";
    public static final String KEY_AD_AdNumManuel = "AdNumManuel";
    public static final String KEY_AD_AdNom = "AdNom";
    public static final String KEY_AD_AdPrenom = "AdPrenom";
    public static final String KEY_AD_AdEMail = "AdEMail";
    public static final String KEY_AdTel1 = "AdTel1";
    public static final String KEY_AdTotNbPartSoc = "AdTotNbPartSoc";

    private String AdNumero;
    private String AdCode;
    private String AdDetailsTypeMembre;
    private String AdNumManuel;
    private String AdNom;
    private String AdPrenom;
    private String AdDateNaiss;
    private String AdLieuNaiss;
    private String AdSexe;
    private String AdNationalite;
    private String AdSitFam;
    private String AdNbreEnfACh;
    private String AdTel1;
    private String AdTel2;
    private String AdTel3;
    private String AdEMail;
    private String AdProfess;
    private String AdDomicile;
    private String AdLieuTrav;
    private String AdActivitePr;
    private String AdTypCarteID;
    private String AdNumCarteID;
    private String AdValideDu;
    private String AdValideAu;
    private String AdTypHabite;
    private String AdEstParti;
    private String AdPartiLe;
    private String AdRemplacePar;
    private int AdGuichet;
    private String  AdNbreCompte;
    private String  AdTotNbPartSoc;

    //for manage accounts's adherent
    private String libelle_produit="";
    private String numero_dossier="";
    private String dateHCree="";
    private String montant_solde="";

    private ArrayList<String> listPieceAdherent = new ArrayList<>();
    private ArrayList<String> listPieceNonFourniesAdherent = new ArrayList<>();
//    private ArrayList<InscripAdherentFrais> listFraisAdherent = new ArrayList<>();
    private ArrayList<EditModel> listFraisAdherent = new ArrayList<>();


    public String getLibelleProduit() {
        return libelle_produit;
    }

    public void setLibelleProduit(String libelle_produit) {
        this.libelle_produit = libelle_produit;
    }

    public String getNumeroDossier() {
        return numero_dossier;
    }

    public void setNumeroDossier(String numero_dossier) {
        this.numero_dossier = numero_dossier;
    }

    public String getDateHCree() {
        return dateHCree;
    }

    public void setDateHCree(String dateHCree) {
        this.dateHCree = dateHCree;
    }

    public String getMontantSolde() {
        return montant_solde;
    }

    public void setMontantSolde(String montant_solde) {
        this.montant_solde = montant_solde;
    }

    public String getAdDetailsTypeMembre() {
        return AdDetailsTypeMembre;
    }

    public void setAdDetailsTypeMembre(String adDetailsTypeMembre) {
        this.AdDetailsTypeMembre = adDetailsTypeMembre;
    }

    public Adherent(){}

    public Adherent(String adNumero, String adCode, String adNumManuel, String adNom, String adPrenom,
                    String adDateNaiss, String adLieuNaiss, String adSexe, String adNationalite,
                    String adSitFam, String adNbreEnfACh, String adTel1, String adTel2, String adTel3,
                    String adEMail, String adProfess, String adDomicile, String adLieuTrav,
                    String adActivitePr, String adTypCarteID, String adNumCarteID,
                    String adValideDu, String adValideAu, String adTypHabite, String adEstParti,
                    String adPartiLe, String adRemplacePar, int adGuichet) {
        this.AdNumero = adNumero;
        this.AdCode = adCode;
        this.AdNumManuel = adNumManuel;
        this.AdNom = adNom;
        this.AdPrenom = adPrenom;
        this.AdDateNaiss = adDateNaiss;
        this.AdLieuNaiss = adLieuNaiss;
        this.AdSexe = adSexe;
        this.AdNationalite = adNationalite;
        this.AdSitFam = adSitFam;
        this.AdNbreEnfACh = adNbreEnfACh;
        this.AdTel1 = adTel1;
        this.AdTel2 = adTel2;
        this.AdTel3 = adTel3;
        this.AdEMail = adEMail;
        this.AdProfess = adProfess;
        this.AdDomicile = adDomicile;
        this.AdLieuTrav = adLieuTrav;
        this.AdActivitePr = adActivitePr;
        this.AdTypCarteID = adTypCarteID;
        this.AdNumCarteID = adNumCarteID;
        this.AdValideDu = adValideDu;
        this.AdValideAu = adValideAu;
        this.AdTypHabite = adTypHabite;
        this.AdEstParti = adEstParti;
        this.AdPartiLe = adPartiLe;
        this.AdRemplacePar = adRemplacePar;
        this.AdGuichet = adGuichet;
    }
    public Adherent(String adCode, String adNumManuel, String adNom, String adPrenom,
                    String adDateNaiss, String adLieuNaiss, String adSexe, String adNationalite,
                    String adSitFam, String adNbreEnfACh, String adTel1, String adTel2, String adTel3,
                    String adEMail, String adProfess, String adDomicile, String adLieuTrav,
                    String adActivitePr, String adTypCarteID, String adNumCarteID,
                    String adValideDu, String adValideAu, String adTypHabite, String adEstParti,
                    String adPartiLe, String adRemplacePar, int adGuichet) {
        this.AdCode = adCode;
        this.AdNumManuel = adNumManuel;
        this.AdNom = adNom;
        this.AdPrenom = adPrenom;
        this.AdDateNaiss = adDateNaiss;
        this.AdLieuNaiss = adLieuNaiss;
        this.AdSexe = adSexe;
        this.AdNationalite = adNationalite;
        this.AdSitFam = adSitFam;
        this.AdNbreEnfACh = adNbreEnfACh;
        this.AdTel1 = adTel1;
        this.AdTel2 = adTel2;
        this.AdTel3 = adTel3;
        this.AdEMail = adEMail;
        this.AdProfess = adProfess;
        this.AdDomicile = adDomicile;
        this.AdLieuTrav = adLieuTrav;
        this.AdActivitePr = adActivitePr;
        this.AdTypCarteID = adTypCarteID;
        this.AdNumCarteID = adNumCarteID;
        this.AdValideDu = adValideDu;
        this.AdValideAu = adValideAu;
        this.AdTypHabite = adTypHabite;
        this.AdEstParti = adEstParti;
        this.AdPartiLe = adPartiLe;
        this.AdRemplacePar = adRemplacePar;
        this.AdGuichet = adGuichet;
    }

    public Adherent(String adNumero, String adCode, String adNumManuel, String adNom, String adPrenom,
                    String adDateNaiss, String adLieuNaiss, String adSexe, String adNationalite,
                    String adSitFam, String adNbreEnfACh, String adTel1, String adTel2, String adTel3,
                    String adEMail, String adProfess, String adDomicile, String adLieuTrav,
                    String adActivitePr, String adTypCarteID, String adNumCarteID,
                    String adValideDu, String adValideAu, String adTypHabite, String adEstParti,
                    String adPartiLe, String adRemplacePar, int adGuichet,String  adNbreCompte) {
        this.AdNumero = adNumero;
        this.AdCode = adCode;
        this.AdNumManuel = adNumManuel;
        this.AdNom = adNom;
        this.AdPrenom = adPrenom;
        this.AdDateNaiss = adDateNaiss;
        this.AdLieuNaiss = adLieuNaiss;
        this.AdSexe = adSexe;
        this.AdNationalite = adNationalite;
        this.AdSitFam = adSitFam;
        this.AdNbreEnfACh = adNbreEnfACh;
        this.AdTel1 = adTel1;
        this.AdTel2 = adTel2;
        this.AdTel3 = adTel3;
        this.AdEMail = adEMail;
        this.AdProfess = adProfess;
        this.AdDomicile = adDomicile;
        this.AdLieuTrav = adLieuTrav;
        this.AdActivitePr = adActivitePr;
        this.AdTypCarteID = adTypCarteID;
        this.AdNumCarteID = adNumCarteID;
        this.AdValideDu = adValideDu;
        this.AdValideAu = adValideAu;
        this.AdTypHabite = adTypHabite;
        this.AdEstParti = adEstParti;
        this.AdPartiLe = adPartiLe;
        this.AdRemplacePar = adRemplacePar;
        this.AdGuichet = adGuichet;
        this.AdNbreCompte = adNbreCompte;
    }

    public String getAdNumero() {
        return this.AdNumero;
    }
/*
    public void setAdNumero(int adNumero) {
        AdNumero = adNumero;
    }
    */

    public String getAdCode() {
        return this.AdCode;
    }

    public void setAdCode(String adCode) {
        this.AdCode = adCode;
    }

    public String getAdNumManuel() {
        return this.AdNumManuel;
    }

    public void setAdNumManuel(String adNumManuel) {
        this.AdNumManuel = adNumManuel;
    }

    public String getAdNom() {
        return this.AdNom;
    }

    public void setAdNom(String adNom) {
        this.AdNom = adNom;
    }

    public String getAdPrenom() {
        return this.AdPrenom;
    }

    public void setAdPrenom(String adPrenom) {
        this.AdPrenom = adPrenom;
    }

    public String getAdDateNaiss() {
        return this.AdDateNaiss;
    }

    public void setAdDateNaiss(String adDateNaiss) {
        this.AdDateNaiss = adDateNaiss;
    }

    public String getAdLieuNaiss() {
        return this.AdLieuNaiss;
    }

    public void setAdLieuNaiss(String adLieuNaiss) {
        this.AdLieuNaiss = adLieuNaiss;
    }

    public String getAdSexe() {
        return this.AdSexe;
    }

    public void setAdSexe(String adSexe) {
        this.AdSexe = adSexe;
    }

    public String getAdNationalite() {
        return this.AdNationalite;
    }

    public void setAdNationalite(String adNationalite) {
        this.AdNationalite = adNationalite;
    }

    public String getAdSitFam() {
        return this.AdSitFam;
    }

    public void setAdSitFam(String adSitFam) {
        this.AdSitFam = adSitFam;
    }

    public String getAdNbreEnfACh() {
        return this.AdNbreEnfACh;
    }

    public void setAdNbreEnfACh(String adNbreEnfACh) {
        this.AdNbreEnfACh = adNbreEnfACh;
    }

    public String getAdTel1() {
        return this.AdTel1;
    }

    public void setAdTel1(String adTel1) {
        this.AdTel1 = adTel1;
    }

    public String getAdTel2() {
        return this.AdTel2;
    }

    public void setAdTel2(String adTel2) {
        this.AdTel2 = adTel2;
    }

    public String getAdTel3() {
        return this.AdTel3;
    }

    public void setAdTel3(String adTel3) {
        this.AdTel3 = adTel3;
    }

    public String getAdEMail() {
        return this.AdEMail;
    }

    public void setAdEMail(String adEMail) {
        this.AdEMail = adEMail;
    }

    public String getAdProfess() {
        return this.AdProfess;
    }

    public void setAdProfess(String adProfess) {
        this.AdProfess = adProfess;
    }

    public String getAdDomicile() {
        return this.AdDomicile;
    }

    public void setAdDomicile(String adDomicile) {
        this.AdDomicile = adDomicile;
    }

    public String getAdLieuTrav() {
        return this.AdLieuTrav;
    }

    public void setAdLieuTrav(String adLieuTrav) {
        this.AdLieuTrav = adLieuTrav;
    }

    public String getAdActivitePr() {
        return this.AdActivitePr;
    }

    public void setAdActivitePr(String adActivitePr) {
        this.AdActivitePr = adActivitePr;
    }

    public String getAdTypCarteID() {
        return this.AdTypCarteID;
    }

    public void setAdTypCarteID(String adTypCarteID) {
        this.AdTypCarteID = adTypCarteID;
    }

    public String getAdNumCarteID() {
        return this.AdNumCarteID;
    }

    public void setAdNumCarteID(String adNumCarteID) {
        this.AdNumCarteID = adNumCarteID;
    }

    public String getAdValideDu() {
        return this.AdValideDu;
    }

    public void setAdValideDu(String adValideDu) {
        this.AdValideDu = adValideDu;
    }

    public String getAdValideAu() {
        return this.AdValideAu;
    }

    public void setAdValideAu(String adValideAu) {
        this.AdValideAu = adValideAu;
    }

    public String getAdTypHabite() {
        return this.AdTypHabite;
    }

    public void setAdTypHabite(String adTypHabite) {
        this.AdTypHabite = adTypHabite;
    }

    public String getAdEstParti() {
        return this.AdEstParti;
    }

    public void setAdEstParti(String adEstParti) {
        this.AdEstParti = adEstParti;
    }

    public String getAdPartiLe() {
        return this.AdPartiLe;
    }

    public void setAdPartiLe(String adPartiLe) {
        this.AdPartiLe = adPartiLe;
    }

    public String getAdRemplacePar() {
        return this.AdRemplacePar;
    }

    public void setAdRemplacePar(String adRemplacePar) {
        this.AdRemplacePar = adRemplacePar;
    }

    public int getAdGuichet() {
        return AdGuichet;
    }

    public void setAdGuichet(int adGuichet) {
        this.AdGuichet = adGuichet;
    }

    public String getAdTotNbPartSoc() {
        return AdTotNbPartSoc;
    }

    public void setAdTotNbPartSoc(String adTotNbPartSoc) {
        AdTotNbPartSoc = adTotNbPartSoc;
    }

    public ArrayList<String> getListPieceAdherent() {
        return listPieceAdherent;
    }
    public void addListPiecedherent (String libelle_piece){
        this.listPieceAdherent.add(libelle_piece);
    }

    public void setListPieceAdherent(ArrayList<String> listPieceAdherent) {
        this.listPieceAdherent = listPieceAdherent;
    }

    public ArrayList<String> getListPieceNonFourniesAdherent() {
        return listPieceNonFourniesAdherent;
    }

    public void setListPieceNonFourniesAdherent(ArrayList<String> listPieceNonFourniesAdherent) {
        this.listPieceNonFourniesAdherent = listPieceNonFourniesAdherent;
    }

    public void addListPieceNonFourniesAdherent (String libelle_piece){
        this.listPieceNonFourniesAdherent.add(libelle_piece);
    }

    public ArrayList<EditModel> getListFraisAdherent() {
        return listFraisAdherent;
    }

    public void setListFraisAdherent(ArrayList<EditModel> listFraisAdherent) {
        this.listFraisAdherent = listFraisAdherent;
    }

    public void addListFraisAdherent (EditModel inscripAdherentFrais){
        this.listFraisAdherent.add(inscripAdherentFrais);
    }

    public String getAdNbreCompte() {
        return this.AdNbreCompte;
    }

    public void setAdNbreCompte(String adNbreCompte) {
        this.AdNbreCompte = adNbreCompte;
    }



    public String getFullName() {
        return this.AdNom+ " "+this.AdPrenom ;
    }

    /**
     * Permet de calculer l'âge d'un adhérent à partir de sa date de naissance
     * @param year: represente l'année de naissance de l'adhérent
     * @param month: represente le mois de naissance de l'adhérent
     * @param day: represente le jour de naissance de l'adhérent
     * @return
     */
    /*private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }*/
    /*public int getAge(int year, int month, int dayOfMonth) {
        return Period.between(
                LocalDate.of(year, month, dayOfMonth),
                LocalDate.now()
        ).getYears();
    }*/

    /**
     * Permet de calculer l'âge d'un adhérent à partir de sa date de naissance
     * @return retourne son age en entier
     */
    public int getAge(){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = sdf.parse(this.getAdDateNaiss());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }



        return age;
    }

    /**
     * To get libelle sexe
     * @return return libelle sexe (Masculin for M or Féminin for F)
     */
    public String getSexeLibelle(){
        String sexeLibelle = "";
        if (this.AdSexe!=null && this.AdSexe.equals("M") ){
            sexeLibelle = "Masculin";
        }else{
            sexeLibelle = "Féminin";
        }
        return sexeLibelle;
    }
    /**
     * To get libelle Type Location
     * @return return libelle Type Location (Propriétaire = P ; Locataire = L ; Crédit bail = C )
     */
    public String getLibelleTypeLocation(){
        String typeLocation = "";
        if (this.AdTypHabite!=null && this.AdTypHabite.equals("P") ){
            typeLocation = "Propriétaire";
        }else if (this.AdTypHabite!=null && this.AdTypHabite.equals("L") ){
            typeLocation = "Locataire";
        }else{
            typeLocation = "Crédit bail";
        }
        return typeLocation;
    }
    /**
     * To get libelle Situation Matrimoniale
     * @return return libelle Situation Matrimoniale (Célibataire = C or Marié for M; Divorcé = D; Veuf = V )
     */
    public String getLibelleSituationMat(){
        String situationMatrimoniale = "";
        if (this.AdSitFam!=null && this.AdSitFam.equals("C") ){
            situationMatrimoniale = "Célibataire";
        }else if (this.AdSitFam!=null && this.AdSitFam.equals("M") ){
            situationMatrimoniale = "Marié";
        }else if (this.AdSitFam!=null && this.AdSitFam.equals("D") ){
            situationMatrimoniale = "Divorcé";
        }else{ //Veuf = V
            situationMatrimoniale = "Veuf";
        }
        return situationMatrimoniale;
    }
    /**
     * To get libelle Type Piece
     * @return return libelle Type Piece ()
     */
    public String getLibelleTypePiece(){
        String libelleTypePiece = "";
         if (this.AdTypCarteID!=null && this.AdTypCarteID.equals("CN") ){
            libelleTypePiece = "Carte nationale d'identité";
        }else if (this.AdTypCarteID!=null && this.AdTypCarteID.equals("CS") ){
            libelleTypePiece = "Carte de séjour";
        }else if (this.AdTypCarteID!=null && this.AdTypCarteID.equals("CC") ){
            libelleTypePiece = "Carte consulaire";
        }else if (this.AdTypCarteID!=null && this.AdTypCarteID.equals("CM") ){
            libelleTypePiece = "Carte militaire";
        }else if (this.AdTypCarteID!=null && this.AdTypCarteID.equals("PS") ){
            libelleTypePiece = "Carte CNPS";
        }else if (this.AdTypCarteID!=null && this.AdTypCarteID.equals("PC") ){
            libelleTypePiece = "Permis de conduire";
        }else if (this.AdTypCarteID!=null && this.AdTypCarteID.equals("PP") ){
            libelleTypePiece = "Passeport";
        }
        return libelleTypePiece;
    }

    /**
     * Permet d'encoder les Type Piece sur 2 caractères avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeAdTypCarteID(String base_decode){
        String base_encode ="";
        if (base_decode.equals("Carte nationale d'identité")){
            base_encode = "CN";
        }else if (base_decode.equals("Carte de séjour")){
            base_encode = "CS";
        }else if (base_decode.equals("Carte consulaire")){
            base_encode = "CC";
        }else if (base_decode.equals("Carte militaire")){
            base_encode = "CM";
        }else if (base_decode.equals("Carte CNPS")){
            base_encode = "PS";
        }else if (base_decode.equals("Permis de conduire")){
            base_encode = "PC";
        }else if (base_decode.equals("Passeport")){
            base_encode = "PP";
        }
        return base_encode;
    }
    /**
     * Permet d'encoder les Type de location sur 1 caractère avant de stocker en BD
     * @param base_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeAdTypHabite(String base_decode){
        String base_encode ="";
        if (base_decode.equals("Propriétaire")){
            base_encode = "P";
        }else if (base_decode.equals("Locataire")){
            base_encode = "L";
        }else if (base_decode.equals("Crédit bail")){
            base_encode = "C";
        }
        return base_encode;
    }

    /**
     * Permet d'encoder la situation matrimoniale sur 1 caractère avant de stocker en BD
     * @param adSitFam_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeAdSitFam(String adSitFam_decode){
        String base_encode ="";
        if (adSitFam_decode.equals("Célibataire")){
            base_encode = "C";
        }else if (adSitFam_decode.equals("Marié")){
            base_encode = "M";
        }else if (adSitFam_decode.equals("Divorcé")){
            base_encode = "D";
        }else if (adSitFam_decode.equals("Veuf")){
            base_encode = "V";
        }
        return base_encode;
    }


    /**
     * Permet d'encoder le sexe sur 1 caractère avant de stocker en BD
     * @param adSexe_decode chaine de caractères à encoder
     * @return
     */
    public static String encodeAdSexe(String adSexe_decode){
        String base_encode ="";
        if (adSexe_decode.equals("Masculin")){
            base_encode = "M";
        }else if (adSexe_decode.equals("Féminin")){
            base_encode = "F";
        }
        return base_encode;
    }

}
