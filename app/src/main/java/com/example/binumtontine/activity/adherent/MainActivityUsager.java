package com.example.binumtontine.activity.adherent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.BrouillardCaisse;
import com.example.binumtontine.activity.ClotureJourneeActivity;
import com.example.binumtontine.activity.CreateOperationExterneDetails;
import com.example.binumtontine.activity.CreateOperationTransfertEnvoyer;
import com.example.binumtontine.activity.CreateOperationTransfertRetrait;
import com.example.binumtontine.activity.ListNomPrenomAdherentTransfert;
import com.example.binumtontine.activity.ListOperationExterneDetails;
import com.example.binumtontine.activity.LoginActivity_NEW;
import com.example.binumtontine.activity.SituationGuichet;
import com.example.binumtontine.activity.collecte.ListCollecteurActivity;
import com.example.binumtontine.adapter.CustomAdapterListAdherent;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class MainActivityUsager extends AppCompatActivity implements SERVER_ADDRESS, SearchView.OnQueryTextListener {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_AD_AdNumero = "AdNumero";
    private static final String KEY_AD_AdCode = "AdCode";
    private static final String KEY_AD_AdNumManuel = "AdNumManuel";
    private static final String KEY_AD_AdNom = "AdNom";
    private static final String KEY_AD_AdPrenom = "AdPrenom";
    private static final String KEY_AD_AdDateNaiss = "AdDateNaiss";
    private static final String KEY_AD_AdLieuNaiss = "AdLieuNaiss";
    private static final String KEY_AD_AdSexe = "AdSexe";
    private static final String KEY_AD_AdNationalite = "AdNationalite";
    private static final String KEY_AD_AdSitFam = "AdSitFam";
    private static final String KEY_AD_AdNbreEnfACh = "AdNbreEnfACh";
    private static final String KEY_AD_AdTel1 = "AdTel1";
    private static final String KEY_AD_AdTel2 = "AdTel2";
    private static final String KEY_AD_AdTel3 = "AdTel3";
    private static final String KEY_AD_AdEMail = "AdEMail";
    private static final String KEY_AD_AdProfess = "AdProfess";

    private static final String KEY_AD_AdDomicile = "AdDomicile";
    private static final String KEY_AD_AdLieuTrav = "AdLieuTrav";
    private static final String KEY_AD_AdActivitePr = "AdActivitePr";
    private static final String KEY_AD_AdTypCarteID = "AdTypCarteID";
    private static final String KEY_AD_AdNumCarteID = "AdNumCarteID";
    private static final String KEY_AD_AdValideDu = "AdValideDu";
    private static final String KEY_AD_AdValideAu = "AdValideAu";
    private static final String KEY_AD_AdTypHabite = "AdTypHabite";
    private static final String KEY_AD_AdEstParti = "AdEstParti";
    private static final String KEY_AD_AdPartiLe = "AdPartiLe";
    private static final String KEY_AD_AdRemplacePar = "AdRemplacePar";
    private static final String KEY_AD_NBRE_COMPTE = "AdNbreCompte";
    //private static final String KEY_AD_NBRE_COMPTE = "AdNbreCompte";

    private static final String KEY_AD_GUICHET = "AdGuichet";

    private static final String KEY_OC_DATE_DEBUT = "OcDateDebut";
    private static final String KEY_OC_DATE_FIN = "OcDateFin";
    private static EditText fromdate; private static String dateDebut; //dateDebut permet de stocker fromDate au format YYYY-MM-DD
    private static EditText todate; private static String dateFin; //dateFin permet de stocker toDate au format YYYY-MM-DD
    private static String maDate;
    private static CircularProgressButton btnHistorique;
    //    private String maDate = "" ;
    private static String endDate ="";
    private static LinearLayout bloc_date;
    private static TextView tv_lbl_nbre_adherent;
    private static TextView tv_nbre_adherent;


    //recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
//    private RecyclerView.Adapter adapter;
    private CustomAdapterListAdherent adapter;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogNewAdherent;
    private ProgressBar progressBar;
    Sprite doubleBounce = new DoubleBounce();
    //model object for our listAdherent data
    private ArrayList<HashMap<String, String>> fraisList = new ArrayList<>();
    //private List<MyList> listAdherent;
    private List<Adherent> listAdherent;
    private boolean isNewAdherent=true;
    private int nbreAdherent= 0;
    private MenuItem searchMenuItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_adherent);
        //Toolbar toolbar = findViewById(R.id.toolbar_list_adherent);
        Toolbar toolbar = findViewById(R.id.toolbar_list_adherent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ACCUEIL DU GUICHET") ;
        getSupportActionBar().setSubtitle(MyData.GUICHET_NAME);

//        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
//        Sprite doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);

        //initializing views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdherent = new ArrayList<>();

        //START Manage Bloc Date
        tv_lbl_nbre_adherent = (TextView) findViewById(R.id.tv_lbl_nbre_adherent);
        tv_nbre_adherent = (TextView) findViewById(R.id.tv_nbre_adherent);
        bloc_date = (LinearLayout) findViewById(R.id.bloc_date);
        fromdate = (EditText) findViewById(R.id.input_txt_fromDate);
        todate = (EditText) findViewById(R.id.input_txt_toDate);
        btnHistorique = (CircularProgressButton) findViewById(R.id.btn_afficher);
//        lanceur();
        btnHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    new MainActivityUsager.FetchNewAdherentGuichetAsyncTask().execute();
                } else {
                    Toast.makeText(MainActivityUsager.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        Date todayDateInit = Calendar.getInstance().getTime();
        //SimpleDateFormat formatterInint = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterInint = new SimpleDateFormat("dd/MM/yyyy");
        maDate = formatterInint.format(todayDateInit);

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTruitonDatePickerDialog(view);
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromdate.getText().toString().trim().equals("")){

                    Toast.makeText(MainActivityUsager.this,
                            "Veuillez renseigner la date de début",
                            Toast.LENGTH_LONG).show();
                }else {
                    showToDatePickerDialog(view);

                }

            }
        });
        //END Manage Bloc Date

        //loading listAdherent view item with this function
        //loadRecyclerViewItem();
        new MainActivityUsager.FetchAdherentGuichetAsyncTask().execute();
    }
    private  void lanceur(){
        // get today and clear time of day
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal1.clear(Calendar.MINUTE);
        cal1.clear(Calendar.SECOND);
        cal1.clear(Calendar.MILLISECOND);

// get start of the month
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("Start of the month:       " + cal1.getTime());
        System.out.println("... in milliseconds:      " + cal1.getTimeInMillis());


        Calendar cal = Calendar.getInstance();
        cal.setTime(cal1.getTime());
//        cal.setTime(date);

// Set the day of the month to the first day of the month

        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMinimum(Calendar.DAY_OF_MONTH));

