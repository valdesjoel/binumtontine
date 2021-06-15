package com.example.binumtontine.activity.parametrageGuichet;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.parametreGenerauxCx.CreateFraisToPayerCx;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FraisToPayerListGxFragment extends Fragment implements View.OnClickListener, OnDateSetListener, SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_FP_PIECE_ID = "FpNumero";
    private static final String KEY_FP_PIECE_LIBELLE = "FpLibelle";
    private static final String KEY_CAISSE_ID = "FcCaisse";
    private static final String KEY_GUICHET_ID = "FcGuichet";
    private static final String KEY_TM_NUMERO = "TmNumero";
    private static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to push intent.extra
    private Boolean action_to_affect=false; // to manage menuItem ACTION_TO_AFFECT and ACTION_NOT_ALREADY_AFFECT


    private ArrayList<HashMap<String, String>> fraisList;
    private ListView fraisListView;
    private ProgressDialog pDialog;

    public FraisToPayerListGxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //to notify the fragment that it should participate in options menu handling.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list_frais_to_payer_cx, container, false);

        /* begin */
        //  setContentView(R.layout.activity_movie_listing);
        fraisListView = (ListView)rootView.findViewById(R.id.movieList);
        new FraisToPayerListGxFragment.FetchMoviesAsyncTask().execute();

        /* end*/

       /* Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar); */

        FloatingActionButton fab = rootView.findViewById(R.id.fab_add_frais_to_payer_cx);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un frais à payer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(getActivity(), CreateFraisToPayerCx.class);
                startActivityForResult(i, 20);


            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        // super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.menu_piece_frais_cx_, menu);
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_to_affect_piece:
                action_to_affect = false;

                Toast.makeText(getContext(),
                        "Liste des frais à affecter au guichet",
                        Toast.LENGTH_LONG).show();
                new FraisToPayerListGxFragment.FetchMoviesAsyncTask().execute();
                // action_to_affect = false;
                // new ProduitEAVGuichetActivity.FetchPiecesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_already_affect_piece:
                action_to_affect = true;

                Toast.makeText(getContext(),
                        "Liste des frais déjà affectée au guichet",
                        Toast.LENGTH_LONG).show();
                new FraisToPayerListGxFragment.FetchMoviesAsyncTask().execute();
                // action_to_affect = true;
                // new ProduitEAVGuichetActivity.FetchPiecesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Fetches the list of movies from the server
     */
    private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading Frais à payer. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "fetch_all_frais_of.php", "GET", null);

            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_TM_NUMERO, String.valueOf(MyData.TYPE_MEMBRE_ID));
            JSONObject jsonObject =(action_to_affect)? httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_frais_gx.php", "GET", httpParams): httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_frais_cx_not_affect_on_guichet.php", "GET", httpParams);


            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    fraisList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    fraisList.clear();
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        Integer movieId = movie.getInt(KEY_FP_PIECE_ID);
                        String movieName = movie.getString(KEY_FP_PIECE_LIBELLE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FP_PIECE_ID, movieId.toString());
                        map.put(KEY_FP_PIECE_LIBELLE, movieName);
                        fraisList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            getActivity().runOnUiThread(new Runnable() {
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
        ListAdapter adapter = new SimpleAdapter(
                getContext(), fraisList,
                R.layout.list_item, new String[]{KEY_FP_PIECE_ID,
                KEY_FP_PIECE_LIBELLE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        fraisListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        fraisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getActivity())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    //to manage update frais Cx

                    Intent intent = new Intent(getActivity(),
                            CreateFraisToPayerGx.class);
                    intent.putExtra(KEY_FP_PIECE_ID, movieId);

                    intent.putExtra(KEY_EXTRA_ACTION_TO_AFFECT, !action_to_affect);
                    startActivityForResult(intent, 20);
                } else {
                    Toast.makeText(getContext(),
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the movie.
            // So refresh the movie listing
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }
    }











    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}