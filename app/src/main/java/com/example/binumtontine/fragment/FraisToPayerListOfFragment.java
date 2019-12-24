package com.example.binumtontine.fragment;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.binumtontine.activity.UpdateEAV;
import com.example.binumtontine.activity.parametreGenerauxOF.CreateFraisToPayerOf;
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

public class FraisToPayerListOfFragment extends Fragment implements View.OnClickListener, OnDateSetListener, SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_FP_PIECE_ID = "FpNumero";
    private static final String KEY_FP_PIECE_LIBELLE = "FpLibelle";


    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;

    public FraisToPayerListOfFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.activity_fragment_param_gen1, container, false);
        View rootView = inflater.inflate(R.layout.activity_list_frais_to_payer_of, container, false);

        /* begin */
        //  setContentView(R.layout.activity_movie_listing);
        movieListView = (ListView)rootView.findViewById(R.id.movieList);
        new FraisToPayerListOfFragment.FetchMoviesAsyncTask().execute();

        /* end*/

       /* Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar); */

        FloatingActionButton fab = rootView.findViewById(R.id.fab_add_frais_to_payer_of);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un frais à payer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(getActivity(), CreateFraisToPayerOf.class);
                startActivityForResult(i, 20);


            }
        });

       /* mySpinner = (JRSpinner) rootView.findViewById(R.id.spn_my_spinner_localite_guichet);

        mySpinner.setItems(getResources().getStringArray(R.array.array_localite)); //this is important, you must set it to set the item list
        mySpinner.setTitle("Sélectionner une localité"); //change title of spinner-dialog programmatically
        mySpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinner.setOnSelectMultipleListener(new JRSpinner.OnSelectMultipleListener() {
            @Override
            public void onMultipleSelected(List<Integer> selectedPosition) {
                //do what you want to selected position list
            }
        });


        // debut Gestion du champ date
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateAgrementCx_txt = (EditText) rootView.findViewById(R.id.input_txt_dateDebut_guichet);
        dateAgrementCx_txt.setInputType(InputType.TYPE_NULL);
        dateAgrementCx_txt.requestFocus();

       // findViewsById();


        setDateTimeField();  */
        //******fin gestion du champ date




        // Inflate the layout for this fragment
        return rootView;
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
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_frais_of.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    movieList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        Integer movieId = movie.getInt(KEY_FP_PIECE_ID);
                        String movieName = movie.getString(KEY_FP_PIECE_LIBELLE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FP_PIECE_ID, movieId.toString());
                        map.put(KEY_FP_PIECE_LIBELLE, movieName);
                        movieList.add(map);
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
                getContext(), movieList,
                R.layout.list_item, new String[]{KEY_FP_PIECE_ID,
                KEY_FP_PIECE_LIBELLE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        movieListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getActivity())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    //to manage update Frais OF
                    /*
                    Intent intent = new Intent(getActivity(),
                            UpdateFraisToPayerOf.class);
                    intent.putExtra(KEY_FP_PIECE_ID, movieId);
                    startActivityForResult(intent, 20);
                    */
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