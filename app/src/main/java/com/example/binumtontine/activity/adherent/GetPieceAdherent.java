
package com.example.binumtontine.activity.adherent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.adapter.CustomAdapterListViewCheckbox;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.HttpJsonParser;

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


public class GetPieceAdherent extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_FC_NUMERO = "FcNumero";
    private static final String KEY_FC_NEW_LIBELLE = "FcNewLibelle";
    private static final String KEY_CAISSE_ID = "FcCaisse";
    private static final String KEY_GUICHET_ID = "FcGuichet";
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_ADHERENT_PIECE = "AD_PIECE";
    private String adherentId;
    private String piecesListId="";

    public ArrayList<String> listPiecesAdherent = new ArrayList<>();
    private JSONObject EverythingJSON;
    private Adherent adherent;

    private ArrayList<HashMap<String, String>> piecesList = new ArrayList<>();
    private ListView lvPieces;
    private ArrayList<CheckBoxModel> checkBoxModelArrayList;
    private CustomAdapterListViewCheckbox customAdapterListViewCheckbox;
    private Button btnselect, btndeselect, btnnext;
    private  String[] animallist = new String[]{"Pièce 1", "Pièce 2", "Pièce 3", "Pièce 4"};
   // private  String[] animallist = MyData.fruitList.toArray(new String[MyData.fruitList.size()]);
    private ProgressDialog pDialog;
    private ProgressDialog pDialogAddPiece;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece_adherent);
        /*listPiecesAdherent.add("b1");
        listPiecesAdherent.add("b2");
        listPiecesAdherent.add("b3");
        listPiecesAdherent.add("b4");*/
        lvPieces = (ListView) findViewById(R.id.lv);
        btnselect = (Button) findViewById(R.id.select);
        btndeselect = (Button) findViewById(R.id.deselect);
        btnnext = (Button) findViewById(R.id.next);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        new GetPieceAdherent.FetchPiecesGuichetAsyncTask().execute();

//        checkBoxModelArrayList = getModel(false);
////        Log.d("*******************",checkBoxModelArrayList.toString());
////        Log.d("*******************",animallist.toString());
//        customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetPieceAdherent.this,checkBoxModelArrayList);
//        lvPieces.setAdapter(customAdapterListViewCheckbox);

        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxModelArrayList = getModel(true);
                customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetPieceAdherent.this, checkBoxModelArrayList);
                lvPieces.setAdapter(customAdapterListViewCheckbox);
            }
        });
       // btnselect.performClick();
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxModelArrayList = getModel(false);
                customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetPieceAdherent.this, checkBoxModelArrayList);
                lvPieces.setAdapter(customAdapterListViewCheckbox);
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPieceAdherent();
//                Intent intent = new Intent(GetPieceAdherent.this,GetFraisAdherent.class);
//                startActivity(intent);
                Intent i = getIntent();
                //send result code 20 to notify about movie update
                setResult(20, i);
                //Finish ths activity and go back to listing activity
                finish();
            }
        });


    }

    private ArrayList<CheckBoxModel> getModel(boolean isSelect){
        ArrayList<CheckBoxModel> list = new ArrayList<>();
       // for(int i = 0; i <animallist.length; i++){
        //for(int i = 0; i <MyData.animallist.length; i++){
       // for(int i = 0; i <MyData.fruitList.size(); i++){
        //for(int i = 0; i <piecesList.size(); i++){
        for(int i = 0; i <piecesList.size(); i++){

            CheckBoxModel checkBoxModel = new CheckBoxModel();
            checkBoxModel.setSelected(isSelect);
            //checkBoxModel.setAnimal(animallist[i]);
            //checkBoxModel.setAnimal(MyData.fruitList.get(i));
            checkBoxModel.setAnimal(piecesList.get(i).get(KEY_FC_NEW_LIBELLE));
            checkBoxModel.setPieceID(piecesList.get(i).get(KEY_FC_NUMERO));
            //checkBoxModel.setPieceID(listPiecesAdherent.get(i));
           // checkBoxModel.setAnimal("good");
           // checkBoxModel.setAnimal(String.valueOf(piecesList.get(i).get(KEY_FC_NEW_LIBELLE)));
            list.add(checkBoxModel);
        }
        return list;
    }

    /**
     * Fetches the list of pieces from the server
     */
    public  class FetchPiecesGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(GetPieceAdherent.this);
            pDialog.setMessage("Loading pieces. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_ADHERENT_ID, adherentId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_piece_by_guichet.php", "GET", httpParams);
            //creating Arraylist
            //List<String> fruitList = new ArrayList<>();
            piecesList.clear();
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    //piecesList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject guichet = movies.getJSONObject(i);
                        Integer guichetId = guichet.getInt(KEY_FC_NUMERO);
                        String guichetDenomination = guichet.getString(KEY_FC_NEW_LIBELLE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FC_NUMERO, guichetId.toString());
                        map.put(KEY_FC_NEW_LIBELLE, guichetDenomination);
                        //adding String Objects to fruitsList ArrayList
                       // MyData.fruitList.add(guichetDenomination);
                        //listPiecesAdherent.add(guichetDenomination);
                        piecesList.add(map);
                    }
                   // Log.d("convert to array","coucouuuuuuuuuuuuuuuuu"+fruitList.size());
                    //String[] item = fruitList.toArray(new String[fruitList.size()]);
                   // Log.d("*********item****",item.length+"");
                    //MyData.animallist = item;

                   // Log.d("********animallist",MyData.animallist.length+"");
                    //Log.d("********animallist",MyData.animallist[0]+"");
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

    /**
     * Checks whether all files are filled. If so then calls UpdateCaisseAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addPieceAdherent() {
        if (true){
/*

        if (!STRING_EMPTY.equals(cx_denominationEditText.getText().toString()) &&
                !STRING_EMPTY.equals(mySpinnerLocalite.getText().toString()) &&
                !STRING_EMPTY.equals(cx_num_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_adresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ccp_phone1.getFullNumberWithPlus()) &&
                !STRING_EMPTY.equals(cx_nom_pcaEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_nom_dgEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_debutEditText.getText().toString())) {

            cxDenomination = cx_denominationEditText.getText().toString();
            cxLocalite = mySpinnerLocalite.getText().toString();
            cx_num_agrem = cx_num_agremEditText.getText().toString();
            cx_date_agrem = cx_date_agremEditText.getText().toString();
            cx_date_debut = cx_date_debutEditText.getText().toString();
            cx_adresse = cx_adresseEditText.getText().toString(); */

          /*  cx_tel1 = cx_tel1EditText.getText().toString();
            cx_tel2 = cx_tel2EditText.getText().toString();
            cx_tel3 = cx_tel3EditText.getText().toString(); */
