package com.example.binumtontine.activity.adherent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.collecte.JourOuvertCollecteur;
import com.example.binumtontine.adapter.CustomAdapterListDemandeRetraitAffilier;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import static java.lang.Double.parseDouble;

public class ListDemandeRetraitAffilierActivity extends AppCompatActivity implements SERVER_ADDRESS {

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

    //recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressDialog pDialog;
    //model object for our listAdherent data
    private ArrayList<HashMap<String, String>> fraisList = new ArrayList<>();
    //private List<MyList> listAdherent;
    private List<Adherent> listAdherent;
    private List<ComptesAdherent> listComptesAdherent;


    /*BEGIN*/
    private static final String KEY_DATA_EAV = "EAV";
    private static final String KEY_DATA_DECOUVERT = "DF";
    private static final String KEY_DATA_COMPTE_COURANT = "CC";
    private static final String KEY_DATA_EAT = "EAT";
    private static final String KEY_DATA_EAP = "EAP";
    private static final String KEY_DATA_DEMANDE_CREDIT = "DC";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private static final String KEY_DfStatut = "DfStatut";
    private static final String KEY_NUM_DOSSIER_COMPTE = "NumDossier";
    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_MONTANT_ACCORDE_COMPTE = "DfMtAccordeCom";
    private static final String KEY_MONTANT_DEMANDE_DECOUV = "DfMtDecouv";
    private static final String KEY_DATE_H_CREE = "DateHCree";
    private static final String KEY_AdNom = "AdNom";
    private static final String KEY_TAUX = "Taux";
    private static final String KEY_TYPE_COMPTE = "TypeCompte";
    private static final String KEY_ADHERENT = "ADHERENT";
//    public static Adherent adherent;
    public static ComptesAdherent adherent;
    public static Adherent affilier;
    private Bundle bundle;


    private static final String KEY_AD_AGENT_COLLECTEUR_ID = "AdAgentCollecteurId";
    private static final String KEY_TYPE_OPERATION = "TypeOperation";

    private static final String KEY_OC_DATE_DEBUT = "OcDateDebut";
    private static final String KEY_OC_DATE_FIN = "OcDateFin";

    private String adherentId;

    private ArrayList<HashMap<String, String>> compteAdherentList;
    private ListView compteAdherentListView;
    private String typeOperation11;

    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private String CgDevise ="A";
    public static String typeOperation = "DR";

    private static EditText fromdate; private static String dateDebut; //dateDebut permet de stocker fromDate au format YYYY-MM-DD
    private static EditText todate; private static String dateFin; //dateFin permet de stocker toDate au format YYYY-MM-DD
    private static String maDate;
    private static CircularProgressButton btnHistorique;
    //    private String maDate = "" ;
    private static String endDate ="";

    /*END*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_compte_adherent_new);
        //Toolbar toolbar = findViewById(R.id.toolbar_list_adherent);
        Toolbar toolbar = findViewById(R.id.toolbar_list_adherent);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle(MyData.GUICHET_NAME) ;

        //initializing views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdherent = new ArrayList<>();
//        listComptesAdherent = new ArrayList<>();

        //loading listAdherent view item with this function
        //loadRecyclerViewItem();
        //new ListCompteAdherentActivity_New.FetchAdherentGuichetAsyncTask().execute();

//        defaultFormat.setCurrency(Currency.getInstance("Fcf"));
        defaultFormat.setCurrency(Currency.getInstance("XAF"));
/*
        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_COMPTE_ID);
//        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        Bundle bundle = intent.getExtras();
//        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
//        adherent = (ComptesAdherent) bundle.getSerializable(KEY_ADHERENT);
        affilier = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        //getSupportActionBar().setTitle("Comptes:"+" "+adherent.getAdNom()+"\n"+adherent.getAdPrenom());
//        getSupportActionBar().setTitle(adherent.getAdNom()+"\n"+adherent.getAdPrenom());
        getSupportActionBar().setTitle("Demande de retrait");
//        getSupportActionBar().setSubtitle(ListCompteAdherentActivity_New.adherent.getAdNom()+" "+ ListCompteAdherentActivity_New.adherent.getAdPrenom());
        getSupportActionBar().setSubtitle(affilier.getAdNom()+" "+ affilier.getAdPrenom());
        */
        getSupportActionBar().setTitle("Demande de retrait");
//        new FetchListDecouvertAdherentAsyncTask().execute();

