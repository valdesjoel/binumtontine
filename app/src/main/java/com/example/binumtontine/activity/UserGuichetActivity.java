/* package com.example.binumtontine.activity;

import eav.os.Bundle;

import com.example.binumtontine.R;
import com.google.eav.material.floatingactionbutton.FloatingActionButton;
import com.google.eav.material.snackbar.Snackbar;
import com.google.eav.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import eav.view.Menu;
import eav.view.MenuInflater;
import eav.view.MenuItem;
import eav.view.View;

import com.example.binumtontine.activity.ui.main.SectionsPagerAdapter;

public class CaisseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caisse);





        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter une caisse", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}*/

package com.example.binumtontine.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;
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

public class UserGuichetActivity extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_USER_GUICHET_ID = "ux_numero";
    private static final String KEY_USER_GUICHET_LOCALITE = "ux_nom";
    private static final String KEY_ProfilCaisseOrGuichet = "profilCaisseOrGuichet";


    private ArrayList<HashMap<String, String>> userGuichetList;
    private ListView userGuichetListView;
    private ProgressDialog pDialog;
    public static String profilCaisseOrGuichet ="caisse"; //Initialisation par défaut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_guichet);
        /* begin */
        //  setContentView(R.layout.activity_movie_listing);
        userGuichetListView = (ListView) findViewById(R.id.userGuichetList);
        new UserGuichetActivity.FetchMoviesAsyncTask().execute();

        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_list_user_guichet);
        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab_list_user_guichet);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un agent de guichet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(UserGuichetActivity.this, CreateUserGuichet.class);
                //startActivity(i);
                startActivityForResult(i, 20);



            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Gestion des profils");
        if(profilCaisseOrGuichet.equals("caisse")){
            getSupportActionBar().setSubtitle(MyData.CAISSE_NAME);
        }else if (profilCaisseOrGuichet.equals("guichet")){

            getSupportActionBar().setSubtitle(MyData.GUICHET_NAME);
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
            pDialog = new ProgressDialog(UserGuichetActivity.this);
            pDialog.setMessage("Loading list agent guichet. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
//            httpParams.put(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID));
            if (profilCaisseOrGuichet.equals("caisse")){
                httpParams.put(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID));
            }else if (profilCaisseOrGuichet.equals("guichet")){
                httpParams.put(KEY_GX_CX_NUMERO, String.valueOf(MyData.GUICHET_ID));
            }

            httpParams.put(KEY_ProfilCaisseOrGuichet, profilCaisseOrGuichet);
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "fetch_all_user_guichet.php", "GET", null);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_user_guichet.php", "GET", httpParams);




            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    userGuichetList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        Integer movieId = movie.getInt(KEY_USER_GUICHET_ID);
                        String movieName = movie.getString(KEY_USER_GUICHET_LOCALITE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_USER_GUICHET_ID, movieId.toString());
                        map.put(KEY_USER_GUICHET_LOCALITE, movieName);
                        userGuichetList.add(map);
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
        ListAdapter adapter = new SimpleAdapter(
                UserGuichetActivity.this, userGuichetList,
                R.layout.list_item, new String[]{KEY_USER_GUICHET_ID,
                KEY_USER_GUICHET_LOCALITE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        userGuichetListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        userGuichetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String userGuichetId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateUserGuichet.class);
                    intent.putExtra(KEY_USER_GUICHET_ID, userGuichetId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(UserGuichetActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        });

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


