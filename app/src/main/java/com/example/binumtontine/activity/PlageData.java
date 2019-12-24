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
package com.example.binumtontine.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlageData extends AppCompatActivity implements  SERVER_ADDRESS {
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
    private static final String KEY_CV_GUICHET = "CvGuichet";
    private static final String KEY_CV_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_CV_MT_SOLDE = "CvMtSolde";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";

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

    private RadioButton rbEtTypTxInterFixe;
    private RadioButton rbEtTypTxInterTaux;
    private RadioButton rbEtTypTxInterPlageFixe;
    private RadioButton rbEtTypTxInterPlageTaux;


    /* manage spinner*/

    private TextView tvGuichetDenomination;

    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialogInitialisationCaisseGuichet;
    private TextInputLayout ET_input_layout_ValeurTaluxInteretEAT;
    private LinearLayout LL_blk_EtPlageTxInter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plage_data);


        guichetId = String.valueOf(MyData.GUICHET_ID);
        guichetDenomination = MyData.GUICHET_NAME;
/*
        mtInitialisationEditText = (EditText) findViewById(R.id.input_txt_montant_initialisation);


        tvGuichetDenomination = (TextView) findViewById(R.id.tv_denomination_guichet);
        tvGuichetDenomination.setText(guichetDenomination);
*/
        rbEtTypTxInterFixe = (RadioButton) findViewById(R.id.rb_EtTypTxInterFixe);

        rbEtTypTxInterTaux = (RadioButton) findViewById(R.id.rb_EtTypTxInterTaux);
        rbEtTypTxInterPlageFixe = (RadioButton) findViewById(R.id.rb_EtTypTxInterPlageFixe);
        rbEtTypTxInterPlageTaux = (RadioButton) findViewById(R.id.rb_EtTypTxInterPlageTaux);
        LL_blk_EtPlageTxInter = (LinearLayout) findViewById(R.id.blk_EtPlageTxInter);
        ET_input_layout_ValeurTaluxInteretEAT = (TextInputLayout) findViewById(R.id.input_layout_ValeurTaluxInteretEAT);
        rbEtTypTxInterFixe.performClick(); // le positionner en dessous des autres


        addButton = (Button) findViewById(R.id.btn_save_plage_data);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(PlageData.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    //initialisationCaisseGuichet();
                } else {
                    Toast.makeText(PlageData.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }




    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_EtTypTxInterFixe:
                if (rbEtTypTxInterFixe.isChecked()) {

                    //EtNaturePas ="F";
                    //ET_input_layout_ValeurTaluxInteretEAT = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    LL_blk_EtPlageTxInter.setVisibility(View.GONE);
                    ET_input_layout_ValeurTaluxInteretEAT.setVisibility(View.GONE);
                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.rb_EtTypTxInterTaux:
                if (rbEtTypTxInterTaux.isChecked()){
                    LL_blk_EtPlageTxInter.setVisibility(View.GONE);
                    ET_input_layout_ValeurTaluxInteretEAT.setVisibility(View.VISIBLE);
                   // EtNaturePas ="S";
               /*     ET_input_layout_ValeurTaluxInteretEAT = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="T";
                    LL_blk_EtPlageTxInter.setVisibility(View.INVISIBLE);
                layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                */
                }


                break;
            case R.id.rb_EtTypTxInterPlageFixe:
                if (rbEtTypTxInterPlageFixe.isChecked()) {
                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
                    ET_input_layout_ValeurTaluxInteretEAT.setVisibility(View.GONE);
                   // EtTypTxInter ="F";
                    /*
                    ET_input_layout_ValeurTaluxInteretEAT = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);

                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rb_EtTypTxInterPlageTaux:
                if (rbEtTypTxInterPlageTaux.isChecked()) {
                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
                    ET_input_layout_ValeurTaluxInteretEAT.setVisibility(View.VISIBLE);
                   // EtTypTxInter ="P";
                    /*
                    ET_input_layout_ValeurTaluxInteretEAT = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
        }
        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(PlageData.this,
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
            pDialogInitialisationCaisseGuichet = new ProgressDialog(PlageData.this);
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

            httpParams.put(KEY_CV_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CV_MT_SOLDE, guichetMontantInitialisation);
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eav_adherent.php", "POST", httpParams);
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
                        Toast.makeText(PlageData.this,
                                "Caisse guichet initialisé avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(PlageData.this,
                                "Some error occurred while initialization caisse guichet",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}