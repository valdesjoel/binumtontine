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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.MvmPartSocAdh;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IncreasePartSocAdhActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    /*Begin*/
    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private Adherent adherent;
    /*Param for get extra*/
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_ADHERENT_ID = "IpMembre";

    private static String STRING_EMPTY = "";

    private EditText MpDetails_ET;
    private EditText MpMtPartsSoc_ET;
    private TextInputLayout til_MpDetails;
    private TextInputLayout til_MpMtPartsSoc;


    private String compteId;
    private String adherentId;
    private String DcDetailsDebl;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String compteSolde;
    private String DcCodeDebl;
    private String MpNewNbPartsSoc;
    private String MpTypOper;
    private String MpModePaiement="C";

    /* manage spinner*/
    // array list for spinner adapter
    private TextView tvAdherentNom;
    private TextView tvMpNbPartsSoc;
    private TextView header_operation_eav_adherent;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvCompteSolde;
    private TextView tvMpNewNbPartsSoc;
    private TextView tvDcCodeDebl;
    /*end manage*/

    private Button addButton;
    private Button decrease;
    private Button increase;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private int minteger = 0;
    MvmPartSocAdh mvmPartSocAdh = new MvmPartSocAdh();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_part_soc_adh);

        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        MpTypOper = intent.getStringExtra(MvmPartSocAdh.KEY_MpTypOper);

        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        //MpNewNbPartsSoc = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        adNom = adherent.getAdNom();
        MpNewNbPartsSoc = adherent.getAdTotNbPartSoc();
        adPrenom = adherent.getAdPrenom();
        adNumManuel = adherent.getAdNumManuel();
        adCode = adherent.getAdCode();


        decrease =  findViewById(R.id.decrease);
        increase =  findViewById(R.id.increase);
        MpMtPartsSoc_ET = (EditText) findViewById(R.id.input_txt_MpMtPartsSoc);
        til_MpMtPartsSoc = (TextInputLayout) findViewById(R.id.input_layout_MpMtPartsSoc);
        MpMtPartsSoc_ET.addTextChangedListener(MyData.onTextChangedListener(MpMtPartsSoc_ET));

        MpDetails_ET = findViewById(R.id.input_txt_MpDetails);
        til_MpDetails = (TextInputLayout) findViewById(R.id.input_layout_MpDetails);


        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvMpNbPartsSoc = (TextView) findViewById(R.id.integer_number);
        header_operation_eav_adherent = (TextView) findViewById(R.id.header_operation_eav_adherent);
        if (MpTypOper.equals("R")){
            header_operation_eav_adherent.setText("REDUIRE PARTS SOCIALES");
        }

        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
        tvCompteSolde = (TextView) findViewById(R.id.tv_solde_compte);
        tvCompteSolde.setText(compteSolde);

        tvMpNewNbPartsSoc = (TextView) findViewById(R.id.tv_MpNewNbPartsSoc);
        tvMpNewNbPartsSoc.setText(MpNewNbPartsSoc);



        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(IncreasePartSocAdhActivity.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEavAdherent();
                } else {
                    Toast.makeText(IncreasePartSocAdhActivity.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    decreaseInteger(decrease);
                } else {
                    Toast.makeText(IncreasePartSocAdhActivity.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    increaseInteger(increase);
                } else {
                    Toast.makeText(IncreasePartSocAdhActivity.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

//        eavID = eavListID.get(position);//pour recuperer l'ID du guichet selectionnée

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
    private void increaseInteger(View view) {
        minteger += 1;
        display(minteger);

    }

    private void decreaseInteger(View view) {
        minteger -= 1;
        display(minteger);
    }

    private void display(Integer number) {
        /*val displayInteger = findViewById<View>(
                R.id.integer_number) as TextView*/
        tvMpNbPartsSoc.setText(""+number);
        //text = "" + number
    }
private String generateCodeDeblocage(){
    String TrTypTransf ="DB";
    String dateOperation = new SimpleDateFormat("ss-ddMM-HHmm-",
            Locale.getDefault()).format(System.currentTimeMillis());
    long first14 = (long) (Math.random() * 10000L);
    return TrTypTransf+dateOperation+first14;
}
    /**
     * Checks whether all files are filled. If so then calls MvmPartSocAdhAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEavAdherent() {
        if (!STRING_EMPTY.equals(MpDetails_ET.getText().toString().trim())
                 ) {

                if (!STRING_EMPTY.equals(MpMtPartsSoc_ET.getText().toString().trim())){

                    Double  h3;
                    h3 = Double.valueOf(MpMtPartsSoc_ET.getText().toString().replaceAll(",", "").trim());
                    MpMtPartsSoc_ET.setText(h3+"");

                    mvmPartSocAdh.setMpAdherent(Integer.parseInt(adherentId));
                    mvmPartSocAdh.setMpNbPartsSoc(Integer.parseInt(tvMpNbPartsSoc.getText().toString()));
                    mvmPartSocAdh.setMpMtPartsSoc(Double.parseDouble(MpMtPartsSoc_ET.getText().toString().trim()));
                    mvmPartSocAdh.setMpTypOper(MpTypOper);
                    mvmPartSocAdh.setMpDetails(MpDetails_ET.getText().toString().trim());
                    mvmPartSocAdh.setMpModePaiement(MpModePaiement);
                    new MvmPartSocAdhAsyncTask().execute();
                }else{
                    avertissementMpMtPartsSoc();
                }
        } else {
            MyData.bipError();
            til_MpDetails.setError("Indiquer le détail de l'opération");
            til_MpDetails.requestFocus();
            Toast.makeText(IncreasePartSocAdhActivity.this,
                    "le détail de l'opération est vide!",
                    Toast.LENGTH_LONG).show();
            return;

        }
    }

    /**
     * Allows to make attention to take a note on code deblocage
     */
    public void avertissementMpMtPartsSoc() {
        MyData.bipError();
        til_MpMtPartsSoc.setError("Indiquer le Montant équivalent du nombre des parts sociales");
        til_MpDetails.requestFocus();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alerte !")
                .setMessage("\tMontant équivalent du nombre des parts sociales absent ! " )
                .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }

    /**
     * AsyncTask for adding deblocage credit
     */
    private class MvmPartSocAdhAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(IncreasePartSocAdhActivity.this);
            pDialog.setMessage("Opération en cours. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(MvmPartSocAdh.KEY_MpAdherent, String.valueOf(mvmPartSocAdh.getMpAdherent()));
            httpParams.put(MvmPartSocAdh.KEY_MpNbPartsSoc, String.valueOf(mvmPartSocAdh.getMpNbPartsSoc()));
            httpParams.put(MvmPartSocAdh.KEY_MpMtPartsSoc, String.valueOf(mvmPartSocAdh.getMpMtPartsSoc()));
            httpParams.put(MvmPartSocAdh.KEY_MpTypOper, mvmPartSocAdh.getMpTypOper());
            httpParams.put(MvmPartSocAdh.KEY_MpUser, String.valueOf(MyData.USER_ID));
            httpParams.put(MvmPartSocAdh.KEY_MpDetails, mvmPartSocAdh.getMpDetails());
            httpParams.put(MvmPartSocAdh.KEY_MpModePaiement, mvmPartSocAdh.getMpModePaiement());
            httpParams.put(Adherent.KEY_AD_AdEMail, adherent.getAdEMail());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "operation_mvt_part_soc_adh.php", "POST", httpParams);
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
                        Toast.makeText(IncreasePartSocAdhActivity.this,
                                "Opération réussie !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();
                    } else {
                        Toast.makeText(IncreasePartSocAdhActivity.this,
                                "Echec!\n Rééssayez ultérieurement ! ",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}