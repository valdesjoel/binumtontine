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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreatePieceToFournirOf extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_FP_CODE = "FpCode";
    private static final String KEY_FP_LIBELLE = "FpLibelle";
    private static final String KEY_FP_TYPE_ADH = "FpTypeAdh";
    private static final String KEY_FC_CAT_ADH = "FpCategAdh";




    private static String STRING_EMPTY = "";

    private EditText FpCodeEditText;
    private EditText FpLibelleEditText;
    private RadioButton rbTypeAdherentPhysique;
    private RadioButton rbTypeAdherentMorale;

    private Spinner spinnerFonctionFrais;


    private String FpCode;
    private String FpLibelle;
    private String FpTypeAdh;






    private Button addButton;
    private Button annulerButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;
    private String FcTypeMembre;

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

        spinnerFonctionFrais = (Spinner) findViewById(R.id.spn_type_membre_fc);
        spinnerFonctionFrais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                // FcFonctionFrais = spinnerFonctionFrais.getSelectedItem().toString();//pour recuperer l'ID de la pièce selectionnée
                // your stuff here
                FcTypeMembre = spinnerFonctionFrais.getSelectedItem().toString();

//                if (spinnerFonctionFrais.getSelectedItem().toString().equals("Part sociale")){
//                    textInputLayoutFcNbrePartMin.setVisibility(View.VISIBLE);
//                    FcFonctionFrais = "P";
//
//                }else{
//                    textInputLayoutFcNbrePartMin.setVisibility(View.GONE);
//                    if (spinnerFonctionFrais.getSelectedItem().toString().equals("Frais d'adhésion")){
//                        FcFonctionFrais = "A";
//                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Fonds de solidarité")){
//                        FcFonctionFrais = "S";
//                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Approvisionnement")){
//                        FcFonctionFrais = "D";
//                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Frais de fonctionnement")){
//                        FcFonctionFrais = "F";
//                    }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });


        deleteButton = (Button) findViewById(R.id.btn_delete_pg_piece_of);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_pg_piece_of);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreatePieceToFournirOf.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addPieceOf();
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
     * Checks whether all files are filled. If so then calls AddPieceOfAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addPieceOf() {

if (!STRING_EMPTY.equals(FpCodeEditText.getText().toString()) &&
        !STRING_EMPTY.equals(FpLibelleEditText.getText().toString())){
            FpCode = FpCodeEditText.getText().toString();
            FpLibelle = FpLibelleEditText.getText().toString();
            if (rbTypeAdherentPhysique.isChecked()){
                FpTypeAdh ="P";
            }else FpTypeAdh="M";




            new AddPieceOfAsyncTask().execute();
        } else {
            Toast.makeText(CreatePieceToFournirOf.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a piece_of
     */
    private class AddPieceOfAsyncTask extends AsyncTask<String, String, String> {
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
            httpParams.put(KEY_FC_CAT_ADH, String.valueOf(FcTypeMembre));

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