        fromdate = (EditText) findViewById(R.id.input_txt_fromDate);
        todate = (EditText) findViewById(R.id.input_txt_toDate);
        todate.setVisibility(View.GONE);
        btnHistorique = (CircularProgressButton) findViewById(R.id.btn_afficher);
        lanceur();
        btnHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    loadByIntervalle();
                } else {
                    Toast.makeText(ListDemandeRetraitAffilierActivity.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        Date todayDateInit = Calendar.getInstance().getTime();
        //SimpleDateFormat formatterInint = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterInint = new SimpleDateFormat("dd/MM/yyyy");
        maDate = formatterInint.format(todayDateInit);
//        fromdate.setText(maDate);
//        fromdate.performClick();

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

                    Toast.makeText(ListDemandeRetraitAffilierActivity.this,
                            "Veuillez renseigner la date de début",
                            Toast.LENGTH_LONG).show();
                }else {
                    showToDatePickerDialog(view);


                }

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /* To manage Menu*/
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_agent_collecteur, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all:
                // new ProduitEAVGuichetActivity.FetchProduitEAVAsyncTask().execute();
                // startActivity(new Intent(this, About.class));
                return true;
            case R.id.action_demande_de_retrait:
                typeOperation = "DR";
                getSupportActionBar().setTitle("Demande de retrait") ;
                new ListDemandeRetraitAffilierActivity.FetchListDecouvertAdherentAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_paiement_cheque:
                typeOperation = "PC";
                getSupportActionBar().setTitle("Paiement chèque") ;
                new ListDemandeRetraitAffilierActivity.FetchListDecouvertAdherentAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_transfert_solde_mensuel:
                typeOperation = "TS";
                getSupportActionBar().setTitle("Transfert solde") ;
                new ListDemandeRetraitAffilierActivity.FetchListDecouvertAdherentAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_demande_de_chequier:
                typeOperation = "DC";
                getSupportActionBar().setTitle("Demande chéquier") ;
                new ListDemandeRetraitAffilierActivity.FetchListDecouvertAdherentAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_ouverture_de_journee:
//                action_to_affect = 4;
                Intent jourOuvertCollecteur = new Intent(ListDemandeRetraitAffilierActivity.this, JourOuvertCollecteur.class);
                startActivityForResult(jourOuvertCollecteur,20);
//                getSupportActionBar().setTitle("Demande de chéquier") ;
//                new ListDemandeRetraitAffilierActivity.FetchListDecouvertAdherentAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
/*
// get start of the next month
        cal.add(Calendar.MONTH, 1);
        System.out.println("Start of the next month:  " + cal.getTime());
        System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
//        new HistoriqueEAV.FetchListOperationsOnCompteAdherentAsyncTask().execute(); */
// Create a Calendar instance
// and set it to the date of interest

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
//        maDate = formatterInint.format(firstDayOfTheMonth);
        maDate = formatterInint.format(todayDateInit);
        endDate = formatterInint.format(todayDateInit);
        System.out.println("Start of the month ********maDate:       " + maDate);
        System.out.println("End of the month ********endDate:       " + endDate);
        fromdate.setText(maDate);
        todate.setText(endDate);
//        String maDate = formatterInint.format(todayDateInit);
        new ListDemandeRetraitAffilierActivity.FetchListDecouvertAdherentAsyncTask().execute();

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
    private  void loadByIntervalle(){
 /*       // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

// get start of the month
        cal.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("Start of the month:       " + cal1.getTime());
        System.out.println("... in milliseconds:      " + cal1.getTimeInMillis());

// get start of the next month
        cal.add(Calendar.MONTH, 1);
        System.out.println("Start of the next month:  " + cal.getTime());
        System.out.println("... in milliseconds:      " + cal.getTimeInMillis()); */
        new ListDemandeRetraitAffilierActivity.FetchListDecouvertAdherentAsyncTask().execute();
/*
// Create a Calendar instance
// and set it to the date of interest

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
        String maDate = formatterInint.format(firstDayOfTheMonth);
        String endDate = formatterInint.format(todayDateInit);
        System.out.println("Start of the month ********maDate:       " + maDate);
        System.out.println("End of the month ********endDate:       " + endDate);
        fromdate.setText(maDate);
        todate.setText(endDate);
//        String maDate = formatterInint.format(todayDateInit);
                new HistoriqueEAV.FetchListOperationsOnCompteAdherentAsyncTask().execute();*/
    }
    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new ListDemandeRetraitAffilierActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new ListDemandeRetraitAffilierActivity.ToDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");


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
//            date = new StringBuilder().append(month + 1).append("/")
//                    .append(day).append("/").append(year).append(" ");
//            String loginUrl="...?child_id="+id+"&date="+URLEncoder.encode(date.toString());
//            ParentInputData().execute(loginUrl);
            btnHistorique.performClick();

        }

    }
    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        // Calendar startDateCalendar=Calendar.getInstance();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