/*
            cx_tel1 = ccp_phone1.getFullNumberWithPlus();
            cx_tel2 = ccp_phone2.getFullNumberWithPlus();
            cx_tel3 = ccp_phone3.getFullNumberWithPlus();

            cx_nom_pca = cx_nom_pcaEditText.getText().toString();
            cx_nom_dg = cx_nom_dgEditText.getText().toString(); */

            //to get pieces provide by Adherent

            //String piecesj = "";
            for (int i = 0; i < CustomAdapterListViewCheckbox.checkBoxModelArrayList.size(); i++){
                if(CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getSelected()) {
                    listPiecesAdherent.add(CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getPieceID());
                    piecesListId+=";"+CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getPieceID();
                    //tv.setText(tv.getText() + " " + CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getAnimal());
                }
            }

            JSONObject jsonPiecesAdherent = new JSONObject();
            //Loop through array of PiecesAdherent and put them to a JSONcontact object
            for (int i = 0; i < listPiecesAdherent.size(); i++) {
                try {
                    //jsonPiecesAdherent.put("Count:" + String.valueOf(i + 1), listPiecesAdherent.get(i));
                    jsonPiecesAdherent.put(adherentId, listPiecesAdherent.get(i));
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

            Intent i = new Intent(GetPieceAdherent.this, GetFraisAdherent.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_ADHERENT, (Serializable) adherent);
            bundle.putString(KEY_ADHERENT_PIECE,piecesListId);

            // bundle.putSerializable(KEY_ADHERENT, adherent);
            i.putExtras(bundle);
            // i.putExtra(KEY_ADHERENT, adherent);
             startActivityForResult(i, 20);
            //startActivity(i);
            //finish();



            //new AddPiecesAdherentAsyncTask().execute();
        } else {
            Toast.makeText(GetPieceAdherent.this,
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
            pDialogAddPiece = new ProgressDialog(GetPieceAdherent.this);
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


/*


            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_ADHERENT_ID, adherentId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_piece_by_guichet.php", "GET", httpParams);
            //creating Arraylist
            //List<String> fruitList = new ArrayList<>();
            piecesList.clear();
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    //piecesList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject guichet = movies.getJSONObject(i);
                        Integer guichetId = guichet.getInt(KEY_FC_NUMERO);
                        String guichetDenomination = guichet.getString(KEY_FC_NEW_LIBELLE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FC_NUMERO, guichetId.toString());
                        map.put(KEY_FC_NEW_LIBELLE, guichetDenomination);
                        //adding String Objects to fruitsList ArrayList
                       // MyData.fruitList.add(guichetDenomination);
                        //listPiecesAdherent.add(guichetDenomination);
                        piecesList.add(map);
                    }
                   // Log.d("convert to array","coucouuuuuuuuuuuuuuuuu"+fruitList.size());
                    //String[] item = fruitList.toArray(new String[fruitList.size()]);
                   // Log.d("*********item****",item.length+"");
                    //MyData.animallist = item;

                   // Log.d("********animallist",MyData.animallist.length+"");
                    //Log.d("********animallist",MyData.animallist[0]+"");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null; */
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
//        Log.d("*******************",checkBoxModelArrayList.toString());
//        Log.d("*******************",animallist.toString());
        customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetPieceAdherent.this, checkBoxModelArrayList);
        lvPieces.setAdapter(customAdapterListViewCheckbox);
/*
        for (int i=0; i<piecesList.size();i++){
            listPiecesAdherent.add(piecesList.get(i).get(KEY_FC_NEW_LIBELLE));
        } */
       /* ListAdapter adapter = new SimpleAdapter(
                GetPieceAdherent.this, piecesList,
                R.layout.list_view_checkbox_item, new String[]{KEY_FC_NUMERO,
                KEY_FC_NEW_LIBELLE},
                new int[]{R.id.pieceId, R.id.animal});

        // updating listview
        //lvPieces.setAdapter(adapter);
        lvPieces.setAdapter(customAdapterListViewCheckbox); */
        //Call MovieUpdateDeleteActivity when a movie is clicked
      /*  lvPieces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String guichetId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateGuichet.class);
                    intent.putExtra(KEY_GUICHET_ID, guichetId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(GetPieceAdherent.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        }); */

    }


}