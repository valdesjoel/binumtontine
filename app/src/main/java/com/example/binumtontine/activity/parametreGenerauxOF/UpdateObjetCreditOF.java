package com.example.binumtontine.activity.parametreGenerauxOF;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class UpdateObjetCreditOF extends AppCompatActivity implements  SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_OC_CODE = "OcCode";
    private static final String KEY_OC_LIBELLE = "OcLibelle";
    private static final String KEY_OC_DETAILS = "OcDetails";
    private static final String KEY_OC_NUMERO = "OcNumero";

    private EditText OcCodeEditText;
    private EditText OcLibelleEditText;
    private EditText OcDetailsEditText;

    private String OcCode;
    private String OcLibelle;
    private String OcDetails;

    private static String STRING_EMPTY = "";

    private TextView headerObjetCreditTextView;



    private TextView cxTitle;
    private String cxName;
    private String objetCreditID;

    private String gxCaisse;
    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> defaultEavList;
    List<Integer> defaultEavListID = new ArrayList<Integer>();
    private int defaultEavId;
    private Spinner spinnerDefaultEAV;
    private TextView tvCaisse;
    private TextInputLayout textInputLayoutCaisse;
    private LinearLayout LL_default_eav;
    /*end manage*/


    private CircularProgressButton updateButton;
    private CircularProgressButton annulerButton;
    private CircularProgressButton deleteButton;
    private int success;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objet_credit_of);

        Intent intent = getIntent();
        headerObjetCreditTextView = (TextView) findViewById(R.id.tv_header_objet_credit);

        headerObjetCreditTextView.setText("Mise à jour d'un objet de créditt");

        OcCodeEditText = (EditText) findViewById(R.id.input_txt_Code_Objet_of);
        OcLibelleEditText = (EditText) findViewById(R.id.input_txt_Libelle_Objet_credit_of);
        OcDetailsEditText = (EditText) findViewById(R.id.input_txt_Details_Objet_credit_of);
        alreadyUpperCase(OcLibelleEditText);

        objetCreditID = intent.getStringExtra(KEY_OC_NUMERO);

        new FetchGuichetDetailsAsyncTask().execute();
        deleteButton = (CircularProgressButton) findViewById(R.id.btn_delete_objet_credit_of);
        deleteButton.setVisibility(View.VISIBLE);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        annulerButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (CircularProgressButton) findViewById(R.id.btn_save_objet_credit_of);
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateObjetCredit();

                } else {
                    Toast.makeText(UpdateObjetCreditOF.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();

                } else {
                    Toast.makeText(UpdateObjetCreditOF.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });



    }

    private void alreadyUpperCase(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    editText.setText(s);
                    editText.setSelection(editText.length()); //fix reverse texting
                }
            }
        });
    }

    /**
     * Fetches single movie details from the server
     */
    private class FetchGuichetDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateObjetCreditOF.this);
            pDialog.setMessage("Chargement des détails sur l'objet crédit. Veuillez patienter svp...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_OC_NUMERO, objetCreditID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_objet_credit_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject guichet;
                if (success == 1) {
                    //Parse the JSON response
                    guichet = jsonObject.getJSONObject(KEY_DATA);

                    OcCode = guichet.getString(KEY_OC_CODE);
                    OcLibelle = guichet.getString(KEY_OC_LIBELLE);
                    OcDetails = guichet.getString(KEY_OC_DETAILS);


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

                    OcCodeEditText.setText(OcCode);
                    OcLibelleEditText.setText(OcLibelle);
                    OcDetailsEditText.setText(OcDetails);


                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateObjetCreditOF.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this Objet crédit?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteObjetCreditAsyncTask
                            new DeleteObjetCreditAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateObjetCreditOF.this,
                                    "Unable to connect to internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Annuler", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a guichet
     */
    private class DeleteObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateObjetCreditOF.this);
            pDialog.setMessage("Suppression de l'Objet Crédit. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            deleteButton.startAnimation() ;// to start animation on button delete
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set guichet_id parameter in request
            httpParams.put(KEY_OC_NUMERO, objetCreditID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_objet_credit.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            deleteButton.startAnimation() ;// to stop animation on button delete
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateObjetCreditOF.this,
                                "Objet Crédit Supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateObjetCreditOF.this,
                                "Some error occurred while deleting Objet Crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateObjetCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateObjetCredit() {

        /*

        if (!STRING_EMPTY.equals(cx_denominationEditText.getText().toString()) &&
                !STRING_EMPTY.equals(mySpinnerLocalite.getText().toString()) &&
                !STRING_EMPTY.equals(cx_num_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_adresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_tel1EditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_nom_pcaEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_nom_dgEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_debutEditText.getText().toString())) {
            */
            if (true){
                //gxCaisse = "1";//gérer plutot dynamiquement
                OcCode = OcCodeEditText.getText().toString();
                OcLibelle = OcLibelleEditText.getText().toString();
                OcDetails = OcDetailsEditText.getText().toString();
              new UpdateObjetCreditAsyncTask().execute();
        } else {
            Toast.makeText(UpdateObjetCreditOF.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateObjetCreditOF.this);
            pDialog.setMessage("Updating Objet Crédit. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            updateButton.startAnimation() ;// to start animation on button update
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_GUICHET_ID, objetCreditID);
            httpParams.put(KEY_OC_NUMERO, String.valueOf(objetCreditID));
            httpParams.put(KEY_OC_CODE, OcCode);
            httpParams.put(KEY_OC_LIBELLE, OcLibelle);
            httpParams.put(KEY_OC_DETAILS, OcDetails);
            //httpParams.put(KEY_GX_CX_NUMERO, gxCaisse);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_objet_credit.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            updateButton.startAnimation() ;// to stop animation on button update
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateObjetCreditOF.this,
                                "Objet Crédit Mis à Jour !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateObjetCreditOF.this,
                                "Some error occurred while updating Objet Crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
      /*  List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < defaultEavList.size(); i++) {
            lables.add(defaultEavList.get(i).getName());//recupère les noms
            defaultEavListID.add(defaultEavList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateObjetCreditOF.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerDefaultEAV.setAdapter(spinnerAdapter);

        //Make uxCaisseDenomination as default Item in caisseList spinner
        if (gxDefaultEavDenomination!=null){
            spinnerDefaultEAV.setSelection(spinnerAdapter.getPosition(gxDefaultEavDenomination));
        }
*/
    }

    /**
     * Async task to get all food categories
     * */
//    private class GetCategories extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(UpdateObjetCreditOF.this);
//            pDialog.setMessage("Fetching EAV's list..");
//            pDialog.setCancelable(false);
//            //pDialog.show();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            ServiceHandler jsonParser = new ServiceHandler();
//
//            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
//            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, objetCreditID));
//            httpParams.add(new BasicNameValuePair(KEY_EV_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_eav_by_guichet.php", ServiceHandler.GET, httpParams);
//
//
//            Log.e("Response: ", "> " + json);
//
//            if (json != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(json);
//                    if (jsonObj != null) {
//                        JSONArray categories = jsonObj
//                                .getJSONArray(KEY_DATA);
//
//                        for (int i = 0; i < categories.length(); i++) {
//                            JSONObject catObj = (JSONObject) categories.get(i);
//                            Category cat = new Category(catObj.getInt(KEY_EAV_ID),
//                                    catObj.getString(KEY_EAV_LIBELLE));
//                            defaultEavList.add(cat);
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                Log.e("JSON Data", "Didn't receive any data from server!");
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//            populateSpinner();
//        }
//
//    }


}