//            String getfromdate = fromdate.getText().toString().trim();
//            String getfrom[] = getfromdate.split("/");
//            int year,month,day;
//            year= Integer.parseInt(getfrom[2]);
//            month = Integer.parseInt(getfrom[1])-1;
//            day = Integer.parseInt(getfrom[0]);
//            final Calendar c = Calendar.getInstance();
//            c.set(year,month,day+1);
//            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this, year,month,day);
//            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//            return datePickerDialog;

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
//            lanceur();

        }

    }

    /* To manage Menu*/
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_home_demande_retrait, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search:
//                // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
//
//                return true;
//            case R.id.action_demande_retrait_add:
//                Intent intent = new Intent(ListDemandeRetraitAffilierActivity.this, OperationCollecte.class);
////                intent.putExtra(KEY_TYPE_OPERATION, "decouvert");
////                startActivityForResult(intent,20);
//
//
////                                intent = new Intent(mCtx,
////                                        OperationCollecte.class);
////                intent = new Intent(mCtx,
////                        ListDemandeRetraitAffilierActivity.class);
//                intent.putExtra(KEY_COMPTE_ID, adherentId);
////                                intent.putExtra(KEY_COMPTE_ID, myList.getAdNumero()+"");
//                intent.putExtra(KEY_TYPE_OPERATION, "retrait");
//
////                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");
//
//                intent.putExtra(KEY_MONTANT_COMPTE, affilier.getAdNbreCompte());
////                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
////                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
//                intent.putExtra(KEY_LIBELLE_PRODUIT, "PRODUIT COLLECTE");
////                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
////                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());
////                                intent.putExtra(KEY_TYPE_COMPTE, myList.getType_compte());
//
//                bundle = new Bundle();
//                bundle.putSerializable(KEY_ADHERENT, (Serializable) affilier);
//                // bundle.putSerializable(KEY_ADHERENT, adherent);
//                intent.putExtras(bundle);
//                                startActivityForResult(intent,20);
//
//
//
//
//
////
////            Intent    intent = new Intent(getApplicationContext(),
////                        OperationCompteCourant.class);
////                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");
////
////                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
////                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
////                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
////                intent.putExtra(KEY_TAUX, myList.getTaux_compte());
////                intent.putExtra(KEY_TYPE_COMPTE, myList.getType_compte());
//
////                intent.putExtra(KEY_TYPE_OPERATION, "decouvert");
////                ((Activity) mCtx).startActivityForResult(intent, 20);
//////
////                Bundle bundle = new Bundle();
////                bundle.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
////                // bundle.putSerializable(KEY_ADHERENT, adherent);
////                intent.putExtras(bundle);
//                return true;
//
//
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    /* To manage Menu*/
    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_adherent, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();

                return true;
            case R.id.action_person_add:
                Intent i = new Intent(ListCompteAdherentActivity_New.this, CreateAdherent.class);
               // startActivityForResult(i,20);
                // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, About.class));
                return true;
            case R.id.action_situation_guichet:
                //action_to_affect = false;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_brouillard_de_caisse:
                //action_to_affect = true;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_cloturer_la_journee:
               // action_to_affect = true;
             //   new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_operation_externe:
              //  action_to_affect = true;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
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


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */


    private void loadRecyclerViewItem() {
        //you can fetch the data from server or some apis
        //for this tutorial I am adding some dummy data directly
     /*   for (int i = 1; i <= 5; i++) {
            MyList myList = new MyList(
                    "Dummy Heading " + i,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi molestie nisi dui."
            );
            listAdherent.add(myList);
        }
        */

        //adapter = new CustomAdapterListAdherent(listAdherent, this);
        adapter = new CustomAdapterListDemandeRetraitAffilier(listComptesAdherent, this,typeOperation);
        recyclerView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
       /* recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateCaisse.class);
                    intent.putExtra(KEY_CAISSE_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(CaisseActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        }); */
    }
    /**
     * Fetches the list of accounts adherent from the server
     */
    private class FetchListDecouvertAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ListDemandeRetraitAffilierActivity.this);
            pDialog.setMessage("Chargement des demandes. Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
//            httpParams.put(KEY_ADHERENT_ID, adherentId);
            httpParams.put(KEY_AD_AGENT_COLLECTEUR_ID, String.valueOf(MyData.COLLECTEUR_ID));
            httpParams.put(KEY_OC_DATE_DEBUT, dateDebut);
            httpParams.put(KEY_OC_DATE_FIN, dateDebut);
            httpParams.put(KEY_TYPE_OPERATION, typeOperation );
            Log.e("dateDebut*******",dateDebut);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_demandes_retrait_by_collecteur.php", "GET", httpParams);

//            Log.e("Response: ", "> **********" + adherentId);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    compteAdherentList = new ArrayList<>();
                    listComptesAdherent = new ArrayList<>();
//                    Log.e("Response: ", "> **********test covid19 " + adherentId);
                    //list EAV
//                    movies = jsonObject.getJSONArray(KEY_DATA_EAV);
//                    //Iterate through the response and populate movies list
//                    for (int i = 0; i < movies.length(); i++) {
//                        JSONObject compte = movies.getJSONObject(i);
//                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
//                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
//                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
//                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
//                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
//
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_COMPTE_ID, compteId.toString());
//                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
//                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
//                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
//                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
//                        map.put(KEY_TYPE_COMPTE, "EAV");
//                        compteAdherentList.add(map);
//                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
//                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
//                                "EAV","");
//                        listComptesAdherent.add(mesComptes);
//                    }
//                    //list Compte courant
//                    movies = jsonObject.getJSONArray(KEY_DATA_COMPTE_COURANT);
//                    //Iterate through the response and populate movies list
//                    for (int i = 0; i < movies.length(); i++) {
//                        JSONObject compte = movies.getJSONObject(i);
//                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
//                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
//                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
//                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
//                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
//
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_COMPTE_ID, compteId.toString());
//                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
//                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
//                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
//                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
//                        map.put(KEY_TYPE_COMPTE, "COMPTE COURANT");
//                        compteAdherentList.add(map);
//                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
//                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
//                                "COMPTE COURANT","");
//                        listComptesAdherent.add(mesComptes);
//                    }
//                    Log.e("Response: ", "> **********test covid19 " + adherentId);
//                    //list Decouvert
                    movies = jsonObject.getJSONArray(KEY_DATA_DECOUVERT);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject compte = movies.getJSONObject(i);
                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
                        String compteDetail = compte.getString(KEY_DfStatut);
                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
                        String compteMontantDemande = compte.getString(KEY_MONTANT_DEMANDE_DECOUV);
                        String compteMontantAccorde = compte.getString(KEY_MONTANT_ACCORDE_COMPTE);
                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
                        String compteNomAffilie = compte.getString(KEY_AdNom);
                        if (compteMontantAccorde==null || compteMontantAccorde.equals("null")){
                            compteMontantAccorde = "0";
                        }

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_COMPTE_ID, compteId.toString());
                        map.put(KEY_DfStatut, compteDetail);
                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
                        map.put(KEY_MONTANT_DEMANDE_DECOUV, defaultFormat.format(parseDouble(compteMontantDemande)));
                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
                        map.put(KEY_TYPE_COMPTE, "DECOUVERT SIMPLE");
                        compteAdherentList.add(map);
                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
                                compteDateCreation.substring(0,10),compteMontantDemande,
                                "DECOUVERT SIMPLE","",compteNomAffilie, defaultFormat.format(parseDouble(compteMontantAccorde)));
                        listComptesAdherent.add(mesComptes);
                        Log.e("Response: ", "> **********compteNumDossier" + compteNumDossier);
                    }
                    //list EAT
