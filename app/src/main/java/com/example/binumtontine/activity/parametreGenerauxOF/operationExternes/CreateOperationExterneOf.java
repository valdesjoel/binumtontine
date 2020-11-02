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
package com.example.binumtontine.activity.parametreGenerauxOF.operationExternes;


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
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.OperationExterne;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateOperationExterneOf extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_ED_CODE = "EdCode";
    private static final String KEY_ED_IS_ON = "EdIsOn";
    private static final String KEY_ED_LIBELLE = "EdLibelle";
    private static final String KEY_ED_NUM_ORDRE = "EdNumOrdr";
    private static final String KEY_ED_TYP_ETAPE = "EdTypEtape";

    private EditText ET_OeCode;
    private EditText ET_OeLibelle;
    private EditText EdNumOrdreEditText;
    private Spinner spinnerOeType;

    private RadioButton rbOeIsOnOn;
    private RadioButton rbOeIsOnOff;


    private String OeCode;
    private String OeLibelle;
    private String EdNumOrdre;
    private String OeIsOn;
    private String OeType;

    private static String STRING_EMPTY = "";


    private Button addButton;
    private Button annulerButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;
    OperationExterne monOE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_externe_of);

        ET_OeCode = (EditText) findViewById(R.id.input_txt_OeCode);
        ET_OeLibelle = (EditText) findViewById(R.id.input_txt_Libelle_operation_externe);

        rbOeIsOnOn = (RadioButton) findViewById(R.id.rb_OeIsOn_On);
        rbOeIsOnOff = (RadioButton) findViewById(R.id.rb_OeIsOn_Off);
        onRadioButtonClicked(rbOeIsOnOn);


        spinnerOeType = (Spinner) findViewById(R.id.spn_OeType);
        spinnerOeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                OeType = spinnerOeType.getSelectedItem().toString();

                if (spinnerOeType.getSelectedItem().toString().equals("Charge")){
                    OeType = "C";
                }else if (spinnerOeType.getSelectedItem().toString().equals("Produit")){
                    OeType = "P";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });
//


        deleteButton = (Button) findViewById(R.id.btn_delete);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateOperationExterneOf.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addOperationExterne();
                } else {
                    Toast.makeText(CreateOperationExterneOf.this,
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
            case R.id.rb_OeIsOn_On:
                if (rbOeIsOnOn.isChecked()) {
                    str = checked1?"Opération activée":"";
                    OeIsOn ="Y";
                }
                break;
            case R.id.rb_OeIsOn_Off:
                if (rbOeIsOnOff.isChecked()){
                    str = checked1?"Opération désactivée":"";
                    OeIsOn ="N";
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddOperationExterneAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addOperationExterne() {

if (!STRING_EMPTY.equals(ET_OeCode.getText().toString().trim()) &&
        !STRING_EMPTY.equals(ET_OeLibelle.getText().toString().trim())
        ){
monOE = new OperationExterne();
monOE.setOeCode(MyData.normalizeSymbolsAndAccents(ET_OeCode.getText().toString()));
monOE.setOeLibelle(MyData.normalizeSymbolsAndAccents(ET_OeLibelle.getText().toString()));
monOE.setOeType(OeType);
monOE.setOeIsOn(OeIsOn);

            new AddOperationExterneAsyncTask().execute();
        } else {
            Toast.makeText(CreateOperationExterneOf.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddOperationExterneAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateOperationExterneOf.this);
            pDialog.setMessage("Ajout d'une opération externe. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(OperationExterne.KEY_OeCode, monOE.getOeCode());
            httpParams.put(OperationExterne.KEY_OeLibelle, monOE.getOeLibelle());
            httpParams.put(OperationExterne.KEY_OeType, monOE.getOeType());
            httpParams.put(OperationExterne.KEY_OeIsOn, monOE.getOeIsOn());
            httpParams.put(OperationExterne.KEY_OeUserCree, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_operation_externe_of.php", "POST", httpParams);
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
                        Toast.makeText(CreateOperationExterneOf.this,
                                "Opération externe Ajoutée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateOperationExterneOf.this,
                                "Une erreur s'est produite lors de l'ajout de l'Opération",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}