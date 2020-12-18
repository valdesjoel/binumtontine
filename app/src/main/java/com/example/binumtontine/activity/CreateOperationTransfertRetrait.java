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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.Transfert;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateOperationTransfertRetrait extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static String STRING_EMPTY = "";
/*
    private EditText ET_OdMontant;
    private EditText ET_OdLibelle;

    private RadioButton rb_OdSensOper_Produits;
    private RadioButton rb_OdSensOper_Charges;
*/
    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> AdherentExpediteursListNom;
    private ArrayList<Category> AdherentExpediteursListPrenom;
    List<Integer> AdherentExpediteursListID = new ArrayList<Integer>();
    List<Integer> AdherentExpediteursListCvNumero = new ArrayList<Integer>();

    private ArrayList<Category> AdherentExpediteursListNomDest;
    private ArrayList<Category> AdherentExpediteursListPrenomDest;
    List<Integer> AdherentExpediteursListIDDest = new ArrayList<Integer>();
    List<Integer> AdherentExpediteursListCvNumeroDest = new ArrayList<Integer>();
    private Spinner spinnerListNomExp;
    private Spinner spinnerListNomDest;

    /*end manage*/


    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchTrnsaction;
/*OperationExterneDetails operationExterneDetails = new OperationExterneDetails();
    private String OeType;*/

    private Transfert transfert = new Transfert();
    private AppCompatEditText acetStatus;
    private ListPopupWindow statusPopupList;
    private EditText TrRefOper;
    private RadioButton rb_TrTypTransf_CC;
    private RadioButton rb_TrTypTransf_CE;
    private RadioButton rb_TrTypTransf_EC;
    private RadioButton rb_TrTypTransf_EE;
    private TextView TrMontant;
    private EditText TrMtFrais;
    private EditText TrMtTaxes;
    private TextView tv_total;

    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private TextView TrNomExp;
    private TextView TrPrenomExp;

    private TextView TrTypPieceIdExpTv;
    private TextView TrPieceIdExp;
    private TextView TrAdresseExp;
    private TextView TrCodeSecret;
    private TextView TrDetailsExp;
    private String TrTypPieceIdExp;
    private TextView TrNomDest;
    private TextView TrPrenomDest;

    private TextView TrTypPieceIdDestTv;
    private TextView TrPieceIdDest;
    private TextView TrAdresseDest;
    private EditText TrDetailsDest;
    private String TrTypPieceIdDest;
    private String TrTypTransf;
    private TextView tv_guichet_exp;
    private TextView tv_guichet_dest;
    private LinearLayout ll_content;
    private String numero_reference="";
    private TextView TrTelDest;
    private TextView TrTelExp;
    private TextView TrIsPaye;
    private TextView TrDateH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operation_transfert_retrait);
        getNumeroReferenceDialog(this);

        TrRefOper = (EditText)findViewById(R.id.input_TrRefOper);
        TrDateH = (TextView)findViewById(R.id.tv_TrDateH);
        TrMontant = (TextView)findViewById(R.id.tv_TrMontant);
        TrIsPaye = (TextView)findViewById(R.id.tv_TrIsPaye);
        TrNomExp = (TextView)findViewById(R.id.tv_TrNomExp);
        TrPrenomExp = (TextView)findViewById(R.id.tv_TrPrenomExp);
        TrTelExp = (TextView)findViewById(R.id.tv_TrTelExp);
        TrTypPieceIdExpTv = (TextView)findViewById(R.id.tv_TrTypPieceIdExp);
        TrPieceIdExp = (TextView)findViewById(R.id.tv_TrPieceIdExp);
        TrAdresseExp = (TextView)findViewById(R.id.tv_TrAdresseExp);
        TrCodeSecret = (TextView)findViewById(R.id.tv_TrCodeSecret);
        TrDetailsExp = (TextView)findViewById(R.id.tv_TrDetailsExp);
        TrNomDest = (TextView)findViewById(R.id.tv_TrNomDest);
        TrPrenomDest = (TextView)findViewById(R.id.tv_TrPrenomDest);
        TrTelDest = (TextView)findViewById(R.id.tv_TrTelDest);
        TrTypPieceIdDestTv = (TextView)findViewById(R.id.tv_TrTypPieceIdDest);
        TrPieceIdDest = (TextView)findViewById(R.id.tv_TrPieceIdDest);
        TrAdresseDest = (TextView)findViewById(R.id.tv_TrAdresseDest);
        TrDetailsDest = (EditText) findViewById(R.id.input_TrDetailsDest);
        tv_guichet_exp = (TextView)findViewById(R.id.tv_guichet_exp);
        tv_guichet_dest = (TextView)findViewById(R.id.tv_guichet_dest);
