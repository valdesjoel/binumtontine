package com.example.binumtontine.activity.parametrageGuichet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.AboutUsActivity;
import com.example.binumtontine.activity.CaisseActivity;
import com.example.binumtontine.activity.PrivacyPolicyActivity;
import com.example.binumtontine.activity.ProduitCpteCourantActivity;
import com.example.binumtontine.activity.ProduitCreditActivity;
import com.example.binumtontine.activity.ProduitEAPActivity;
import com.example.binumtontine.activity.ProduitEATActivity;
import com.example.binumtontine.activity.ProduitEAVActivity;
import com.example.binumtontine.activity.UsersCaisseActivity;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.fragment.UpdateOFFragment;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProduitGuichetFragment extends AppCompatActivity implements View.OnClickListener, SERVER_ADDRESS {

    /* Begin */
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_PRE_PARAM_ID = "pre_numero";
    private static final String KEY_PRE_PARAM_EAV = "pre_eav";
    private static final String KEY_PRE_PARAM_EAT = "pre_eat";
    private static final String KEY_PRE_PARAM_EAP = "pre_eap";
    private static final String KEY_PRE_PARAM_CC = "pre_compte_courant";
    private static final String KEY_PRE_PARAM_CREDIT = "pre_credit";
    private static final String KEY_PRE_PARAM_TRANSFERT_CLASS = "pre_transfert_classique";
    private static final String KEY_PRE_PARAM_TRANSFERT_MTN = "pre_transfert_mtn";
    private static final String KEY_PRE_PARAM_TRANSFERT_ORANGE = "pre_transfert_orange";
    private static final String KEY_PRE_PARAM_TRANSFERT_EU = "pre_transfert_eu";
    private static final String KEY_PRE_PARAM_OPERATION_EXTERNE = "pre_operation_externe";
    private static final String KEY_PRE_PARAM_APPELLATION_PRODUIT = "pre_appellation_produit";



    private static String STRING_EMPTY = "";

    private CheckBox eav, eap, eat, cptecourant, credit, transfert_classique,
            transfert_to_mtn,transfert_to_orange, transfert_eu, op_externe ;
    //private CheckBox appellationProduitEpargne;
    private RadioButton appellationProduitEpargne;
    private RadioButton appellationProduitDepot;

    private String preParamId="1"; //car on a un seul enregistrement un BD donc son ID est connu

    private Boolean preEAV;
    private Boolean preEAT;
    private Boolean preEAP;
    private Boolean preCpteCourant;
    private Boolean preCredit;
    private Boolean preTransfertClassique;
    private Boolean preTransfertMTN;
    private Boolean preTransfertOrange;
    private Boolean preTransfertEU;
    private Boolean preOperationExterne;
    private Boolean preAppellationProduitEpargne;
    private Boolean preAppellationProduitDepot;

    private Button btnPreParam;
    private int success;
    private ProgressDialog pDialog;

    /* End */



    private  CardView produitEAVCard;
    private  CardView produitEAPCard;
    private  CardView produitEATCard;
    private  CardView produitCC_Card;
    private  CardView produitTransfert_Card;
    private  CardView produitCredit_Card;

    private  CardView produitTransfert_MTN_Card;
    private  CardView produitTransfert_ORANGE_Card;
    private  CardView produitTransfert_EU_Card;
    private  CardView produit_Op_Ext_Card;

    /* Pour gérer l'appellation des produits */
    private TextView tvEAV;
    private TextView tvEAP;
    private TextView tvEAT;
    private TextView tvFooterEAV;
    private TextView tvFooterEAP;
    private TextView tvFooterEAT;

    public ProduitGuichetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_produit_caisse);

        tvEAV = (TextView) findViewById(R.id.tv_eav);
        tvEAP = (TextView) findViewById(R.id.tv_eap);
        tvEAT = (TextView) findViewById(R.id.tv_eat);

        tvFooterEAV = (TextView) findViewById(R.id.tv_footer_eav);
        tvFooterEAP = (TextView) findViewById(R.id.tv_footer_eap);
        tvFooterEAT = (TextView) findViewById(R.id.tv_footer_eat);


        produitEAVCard = (CardView) findViewById(R.id.produitEAV_card);
        produitCC_Card = (CardView) findViewById(R.id.produitCC_card);
        produitEATCard = (CardView) findViewById(R.id.produitEAT_card);
        produitEAPCard = (CardView) findViewById(R.id.produitEAP_card);
        produitTransfert_Card = (CardView) findViewById(R.id.produitTransfert_card);
        produitCredit_Card = (CardView) findViewById(R.id.produitCredit_card);

        produitTransfert_MTN_Card = (CardView) findViewById(R.id.produitTransfert_MTN_card);
        produitTransfert_ORANGE_Card = (CardView) findViewById(R.id.produitTransfert_ORANGE_card);
        produitTransfert_EU_Card = (CardView) findViewById(R.id.produitTransfert_EU_card);
        produit_Op_Ext_Card = (CardView) findViewById(R.id.produitOperation_externe_card);

        produitEAVCard.setOnClickListener(this);
        produitCC_Card.setOnClickListener(this);
        produitEATCard.setOnClickListener(this);
        produitEAPCard.setOnClickListener(this);
        produitTransfert_Card.setOnClickListener(this);
        produitCredit_Card.setOnClickListener(this);

        produitTransfert_MTN_Card.setOnClickListener(this);
        produitTransfert_ORANGE_Card.setOnClickListener(this);
        produitTransfert_EU_Card.setOnClickListener(this);
        produit_Op_Ext_Card.setOnClickListener(this);
        new ProduitGuichetFragment.FetchMovieDetailsAsyncTask().execute();

    }











    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.produitEAV_card :
                i = new Intent(getBaseContext(), ProduitEAVGuichetActivity.class);
              //  i = new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
                break;
            case R.id.produitEAP_card :
                i = new Intent(getBaseContext(), ProduitEAPGuichetActivity.class);
                startActivity(i);
                break;
            case R.id.produitEAT_card :
              //  i = new Intent(getBaseContext(), ProduitEATActivity.class);
                i = new Intent(getBaseContext(), ProduitEATGuichetActivity.class);
                startActivity(i);
                break;
            case R.id.produitCC_card :
                i = new Intent(getBaseContext(), ProduitCpteCourantGuichetActivity.class);
                startActivity(i);
                break;
            case R.id.produitCredit_card :
                i = new Intent(getBaseContext(), ProduitCreditActivity.class);
                startActivity(i);
                break;
            case R.id.majOF_card :

                AppCompatActivity activityMajOf = (AppCompatActivity) v.getContext();
                Fragment myFragmentMajOF = new UpdateOFFragment();
               // activityMajOf.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragmentMajOF).addToBackStack(null).commit();

                break;
            case R.id.caisse_card :
                i = new Intent(getBaseContext(), CaisseActivity.class);
                startActivity(i);
                break;
            case R.id.user_card :
               /* AppCompatActivity activityUserCard = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FriendsFragment();
                activityUserCard.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragment).addToBackStack(null).commit();*/
                i = new Intent(getBaseContext(), UsersCaisseActivity.class);
                startActivity(i);
                break;
            case R.id.preParam_card :
                i = new Intent(getBaseContext(), PrivacyPolicyActivity.class);
                startActivity(i);
                break;
            case R.id.param_piece_frais_of_card:
                i = new Intent(getBaseContext(), AboutUsActivity.class);
                startActivity(i);
                break;

        }

    }

    /**
     * Fetches single preParamProduit details from the server
     */
    private class FetchMovieDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ProduitGuichetFragment.this);
           // pDialog = new ProgressDialog(getBaseContext());
            pDialog.setMessage("Loading Pre Paramètres Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_PRE_PARAM_ID, preParamId);

            //  HttpJsonParser httpJsonParser = new HttpJsonParser();
            /*JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_user.php", "GET", null);*/
            /*JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_pre_parametrage_details.php", "GET", null);*/


            try {
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        BASE_URL + "get_pre_parametrage_details.php", "GET", httpParams);
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject preParamProduit;
                if (success == 1) {
                    //Parse the JSON response
                    preParamProduit = jsonObject.getJSONObject(KEY_DATA);
                    preEAV = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_EAV));
                    preEAT = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_EAT));
                    preEAP = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_EAP));
                    preCpteCourant = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_CC));
                    preCredit = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_CREDIT));
                    preTransfertClassique = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_TRANSFERT_CLASS));
                    preTransfertMTN = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_TRANSFERT_MTN));
                    preTransfertOrange = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_TRANSFERT_ORANGE));
                    preTransfertEU = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_TRANSFERT_EU));
                    preOperationExterne = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_OPERATION_EXTERNE));
                    preAppellationProduitEpargne = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_APPELLATION_PRODUIT));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                   /* eav.setChecked(preEAV);
                    eat.setChecked(preEAT);
                    eap.setChecked(preEAP);
                    cptecourant.setChecked(preCpteCourant);
                    credit.setChecked(preCredit);
                    transfert_classique.setChecked(preTransfertClassique);
                    transfert_to_mtn.setChecked(preTransfertMTN);
                    transfert_to_orange.setChecked(preTransfertOrange);
                    transfert_eu.setChecked(preTransfertEU);
                    op_externe.setChecked(preOperationExterne);
                    appellationProduitEpargne.setChecked(preAppellationProduitEpargne);
                    appellationProduitDepot.setChecked(!preAppellationProduitEpargne);*/

                    produitEAVCard.setClickable(preEAV);
                    produitEATCard.setClickable(preEAT);
                    produitEAPCard.setClickable(preEAP);
                    produitCC_Card.setClickable(preCpteCourant);
                    produitTransfert_Card.setClickable(preTransfertClassique);
                    produitTransfert_MTN_Card.setClickable(preTransfertMTN);
                    produitTransfert_ORANGE_Card.setClickable(preTransfertOrange);
                    produitTransfert_EU_Card.setClickable(preTransfertEU);
                    produit_Op_Ext_Card.setClickable(preOperationExterne);
                    Resources resources=getResources();
                    String[] produitName = resources.getStringArray(R.array.array_produit);

                    if (!preAppellationProduitEpargne){
                        tvEAV.setText(produitName[0]);
                        tvEAP.setText(produitName[1]);
                        tvEAT.setText(produitName[2]);
                        tvFooterEAV.setText(produitName[3]);
                        tvFooterEAP.setText(produitName[4]);
                        tvFooterEAT.setText(produitName[5]);

                    }

                    /* prévoir une fonction pour gérer l'appellation */
                    //produit_Op_Ext_Card.setClickable(preOperationExterne);
                }
            });
        }


    }
}