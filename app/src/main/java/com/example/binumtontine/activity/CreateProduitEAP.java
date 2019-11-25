package com.example.binumtontine.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateProduitEAP extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_MOVIE_NAME = "movie_name";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_YEAR = "year";
    private static final String KEY_RATING = "rating";

    private static String STRING_EMPTY = "";
    private EditText movieNameEditText;
    private EditText genreEditText;
    private EditText yearEditText;
    private EditText ratingEditText;
    private String movieName;
    private String genre;
    private String year;
    private String rating;
    private Button addButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_add_movie);
        setContentView(R.layout.activity_eap);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eap);
        setSupportActionBar(toolbar);
        setToolbarTitle();*/
        movieNameEditText = (EditText) findViewById(R.id.input_txt_Code_EAP);
        genreEditText = (EditText) findViewById(R.id.input_txt_LibelleEAP);
        yearEditText = (EditText) findViewById(R.id.input_txt_MinMtMiseEAP);
        ratingEditText = (EditText) findViewById(R.id.input_txt_DureeMinEAP);
        addButton = (Button) findViewById(R.id.btn_save_eap);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addMovie();
                } else {
                    Toast.makeText(CreateProduitEAP.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAP");

    }

    /**
     * Checks whether all files are filled. If so then calls AddMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addMovie() {
        if (!STRING_EMPTY.equals(movieNameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(genreEditText.getText().toString()) &&
                !STRING_EMPTY.equals(yearEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ratingEditText.getText().toString())) {

            movieName = movieNameEditText.getText().toString();
            genre = genreEditText.getText().toString();
            year = yearEditText.getText().toString();
            rating = ratingEditText.getText().toString();
            new CreateProduitEAP.AddMovieAsyncTask().execute();
        } else {
            Toast.makeText(CreateProduitEAP.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateProduitEAP.this);
            pDialog.setMessage("Adding EAP. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_MOVIE_NAME, movieName);
            httpParams.put(KEY_GENRE, genre);
            httpParams.put(KEY_YEAR, year);
            httpParams.put(KEY_RATING, rating);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eap.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(CreateProduitEAP.this,
                                "EAP Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateProduitEAP.this,
                                "Some error occurred while adding EAP",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}