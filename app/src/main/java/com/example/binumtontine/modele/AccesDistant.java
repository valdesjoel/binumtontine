package com.example.binumtontine.modele;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;


import com.example.binumtontine.R;
import com.example.binumtontine.controleur.OrganeFaitierControler;
import com.example.binumtontine.dao.AccesHTTP;
import com.example.binumtontine.dao.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AccesDistant implements AsyncResponse {
//    private static final String SERVER = Context.getString(R.string.server_address);
    //propriétés
//private static final String BASE_URL = "http://binumt.diff-itc.net/binumTontine/";
   // private static final String SERVEUR_ADDR = "http://192.168.1.102/binumTontine/serveurBinumt.php";
    private static final String SERVEUR_ADDR = "http://binumt.diff-itc.net/binumTontine/serveurBinumt.php";
    private OrganeFaitierControler organeFaitierControler;

    public AccesDistant(){
        organeFaitierControler = OrganeFaitierControler.getInstance(null);
    }
    /**
     * retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        Log.d("serveur","***************"+output);
        //découpage du message reçu avec %
        String[] message = output.split("%");
        //dans message[0]: "enreg", "dernier", "Erreur !"
        //dans message[1] : reste du message

        //s'il y'a deux cases
        if (message.length>1){
            if (message[0].equals("enreg")){
                Log.d("enreg","*********************"+message[1]);
            }else {
                if (message[0].equals("dernier")){
                    Log.d("dernier","*********************"+message[1]);
                    try {
                        JSONObject info = new JSONObject(message[1]);
                        Integer numero_of = info.getInt("OfNumero");
                        String sigle = info.getString("OfSigle");
                        String libelle = info.getString("OfNom");
                        String num_agrement = info.getString("OfNumAgrem");
                        String date_agrement= info.getString("OfDateAgrem");
                        Integer boite_postale = info.getInt("OfBP");
                        String ville_of= info.getString("OfVille");
                        String pays_of = info.getString("OfPays");
                        String adresse_of = info.getString("OfAdresse");
                        String telephone1_of = info.getString("OfTel1");
                        String siege_of = info.getString("OfSiege");
                        String nom_pca_of = info.getString("OfNomPCA");
                        String nom_vpca_of = info.getString("OfNomVPCA");
                        String nom_dg_of = info.getString("OfNomDG");

                        OrganeFaitier_modele organeFaitier_modele = new OrganeFaitier_modele(numero_of,
                                sigle,libelle,num_agrement,date_agrement,boite_postale,ville_of,
                                pays_of, adresse_of, telephone1_of, siege_of, nom_pca_of, nom_vpca_of, nom_dg_of
                        );
                        organeFaitierControler.setOrganeFaitier_modele(organeFaitier_modele);

                    } catch (JSONException e) {
                        Log.d("erreur","***************************conversion JSON impossible "+e.toString());
                    }

                }else {
                    if (message[0].equals("Erreur !")){
                        Log.d("Erreur !","*********************"+message[1]);

                    }
                }
            }
        }

    }

    public void envoi(String operation, JSONArray lesDonneesJSON){
        AccesHTTP accesDonneesHTTP = new AccesHTTP();
        //lien de delegation
        accesDonneesHTTP.delegate = this;
        //ajout paramètres
        accesDonneesHTTP.addParam("operation",operation);
        accesDonneesHTTP.addParam("lesdonnees",lesDonneesJSON.toString());

        //appel au serveur
        accesDonneesHTTP.execute(SERVEUR_ADDR);
    }
}