// Extract the Date from the Calendar instance

        Date firstDayOfTheMonth = cal.getTime();
        System.out.println("Start of the month ********firstDayOfTheMonth:       " + firstDayOfTheMonth);
        Date todayDateInit = Calendar.getInstance().getTime();
        //SimpleDateFormat formatterInint = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterInint = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat formatterEnd = new SimpleDateFormat("dd/MM/yyyy");
        maDate = formatterInint.format(firstDayOfTheMonth);
        endDate = formatterInint.format(todayDateInit);
        System.out.println("Start of the month ********maDate:       " + maDate);
        System.out.println("End of the month ********endDate:       " + endDate);
        fromdate.setText(maDate);
        todate.setText(endDate);
//        String maDate = formatterInint.format(todayDateInit);
        new MainActivityUsager.FetchNewAdherentGuichetAsyncTask().execute();

//        String getfromdate = fromdate.getText().toString().trim();
        String getfrom[] = maDate.split("/");
        String getTo[] = endDate.split("/");
        String year,month,day;
        String yearTo,monthTo,dayTo;
//        year= Integer.parseInt(getfrom[2]);
        year= getfrom[2];
//        month = Integer.parseInt(getfrom[1])-1;
        month = getfrom[1];
        day = getfrom[0];
        dateDebut = ""+year+"-"+month+"-"+day;

        yearTo= getTo[2];
//        month = Integer.parseInt(getfrom[1])-1;
        monthTo = getTo[1];
        dayTo = getTo[0];
        dateFin = ""+yearTo+"-"+monthTo+"-"+dayTo;
        System.out.println("dateDebut*******:       " + dateDebut);
        System.out.println("dateFin ********:       " + dateFin);
    }

    public void avertissement() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Avertissement !")
                .setMessage("Vous êtes en mode consultation unique !")
                .setNegativeButton("Non", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }

                })
                .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }

