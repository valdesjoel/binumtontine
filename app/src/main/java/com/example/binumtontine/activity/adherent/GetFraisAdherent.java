
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
import com.example.binumtontine.adapter.CustomeAdapter;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GetFraisAdherent extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private int success;
    private static final String KEY_DATA = "data";



    private static final String KEY_AD_GX_NUMERO = "AdGuichet";
    private static final String KEY_AD_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_AD_NOM = "AdNom";
    private static final String KEY_AD_PRENOM = "AdPrenom";
    private static final String KEY_AD_DATE_NAISS = "AdDateNaiss";
    private static final String KEY_AD_LIEU_NAISS = "AdLieuNaiss";
    private static final String KEY_AD_SEXE = "AdSexe";
    private static final String KEY_AD_NATIONALITE = "AdNationalite";
    private static final String KEY_AD_SIT_FAM = "AdSitFam";
    private static final String KEY_AD_NBRE_ENFANT = "AdNbreEnfACh";
    private static final String KEY_AD_TEL1 = "AdTel1";
    private static final String KEY_AD_TEL2 = "AdTel2";
    private static final String KEY_AD_TEL3 = "AdTel3";
    private static final String KEY_AD_EMAIL = "AdEMail";
    private static final String KEY_AD_PROFESSION = "AdProfess";
    private static final String KEY_AD_DOMICILE = "AdDomicile";
    private static final String KEY_AD_LIEU_TRAVAIL = "AdLieuTrav";
    private static final String KEY_AD_ACTIVITE_PRINC = "AdActivitePr";
    private static final String KEY_AD_TYPE_CARTE_ID = "AdTypCarteID";
    private static final String KEY_AD_NUM_CARTE_ID = "AdNumCarteID";
    private static final String KEY_AD_VALIDE_DU = "AdValideDu";
    private static final String KEY_AD_VALIDE_AU = "AdValideAu";
    private static final String KEY_AD_TYPE_HABITE = "AdTypHabite";



    private static final String KEY_FC_NUMERO = "FcNumero";

    private static final String KEY_FC_NEW_LIBELLE = "FcNewLibelle";
    private static final String KEY_FC_FONCTION = "FcFonction";
    private static final String KEY_FC_NBRE_PART_MIN_J = "FcNbrePartMinJ";
    private static final String KEY_FC_NBRE_PART_MIN_F = "FcNbrePartMinF";
    private static final String KEY_FC_NBRE_PART_MIN_H = "FcNbrePartMinH";
    private static final String KEY_FC_VALEUR = "FcVal";
    private static final String KEY_CAISSE_ID = "FcCaisse";
    private static final String KEY_GUICHET_ID = "FcGuichet";

    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_ADHERENT_PIECE = "AD_PIECE";
    private static final String KEY_ADHERENT_FRAIS_ID = "AD_FRAIS_ID";
    private static final String KEY_ADHERENT_FRAIS_MONTANT = "AD_FRAIS_MONTANT";
    private static final String KEY_ADHERENT_FRAIS_FONCTION = "AD_FRAIS_FONCTION";


    private ArrayList<HashMap<String, String>> fraisList = new ArrayList<>();
    private ListView lvOld;
    private Button btnselect, btnClean, btnnext;
    private  String[] animallist = new String[]{"Pièce 1", "Pièce 2", "Pièce 3", "Pièce 4"};
   // private  String[] animallist = MyData.fruitList.toArray(new String[MyData.fruitList.size()]);
    private ProgressDialog pDialog;
    private String adherentId;
    private String piecesListId="";
    private String fraisListId="";
    private String fraisListMontant="";
    private String fraisListFonction="";
    private Adherent adherent;
    /*Begin*/

    private Button btn;
    private ListView lv;
    private CustomeAdapter customeAdapter;
    public ArrayList<EditModel> editModelArrayList;
    /*End*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frais_adherent);

        lvOld = (ListView) findViewById(R.id.lv_frais_adherent);
        //btnselect = (Button) findViewById(R.id.select);
        btnClean = (Button) findViewById(R.id.btn_clean);
        btnnext = (Button) findViewById(R.id.save_frais_adherent);

        /*BEGIN*/
        lv = (ListView) findViewById(R.id.lv_frais_adherent);
       // btn = (Button) findViewById(R.id.save_frais_adherent);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        piecesListId = bundle.getString(KEY_ADHERENT_PIECE);
/*
        editModelArrayList = populateList();
        customeAdapter = new CustomeAdapter(this,editModelArrayList);
        lv.setAdapter(customeAdapter);
        */
