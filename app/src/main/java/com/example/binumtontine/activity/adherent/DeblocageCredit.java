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
import com.example.binumtontine.modele.DemandeCreditModele;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DeblocageCredit extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    /*Begin*/
    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private Adherent adherent;
    /*Param for get extra*/
    private static final String KEY_COMPTE_ID = "Numero";

    private static String STRING_EMPTY = "";

    private EditText DcDetailsDebl_ET;
    private TextInputLayout til_DcDetailsDebl;


    private String compteId;
    private String DcDetailsDebl;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String compteSolde;
    private String DcCodeDebl;
    private String libelleProduit;

    /* manage spinner*/
    // array list for spinner adapter
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvCompteSolde;
    private TextView tvLibelleProduit;
    private TextView tvDcCodeDebl;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deblocage_credit);

        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);

        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        libelleProduit = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        adNom = adherent.getAdNom();
        adPrenom = adherent.getAdPrenom();
        adNumManuel = adherent.getAdNumManuel();
        adCode = adherent.getAdCode();

        DcDetailsDebl_ET = (EditText) findViewById(R.id.input_txt_DcDetailsDebl);
        til_DcDetailsDebl = (TextInputLayout) findViewById(R.id.input_layout_DcDetailsDebl);

        tvDcCodeDebl = (TextView) findViewById(R.id.tv_DcCodeDebl);
        tvDcCodeDebl.setText(generateCodeDeblocage());

        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);

        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
        tvCompteSolde = (TextView) findViewById(R.id.tv_solde_compte);
        tvCompteSolde.setText(compteSolde);

        tvLibelleProduit = (TextView) findViewById(R.id.tv_libelle_produit_adherent);
        tvLibelleProduit.setText(libelleProduit);



        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(DeblocageCredit.this,
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
                    Toast.makeText(DeblocageCredit.this,
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

private String generateCodeDeblocage(){
    String TrTypTransf ="DB";
    String dateOperation = new SimpleDateFormat("ss-ddMM-HHmm-",
            Locale.getDefault()).format(System.currentTimeMillis());
    long first14 = (long) (Math.random() * 10000L);
    return TrTypTransf+dateOperation+first14;
}
    /**
     * Checks whether all files are filled. If so then calls DeblocageCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEavAdherent() {
        if (!STRING_EMPTY.equals(DcDetailsDebl_ET.getText().toString().trim())
                 ) {
                DcDetailsDebl = DcDetailsDebl_ET.getText().toString().trim();
                DcCodeDebl = tvDcCodeDebl.getText().toString();
                avertissementDeblocage();
        } else {
            MyData.bipError();
            til_DcDetailsDebl.setError("Indiquer le détail du déblocage");
            til_DcDetailsDebl.requestFocus();
            Toast.makeText(DeblocageCredit.this,
                    "le détail du déblocage est vide!",
                    Toast.LENGTH_LONG).show();
            return;

        }
    }

    /**
     * Allows to make attention to take a note on code deblocage
     */
    public void avertissementDeblocage() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alerte !")
                .setMessage("Code déblocage: " + DcCodeDebl+
                        "\n\t\t Etes-vous sûr d'avoir déjà noté ce code car nécessaire lors du décaissement ?")
                .setNegativeButton("Non", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }

                })
                .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeblocageCredit.DeblocageCreditAsyncTask().execute();
                    }

                })
                .show();
    }

    /**
     * AsyncTask for adding deblocage credit
     */
    private class DeblocageCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(DeblocageCredit.this);
            pDialog.setMessage("Déblocage en cours. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(DemandeCreditModele.KEY_DcNumero, compteId);
            httpParams.put(DemandeCreditModele.KEY_DcCodeDebl, DcCodeDebl);
            httpParams.put(DemandeCreditModele.KEY_DcDetailsDebl, DcDetailsDebl);
//            httpParams.put(DemandeCreditModele.KEY_DcUserCree, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "deblocage_credit.php", "POST", httpParams);
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
                        Toast.makeText(DeblocageCredit.this,
                                "Opération réussie !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();
                    } else {
                        Toast.makeText(DeblocageCredit.this,
                                "Echec!\n Rééssayez ultérieurement ! ",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}