//    boolean doubleBackToExitPressedOnce = false;
//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "SVP veuillez cliquer deux fois pour sortir !", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }
@Override
public void onBackPressed() {
    new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Quitter l'application")
            .setMessage("Etes-vous sûr de vouloir vous déconnecter ?")
            .setPositiveButton("Oui", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            })
            .setNegativeButton("Non", null)
            .show();
}
    /* To manage Menu*/
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_adherent, menu);
        /*Start manage searchable item*/

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Rechercher un membre");
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        /*End manage searchable item*/
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();

                return true;
            case R.id.action_person_add:
                if (MyData.USER_PROFIL.equals("Administrateur caisse")){
                    avertissement();
                }else if (MyData.USER_PROFIL.equals("Agent de guichet")){

                    Intent i = new Intent(MainActivityUsager.this, CreateAdherent.class);
                    startActivityForResult(i,20);
                }
                return true;
            case R.id.action_situation_guichet:
                Intent intent_situation_guichet = new Intent(MainActivityUsager.this, SituationGuichet.class);
                startActivityForResult(intent_situation_guichet,20);
                return true;
            case R.id.action_brouillard_de_caisse:
                //action_to_affect = true;

                Intent intent_brouillard = new Intent(MainActivityUsager.this, BrouillardCaisse.class);
                startActivityForResult(intent_brouillard,20);
                return true;
            case R.id.action_nouveaux_adherent:
                try {
                    if (isNewAdherent){
                        lanceur();
                        bloc_date.setVisibility(View.VISIBLE);
    //                    recyclerView.setLayoutParams(marginLayoutParams);
    //                progressBar.setVisibility(View.VISIBLE);
                        item.setTitle("Tous les Adhérents");
                        isNewAdherent= false;
//                        new FetchNewAdherentGuichetAsyncTask().execute();
                    }else{
                        new FetchAdherentGuichetAsyncTask().execute();
                        item.setTitle("Nouveaux Adhérents");
                        isNewAdherent= true;
                        bloc_date.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
                case R.id.action_cloturer_la_journee:

                    Intent intent_cloture_journee = new Intent(MainActivityUsager.this, ClotureJourneeActivity.class);
                    startActivityForResult(intent_cloture_journee,20);
                return true;
                case R.id.action_operation_externe:

                return true;
                case R.id.action_add_operation_externe:
                    if (MyData.USER_PROFIL.equals("Administrateur caisse")){
                        avertissement();
                    }else if (MyData.USER_PROFIL.equals("Agent de guichet")){
                        Intent intent_OperationExterneDetails = new Intent(MainActivityUsager.this, CreateOperationExterneDetails.class);
                        startActivityForResult(intent_OperationExterneDetails,20);
                    }

                return true;
                case R.id.action_list_operation_externe:

                    Intent intent_list_OperationExterneDetails = new Intent(MainActivityUsager.this, ListOperationExterneDetails.class);
                    startActivityForResult(intent_list_OperationExterneDetails,20);

                return true;
                case R.id.action_transfert:

                return true;
                case R.id.action_transfert_envoie:

                    if (MyData.USER_PROFIL.equals("Administrateur caisse")){
                        avertissement();
                    }else if (MyData.USER_PROFIL.equals("Agent de guichet")){
                        Intent intent_transfert_envoie = new Intent(MainActivityUsager.this, CreateOperationTransfertEnvoyer.class);
                        startActivityForResult(intent_transfert_envoie,20);
                    }

                return true;
                case R.id.action_transfert_retrait:

                    if (MyData.USER_PROFIL.equals("Administrateur caisse")){
                        avertissement();
                    }else if (MyData.USER_PROFIL.equals("Agent de guichet")){
                        Intent intent_transfert_retrait = new Intent(MainActivityUsager.this, CreateOperationTransfertRetrait.class);
                        startActivityForResult(intent_transfert_retrait,20);
                    }

                return true;
                case R.id.action_cotisations_annuelle:
              //  action_to_affect = true;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_jour_anterieur:
               // action_to_affect = true;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_collecte_journaliere:

                    Intent intent_list_collecteur = new Intent(MainActivityUsager.this, ListCollecteurActivity.class);
                    startActivityForResult(intent_list_collecteur,20);

                return true;
                case R.id.action_logout:

                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Quitter l'application")
                            .setMessage("Etes-vous sûr de vouloir vous déconnecter ?")
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }

                            })
                            .setNegativeButton("Non", null)
                            .show();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadRecyclerViewItem() {
        //you can fetch the data from server or some apis

        tv_nbre_adherent.setText(listAdherent.size()+"");
        adapter = new CustomAdapterListAdherent(listAdherent, this);
        recyclerView.setAdapter(adapter);

    }
    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new ToDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
