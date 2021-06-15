
package com.example.binumtontine.activity.adherent;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.adapter.CustomAdapterListViewCheckbox;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ComiteCredit;
import com.example.binumtontine.modele.MembreComiteCredit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GetMembreComiteCredit extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_FC_NUMERO = "FcNumero";
    private static final String KEY_FC_NEW_LIBELLE = "FcNewLibelle";
    private static final String KEY_CAISSE_ID = "FcCaisse";
    private static final String KEY_GUICHET_ID = "FcGuichet";
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_ADHERENT_PIECE = "AD_PIECE";
    private static final String KEY_TYPE_MEMBRE = "FcCategAdh"; //pour l'id du type de membre
    private String CmNumero;
    private String typeMembreId;
    private String membres_comite_dmde_cred_ListId ="";

    public ArrayList<String> listPiecesAdherent = new ArrayList<>();
    private JSONObject EverythingJSON;
    private Adherent adherent;

    private ArrayList<HashMap<String, String>> membres_comite_dmde_credList = new ArrayList<>();
    private ListView lvPieces;
    private ArrayList<CheckBoxModel> checkBoxModelArrayList;
    private CustomAdapterListViewCheckbox customAdapterListViewCheckbox;
    private Button btnselect, btndeselect, btnCancel,btnnext;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogAddPiece;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membres_comite_dmde_cred);

        lvPieces = (ListView) findViewById(R.id.lv);
        btnselect = (Button) findViewById(R.id.select);
        btndeselect = (Button) findViewById(R.id.deselect);
        btnnext = (Button) findViewById(R.id.next);
        btnCancel = (Button) findViewById(R.id.previous);
        Intent intent = getIntent();
        CmNumero = intent.getStringExtra(ComiteCredit.KEY_CmNumero);
        new FetchMembresComiteDmdeCredAsyncTask().execute();


        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxModelArrayList = getModel(true);
                customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetMembreComiteCredit.this, checkBoxModelArrayList);
                lvPieces.setAdapter(customAdapterListViewCheckbox);
            }
        });
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxModelArrayList = getModel(false);
                customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetMembreComiteCredit.this, checkBoxModelArrayList);
                lvPieces.setAdapter(customAdapterListViewCheckbox);
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPieceAdherent();
           /*     Intent i = getIntent();
                //send result code 20 to notify about movie update
                setResult(20, i);
                //Finish ths activity and go back to listing activity
                finish(); */
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }

    private ArrayList<CheckBoxModel> getModel(boolean isSelect){
        ArrayList<CheckBoxModel> list = new ArrayList<>();
        for(int i = 0; i < membres_comite_dmde_credList.size(); i++){

            CheckBoxModel checkBoxModel = new CheckBoxModel();
            checkBoxModel.setSelected(isSelect);
            checkBoxModel.setAnimal(membres_comite_dmde_credList.get(i).get(MembreComiteCredit.KEY_McNom));
            checkBoxModel.setPieceID(membres_comite_dmde_credList.get(i).get(MembreComiteCredit.KEY_McNumero));
            list.add(checkBoxModel);
        }
        return list;
    }

    /**
     * Fetches the list of pieces from the server
     */
    public  class FetchMembresComiteDmdeCredAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(GetMembreComiteCredit.this);
            pDialog.setMessage("Chargement des membres. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(MembreComiteCredit.KEY_McComiteCredit, CmNumero);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_membres_comite_dmde_cred.php", "GET", httpParams);
            Log.e("Response: ", "> " + jsonObject+"");
            Log.e("Response: ", "> " + httpParams+"");
            //creating Arraylist
            //List<String> fruitList = new ArrayList<>();
            membres_comite_dmde_credList.clear();
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    //piecesList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject membre_comite = movies.getJSONObject(i);
                        Integer guichetId = membre_comite.getInt(MembreComiteCredit.KEY_McNumero);
                  //      String membre_nom_type = membre_comite.getString(MembreComiteCredit.KEY_McNom);
                        String membre_nom_type = membre_comite.getString(MembreComiteCredit.KEY_McNom).concat(" ").concat(membre_comite.getString(MembreComiteCredit.KEY_McPrenom)).concat(" - ")
                                .concat(MembreComiteCredit.decodeMcTypMembre(membre_comite.getString(MembreComiteCredit.KEY_McTypMembre)));
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(MembreComiteCredit.KEY_McNumero, guichetId.toString());
                        map.put(MembreComiteCredit.KEY_McNom, membre_nom_type);
                        Log.e("membre_nom_type: ", "> " + membre_nom_type+"");
                        membres_comite_dmde_credList.add(map);
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
                    populateGuichetList();
                }
            });
        }



    }
    public void notificationObjetVide() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aucun membre du comité de crédit n'a été sélectionné")
                .setMessage("Veuillez sélectionner les membres ayant participés au comité de crédit !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        spinnerListCredit.setError ("Donnez vos observations SVP!");
                    }

                })
                .show();
    }
    /**
     * Checks whether all files are filled. If so then calls UpdateCaisseAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addPieceAdherent() {
        if (true){

            //to get pieces provide by Adherent

            //String piecesj = "";
            for (int i = 0; i < CustomAdapterListViewCheckbox.checkBoxModelArrayList.size(); i++){
                if(CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getSelected()) {
                    listPiecesAdherent.add(CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getPieceID());
                    membres_comite_dmde_cred_ListId +=";"+CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getPieceID();
                    //tv.setText(tv.getText() + " " + CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getAnimal());
                }
            }
            /*BEGIN A bien gérer */
            if (listPiecesAdherent.size()==0){
                notificationObjetVide();
                return;
            }else {
                finish();
            }
            /*END A bien gérer */
            JSONObject jsonPiecesAdherent = new JSONObject();
            //Loop through array of PiecesAdherent and put them to a JSONcontact object
            for (int i = 0; i < listPiecesAdherent.size(); i++) {
                try {
                    //jsonPiecesAdherent.put("Count:" + String.valueOf(i + 1), listPiecesAdherent.get(i));
                    jsonPiecesAdherent.put(CmNumero, listPiecesAdherent.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            EverythingJSON = new JSONObject();
            try {
                EverythingJSON.put("piecesAdherent", jsonPiecesAdherent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
/*
            Intent i = new Intent(GetMembreComiteCredit.this, GetFraisAdherent.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_ADHERENT, (Serializable) adherent);
            bundle.putString(KEY_ADHERENT_PIECE, membres_comite_dmde_cred_ListId);

            // bundle.putSerializable(KEY_ADHERENT, adherent);
            i.putExtras(bundle);
            // i.putExtra(KEY_ADHERENT, adherent);
             startActivityForResult(i, 20);*/
            //startActivity(i);
//            finish();



            //new AddPiecesAdherentAsyncTask().execute();
        } else {
            Toast.makeText(GetMembreComiteCredit.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * Fetches the list of movies from the server
     */
    public  class AddPiecesAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogAddPiece = new ProgressDialog(GetMembreComiteCredit.this);
            pDialogAddPiece.setMessage("Adding pieces. Please wait...");
            pDialogAddPiece.setIndeterminate(false);
            pDialogAddPiece.setCancelable(false);
            pDialogAddPiece.show();
        }

        @Override
        protected String doInBackground(String... params) {

//Create Array of Post Variabels
            ArrayList<NameValuePair> postVars = new ArrayList<NameValuePair>();

            //Add a 1st Post Value called JSON with String value of JSON inside
            //This is first and last post value sent because server side will decode the JSON and get other vars from it.
            postVars.add(new BasicNameValuePair("JSON", EverythingJSON.toString()));

            //Declare and Initialize Http Clients and Http Posts
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(BASE_URL + "add_inscrip_adherent_piece.php");

            //Format it to be sent
            try {
                httppost.setEntity(new UrlEncodedFormEntity(postVars));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            /* Send request and Get the Response Back */
            try {
                HttpResponse response = httpclient.execute(httppost);
                String responseBody = EntityUtils.toString(response.getEntity());
                Log.d("response*****", responseBody);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Log.v("MAD", "Error sending... ");
            } catch (IOException e) {
                e.printStackTrace();
                Log.v("MAD", "Error sending... ");
            }
            return null;

        }

        protected void onPostExecute(String result) {
            pDialogAddPiece.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //populateGuichetList();
                    //finish();
                }
            });
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
            setResult(20, intent);
            finish();
           /* finish();
            startActivity(intent);
            */
        }
    }

        /**
         * Updating parsed JSON data into ListView
         * */
    private void populateGuichetList() {

        checkBoxModelArrayList = getModel(false);
        Log.d("*******************",checkBoxModelArrayList.toString());
//        Log.d("*******************",animallist.toString());
        customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetMembreComiteCredit.this, checkBoxModelArrayList);
        lvPieces.setAdapter(customAdapterListViewCheckbox);

    }

}