/*
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        TrRefOper = (EditText)findViewById(R.id.input_TrRefOper);
        rb_TrTypTransf_CC = (RadioButton) findViewById(R.id.rb_TrTypTransf_CC);
        rb_TrTypTransf_CE = (RadioButton) findViewById(R.id.rb_TrTypTransf_CE);
        rb_TrTypTransf_EC = (RadioButton) findViewById(R.id.rb_TrTypTransf_EC);
        rb_TrTypTransf_EE = (RadioButton) findViewById(R.id.rb_TrTypTransf_EE);
        TrMontant = (EditText) findViewById(R.id.input_txt_TrMontant);
        TrMontant.addTextChangedListener(MyData.onTextChangedListener(TrMontant));
        TrMtFrais = (EditText) findViewById(R.id.input_txt_TrMtFrais);
        TrMtTaxes = (EditText) findViewById(R.id.input_txt_TrMtTaxes);
        tv_total = (TextView) findViewById(R.id.tv_total);

        TrNomExp = (EditText) findViewById(R.id.input_TrNomExp);
        TrPrenomExp = (EditText) findViewById(R.id.input_TrPrenomExp);
        ccp_phone1 = (CountryCodePicker) findViewById(R.id.ccp_TrTelExp);
        editTextCarrierPhone1 = (EditText) findViewById(R.id.editText_carrierTrTelExp);
        ccp_phone1.registerCarrierNumberEditText(editTextCarrierPhone1);
        spinnerListNomExp = (Spinner) findViewById(R.id.spn_list_nom_exp);
        spinnerListNomDest = (Spinner) findViewById(R.id.spn_list_nom_dest);
        spinnerListNomExp.setOnItemSelectedListener(CreateOperationTransfertRetrait.this);
        spinnerListNomDest.setOnItemSelectedListener(CreateOperationTransfertRetrait.this);

        TrTypPieceIdExpSpinner = (JRSpinner)findViewById(R.id.spn_TrTypPieceIdExp);
        TrTypPieceIdExpSpinner.setItems(getResources().getStringArray(R.array.array_type_piece)); //this is important, you must set it to set the item list
        TrTypPieceIdExpSpinner.setTitle("Sélectionner une pièce d'identification"); //change title of spinner-dialog programmatically
        TrTypPieceIdExpSpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        TrTypPieceIdExpSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte nationale d'identité")){
                    TrTypPieceIdExp = "CN";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte de séjour")){
                    TrTypPieceIdExp = "CS";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte consulaire")){
                    TrTypPieceIdExp = "CC";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte militaire")){
                    TrTypPieceIdExp = "CM";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte CNPS")){
                    TrTypPieceIdExp = "PS";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Permis de conduire")){
                    TrTypPieceIdExp = "PC";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Passeport")){
                    TrTypPieceIdExp = "PP";
                }


            }
        });
        TrPieceIdExp = (EditText) findViewById(R.id.input_TrPieceIdExp);
        TrAdresseExp = (EditText) findViewById(R.id.input_TrAdresseExp);
        TrDetailsExp = (EditText) findViewById(R.id.input_TrDetailsExp);

        TrNomDest = (EditText) findViewById(R.id.input_TrNomDest);
        TrPrenomDest = (EditText) findViewById(R.id.input_TrPrenomDest);


        TrTypPieceIdDestSpinner = (JRSpinner)findViewById(R.id.spn_TrTypPieceIdDest);
        TrTypPieceIdDestSpinner.setItems(getResources().getStringArray(R.array.array_type_piece)); //this is important, you must set it to set the item list
        TrTypPieceIdDestSpinner.setTitle("Sélectionner une pièce d'identification"); //change title of spinner-dialog programmatically
        TrTypPieceIdDestSpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        TrTypPieceIdDestSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte nationale d'identité")){
                    TrTypPieceIdDest = "CN";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte de séjour")){
                    TrTypPieceIdDest = "CS";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte consulaire")){
                    TrTypPieceIdDest = "CC";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte militaire")){
                    TrTypPieceIdDest = "CM";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte CNPS")){
                    TrTypPieceIdDest = "PS";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Permis de conduire")){
                    TrTypPieceIdDest = "PC";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Passeport")){
                    TrTypPieceIdDest = "PP";
                }


            }
        });
        TrPieceIdDest = (EditText) findViewById(R.id.input_TrPieceIdDest);
        TrAdresseDest = (EditText) findViewById(R.id.input_TrAdresseDest);
        TrDetailsDest = (EditText) findViewById(R.id.input_TrDetailsDest);
        tv_guichet_exp = (TextView) findViewById(R.id.tv_guichet_exp);
        tv_guichet_exp.setText(MyData.GUICHET_NAME);

        ccp_phone2 = (CountryCodePicker) findViewById(R.id.ccp_TrTelDest);
        editTextCarrierPhone2 = (EditText) findViewById(R.id.editText_carrierTrTelDest);
        ccp_phone2.registerCarrierNumberEditText(editTextCarrierPhone2);

        new GetAdherentListExp().execute();
*/
        addButton = (Button) findViewById(R.id.btn_save);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateOperationTransfertRetrait.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addOperationRetrait();
                } else {
                    Toast.makeText(CreateOperationTransfertRetrait.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

private void getNumeroReferenceDialog(Context c) {
    final EditText taskEditText = new EditText(c);
    AlertDialog dialog = new AlertDialog.Builder(c)
            .setTitle("Numéro de la transaction:")
            .setMessage("Saisir le numéro de la transaction (*)")
            .setView(taskEditText)
            .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    numero_reference = String.valueOf(taskEditText.getText().toString().trim());
                    new CreateOperationTransfertRetrait.FetchTransactionByNumeroReferenceDetailsAsyncTask().execute();
                }
            })
            .setNegativeButton("Annuler", null)
            .create();
    dialog.show();
}
    public void onRadioButtonClicked(View view) {
//        boolean checked1 = ((RadioButton) view).isChecked();
//        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.rb_TrTypTransf_CC:
                if (rb_TrTypTransf_CC.isChecked()) {
                    ll_content.setVisibility(View.VISIBLE);
                    TrTypTransf ="CC";
                   // loadOperationExterneList();
                    long first14 = (long) (Math.random() * 100000000000000L);
                    TrRefOper.setText(TrTypTransf+first14);
                }
                break;
            case R.id.rb_TrTypTransf_CE:
                if (rb_TrTypTransf_CE.isChecked()) {
                    ll_content.setVisibility(View.VISIBLE);
                    TrTypTransf ="CE";
                    long first14 = (long) (Math.random() * 100000000000000L);
                    TrRefOper.setText(TrTypTransf+first14);
                }
                break;
            case R.id.rb_TrTypTransf_EC:
                if (rb_TrTypTransf_EC.isChecked()) {
                    ll_content.setVisibility(View.VISIBLE);
                    TrTypTransf ="EC";
                    long first14 = (long) (Math.random() * 100000000000000L);
                    TrRefOper.setText(TrTypTransf+first14);
                }
                break;
            case R.id.rb_TrTypTransf_EE:
                if (rb_TrTypTransf_EE.isChecked()) {
                    ll_content.setVisibility(View.VISIBLE);
                    TrTypTransf ="EE";
                    long first14 = (long) (Math.random() * 100000000000000L);
                    TrRefOper.setText(TrTypTransf+first14);
                }
                break;
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        List<String> lablesDest = new ArrayList<String>();

        //tvCaisse.setText("");

        try {
            for (int i = 0; i < AdherentExpediteursListNom.size(); i++) {
                lables.add(AdherentExpediteursListNom.get(i).getName()+ " " +AdherentExpediteursListNom.get(i).getTaux());//recupère les noms
                AdherentExpediteursListID.add(AdherentExpediteursListNom.get(i).getId()); //recupère les Id AdNumero
                AdherentExpediteursListCvNumero.add(AdherentExpediteursListNom.get(i).getFk()); //recupère les Id des compte eav
             lablesDest.add(AdherentExpediteursListNomDest.get(i).getName()+ " " +AdherentExpediteursListNomDest.get(i).getTaux());//recupère les noms
                AdherentExpediteursListIDDest.add(AdherentExpediteursListNomDest.get(i).getId()); //recupère les Id AdNumero
                AdherentExpediteursListCvNumeroDest.add(AdherentExpediteursListNomDest.get(i).getFk()); //recupère les Id des compte eav
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateOperationTransfertRetrait.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> spinnerAdapterDest = new ArrayAdapter<String>(CreateOperationTransfertRetrait.this,
                android.R.layout.simple_spinner_item, lablesDest);

        // Drop down layout style - list view with radio button
        spinnerAdapterDest
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListNomExp.setAdapter(spinnerAdapter);
        spinnerListNomDest.setAdapter(spinnerAdapterDest);
    }


    /**
     * Fetches single transction details from the server
     */
    private class FetchTransactionByNumeroReferenceDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogFetchTrnsaction = new ProgressDialog(CreateOperationTransfertRetrait.this);
            pDialogFetchTrnsaction.setMessage("Chargement en cours. SVP Patientez...");
            pDialogFetchTrnsaction.setIndeterminate(false);
            pDialogFetchTrnsaction.setCancelable(false);
            pDialogFetchTrnsaction.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(Transfert.KEY_TrRefOper, numero_reference);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_transaction_by_num_ref.php", "GET", httpParams);
            try {
//                int success = jsonObject.getInt(KEY_SUCCESS);
                success = jsonObject.getInt(KEY_SUCCESS);
                Log.e("get_transaction_by_num_ref ",jsonObject.toString());
                JSONObject transfertJSONObject;
                if (success == 1) {
                    //Parse the JSON response
                    transfertJSONObject = jsonObject.getJSONObject(KEY_DATA);

                    transfert.setTrNumero(transfertJSONObject.getInt(Transfert.KEY_TrNumero));
                    transfert.setTrRefOper(transfertJSONObject.getString(Transfert.KEY_TrRefOper));
                    transfert.setTrDateH(transfertJSONObject.getString(Transfert.KEY_TrDateH));
                    transfert.setTrTypTransf(transfertJSONObject.getString(Transfert.KEY_TrTypTransf));
                    transfert.setTrCptEAVExp(transfertJSONObject.getString(Transfert.KEY_TrCptEAVExp));
                    transfert.setTrNomExp(transfertJSONObject.getString(Transfert.KEY_TrNomExp));
                    transfert.setTrPrenomExp(transfertJSONObject.getString(Transfert.KEY_TrPrenomExp));
                    transfert.setTrTelExp(transfertJSONObject.getString(Transfert.KEY_TrTelExp));
                    transfert.setTrPieceIdExp(transfertJSONObject.getString(Transfert.KEY_TrPieceIdExp));
                    transfert.setTrTypPieceIdExp(transfertJSONObject.getString(Transfert.KEY_TrTypPieceIdExp));
                    transfert.setTrAdresseExp(transfertJSONObject.getString(Transfert.KEY_TrAdresseExp));
                    transfert.setTrDetailsExp(transfertJSONObject.getString(Transfert.KEY_TrDetailsExp));
                    transfert.setTrGuichetExp(transfertJSONObject.getString(Transfert.KEY_TrGuichetExp));
                    transfert.setTrAgentExp(transfertJSONObject.getString(Transfert.KEY_TrAgentExp));
                    transfert.setTrCptEAVDest(transfertJSONObject.getString(Transfert.KEY_TrCptEAVDest));
                    transfert.setTrNomDest(transfertJSONObject.getString(Transfert.KEY_TrNomDest));
                    transfert.setTrPrenomDest(transfertJSONObject.getString(Transfert.KEY_TrPrenomDest));
                    transfert.setTrPieceIdDest(transfertJSONObject.getString(Transfert.KEY_TrPieceIdDest));
                    transfert.setTrTypPieceIdDest(transfertJSONObject.getString(Transfert.KEY_TrTypPieceIdDest));
                    transfert.setTrAdresseDest(transfertJSONObject.getString(Transfert.KEY_TrAdresseDest));
                    transfert.setTrDetailsDest(transfertJSONObject.getString(Transfert.KEY_TrDetailsDest));
                    transfert.setTrMontant(transfertJSONObject.getString(Transfert.KEY_TrMontant));
                    transfert.setTrCodeSecret(transfertJSONObject.getString(Transfert.KEY_TrCodeSecret));
                    transfert.setTrIsPaye(transfertJSONObject.getString(Transfert.KEY_TrIsPaye));
//                    transfert.setTrGuichetDest(transfertJSONObject.getString(Transfert.KEY_TrGuichetDest));


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFetchTrnsaction.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Populate the Edit Texts once the network activity is finished executing
                        try {
//                        cx_denominationEditText.setText(cxDenomination);
                            notificationSuccessGetnumRef();
                            TrRefOper.setText(transfert.getTrRefOper());
                            TrDateH.setText(transfert.getTrDateH());
                            TrMontant.setText(transfert.getTrMontant());
                            TrIsPaye.setText(transfert.getTrIsPaye());
                            TrNomExp.setText(transfert.getTrNomExp());
                            TrPrenomExp.setText(transfert.getTrPrenomExp());
                            TrTelExp.setText(transfert.getTrTelExp());
                            TrTypPieceIdExpTv.setText(transfert.getTrTypPieceIdExp());
                            TrPieceIdExp.setText(transfert.getTrPieceIdExp());
                            TrAdresseExp.setText(transfert.getTrAdresseExp());
                            TrDetailsExp.setText(transfert.getTrDetailsExp());

                            TrNomDest.setText(transfert.getTrNomDest());
                            TrPrenomDest.setText(transfert.getTrPrenomDest());
                            TrTelDest.setText(transfert.getTrTelDest());
                            TrTypPieceIdDestTv.setText(Transfert.decodeTypePiece(transfert.getTrTypPieceIdDest()));
                            TrPieceIdDest.setText(transfert.getTrPieceIdDest());
                            TrAdresseDest.setText(transfert.getTrAdresseDest());
                            TrDetailsDest.setText(transfert.getTrDetailsDest());
                            TrCodeSecret.setText(transfert.getTrCodeSecret());

                            tv_guichet_exp.setText(transfert.getTrGuichetExp());
//                            tv_guichet_dest.setText(transfert.getTrGuichetDest());
                            if (transfert.getTrIsPaye().equals("Y")) {
                                addButton.setVisibility(View.GONE);
                                TrIsPaye.setText("Déjà Payé");
//                            TrIsPaye.setBackgroundColor(Color.YELLOW);
                                TrIsPaye.setTextColor(Color.GREEN);
                            } else {
                                addButton.setVisibility(View.VISIBLE);
                                TrIsPaye.setText("En attente de paiement");
                            TrIsPaye.setTextColor(Color.RED);
//                                TrIsPaye.setBackgroundColor(Color.rgb(235, 140, 52));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        notificationBadNumRef();
                        addButton.setVisibility(View.GONE);
                    }


                }
            });
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

//        eavID = OdOperExterneListID.get(position);//pour recuperer l'ID du guichet selectionnée
//        transfert.setTrCptEAVExp(AdherentExpediteursListID.get(position)); ;//pour recuperer l'ID du cpte eav selectionné


        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_nom_exp:
                // your stuff here

                break;
            case R.id.spn_list_nom_dest:
                // your stuff here

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void notificationChampsVides() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Liste Opération externe absente ")
                .setMessage("Veuilez affecter des Opérations externes sur la caisse de ce guichet!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }

                })
                .show();
    }
    public void notificationSuccessAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Transfert réussi ")
                .setMessage("L'enregistrement de votre opération s'est bien effectué !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();
                    }

                })
                .show();
    }
    public void notificationSuccessGetnumRef() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Numéro de référence correct ")
                .setMessage("Votre numéro de référence est correct !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     /*   Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();*/
                    }

                })
                .show();
    }
    public void notificationEchecAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Opération échouée ")
                .setMessage("L'enregistrement de votre opération a échoué!"
//                        +"\n Solde en caisse insuffisant !"
                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationBadNumRef() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Numéro de référence incorrect ")
                .setMessage("Votre numéro de référence n'a pas été trouvé !"
//                        +"\n Solde en caisse insuffisant !"
                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    /**
     * Checks whether all files are filled. If so then calls AddOperationRetraitTransfertAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addOperationRetrait() {
        try {
          /*  if (!STRING_EMPTY.equals(TrMontant.getText().toString().trim()) &&
                    !STRING_EMPTY.equals(TrNomDest.getText().toString().trim())
//                    &&                    operationExterneDetails.getOdOperExterne() !=0
            )*/
            if (!STRING_EMPTY.equals(TrDetailsDest.getText().toString().trim()) &&
                    transfert.getTrIsPaye().equals("N")
            ) {
/*
                Double montantOperation;

                if (!(TrMontant.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)) {
                    montantOperation = Double.valueOf(TrMontant.getText().toString().replaceAll(",", "").trim());
                    TrMontant.setText(montantOperation + "");
                }
                */
//                transfert.setTrRefOper(TrRefOper.getText().toString().trim());
//                transfert.setTrTypTransf(TrTypTransf);
////                transfert.setTrCptEAVExp(TrCptEAVExp.getText().toString().trim());
////                transfert.setTrCptEAVExp(TrCptEAVExp.getText().toString().trim());
//                transfert.setTrNomExp(TrNomExp.getText().toString().trim());
//                transfert.setTrPrenomExp(TrPrenomExp.getText().toString().trim());
////                transfert.setTrTelExp(ccp_phone1.getFullNumberWithPlus());
//                transfert.setTrPieceIdExp(TrPieceIdExp.getText().toString().trim());
//                transfert.setTrTypPieceIdExp(TrTypPieceIdExp);
//                transfert.setTrAdresseDest(TrAdresseExp.getText().toString().trim());
//                transfert.setTrDetailsExp(TrDetailsExp.getText().toString().trim());
////                transfert.setTrGuichetExp(String.valueOf(MyData.GUICHET_ID));
////                transfert.setTrAgentExp(String.valueOf(MyData.USER_ID));
////                transfert.setTrCptEAVDest(TrCptEAVExp.getText().toString().trim());
//                transfert.setTrNomDest(TrNomDest.getText().toString().trim());
//                transfert.setTrPrenomDest(TrPrenomDest.getText().toString().trim());
////                transfert.setTrTelDest(ccp_phone2.getFullNumberWithPlus());
//                transfert.setTrPieceIdDest(TrPieceIdDest.getText().toString().trim());
//                transfert.setTrTypPieceIdDest(TrTypPieceIdDest);
//                transfert.setTrAdresseDest(TrAdresseDest.getText().toString().trim());
                transfert.setTrDetailsDest(TrDetailsDest.getText().toString().trim());
//                transfert.setTrMontant(TrMontant.getText().toString().trim());
//                transfert.setTrMtFrais(TrMtFrais.getText().toString().trim());
//                transfert.setTrMtTaxes(TrMtTaxes.getText().toString().trim());

                transfert.setTrGuichetDest(String.valueOf(MyData.GUICHET_ID));
                transfert.setTrAgentDest(String.valueOf(MyData.USER_ID));
//                transfert.setTrCodeSecret(TrCodeSecret.getText().toString().trim());
//                transfert.setTrToken(TrToken.getText().toString().trim());

//                operationExterneDetails.setOdLibelle(MyData.normalizeSymbolsAndAccents( ET_OdLibelle.getText().toString().trim()));


                new AddOperationRetraitTransfertAsyncTask().execute();


            }else{
                TrDetailsDest.setError("Remplissez le détail SVP! ");
                TrDetailsDest.requestFocus();
            }
           /* else {
                if (STRING_EMPTY.equals(TrMontant.getText().toString())){
                    TrMontant.setError("Remplissez le montant SVP! ");
                    TrMontant.requestFocus();
                }else if (STRING_EMPTY.equals(TrNomDest.getText().toString())){
                    TrNomDest.setError("Remplissez le nom de l'expéditeur  SVP! ");
                    TrNomDest.requestFocus();
               }*/
               /*else if (operationExterneDetails.getOdOperExterne() ==0){
                    notificationChampsVides();
                }*/

//            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddOperationRetraitTransfertAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateOperationTransfertRetrait.this);
            pDialog.setMessage("Retrait du transfert en cours . Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(Transfert.KEY_TrNumero , String.valueOf(transfert.getTrNumero()));
            httpParams.put(Transfert.KEY_TrRefOper , String.valueOf(transfert.getTrRefOper()));
            httpParams.put(Transfert.KEY_TrTypTransf , transfert.getTrTypTransf());
            httpParams.put(Transfert.KEY_TrCptEAVExp , String.valueOf(transfert.getTrCptEAVExp()));
            httpParams.put(Transfert.KEY_TrNomExp , String.valueOf(transfert.getTrNomExp()));
            httpParams.put(Transfert.KEY_TrPrenomExp , String.valueOf(transfert.getTrPrenomExp()));
            httpParams.put(Transfert.KEY_TrTelExp , String.valueOf(transfert.getTrTelExp()));
            httpParams.put(Transfert.KEY_TrPieceIdExp , String.valueOf(transfert.getTrPieceIdExp()));
            httpParams.put(Transfert.KEY_TrTypPieceIdExp , String.valueOf(transfert.getTrTypPieceIdExp()));
            httpParams.put(Transfert.KEY_TrAdresseExp , String.valueOf(transfert.getTrAdresseExp()));
            httpParams.put(Transfert.KEY_TrDetailsExp , String.valueOf(transfert.getTrDetailsExp()));
            httpParams.put(Transfert.KEY_TrGuichetExp , String.valueOf(transfert.getTrGuichetExp()));
            httpParams.put(Transfert.KEY_TrAgentExp , String.valueOf(transfert.getTrAgentExp()));
            httpParams.put(Transfert.KEY_TrCptEAVDest , String.valueOf(transfert.getTrCptEAVDest()));
            httpParams.put(Transfert.KEY_TrNomDest , String.valueOf(transfert.getTrNomDest()));
            httpParams.put(Transfert.KEY_TrPrenomDest , String.valueOf(transfert.getTrPrenomDest()));
            httpParams.put(Transfert.KEY_TrTelDest , String.valueOf(transfert.getTrTelDest()));
            httpParams.put(Transfert.KEY_TrPieceIdDest , String.valueOf(transfert.getTrPieceIdDest()));
            httpParams.put(Transfert.KEY_TrTypPieceIdDest , String.valueOf(transfert.getTrTypPieceIdDest()));
            httpParams.put(Transfert.KEY_TrAdresseDest , String.valueOf(transfert.getTrAdresseDest()));
            httpParams.put(Transfert.KEY_TrDetailsDest , String.valueOf(transfert.getTrDetailsDest()));
            httpParams.put(Transfert.KEY_TrMontant , String.valueOf(transfert.getTrMontant()));
            httpParams.put(Transfert.KEY_TrMtFrais , String.valueOf(transfert.getTrMtFrais()));
            httpParams.put(Transfert.KEY_TrMtTaxes , String.valueOf(transfert.getTrMtTaxes()));
            httpParams.put(Transfert.KEY_TrCodeSecret , String.valueOf(transfert.getTrCodeSecret()));
            httpParams.put(Transfert.KEY_TrToken , String.valueOf(transfert.getTrToken()));

            httpParams.put(Transfert.KEY_TrGuichetDest , String.valueOf(transfert.getTrGuichetDest()));
            httpParams.put(Transfert.KEY_TrAgentDest , String.valueOf(transfert.getTrAgentDest()));
//            httpParams.put(Transfert.KEY_TrToken , String.valueOf(transfert.getTrToken()));
           /* httpParams.put(OperationExterneDetails.KEY_OdLibelle , String.valueOf(operationExterneDetails.getOdLibelle()));
            httpParams.put(OperationExterneDetails.KEY_OdMontant , String.valueOf(operationExterneDetails.getOdMontant()));
            httpParams.put(OperationExterneDetails.KEY_OdSensOper , String.valueOf(operationExterneDetails.getOdSensOper()));
            httpParams.put(OperationExterneDetails.KEY_OdGuichet , String.valueOf(MyData.GUICHET_ID));
            httpParams.put(OperationExterneDetails.KEY_OdUserCree , String.valueOf(MyData.USER_ID));
*/
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_operation_retrait_transfert.php", "POST", httpParams);
            try {
                Log.e("add_operation_retrait_transfert", jsonObject+"");
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
//                        Toast.makeText(CreateOperationExterneDetails.this,
//                                "Compte créé avec succès", Toast.LENGTH_LONG).show();
                        notificationSuccessAdd();


                    } else  if (success == -1) {

                        notificationEchecAdd();

                    } else {
                        Toast.makeText(CreateOperationTransfertRetrait.this,
                                "Erreur lors du transfert !",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}