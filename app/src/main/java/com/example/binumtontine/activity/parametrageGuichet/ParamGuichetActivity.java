package com.example.binumtontine.activity.parametrageGuichet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.binumtontine.activity.UserGuichetActivity;
import com.example.binumtontine.activity.adherent.InitialisationCaisseGuichet;
import com.example.binumtontine.activity.adherent.ListDemandeRetraitAffilierActivity;
import com.example.binumtontine.activity.adherent.MiseAJourCaisseGuichet;
import com.example.binumtontine.activity.parametreGenerauxCx.ListTypeMembrePFActivity;
import com.example.binumtontine.activity.parametreGenerauxCx.ParametreGenerauxCxActivity;
import com.example.binumtontine.activity.parametreGenerauxOF.ParametreGenerauxOFActivity;
import com.example.binumtontine.activity.parametreGenerauxOF.PieceToFournirActivity;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.binumtontine.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.binumtontine.dao.SERVER_ADDRESS.BASE_URL;
import static java.lang.Double.parseDouble;

public class ParamGuichetActivity extends AppCompatActivity {


    private  Button save;
    private  Button cancel;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    // This listview is just under above button.
    private ListView userDataListView = null;
    private View popupInputDialogView = null;

    private int success;
    private ProgressDialog pDialogInitialisationCaisseGuichet;
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_CG_GUICHET = "CgGuichet";

    private static final String KEY_CG_LAST_SOLDE = "CgLastSolde";
    private static final String KEY_CM_ID = "CgNumero";
    private ProgressDialog pDialogFetchLastSolde;
    private String CgLastSolde;
    private String CgNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_param_guichet);
        Toolbar toolbar = findViewById(R.id.toolbar_param_home_guichet);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        save = (Button)findViewById(R.id.button_save_user_data);
        cancel = (Button)findViewById(R.id.button_cancel_user_data);
        userNameEditText = (EditText)findViewById(R.id.userName);
        passwordEditText = (EditText)findViewById(R.id.password);
        emailEditText = (EditText)findViewById(R.id.email);
//        new ParamGuichetActivity.FetchLastSoldeGuichetDetailsAsyncTask().execute();



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void onButtonClick(View v){
        switch (v.getId()) {

            case R.id.produit_guichet_card:
                // do your code
                Intent myIntent = new Intent(getBaseContext(),   ProduitGuichetFragment.class);
                startActivity(myIntent);
                break;

            case R.id.piece_frais_card:
                // do your code
                Intent piece_to_fournir_intent = new Intent(getBaseContext(),   ListTypeMembrePFgxActivity.class);
                //Intent piece_to_fournir_intent = new Intent(getBaseContext(),   ParametreGenerauxGxActivity.class);
                startActivity(piece_to_fournir_intent);
                break;
            case R.id.type_membre_guichet_card:
                // do your code
                Intent type_membre_guichet_intent = new Intent(getBaseContext(),   TypeMembreGxActivity.class);
                startActivity(type_membre_guichet_intent);
                break;
            case R.id.add_user_guichet_card:
                // do your code
                Intent add_user_guichet_intent = new Intent(getBaseContext(),   UserGuichetActivity.class);
                UserGuichetActivity.profilCaisseOrGuichet="guichet";
                startActivity(add_user_guichet_intent);
                break;
            case R.id.workflow_guichet_card:
                // do your code
                Intent workflow_guichet_intent = new Intent(getBaseContext(),   ListWorkflowGuichet.class);
                startActivity(workflow_guichet_intent);
                break;
            case R.id.collector_card:
                // do your code
                Intent collector_intent = new Intent(getBaseContext(),   ListDemandeRetraitAffilierActivity.class);
                startActivity(collector_intent);
                break;
            case R.id.init_caisse_guichet_card:
                // do your code
                new ParamGuichetActivity.FetchLastSoldeGuichetDetailsAsyncTask().execute();

/*

                // When click the open input popup dialog button.


                        // Create a AlertDialog Builder.
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ParamGuichetActivity.this);
                        // Set title, icon, can not cancel properties.
                        alertDialogBuilder.setTitle("User Data Collection Dialog.");
                        alertDialogBuilder.setIcon(R.drawable.ic_launcher_background);
                        alertDialogBuilder.setCancelable(false);

                        // Init popup dialog view and it's ui controls.
                            //initPopupViewControls();

                        // Set the inflated layout view object to the AlertDialog builder.
                        alertDialogBuilder.setView(popupInputDialogView);

                        // Create AlertDialog and show.
                        final AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                        // When user click the save user data button in the popup dialog.
                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // Get user data from popup dialog editeext.
                                String userName = userNameEditText.getText().toString();
                                String password = passwordEditText.getText().toString();
                                String email = emailEditText.getText().toString();

                                // Create data for the listview.
                                String[] titleArr = { "User Name", "Password", "Email"};
                                String[] dataArr = {userName, password, email};

                                ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

                                int titleLen = titleArr.length;
                                for(int i =0; i < titleLen; i++) {
                                    Map<String,Object> listItemMap = new HashMap<String,Object>();
                                    listItemMap.put("title", titleArr[i]);
                                    listItemMap.put("data", dataArr[i]);
                                    itemDataList.add(listItemMap);
                                }

                                SimpleAdapter simpleAdapter = new SimpleAdapter(ParamGuichetActivity.this,itemDataList,android.R.layout.simple_list_item_2,
                                        new String[]{"title","data"},new int[]{android.R.id.text1,android.R.id.text2});

                                userDataListView.setAdapter(simpleAdapter);

                                alertDialog.cancel();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.cancel();
                            }
                        });

**/
                break;



            default:
                break;
        }

    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class GetCaisseGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialogInitialisationCaisseGuichet = new ProgressDialog(ParamGuichetActivity.this);
            pDialogInitialisationCaisseGuichet.setMessage("Veuillez patienter...");
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

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_caisse_guichet.php", "POST", httpParams);
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
                        Toast.makeText(ParamGuichetActivity.this,
                                "Caisse guichet initialisé avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(ParamGuichetActivity.this,
                                "Some error occurred while initialization caisse guichet",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Fetches single caisse_guichet details from the server
     */
    private class FetchLastSoldeGuichetDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogFetchLastSolde = new ProgressDialog(ParamGuichetActivity.this);
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
                 success = jsonObject.getInt(KEY_SUCCESS);

             /*   JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
//                    CgLastSolde = user.getString(KEY_CG_LAST_SOLDE);
                    CgLastSolde = user.getString(KEY_CG_LAST_SOLDE);
                    CgNumero = user.getString(KEY_CM_ID);


                }*/
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
                        Intent init_caisse_guichet_intent = new Intent(getBaseContext(),   InitialisationCaisseGuichet.class);
                        startActivity(init_caisse_guichet_intent);
                    }else if (success == 1){
                        Intent init_caisse_guichet_intent = new Intent(getBaseContext(),   MiseAJourCaisseGuichet.class);
                        startActivity(init_caisse_guichet_intent);
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


}
