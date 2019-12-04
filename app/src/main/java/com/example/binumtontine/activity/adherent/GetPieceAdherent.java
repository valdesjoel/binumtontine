
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


public class GetPieceAdherent extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_FC_NUMERO = "FcNumero";
    private static final String KEY_FC_NEW_LIBELLE = "FcNewLibelle";
    private static final String KEY_CAISSE_ID = "FcCaisse";
    private static final String KEY_GUICHET_ID = "FcGuichet";


    private ArrayList<HashMap<String, String>> piecesList = new ArrayList<>();
    private ListView lvPieces;
    private ArrayList<Model> modelArrayList;
    private CustomAdapterListViewCheckbox customAdapterListViewCheckbox;
    private Button btnselect, btndeselect, btnnext;
    private  String[] animallist = new String[]{"Pièce 1", "Pièce 2", "Pièce 3", "Pièce 4"};
   // private  String[] animallist = MyData.fruitList.toArray(new String[MyData.fruitList.size()]);
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece_adherent);

        lvPieces = (ListView) findViewById(R.id.lv);
        btnselect = (Button) findViewById(R.id.select);
        btndeselect = (Button) findViewById(R.id.deselect);
        btnnext = (Button) findViewById(R.id.next);

        new GetPieceAdherent.FetchPiecesGuichetAsyncTask().execute();
        modelArrayList = getModel(false);
//        Log.d("*******************",modelArrayList.toString());
//        Log.d("*******************",animallist.toString());
        customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(this,modelArrayList);
        lvPieces.setAdapter(customAdapterListViewCheckbox);

        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(true);
                customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetPieceAdherent.this,modelArrayList);
                lvPieces.setAdapter(customAdapterListViewCheckbox);
            }
        });
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(false);
                customAdapterListViewCheckbox = new CustomAdapterListViewCheckbox(GetPieceAdherent.this,modelArrayList);
                lvPieces.setAdapter(customAdapterListViewCheckbox);
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetPieceAdherent.this,GetFraisAdherent.class);
                startActivity(intent);
            }
        });


    }

    private ArrayList<Model> getModel(boolean isSelect){
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i <animallist.length; i++){
        //for(int i = 0; i <MyData.animallist.length; i++){
       // for(int i = 0; i <MyData.fruitList.size(); i++){

            Model model = new Model();
            model.setSelected(isSelect);
            model.setAnimal(animallist[i]);
            //model.setAnimal(MyData.fruitList.get(i));
            //model.setAnimal(String.valueOf(piecesList.get(i).get(KEY_FC_NEW_LIBELLE)));
            list.add(model);
        }
        return list;
    }

    /**
     * Fetches the list of movies from the server
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
     * Updating parsed JSON data into ListView
     * */
    private void populateGuichetList() {
        ListAdapter adapter = new SimpleAdapter(
                GetPieceAdherent.this, piecesList,
                R.layout.list_view_checkbox_item, new String[]{KEY_FC_NUMERO,
                KEY_FC_NEW_LIBELLE},
                new int[]{R.id.movieId, R.id.animal});
        // updating listview
        lvPieces.setAdapter(adapter);
        //lvPieces.setAdapter(customAdapterListViewCheckbox);
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