package com.example.binumtontine.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.Adherent;
import com.example.binumtontine.activity.adherent.DemandeCredit;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.Transfert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class ListNomPrenomAvalisteDemandeCredit extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_GUICHET_ID = "gx_numero";
    private static final String KEY_GUICHET_DENOMINATION = "gx_denomination";
    private static final String KEY_CAISSE_ID = "cx_numero";
    private static final String KEY_CAISSE_DENOMINATION = "cx_denomination";
    public static final String KEY_TrGuichetExp = "TrGuichetExp";

    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;
    private String trGuichetExp;
    private String sens;
    private EditText inputSearch;
//    private ListAdapter adapter;
    private SimpleAdapter adapter;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_list_nom_prenom_avaliste_demande_credit);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);
        movieListView = (ListView) findViewById(R.id.movieList);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        Intent intent = getIntent();
        trGuichetExp = intent.getStringExtra(KEY_GUICHET_ID);
        sens = intent.getStringExtra("sens");
        new ListNomPrenomAvalisteDemandeCredit.FetchMoviesAsyncTask().execute();
        defaultFormat.setCurrency(Currency.getInstance("XAF"));

        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar);
        if (sens.equals("avaliste1")) {
            getSupportActionBar().setTitle("Avaliste 1");
        }else if (sens.equals("avaliste2")){
            getSupportActionBar().setTitle("Avaliste 2");
        }else if (sens.equals("avaliste3")){
            getSupportActionBar().setTitle("Avaliste 3");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        try {
            inputSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    ListNomPrenomAvalisteDemandeCredit.this.adapter.getFilter().filter(cs);
    //                ListNomPrenomAdherentTransfert.this.adapter.;
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
/**
 * Fetches the list of movies from the server
 */
private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(ListNomPrenomAvalisteDemandeCredit.this);
        pDialog.setMessage("Loading list's avalistes. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();
        httpParams.put(KEY_TrGuichetExp, trGuichetExp);
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                BASE_URL + "get_adherents_expediteurs.php", "GET", httpParams);
        try {
            int success = jsonObject.getInt(KEY_SUCCESS);
            Log.e("jsonObject", jsonObject+"");
            JSONArray movies;
            if (success == 1) {
                movieList = new ArrayList<>();
                movies = jsonObject.getJSONArray(KEY_DATA);
                //Iterate through the response and populate movies list
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);
                    Integer gx_numero = movie.getInt(Adherent.KEY_AD_AdNumero);
                    String cx_denomination = movie.getString(Adherent.KEY_AD_AdNom);
                    String gx_denomination = movie.getString(Adherent.KEY_AD_AdPrenom);
                    Integer TrCptEAVExp = movie.getInt("TrCptEAVExp");
                    String cvMtSolde = movie.getString("CvMtSolde");
                    String AdTel1 = movie.getString("AdTel1");
                    String AdTypCarteID = movie.getString("AdTypCarteID");
                    String AdNumCarteID = movie.getString("AdNumCarteID");
                    String AdDomicile = movie.getString("AdDomicile");


//                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(Adherent.KEY_AD_AdNumero, gx_numero.toString());
                    map.put(Adherent.KEY_AD_AdNom, cx_denomination);
                    map.put(Adherent.KEY_AD_AdPrenom, gx_denomination);
                    map.put("TrCptEAVExp", TrCptEAVExp.toString());
                    map.put("CvMtSolde", cvMtSolde);
                    map.put("AdTel1", AdTel1);
                    map.put("AdTypCarteID", AdTypCarteID);
                    map.put("AdNumCarteID", AdNumCarteID);
                    map.put("AdDomicile", AdDomicile);

//                    map.put(KEY_GUICHET_DENOMINATION, gx_denomination+i+1);
                    movieList.add(map);
                    Log.e("get_adherents_expediteurs><<<<<<  ",gx_numero+"  "+cx_denomination+"  "+gx_denomination+" "+cvMtSolde);
                }
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
                populateMovieList();
            }
        });
    }

}

    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateMovieList() {

        try {
//        ListAdapter adapter = new SimpleAdapter(
            adapter = new SimpleAdapter(
                    ListNomPrenomAvalisteDemandeCredit.this, movieList,
                    R.layout.list_item_nom_prenom_adherent_transfert, new String[]{Adherent.KEY_AD_AdNumero,
                    Adherent.KEY_AD_AdNom,Adherent.KEY_AD_AdPrenom,"TrCptEAVExp","CvMtSolde","AdTel1","AdTypCarteID","AdNumCarteID", "AdDomicile"},
                    new int[]{R.id.movieId, R.id.movieName, R.id.movieNameDetails, R.id.TrCptEAVExp, R.id.CvMtSolde, R.id.AdTel1,
                            R.id.AdTypCarteID, R.id.AdNumCarteID, R.id.AdDomicile});
     /*   TextView cx = ((TextView) findViewById(R.id.movieNameDetails));
        cx.setVisibility(View.VISIBLE);*/
            // updating listview
            movieListView.setAdapter(adapter);
            //Call MovieUpdateDeleteActivity when a movie is clicked
            movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Check for network connectivity
                    if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                        String movieId = ((TextView) view.findViewById(R.id.movieId))
                                .getText().toString();
                        String guichetDenomination = ((TextView) view.findViewById(R.id.movieName))
                                .getText().toString();
                        String prenom = ((TextView) view.findViewById(R.id.movieNameDetails))
                                .getText().toString();
                        String AdTel1 = ((TextView) view.findViewById(R.id.AdTel1))
                                .getText().toString();
                        String AdTypCarteID = ((TextView) view.findViewById(R.id.AdTypCarteID))
                                .getText().toString();
                        String AdNumCarteID = ((TextView) view.findViewById(R.id.AdNumCarteID))
                                .getText().toString();
                        String AdDomicile = ((TextView) view.findViewById(R.id.AdDomicile))
                                .getText().toString();
                        String trCptEAVExp = ((TextView) view.findViewById(R.id.TrCptEAVExp))
                                .getText().toString();
                        String cvMtSolde = ((TextView) view.findViewById(R.id.CvMtSolde))
                                .getText().toString();
                        /*if (sens.equals("avaliste1")){
                            DemandeCredit.DcNomAval1ET.setText(guichetDenomination);
                            DemandeCredit.DcPrenomAval1ET.setText(prenom);
                           CreateOperationTransfertEnvoyer.ccp_phone1.setFullNumber(AdTel1);
                            CreateOperationTransfertEnvoyer.TrTypPieceIdExpSpinner.setText(Transfert.decodeTypePiece(AdTypCarteID) );
                            CreateOperationTransfertEnvoyer.TrPieceIdExp.setText(AdNumCarteID);
                            CreateOperationTransfertEnvoyer.TrAdresseExp.setText(AdDomicile);
//                            DemandeCredit.demandeCreditModele.setDcCp(trCptEAVExp);
                            DemandeCredit.DcMtSoldAvalist1ET.setText(cvMtSolde);
//                            CreateOperationTransfertEnvoyer.tv_solde_eav.setText(cvMtSolde);
                            CreateOperationTransfertEnvoyer.tv_solde_eav.setText(defaultFormat.format(parseDouble(cvMtSolde)));
                        }else if (sens.equals("dest")){
                            CreateOperationTransfertEnvoyer.TrNomDest.setText(guichetDenomination);
                            CreateOperationTransfertEnvoyer.TrPrenomDest.setText(prenom);
                            CreateOperationTransfertEnvoyer.ccp_phone2.setFullNumber(AdTel1);
                            CreateOperationTransfertEnvoyer.TrTypPieceIdDestSpinner.setText(Transfert.decodeTypePiece(AdTypCarteID));
                            CreateOperationTransfertEnvoyer.TrPieceIdDest.setText(AdNumCarteID);
                            CreateOperationTransfertEnvoyer.TrAdresseDest.setText(AdDomicile);
                            CreateOperationTransfertEnvoyer.transfert.setTrCptEAVDest(trCptEAVExp);
                        }*/
                        if (sens.equals("avaliste1")){
                            DemandeCredit.demandeCreditModele.setDcNumAvaliste1(movieId);
                            DemandeCredit.demandeCreditModele.setDcMtSoldAvalist1(cvMtSolde);
                            DemandeCredit.DcNomAval1ET.setText(guichetDenomination);
                            DemandeCredit.DcPrenomAval1ET.setText(prenom);
                            DemandeCredit.DcMtSoldAvalist1ET.setText(cvMtSolde);
//                            CreateOperationTransfertEnvoyer.tv_solde_eav.setText(defaultFormat.format(parseDouble(cvMtSolde)));
                        }else if (sens.equals("avaliste2")){
                            DemandeCredit.demandeCreditModele.setDcNumAvaliste2(movieId);
                            DemandeCredit.demandeCreditModele.setDcMtSoldAvalist2(cvMtSolde);
                            DemandeCredit.DcNomAval2ET.setText(guichetDenomination);
                            DemandeCredit.DcPrenomAval2ET.setText(prenom);
                            DemandeCredit.DcMtSoldAvalist2ET.setText(cvMtSolde);

                        }else if (sens.equals("avaliste3")){
                            DemandeCredit.demandeCreditModele.setDcNumAvaliste3(movieId);
                            DemandeCredit.demandeCreditModele.setDcMtSoldAvalist3(cvMtSolde);
                            DemandeCredit.DcNomAval3ET.setText(guichetDenomination);
                            DemandeCredit.DcPrenomAval3ET.setText(prenom);
                            DemandeCredit.DcMtSoldAvalist3ET.setText(cvMtSolde);

                        }
                     /*   Toast.makeText(ListNomPrenomAvalisteDemandeCredit.this,
                                guichetDenomination+" "+cvMtSolde,
                                Toast.LENGTH_LONG).show();*/
                        finish();
                     /*   Intent intent = new Intent(getApplicationContext(),
                                ParamGuichetActivity.class);
                        intent.putExtra(KEY_GUICHET_ID, movieId);
                        startActivityForResult(intent, 20);*/

                    } else {
                        Toast.makeText(ListNomPrenomAvalisteDemandeCredit.this,
                                "Unable to connect to internet",
                                Toast.LENGTH_LONG).show();

                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the movie.
            // So refresh the movie listing

            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

}


