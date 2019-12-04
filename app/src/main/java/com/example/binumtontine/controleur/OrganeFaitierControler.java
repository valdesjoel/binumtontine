package com.example.binumtontine.controleur;

import android.content.Context;

import com.example.binumtontine.activity.UpdateOrganeFaitier;
import com.example.binumtontine.fragment.UpdateOFFragment;
//import com.example.binumtontine.vue.CreateOrganeFaitier;
import com.example.binumtontine.dao.Serializer;
import com.example.binumtontine.modele.AccesDistant;
import com.example.binumtontine.modele.OrganeFaitier_modele;

import org.json.JSONArray;

import static java.security.AccessController.getContext;

public final class OrganeFaitierControler {
    private static OrganeFaitierControler instance = null;
    private static OrganeFaitier_modele organeFaitier_modele;
    //  private static AccesLocalSQLite accesLocalSQLite; // accès aux méthodes d'accès à la bd (les CRUD )
    private static AccesDistant accesDistant; //accès à une bd distante
    private static String nomFichier = "saveOrganeFaitier";
    private static Context contexte;

    private OrganeFaitierControler(){
        super();
    }

    public static final OrganeFaitierControler getInstance(Context context){
        if (context!=null){
            OrganeFaitierControler.contexte=context;
        }
        if(OrganeFaitierControler.instance==null){
            OrganeFaitierControler.instance = new OrganeFaitierControler();
            //   accesLocalSQLite = new AccesLocalSQLite(context);
            accesDistant = new AccesDistant();
            //    organeFaitier_modele = accesLocalSQLite.recupDernierOF_In_BD();
            accesDistant.envoi("dernier",new JSONArray());
            //recupereSerializeOF(context); //deserialise un OF
        }
        return OrganeFaitierControler.instance;
    }

    public void creerOrganeFaitier(Integer numero, String sigle, String libelle, String num_agrement,
                                   String date_agrement, Integer boite_postale, String ville_of,
                                   String pays_of, String adresse_of, String telephone1_of,String telephone2_of,String telephone3_of,
                                   String siege_of, String nom_pca_of, String nom_vpca_of, String nom_dg_of, Context context){
        //organeFaitier_modele = new OrganeFaitier_modele(sigle, libelle, num_agrement, date_agrement, boite_postale, ville_of, pays_of, adresse_of, telephone1_of, siege_of, nom_pca_of, nom_vpca_of, nom_dg_of);
        organeFaitier_modele = new OrganeFaitier_modele(numero, sigle, libelle, num_agrement, date_agrement, boite_postale, ville_of, pays_of, adresse_of, telephone1_of,telephone2_of,telephone3_of, siege_of, nom_pca_of, nom_vpca_of, nom_dg_of);
        // Serializer.serialize(nomFichier, organeFaitier_modele, context); //serialise un OF
        //accesLocalSQLite.ajout(organeFaitier_modele); //insertion dans SQL
        accesDistant.envoi("enreg",organeFaitier_modele.convertTOJSONArray());
    }
    private static void recupereSerializeOF(Context context){
        organeFaitier_modele = (OrganeFaitier_modele)Serializer.deSerialize(nomFichier,context);
    }
    public Integer getNumero(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getNumero();
        }
    }
    public String getSigle(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getSigle();
        }
    }
    public String getLibelle(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getLibelle();
        }
    }
    public String getNum_agrement(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getNum_agrement();
        }
    }

    public String getDate_agrement(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getDate_agrement();
        }
    }
    public Integer getBoite_postale(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getBoite_postale();
        }
    }
    public String getVille_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getVille_of();
        }
    }
    public String getPays_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getPays_of();
        }
    }

    public String getAdresse_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getAdresse_of();
        }
    }
    public String getTelephone1_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getTelephone1_of();
        }
    }
     public String getTelephone2_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getTelephone2_of();
        }
    }
     public String getTelephone3_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getTelephone3_of();
        }
    }

    public String getSiege_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getSiege_of();
        }
    }
    public String getNom_pca_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getNom_pca_of();
        }
    }
    public String getNom_vpca_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getNom_vpca_of();
        }
    }
    public String getNom_dg_of(){
        if (organeFaitier_modele==null){
            return null;
        }else {
            return organeFaitier_modele.getNom_dg_of();
        }
    }


    public void setOrganeFaitier_modele(OrganeFaitier_modele paramOrganeFaitier_modele){
        OrganeFaitierControler.organeFaitier_modele = paramOrganeFaitier_modele;

        ((UpdateOrganeFaitier)contexte).recupOrganeFaitier();
    }
}
