package com.example.binumtontine.activity.adherent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.CreateGuichet;
import com.example.binumtontine.adapter.RecordAdapter;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

import static com.example.binumtontine.dao.SERVER_ADDRESS.BASE_URL;
import static java.lang.Double.parseDouble;

public class HistoriqueEAV extends AppCompatActivity {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_CODE_COMPTE_EAV = "OcCodeCompteEAV";
    private static final String KEY_TYPE_OPERATION = "OcTypeOperation";
    private static final String KEY_DATE_OPERATION = "OcDateOperation";
    private static final String KEY_OC_MONTANT = "OcMontant";
    private static final String KEY_OC_NEW_SOLDE = "OcNewSolde";
    private static final String KEY_OC_DATE_DEBUT = "OcDateDebut";
    private static final String KEY_OC_DATE_FIN = "OcDateFin";
    private static final String KEY_ADHERENT = "ADHERENT";

    private List<Record> listOperationCompteAdherent;
//    private ListView recordsView;

    private RecordAdapter recordAdapter;
    private ProgressDialog pDialog;
    private String compteId;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    private static EditText fromdate; private static String dateDebut; //dateDebut permet de stocker fromDate au format YYYY-MM-DD
    private static EditText todate; private static String dateFin; //dateFin permet de stocker toDate au format YYYY-MM-DD
    private static String maDate;
    private static CircularProgressButton btnHistorique;
//    private String maDate = "" ;
    private static String endDate ="";
    public static Adherent adherent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_historique_eav);

        Toolbar toolbar = findViewById(R.id.toolbar_list_operation_compte_adherent);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);

        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);

//        Toast.makeText(HistoriqueEAV.this,
//                compteId,
//                Toast.LENGTH_LONG).show();
        /*BEGIN
        To manage format number with currency symbol*/
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) defaultFormat).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("F");
        ((DecimalFormat) defaultFormat).setDecimalFormatSymbols(decimalFormatSymbols);
        /*END
        To manage format number with currency symbol*/


        fromdate = (EditText) findViewById(R.id.input_txt_fromDate);
        todate = (EditText) findViewById(R.id.input_txt_toDate);
        btnHistorique = (CircularProgressButton) findViewById(R.id.btn_afficher);
        lanceur();
        btnHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    loadByIntervalle();
                } else {
                    Toast.makeText(HistoriqueEAV.this,
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

                    Toast.makeText(HistoriqueEAV.this,
                            "Veuillez renseigner la date de début",
                            Toast.LENGTH_LONG).show();
                }else {
                    showToDatePickerDialog(view);


                }

            }
        });

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
        maDate = formatterInint.format(firstDayOfTheMonth);
        endDate = formatterInint.format(todayDateInit);
        System.out.println("Start of the month ********maDate:       " + maDate);
        System.out.println("End of the month ********endDate:       " + endDate);
        fromdate.setText(maDate);
        todate.setText(endDate);
//        String maDate = formatterInint.format(todayDateInit);
                new HistoriqueEAV.FetchListOperationsOnCompteAdherentAsyncTask().execute();

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
        new HistoriqueEAV.FetchListOperationsOnCompteAdherentAsyncTask().execute();

    }
    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new ToDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

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
//            lanceur();
            btnHistorique.performClick();

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    /**
     * Fetches the list of operations or transactions's adherent from the server
     */
    private class FetchListOperationsOnCompteAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(HistoriqueEAV.this);
            pDialog.setMessage("Chargement des opérations sur le compte. Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CODE_COMPTE_EAV, compteId);
            httpParams.put(KEY_OC_DATE_DEBUT, dateDebut);
            httpParams.put(KEY_OC_DATE_FIN, dateFin);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_operation_eav_by_compte.php", "GET", httpParams);
//            Log.e("Response: ", "> **********" + dateDebut);
//            Log.e("Response: ", "> **********" + dateFin);
//            Log.e("Response: ", "> **********" + todate.getText().toString());
            try {
                listOperationCompteAdherent = new ArrayList<>();
                int success = jsonObject.getInt(KEY_SUCCESS);
//                int success = 1;
                JSONArray movies;
                if (success == 1) {
                    //list opération on EAV's account
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    Double total_retait = 0.0, total_depot = 0.0, body_total = 0.0;
                    String title_total = " TOTAL ";
                    for (int i = 0; i < movies.length(); i++) {
//                    for (int i = 0; i < 1; i++) {
                        JSONObject operation = movies.getJSONObject(i);
                        String typeOperation = operation.getString(KEY_TYPE_OPERATION);
                        String dateOperation = operation.getString(KEY_DATE_OPERATION);
                        String montantOperation = operation.getString(KEY_OC_MONTANT);
                        String nouveauSolde = operation.getString(KEY_OC_NEW_SOLDE);

//                        Record record = new Record("12","jurhdd","bfbfb","cbbbfb");
                        Record record = new Record(typeOperation,dateOperation,defaultFormat.format(parseDouble(montantOperation)),
                                defaultFormat.format(parseDouble(nouveauSolde)));
                        if (typeOperation.equals("D") || typeOperation.equals("I")){
                            total_depot += parseDouble(montantOperation);
//                        }else if (typeOperation.equals("R")){
                        }else{
                            total_retait += parseDouble(montantOperation);
                        }
//                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
//                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
//                                "EAV","");
                        listOperationCompteAdherent.add(record);
                    }
                    //To make a empty row between recording and total
                    Record recordEmpty = new Record("","","",
                            "");
                    listOperationCompteAdherent.add(recordEmpty);

                    body_total = total_depot - total_retait;
                    Record recordTotal = new Record(defaultFormat.format(total_depot),title_total,defaultFormat.format(total_retait),
                            defaultFormat.format((body_total)));
                    listOperationCompteAdherent.add(recordTotal);
                    //To make a empty row after recording and total
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
                    loadRecyclerViewItem();
                }
            });
        }
    }
    private void loadRecyclerViewItem() {
        final ListView recordsView = (ListView) findViewById(R.id.records_view);
        recordAdapter= new RecordAdapter(this, listOperationCompteAdherent);
        recordsView.setAdapter(recordAdapter);
    }
}