//        MainActivityUsager.this.adapter.getFilter().filter(newText);
        return true;
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
       /*     final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
            */

            String getfrom[] = (fromdate.getText().toString().trim()).split("/");
            int year,month,day;
            year= Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month-1,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            int selectedMonth = month+1; //j'atoute 1 parce qu'il compte à partir de 0
            if (selectedMonth<10 && day<10){
                fromdate.setText("0"+day + "/0" + selectedMonth  + "/" + year);
                dateDebut = year+"-0"+selectedMonth+"-0"+day;

            }else if (day<10){
                fromdate.setText("0"+day + "/" + selectedMonth  + "/" + year);
                dateDebut = year+"-"+selectedMonth+"-0"+day;
            }else if (selectedMonth<10){
                fromdate.setText(day + "/0" + selectedMonth  + "/" + year);
                dateDebut = year+"-0"+selectedMonth+"-"+day;
            }else {
                fromdate.setText(day + "/" + selectedMonth  + "/" + year);
                dateDebut = year+"-"+selectedMonth+"-"+day;
            }
            btnHistorique.performClick();
        }


    }
    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        // Calendar startDateCalendar=Calendar.getInstance();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker


            String getTo[] = (todate.getText().toString().trim()).split("/");

            int yearTo,monthTo,dayTo;
            yearTo= Integer.parseInt(getTo[2]);
            monthTo = Integer.parseInt(getTo[1]);
            dayTo = Integer.parseInt(getTo[0]);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, yearTo,
                    monthTo-1,dayTo);
            return datePickerDialog;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            int selectedMonth = month+1;
//            todate.setText(day + "/" + selectedMonth  + "/" + year);
            if (selectedMonth<10 && day<10){
                todate.setText("0"+day + "/0" + selectedMonth  + "/" + year);
                dateFin = year+"-0"+selectedMonth+"-0"+day;
            }else if (day<10){
                todate.setText("0"+day + "/" + selectedMonth  + "/" + year);
                dateFin = year+"-"+selectedMonth+"-0"+day;
            }else if (selectedMonth<10){
                todate.setText(day + "/0" + selectedMonth  + "/" + year);
                dateFin = year+"-0"+selectedMonth+"-"+day;
            }else {
                todate.setText(day + "/" + selectedMonth  + "/" + year);
                dateFin = year+"-"+selectedMonth+"-"+day;
            }
            btnHistorique.performClick();

        }

    }
    /**
     * Fetches the listAdherent of movies from the server
     */
    public  class FetchAdherentGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityUsager.this);
            pDialog.setMessage("Loading adherents. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_AD_GUICHET, String.valueOf(MyData.GUICHET_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_adherents.php", "GET", httpParams);
            //creating Arraylist
            //List<String> fruitList = new ArrayList<>();
