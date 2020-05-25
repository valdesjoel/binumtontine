/*package com.example.binumtontine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import eav.os.Bundle;

import com.example.binumtontine.R;

public class CreateProduitEAV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_param_produit_eav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAV");

    }
}*/
package com.example.binumtontine.activity.adherent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InitialisationCaisseGuichet extends AppCompatActivity implements  SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_EV_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";
    /* end*/
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";

    private static final String KEY_CV_PRODUIT = "CvProduit";
    private static final String KEY_CV_MEMBRE = "CvMembre";
    private static final String KEY_CG_GUICHET = "CgGuichet";
    private static final String KEY_CV_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_CG_LAST_SOLDE = "CgLastSolde";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_CM_ID = "CgNumero";

    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";



    private static String STRING_EMPTY = "";

    private EditText mtInitialisationEditText;


    private String guichetId;
    private String guichetMontantInitialisation;
    private String guichetDenomination;


    /* manage spinner*/

    private TextView tvGuichetDenomination;

    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialogInitialisationCaisseGuichet;
    private ProgressDialog pDialogFetchLastSolde;
    private String CgNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialisation_caisse_guichet);


        guichetId = String.valueOf(MyData.GUICHET_ID);
        guichetDenomination = MyData.GUICHET_NAME;
        mtInitialisationEditText = (EditText) findViewById(R.id.input_txt_montant_initialisation);


        tvGuichetDenomination = (TextView) findViewById(R.id.tv_denomination_guichet);
        tvGuichetDenomination.setText(guichetDenomination);




        addButton = (Button) findViewById(R.id.btn_save_montant_initialisation);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(InitialisationCaisseGuichet.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    initialisationCaisseGuichet();
                } else {
                    Toast.makeText(InitialisationCaisseGuichet.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }




    /**
     * Fetches single caisse_guichet details from the server
     */
    private class FetchLastSoldeGuichetDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogFetchLastSolde = new ProgressDialog(InitialisationCaisseGuichet.this);
            pDialogFetchLastSolde.setMessage("Loading guichet Details. Please wait...");
            pDialogFetchLastSolde.setIndeterminate(false);
            pDialogFetchLastSolde.setCancelable(false);
            pDialogFetchLastSolde.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CG_GUICHET, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_caisse_guichet_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
//                    CgLastSolde = user.getString(KEY_CG_LAST_SOLDE);
                    guichetMontantInitialisation = user.getString(KEY_CG_LAST_SOLDE);
                    CgNumero = user.getString(KEY_CM_ID);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFetchLastSolde.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {

                    //Populate the Edit Texts once the network activity is finished executing
                    if (success == 0) {

                    }

                    //defaultFormat.setCurrency(Currency.getInstance("FCF"));
                  /*  CgLastSoldeTextView.setText(defaultFormat.format(parseDouble(CgLastSolde)));
                    //NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
                    CgDevise.toUpperCase();
                    CgLastSoldeTextView.setTypeface(null, Typeface.BOLD);
                    CgLastSoldeTextView.setText(CgLastSoldeTextView.getText()+ CgDevise);
                    */



                }
            });
        }


    }



    /**
     * Checks whether all files are filled. If so then calls InitialisationCaisseGuichetAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void initialisationCaisseGuichet() {
        if (!STRING_EMPTY.equals(mtInitialisationEditText.getText().toString()) &&
                Integer.parseInt(guichetId) !=0 ) {


                guichetMontantInitialisation = mtInitialisationEditText.getText().toString();

                new InitialisationCaisseGuichetAsyncTask().execute();


        } else {
            Toast.makeText(InitialisationCaisseGuichet.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class InitialisationCaisseGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialogInitialisationCaisseGuichet = new ProgressDialog(InitialisationCaisseGuichet.this);
            pDialogInitialisationCaisseGuichet.setMessage("Initialisation caisse guichet. Please wait...");
            pDialogInitialisationCaisseGuichet.setIndeterminate(false);
            pDialogInitialisationCaisseGuichet.setCancelable(false);
            pDialogInitialisationCaisseGuichet.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_EAV_ID, uxGuichetId);

            httpParams.put(KEY_CG_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CG_LAST_SOLDE, guichetMontantInitialisation);
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_caisse_guichet_new.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogInitialisationCaisseGuichet.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(InitialisationCaisseGuichet.this,
                                "Caisse guichet initialisé avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(InitialisationCaisseGuichet.this,
                                "Some error occurred while initialization caisse guichet",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}