//                    movies = jsonObject.getJSONArray(KEY_DATA_EAT);
//                    //Iterate through the response and populate movies list
//                    for (int i = 0; i < movies.length(); i++) {
//                        JSONObject compte = movies.getJSONObject(i);
//                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
//                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
//                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
//                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
//                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
//                        String compteTaux = compte.getString(KEY_TAUX);
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_COMPTE_ID, compteId.toString());
//                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
//                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
//                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
//                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
//                        map.put(KEY_TAUX, compteTaux);
//                        map.put(KEY_TYPE_COMPTE, "EAT");
//                        compteAdherentList.add(map);
//                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
//                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
//                                "EAT",compteTaux);
//                        listComptesAdherent.add(mesComptes);
//                    }
//                    //list EAP
//                    movies = jsonObject.getJSONArray(KEY_DATA_EAP);
//                    //Iterate through the response and populate movies list
//                    for (int i = 0; i < movies.length(); i++) {
//                        JSONObject compte = movies.getJSONObject(i);
//                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
//                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
//                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
//                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
//                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
//                        String compteTaux = compte.getString(KEY_TAUX);
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_COMPTE_ID, compteId.toString());
//                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
//                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
//                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
//                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
//                        map.put(KEY_TAUX, compteTaux);
//                        map.put(KEY_TYPE_COMPTE, "EAP");
//                        compteAdherentList.add(map);
//
//                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
//                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
//                                "EAP",compteTaux);
//                        listComptesAdherent.add(mesComptes);
//                    }
//                    //list Demande de crédit
//                    movies = jsonObject.getJSONArray(KEY_DATA_DEMANDE_CREDIT);
//                    //Iterate through the response and populate movies list
//                    for (int i = 0; i < movies.length(); i++) {
//                        JSONObject compte = movies.getJSONObject(i);
//                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
//                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
//                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
//                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
//                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
//                        String compteTaux = compte.getString(KEY_TAUX);
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(KEY_COMPTE_ID, compteId.toString());
//                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
//                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
//                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
//                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
//                        map.put(KEY_TAUX, compteTaux);
//                        map.put(KEY_TYPE_COMPTE, "CREDIT");
//                        compteAdherentList.add(map);
//
//                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
//                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
//                                "CREDIT",compteTaux);
//                        listComptesAdherent.add(mesComptes);
//                    }
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
                    //populateGuichetList();
                    loadRecyclerViewItem();
                }
            });
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
            pDialog = new ProgressDialog(ListDemandeRetraitAffilierActivity.this);
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
            //httpParams.put(KEY_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_adherents.php", "GET", httpParams);
            //creating Arraylist
            //List<String> fruitList = new ArrayList<>();
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    //fraisList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);

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
                      /*  Integer guichetId = guichet.getInt(KEY_FC_NUMERO);
                        String guichetDenomination = guichet.getString(KEY_FC_NEW_LIBELLE);
                        String guichetDenomination = guichet.getString(KEY_FC_NEW_LIBELLE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FC_NUMERO, guichetId.toString());
                        map.put(KEY_FC_NEW_LIBELLE, guichetDenomination);*/
                        //adding String Objects to fruitsList ArrayList
                        // MyData.fruitList.add(guichetDenomination);
                       /* fraisList.add(map);*/
                        listAdherent.add(myList);
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
                   // populateGuichetList();
                    loadRecyclerViewItem();
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