//            Log.e("*******",jsonObject.toString());
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    //fraisList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    listAdherent.clear();
                    //Iterate through the response and populate movies listAdherent
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject guichet = movies.getJSONObject(i);
                        String adherentID = guichet.getString(KEY_AD_AdNumero);
                        String adherentCode = guichet.getString(KEY_AD_AdCode);
                        String adherentNumManuel = guichet.getString(KEY_AD_AdNumManuel);
                        String adherentNom = guichet.getString(KEY_AD_AdNom);
                        String adherentPrenom = guichet.getString(KEY_AD_AdPrenom);
                        String adherentDateNaiss = guichet.getString(KEY_AD_AdDateNaiss);
                        String adherentLieuNaiss = guichet.getString(KEY_AD_AdLieuNaiss);
                        String adherentSexe = guichet.getString(KEY_AD_AdSexe);
                        String adherentNationalite = guichet.getString(KEY_AD_AdNationalite);
                        String adherentSituaFamiliale = guichet.getString(KEY_AD_AdSitFam);
                        String adherentNbreEnfant = guichet.getString(KEY_AD_AdNbreEnfACh);
                        String adherentTel1 = guichet.getString(KEY_AD_AdTel1);
                        String adherentTel2 = guichet.getString(KEY_AD_AdTel2);
                        String adherentTel3 = guichet.getString(KEY_AD_AdTel3);
                        String adherentEmail = guichet.getString(KEY_AD_AdEMail);
                        String adherentProfession = guichet.getString(KEY_AD_AdProfess);
                        String adherentDomicile = guichet.getString(KEY_AD_AdDomicile);
                        String adherentLieuTravail = guichet.getString(KEY_AD_AdLieuTrav);
                        String adherentActivitePrincipale = guichet.getString(KEY_AD_AdActivitePr);
                        String adherentTypeCarteID = guichet.getString(KEY_AD_AdTypCarteID);
                        String adherentNumCarteID = guichet.getString(KEY_AD_AdNumCarteID);
                        String adherentValideDu = guichet.getString(KEY_AD_AdValideDu);
                        String adherentValideAu = guichet.getString(KEY_AD_AdValideAu);
                        String adherentTypHabite = guichet.getString(KEY_AD_AdTypHabite);
                        String adherentEstParti = guichet.getString(KEY_AD_AdEstParti);
                        String adherentPartiLe = guichet.getString(KEY_AD_AdPartiLe);
                        String adherentRemplacePar = guichet.getString(KEY_AD_AdRemplacePar);
                        String adherentNbreCompte = guichet.getString(KEY_AD_NBRE_COMPTE);
                        String adherentAdTotNbPartSoc = guichet.getString(Adherent.KEY_AdTotNbPartSoc);
                        //MyList myList = new MyList(
                        Adherent myList = new Adherent(
                                adherentID ,
                                adherentCode,
                                adherentNumManuel,
                                adherentNom ,
                                adherentPrenom,
                                adherentDateNaiss,
                                adherentLieuNaiss,
                                adherentSexe,
                                adherentNationalite,
                                adherentSituaFamiliale,
                                adherentNbreEnfant,
                                adherentTel1,
                                adherentTel2,
                                adherentTel3,
                                adherentEmail,
                                adherentProfession,
                                adherentDomicile,
                                adherentLieuTravail,
                                adherentActivitePrincipale,
                                adherentTypeCarteID,
                                adherentNumCarteID,
                                adherentValideDu,
                                adherentValideAu,
                                adherentTypHabite,
                                adherentEstParti,
                                adherentPartiLe,
                                adherentRemplacePar,
                                MyData.GUICHET_ID,
                                adherentNbreCompte

                        );
                        myList.setAdTotNbPartSoc(adherentAdTotNbPartSoc);

                        listAdherent.add(myList);
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
                   // populateGuichetList();
//                    loadRecyclerViewItem();

                    try {
                        loadRecyclerViewItem();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }



    }
    /**
     * Fetches the listAdherent of current month from the server
     */
    public  class FetchNewAdherentGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogNewAdherent = new ProgressDialog(MainActivityUsager.this);
            pDialogNewAdherent.setMessage("Loading new adherents. Please wait...");
            pDialogNewAdherent.setIndeterminate(false);
            pDialogNewAdherent.setCancelable(false);
            pDialogNewAdherent.show();
//            progressBar = new ProgressBar(MainActivityUsager.this);
//            progressBar.setIndeterminateDrawable(doubleBounce);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_AD_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_OC_DATE_DEBUT, dateDebut);
            httpParams.put(KEY_OC_DATE_FIN, dateFin);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_new_adherents.php", "GET", httpParams);
            //creating Arraylist
            //List<String> fruitList = new ArrayList<>();
