package com.example.binumtontine.activity.adherent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;
import com.example.binumtontine.adapter.RecordAdapter;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.binumtontine.dao.SERVER_ADDRESS.BASE_URL;
import static java.lang.Double.parseDouble;

public class HistoriqueCpteCourant extends AppCompatActivity {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_CODE_COMPTE_COURANT = "OccCodeCompteCourant";
    private static final String KEY_TYPE_OPERATION = "OccTypeOperation";
    private static final String KEY_DATE_OPERATION = "OccDateOperation";
    private static final String KEY_OC_MONTANT = "OccMontant";
    private static final String KEY_OC_NEW_SOLDE = "OccNewSolde";

    private List<Record> listOperationCompteAdherent;
//    private ListView recordsView;

    private RecordAdapter recordAdapter;
    private ProgressDialog pDialog;
    private String compteId;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_historique_eav);

        Toolbar toolbar = findViewById(R.id.toolbar_list_operation_compte_adherent);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);

//        Toast.makeText(HistoriqueEAV.this,
//                compteId,
//                Toast.LENGTH_LONG).show();
        /*BEGIN
        To manage format number with currency symbol*/
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) defaultFormat).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("F");
        ((DecimalFormat) defaultFormat).setDecimalFormatSymbols(decimalFormatSymbols);
        /*END
        To manage format number with currency symbol*/

//        defaultFormat.setCurrency(Currency.getInstance("XAF"));
//        defaultFormat.setCurrency(Currency.getInstance("Fcf"));
        listOperationCompteAdherent = new ArrayList<>();
new HistoriqueCpteCourant.FetchListOperationsOnCompteAdherentAsyncTask().execute();
//        final ListView recordsView = (ListView) findViewById(R.id.records_view);
//        recordAdapter= new RecordAdapter(this, new ArrayList<Record>());
//        recordsView.setAdapter(recordAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Fetches the list of operations or transactions's adherent from the server
     */
    private class FetchListOperationsOnCompteAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(HistoriqueCpteCourant.this);
            pDialog.setMessage("Chargement des opérations sur le compte. Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CODE_COMPTE_COURANT, compteId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_operation_compte_courant_by_compte.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
//                int success = 1;
                JSONArray movies;
                if (success == 1) {
                    //list opération on EAV's account
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
//                    for (int i = 0; i < 1; i++) {
                        JSONObject operation = movies.getJSONObject(i);
                        String typeOperation = operation.getString(KEY_TYPE_OPERATION);
                        String dateOperation = operation.getString(KEY_DATE_OPERATION);
                        String montantOperation = operation.getString(KEY_OC_MONTANT);
                        String nouveauSolde = operation.getString(KEY_OC_NEW_SOLDE);

//                        Record record = new Record("12","jurhdd","bfbfb","cbbbfb");
                        Record record = new Record(typeOperation,dateOperation,defaultFormat.format(parseDouble(montantOperation)),
                                defaultFormat.format(parseDouble(nouveauSolde)));
//                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
//                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
//                                "EAV","");
                        listOperationCompteAdherent.add(record);
                    }

                }
            } catch (JSONException e) {
//            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //populateGuichetList();
                    loadRecyclerViewItem();
                }
            });
        }

    }
    private void loadRecyclerViewItem() {
        //you can fetch the data from server or some apis
        //for this tutorial I am adding some dummy data directly
     /*   for (int i = 1; i <= 5; i++) {
            MyList myList = new MyList(
                    "Dummy Heading " + i,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi molestie nisi dui."
            );
            listAdherent.add(myList);
        }
        */

        //adapter = new CustomAdapterListAdherent(listAdherent, this);

        final ListView recordsView = (ListView) findViewById(R.id.records_view);
//        recordAdapter= new RecordAdapter(this, new ArrayList<Record>());
        recordAdapter= new RecordAdapter(this, listOperationCompteAdherent);
        recordsView.setAdapter(recordAdapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
       /* recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateCaisse.class);
                    intent.putExtra(KEY_CAISSE_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(CaisseActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        }); */
    }
}