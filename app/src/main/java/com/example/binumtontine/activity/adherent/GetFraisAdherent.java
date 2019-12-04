
package com.example.binumtontine.activity.adherent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.adapter.CustomAdapterListViewCheckbox;
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
    private static final String KEY_DATA = "data";
    private static final String KEY_FC_NUMERO = "FcNumero";

    private static final String KEY_FC_NEW_LIBELLE = "FcNewLibelle";
    private static final String KEY_CAISSE_ID = "FcCaisse";
    private static final String KEY_GUICHET_ID = "FcGuichet";


    private ArrayList<HashMap<String, String>> fraisList = new ArrayList<>();
    private ListView lv;
    private ArrayList<Model> modelArrayList;
    private CustomAdapterListViewCheckbox customAdapterListViewCheckbox;
    private Button btnselect, btnClean, btnnext;
    private  String[] animallist = new String[]{"Pièce 1", "Pièce 2", "Pièce 3", "Pièce 4"};
   // private  String[] animallist = MyData.fruitList.toArray(new String[MyData.fruitList.size()]);
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frais_adherent);

        lv = (ListView) findViewById(R.id.lv_frais_adherent);
        //btnselect = (Button) findViewById(R.id.select);
        btnClean = (Button) findViewById(R.id.btn_clean);
        btnnext = (Button) findViewById(R.id.save_frais_adherent);

        new FetchFraisGuichetAsyncTask().execute();
       // modelArrayList = getModel(false);
//        Log.d("*******************",modelArrayList.toString());
//        Log.d("*******************",animallist.toString());
//        customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(this,modelArrayList);
//        lv.setAdapter(customAdapterListViewCheckbox);

//        btnselect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                modelArrayList = getModel(true);
//                customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetFraisAdherent.this,modelArrayList);
//                lv.setAdapter(customAdapterListViewCheckbox);
//            }
//        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //modelArrayList = getModel(false);
               // customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetFraisAdherent.this,modelArrayList);
                //lv.setAdapter(customAdapterListViewCheckbox);
                finish();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetFraisAdherent.this,NextActivity.class);
                startActivity(intent);
            }
        });


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
                        JSONObject guichet = movies.getJSONObject(i);
                        Integer guichetId = guichet.getInt(KEY_FC_NUMERO);
                        String guichetDenomination = guichet.getString(KEY_FC_NEW_LIBELLE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FC_NUMERO, guichetId.toString());
                        map.put(KEY_FC_NEW_LIBELLE, guichetDenomination);
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
        ListAdapter adapter = new SimpleAdapter(
                GetFraisAdherent.this, fraisList,
                R.layout.list_item, new String[]{KEY_FC_NUMERO,
                KEY_FC_NEW_LIBELLE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        lv.setAdapter(adapter);
        //lv.setAdapter(customAdapterListViewCheckbox);
        //Call MovieUpdateDeleteActivity when a movie is clicked
      /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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