//            Log.e("*******",jsonObject.toString());
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    //fraisList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    listAdherent.clear();
                    //Iterate through the response and populate movies listAdherent
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject guichet = movies.getJSONObject(i);
                        String adherentID = guichet.getString(KEY_AD_AdNumero);
                        String adherentCode = guichet.getString(KEY_AD_AdCode);
                        String adherentNumManuel = guichet.getString(KEY_AD_AdNumManuel);
                        String adherentNom = guichet.getString(KEY_AD_AdNom);
                        String adherentPrenom = guichet.getString(KEY_AD_AdPrenom);
                        String adherentDateNaiss = guichet.getString(KEY_AD_AdDateNaiss);
                        String adherentLieuNaiss = guichet.getString(KEY_AD_AdLieuNaiss);
                        String adherentSexe = guichet.getString(KEY_AD_AdSexe);
                        String adherentNationalite = guichet.getString(KEY_AD_AdNationalite);
                        String adherentSituaFamiliale = guichet.getString(KEY_AD_AdSitFam);
                        String adherentNbreEnfant = guichet.getString(KEY_AD_AdNbreEnfACh);
                        String adherentTel1 = guichet.getString(KEY_AD_AdTel1);
                        String adherentTel2 = guichet.getString(KEY_AD_AdTel2);
                        String adherentTel3 = guichet.getString(KEY_AD_AdTel3);
                        String adherentEmail = guichet.getString(KEY_AD_AdEMail);
                        String adherentProfession = guichet.getString(KEY_AD_AdProfess);
                        String adherentDomicile = guichet.getString(KEY_AD_AdDomicile);
                        String adherentLieuTravail = guichet.getString(KEY_AD_AdLieuTrav);
                        String adherentActivitePrincipale = guichet.getString(KEY_AD_AdActivitePr);
                        String adherentTypeCarteID = guichet.getString(KEY_AD_AdTypCarteID);
                        String adherentNumCarteID = guichet.getString(KEY_AD_AdNumCarteID);
                        String adherentValideDu = guichet.getString(KEY_AD_AdValideDu);
                        String adherentValideAu = guichet.getString(KEY_AD_AdValideAu);
                        String adherentTypHabite = guichet.getString(KEY_AD_AdTypHabite);
                        String adherentEstParti = guichet.getString(KEY_AD_AdEstParti);
                        String adherentPartiLe = guichet.getString(KEY_AD_AdPartiLe);
                        String adherentRemplacePar = guichet.getString(KEY_AD_AdRemplacePar);
                        String adherentNbreCompte = guichet.getString(KEY_AD_NBRE_COMPTE);
                        String adherentAdTotNbPartSoc = guichet.getString(Adherent.KEY_AdTotNbPartSoc);
                        //MyList myList = new MyList(
                        Adherent myList = new Adherent(
                                adherentID ,
                                adherentCode,
                                adherentNumManuel,
                                adherentNom ,
                                adherentPrenom,
                                adherentDateNaiss,
                                adherentLieuNaiss,
                                adherentSexe,
                                adherentNationalite,
                                adherentSituaFamiliale,
                                adherentNbreEnfant,
                                adherentTel1,
                                adherentTel2,
                                adherentTel3,
                                adherentEmail,
                                adherentProfession,
                                adherentDomicile,
                                adherentLieuTravail,
                                adherentActivitePrincipale,
                                adherentTypeCarteID,
                                adherentNumCarteID,
                                adherentValideDu,
                                adherentValideAu,
                                adherentTypHabite,
                                adherentEstParti,
                                adherentPartiLe,
                                adherentRemplacePar,
                                MyData.GUICHET_ID,
                                adherentNbreCompte

                        );
                        myList.setAdTotNbPartSoc(adherentAdTotNbPartSoc);
                        listAdherent.add(myList);
                    }
                    nbreAdherent = listAdherent.size();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogNewAdherent.dismiss();
//            isNewAdherent=false;
//            progressBar.setVisibility(View.GONE);
            runOnUiThread(new Runnable() {
                public void run() {

//                   if (listAdherent.isEmpty()){
//                       new AlertDialog.Builder(MainActivityUsager.this)
//                               .setIcon(android.R.drawable.ic_dialog_alert)
//                               .setTitle("Aucun adhérent trouvé")
//                               .setMessage("Il n'ya aucun adhérent enregistré durant cette période")
//                               .setPositiveButton("Oui", new DialogInterface.OnClickListener()
//                               {
//                                   @Override
//                                   public void onClick(DialogInterface dialog, int which) {
//                                       finish();
//                                   }
//
//                               })
//                               .setNegativeButton("Non", null)
//                               .show();
//                   }
//                    loadRecyclerViewItem();
                   try {
                        loadRecyclerViewItem();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
            finish();
            startActivity(intent);
        }
        CreateAdherent.to_update_adherent = false;//to set default value at false
    }

    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateGuichetList() {
      /*  ListAdapter adapter = new SimpleAdapter(
                GetFraisAdherent.this, fraisList,
                R.layout.list_item, new String[]{KEY_FC_NUMERO,
                KEY_FC_NEW_LIBELLE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        lv.setAdapter(adapter); */
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
