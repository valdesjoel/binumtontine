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

public class UserActivity extends AppCompatActivity  implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_USER_ID = "ux_numero";
    private static final String KEY_USER_NAME = "ux_nom";

    private ArrayList<HashMap<String, String>> userList;
    private ListView userListView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_cx);

        userListView = (ListView) findViewById(R.id.userList);
        new FetchListUsersCaisseAsyncTask().execute();


        Toolbar toolbar = findViewById(R.id.toolbar_list_user_cx);
        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab_list_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un administrateur de caisse", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(UserActivity.this, CreateUser.class);
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
        getSupportActionBar().setTitle("Administrateurs de caisse");

    }




    /**
     * Fetches the list of users from the server
     */
    private class FetchListUsersCaisseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UserActivity.this);
            pDialog.setMessage("Loading users caisses. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_user.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray users;
                if (success == 1) {
                    userList = new ArrayList<>();
                    users = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate users list
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject movie = users.getJSONObject(i);
                        Integer userId = movie.getInt(KEY_USER_ID);
                        String userName = movie.getString(KEY_USER_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_USER_ID, userId.toString());
                        map.put(KEY_USER_NAME, userName);
                        userList.add(map);
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
                UserActivity.this, userList,
                R.layout.list_item, new String[]{KEY_USER_ID,
                KEY_USER_NAME},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        userListView.setAdapter(adapter);
        //Call UserUpdateDeleteActivity when a user is clicked
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateUser.class);
                    intent.putExtra(KEY_USER_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(UserActivity.this,
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


