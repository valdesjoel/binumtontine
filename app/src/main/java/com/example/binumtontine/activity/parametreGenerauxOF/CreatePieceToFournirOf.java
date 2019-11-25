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
package com.example.binumtontine.activity.parametreGenerauxOF;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreatePieceToFournirOf extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_FP_CODE = "FpCode";
    private static final String KEY_FP_LIBELLE = "FpLibelle";
    private static final String KEY_FP_TYPE_ADH = "FpTypeAdh";




    private static String STRING_EMPTY = "";

    private EditText FpCodeEditText;
    private EditText FpLibelleEditText;
    private RadioButton rbTypeAdherentPhysique;
    private RadioButton rbTypeAdherentMorale;


    private String FpCode;
    private String FpLibelle;
    private String FpTypeAdh;






    private Button addButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_piece_to_fournir_of);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */



        FpCodeEditText = (EditText) findViewById(R.id.input_txt_Code_pg_piece_of);
        FpLibelleEditText = (EditText) findViewById(R.id.input_txt_Libelle_pg_piece_of);
        //FpTypeEditText = (EditText) findViewById(R.id.input_pg);
        rbTypeAdherentPhysique = (RadioButton) findViewById(R.id.rb_type_adherent_pg_piece_of_physique);
        rbTypeAdherentMorale = (RadioButton) findViewById(R.id.rb_type_adherent_pg_piece_of_morale);


        deleteButton = (Button) findViewById(R.id.btn_delete_pg_piece_of);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_pg_piece_of);
        //cirLoginButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEAV();
                } else {
                    Toast.makeText(CreatePieceToFournirOf.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'une pièce à fournir");

    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_type_adherent_pg_piece_of_physique:
                if (rbTypeAdherentPhysique.isChecked()) {
                    str = checked1?"Personne Physique Selected":"Personne Physique Deselected";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                //    FpTypeAdh="P";

                }

                break;
            case R.id.rb_type_adherent_pg_piece_of_morale:
                if (rbTypeAdherentMorale.isChecked()){
                    str = checked1?"Personne Morale Selected":"Personne Morale Deselected";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                  //  FpTypeAdh ="M";

        }


                break;

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddEAVAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEAV() {
       /* if (!STRING_EMPTY.equals(FpCodeEditText.getText().toString()) &&

                !STRING_EMPTY.equals(FpLibelleEditText.getText().toString()) &&
                !STRING_EMPTY.equals(FpValEditText.getText().toString()) &&
                !STRING_EMPTY.equals(FpBaseSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(FpTypeAdhEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_multi_eav_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_paie_ps_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_agios_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_typ_fr_agiosEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_mt_tx_agios_prelevEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_plage_agios_fromEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_plage_agios_toEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_cheque_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_frais_clot_cptEditText.getText().toString()) &&

                !STRING_EMPTY.equals(FpTypeEditText.getText().toString()) &&
                !STRING_EMPTY.equals(FpNature.getText().toString())) { */
if (true){
            FpCode = FpCodeEditText.getText().toString();
            FpLibelle = FpLibelleEditText.getText().toString();
            if (rbTypeAdherentPhysique.isChecked()){
                FpTypeAdh ="P";
            }else FpTypeAdh="M";




            new AddEAVAsyncTask().execute();
        } else {
            Toast.makeText(CreatePieceToFournirOf.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddEAVAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreatePieceToFournirOf.this);
            pDialog.setMessage("Adding new Pièce. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_FP_CODE, FpCode);
            httpParams.put(KEY_FP_LIBELLE, FpLibelle);
            httpParams.put(KEY_FP_TYPE_ADH,FpTypeAdh );

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_piece_of.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(CreatePieceToFournirOf.this,
                                "Pièce Ajoutée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreatePieceToFournirOf.this,
                                "Some error occurred while adding Pièce",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}