/*
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetFraisAdherent.this,NextActivity.class);
                startActivity(intent);
            }
        });
        */
        /*END*/


        new FetchFraisGuichetAsyncTask().execute();


       // checkBoxModelArrayList = getModel(false);
//        Log.d("*******************",checkBoxModelArrayList.toString());
//        Log.d("*******************",animallist.toString());
//        customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(this,checkBoxModelArrayList);
//        lvOld.setAdapter(customAdapterListViewCheckbox);

//        btnselect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkBoxModelArrayList = getModel(true);
//                customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetFraisAdherent.this,checkBoxModelArrayList);
//                lvOld.setAdapter(customAdapterListViewCheckbox);
//            }
//        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkBoxModelArrayList = getModel(false);
               // customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetFraisAdherent.this,checkBoxModelArrayList);
                //lvOld.setAdapter(customAdapterListViewCheckbox);
                finish();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdherent();
//                Intent intent = new Intent(GetFraisAdherent.this,NextActivity.class);
//                startActivity(intent);
            }
        });


    }

    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();

        //for(int i = 0; i < 8; i++){
        for(int i = 0; i < fraisList.size(); i++){
            EditModel editModel = new EditModel();
           // editModel.setEditTextValue(String.valueOf(i));

            editModel.setEditTextValue(fraisList.get(i).get(KEY_FC_VALEUR));
            editModel.setFraisLibelle(fraisList.get(i).get(KEY_FC_NEW_LIBELLE));
            editModel.setFraisID(fraisList.get(i).get(KEY_FC_NUMERO));
            editModel.setFraisFonction(fraisList.get(i).get(KEY_FC_FONCTION));
            list.add(editModel);
        }

        return list;
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateCaisseAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addAdherent() {
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



            //to get pieces provide by Adherent

            //String piecesj = "";
            /*
            for (int i = 0; i < CustomAdapterListViewCheckbox.checkBoxModelArrayList.size(); i++){
                if(CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getSelected()) {
                    listPiecesAdherent.add(CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getPieceID());
                    piecesListId+=";"+CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getPieceID();
                    //tv.setText(tv.getText() + " " + CustomAdapterListViewCheckbox.checkBoxModelArrayList.get(i).getAnimal());
                }
            }
            */

            for (int i = 0; i < CustomeAdapter.editModelArrayList.size(); i++){

                //tv.setText(tv.getText() + " " + CustomeAdapter.editModelArrayList.get(i).getEditTextValue() +System.getProperty("line.separator"));
                fraisListMontant+=";"+CustomeAdapter.editModelArrayList.get(i).getEditTextValue();
                fraisListFonction+=";"+CustomeAdapter.editModelArrayList.get(i).getFraisFonction();
                fraisListId+=";"+CustomeAdapter.editModelArrayList.get(i).getFraisID();
            }

/*
            Intent i = new Intent(GetPieceAdherent.this, GetFraisAdherent.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_ADHERENT, (Serializable) adherent);
            bundle.putString(KEY_ADHERENT_PIECE,piecesListId);

            // bundle.putSerializable(KEY_ADHERENT, adherent);
            i.putExtras(bundle);
            // i.putExtra(KEY_ADHERENT, adherent);
            // startActivityForResult(intent, 20);
            startActivity(i);
            finish();
            */

            new AddAdherentAsyncTask().execute();
        } else {
            Toast.makeText(GetFraisAdherent.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }


    /**
     * Fetches the list of movies from the server
     */
    public  class FetchFraisGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(GetFraisAdherent.this);
            pDialog.setMessage("Loading frais. Please wait...");
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
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_frais_by_guichet.php", "GET", httpParams);
            //creating Arraylist
            //List<String> fruitList = new ArrayList<>();
            fraisList.clear();
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    //fraisList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject fraisGuichet = movies.getJSONObject(i);
                        Integer fraisGuichetId = fraisGuichet.getInt(KEY_FC_NUMERO);
                        String fraisGuichetLibelle = fraisGuichet.getString(KEY_FC_NEW_LIBELLE);
                        String fraisGuichetFonction = fraisGuichet.getString(KEY_FC_FONCTION);
                        String fraisGuichetNbrePartMinJ = fraisGuichet.getString(KEY_FC_NBRE_PART_MIN_J);
                        String fraisGuichetNbrePartMinF = fraisGuichet.getString(KEY_FC_NBRE_PART_MIN_F);
                        String fraisGuichetNbrePartMinH = fraisGuichet.getString(KEY_FC_NBRE_PART_MIN_H);
                        String fraisGuichetValeur = fraisGuichet.getString(KEY_FC_VALEUR);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FC_NUMERO, fraisGuichetId.toString());
                        map.put(KEY_FC_NEW_LIBELLE, fraisGuichetLibelle);
                        map.put(KEY_FC_FONCTION, fraisGuichetFonction);
                        map.put(KEY_FC_NBRE_PART_MIN_J, fraisGuichetNbrePartMinJ);
                        map.put(KEY_FC_NBRE_PART_MIN_F, fraisGuichetNbrePartMinF);
                        map.put(KEY_FC_NBRE_PART_MIN_H, fraisGuichetNbrePartMinH);
                        map.put(KEY_FC_VALEUR, fraisGuichetValeur);
                        //adding String Objects to fruitsList ArrayList
                       // MyData.fruitList.add(guichetDenomination);
                        fraisList.add(map);
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
     * Updating parsed JSON data into ListView
     * */
    private void populateGuichetList() {
        /*
        ListAdapter adapter = new SimpleAdapter(
                GetFraisAdherent.this, fraisList,
                R.layout.list_item, new String[]{KEY_FC_NUMERO,
                KEY_FC_NEW_LIBELLE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        lvOld.setAdapter(adapter); */

        editModelArrayList = populateList();
        customeAdapter = new CustomeAdapter(GetFraisAdherent.this,editModelArrayList);
        lv.setAdapter(customeAdapter);
        //lvOld.setAdapter(customAdapterListViewCheckbox);
        //Call MovieUpdateDeleteActivity when a movie is clicked
      /*  lvOld.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    /**
     * AsyncTask for adding a adherent
     */
    private class AddAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // addButton.startAnimation() ;// to start animation on button save
            //Display proggress bar
            pDialog = new ProgressDialog(GetFraisAdherent.this);
            pDialog.setMessage("Adding Adhérent. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            // httpParams.put(KEY_AD_GX_NUMERO, gxCaisse);
            //httpParams.put(KEY_AD_GX_NUMERO, String.valueOf(caisseID));
            httpParams.put(KEY_AD_GX_NUMERO, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_AD_NUM_MANUEL, adherent.getAdNumManuel());
            httpParams.put(KEY_AD_NOM, adherent.getAdNom());
            httpParams.put(KEY_AD_PRENOM, adherent.getAdPrenom());
            httpParams.put(KEY_AD_DATE_NAISS, adherent.getAdDateNaiss());
            httpParams.put(KEY_AD_LIEU_NAISS, adherent.getAdLieuNaiss());
            httpParams.put(KEY_AD_SEXE, adherent.getAdSexe());
            httpParams.put(KEY_AD_NATIONALITE, adherent.getAdNationalite());
            httpParams.put(KEY_AD_SIT_FAM, adherent.getAdSitFam());
            httpParams.put(KEY_AD_NBRE_ENFANT, adherent.getAdNbreEnfACh());
            httpParams.put(KEY_AD_TEL1, adherent.getAdTel1());
            httpParams.put(KEY_AD_TEL2, adherent.getAdTel2());
            httpParams.put(KEY_AD_TEL3, adherent.getAdTel3());
            httpParams.put(KEY_AD_EMAIL, adherent.getAdEMail());
            httpParams.put(KEY_AD_PROFESSION, adherent.getAdProfess());
            httpParams.put(KEY_AD_DOMICILE, adherent.getAdDomicile());
            httpParams.put(KEY_AD_LIEU_TRAVAIL, adherent.getAdLieuTrav());
            httpParams.put(KEY_AD_ACTIVITE_PRINC, adherent.getAdActivitePr());
            httpParams.put(KEY_AD_TYPE_CARTE_ID, adherent.getAdTypCarteID());
            httpParams.put(KEY_AD_NUM_CARTE_ID, adherent.getAdNumCarteID());
            httpParams.put(KEY_AD_VALIDE_DU, adherent.getAdValideDu());
            httpParams.put(KEY_AD_VALIDE_AU, adherent.getAdValideAu());
            httpParams.put(KEY_AD_TYPE_HABITE, adherent.getAdTypHabite());
            httpParams.put(KEY_ADHERENT_PIECE, piecesListId);
            httpParams.put(KEY_ADHERENT_FRAIS_ID, fraisListId);
            httpParams.put(KEY_ADHERENT_FRAIS_MONTANT, fraisListMontant);
            httpParams.put(KEY_ADHERENT_FRAIS_FONCTION, fraisListFonction);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_adherent_1.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
           // addButton.revertAnimation(); // to stop animation on button save
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(GetFraisAdherent.this,
                                "Adhérent ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(GetFraisAdherent.this,
                                "Some error occurred while adding Adhérent",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }



}