/*package com.example.binumtontine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import eav.os.Bundle;

import com.example.binumtontine.R;

public class CreateProduitEAV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_param_produit_eav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAV");

    }
}*/
package com.example.binumtontine.activity.adherent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.MouvementEAV;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class CalculFraisTenueCompteAnnuel extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_EV_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";
    /* end*/
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";

    private static final String KEY_CV_PRODUIT = "CvProduit";
    private static final String KEY_CV_MEMBRE = "CvMembre";
    private static final String KEY_CV_GUICHET = "CvGuichet";
    private static final String KEY_CV_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_CV_MT_SOLDE = "CvMtSolde";
    private static final String KEY_CV_NATURE_OPERATION = "NatureOp";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";

    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_TYPE_OPERATION = "TypeOperation";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private Adherent adherent;
    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_CV_NUMERO = "CvNumero";

    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";



    private static String STRING_EMPTY = "";

    private EditText EavDepotMinEditText;
    private EditText NumDossierEditText;

    public static RadioButton rb_depot;
    public static RadioButton rb_retrait;


    /*start*/
//    private ProgressDialog pDialog;
//    private List<Record> listOperationCompteAdherent;
    private List<MouvementEAV> listOperationCompteAdherent;

//    private String compteId;
    private static final String KEY_CODE_COMPTE_EAV = "OcCodeCompteEAV";
    private static final String KEY_OC_DATE_INIT = "OcDateInit";
    private static final String KEY_TYPE_OPERATION_EAV = "OcTypeOperation";
    private static final String KEY_DATE_OPERATION = "OcDateOperation";
    private static final String KEY_OC_MONTANT = "OcMontant";
    private static final String KEY_OC_NEW_SOLDE = "OcNewSolde";
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private double SoldeInit;
    /*end*/


    private String compteId;
    private String eavDepotMin;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String compteSolde;
    private String typeOperation;
    private String adNumDossier;
    private String libelleProduit;

    private String natureOperation;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> eavList;
    List<Integer> eavListID = new ArrayList<Integer>();
    private int eavID;
    private Spinner spinnerListEAV;
    private TextView tvAdherentNom;
    private TextView tvHeaderOperationEAV;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvCompteSolde;
    private TextView tvLibelleProduit;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEavList;
    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_eav_adherent);


        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);

        Toast.makeText(CalculFraisTenueCompteAnnuel.this,
                compteId,
                Toast.LENGTH_LONG).show();
        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        typeOperation = intent.getStringExtra(KEY_TYPE_OPERATION);
        libelleProduit = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        adNom = adherent.getAdNom();
        adPrenom = adherent.getAdPrenom();
        adNumManuel = adherent.getAdNumManuel();
        adCode = adherent.getAdCode();

        //adNumDossier = intent.getStringExtra(KEY_ADHERENT_NUM_DOSSIER);

        EavDepotMinEditText = (EditText) findViewById(R.id.input_txt_depot_min);
        EavDepotMinEditText.addTextChangedListener(MyData.onTextChangedListener(EavDepotMinEditText));

        NumDossierEditText = (EditText) findViewById(R.id.input_txt_numero_bordereau_operation);

        rb_depot = (RadioButton) findViewById(R.id.rb_nature_operation_depot);
        //rb_depot.performClick();
        //onRadioButtonClicked(rb_depot);
        rb_retrait = (RadioButton) findViewById(R.id.rb_nature_operation_retrait);
        tvHeaderOperationEAV = (TextView) findViewById(R.id.header_operation_eav_adherent);
        spinnerListEAV = (Spinner) findViewById(R.id.spn_mode_paiement);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);

        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
        tvCompteSolde = (TextView) findViewById(R.id.tv_solde_compte);
        tvCompteSolde.setText(compteSolde);

        tvLibelleProduit = (TextView) findViewById(R.id.tv_libelle_produit_adherent);
        tvLibelleProduit.setText(libelleProduit);

        if (typeOperation.equals("depot")){
            tvHeaderOperationEAV.setText("TYPE OPERATION: DEPOT");

            rb_depot.setChecked(true);
            rb_retrait.setVisibility(View.GONE);
            rb_depot.setVisibility(View.VISIBLE);
            onRadioButtonClicked(rb_depot);
        }else if(typeOperation.equals("retrait")){
            tvHeaderOperationEAV.setText("TYPE OPERATION: RETRAIT");
            rb_retrait.setChecked(true);

            onRadioButtonClicked(rb_retrait);
            rb_retrait.setVisibility(View.VISIBLE);
            rb_depot.setVisibility(View.GONE);
        }


        listOperationCompteAdherent = new ArrayList<>();
        new CalculFraisTenueCompteAnnuel.FetchListOperationsOnCompteAdherentAsyncTask().execute();

       /* tvAdherentNumDossier = (TextView) findViewById(R.id.tv_num_dossier_adherent);
        tvAdherentNumDossier.setText(adNumDossier);*/
/*
        eavList = new ArrayList<Category>();
        // spinner item select listener
        spinnerListEAV.setOnItemSelectedListener(OperationEAV.this);
        new GetProduitEAVList().execute();
*/
        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CalculFraisTenueCompteAnnuel.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEavAdherent();
                } else {
                    Toast.makeText(CalculFraisTenueCompteAnnuel.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }


    /**
     * To manage Radio button
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_nature_operation_depot:
                if (rb_depot.isChecked()) {
                    natureOperation = "D";
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_nature_operation_retrait:
                if (rb_retrait.isChecked()) {
                    natureOperation = "R";
                    // str = checked1?"Nature frais taux":"";

                }

                break;


        }
        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < eavList.size(); i++) {
            lables.add(eavList.get(i).getName());//recupère les noms
            eavListID.add(eavList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CalculFraisTenueCompteAnnuel.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListEAV.setAdapter(spinnerAdapter);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public double Calc_Frais_Tenue_Cpt_Annuel (int Annee) throws ParseException {
//        int i, j, jour, MaxJours;
//        Date DateJr;
//        String ModeInter = "M"; //ModeInter: Char/1; // Pour indiquer si Min/Max/Moy,
//        String TypValInt = "F";//TypValInt: Char/1; // Pour indiquer si Taux ou Montant fixe
//        double  TotalCred, TotalDeb, NewMtSolde, TxInterCpt=0, MtTotalInter;
////        double SoldeInit;
//
//        double[][] TablMinMaxCpt = new double[366][5];
///*TablMinMaxCpt: Tableau 1..366, 1..5 de Réels
// Colonne 1:Jour, 2:SoldeMin, 3:SoldeMax, 4:SoldeMoy, 5:Val*Taux_Jr
// ou Val represente le choix du solde lu dans le paramétrage du compte
// et sera soit SoldeMin(par défaut), SoldeMoyen, SoldeMax.*/
//
//        //BEGIN
//        // Prendre en compte année Bixestille / IsLeapYear pour Fevrier = 29 jours
//        Year anotherYear = Year.of(Annee);
//        boolean isLeap = anotherYear.isLeap();
//        //Si AnneeBixestille alors MaxJours=366 sinon MaxJours=365
//        if (isLeap) {
//            MaxJours=366;
//        }else{
//            MaxJours=365;
//        }
//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//        String sDate1="01-01-"+Annee;
////        Date date1=dateFormatter.parse(sDate1);
//       // Ad_JourneeAnterieureEditText.setText(dateFormatter.format(newDate.getTime()));
////        DateJour=ConvertirDate(01/01/Annee);
//        DateJr=dateFormatter.parse(sDate1);
//        SoldeInit = selectMontantFrom_31_12_Annee_1(); //Initialiser SoldeInit du compte NumCompte avec MtSolde fin 31/12/(Annee -1)
//
//        jour=1;
//        MtTotalInter=0;	// Montant Total des Intérêts calculés
//
//        //Filtrer Table des MouvemDAV Filtré NumCompte/Membre, sur Annee et la trier par Date Opération
//        //Se positionner sur le premier enregistrement
////        ArrayList<> MouvemDAV = new ArrayList<>();
//  /*Fetch all MouvementDAV
//  Mouvement.add();
//  */
//
//        int compteurMvt =0;
//        while(compteurMvt<listOperationCompteAdherent.size()){ // Tant que l'on n'est pas en fin de table/filtrée
//
//            // debut
//            // Initialisations du jour
//            TablMinMaxCpt[jour][0]=jour;
//            TablMinMaxCpt[jour][1]=SoldeInit; 	// Mt. SoldeMin
//            TablMinMaxCpt[jour][2]=SoldeInit; 	// Mt. SoldeMax
//            TablMinMaxCpt[jour][3]=SoldeInit; 	// Mt. SoldeMoyen
//            NewMtSolde=SoldeInit;
//
//            TotalCred=0; TotalDeb=0;   i=0;
//            while(listOperationCompteAdherent.get(compteurMvt).getMvDateDeValeur().equals(DateJr)){ // Tant que l'on est sur le jours en cours/DateJour
//                //debut
//                if (listOperationCompteAdherent.get(compteurMvt).getMvSensMvm().equals("D")) {
//                    TotalCred=Double.parseDouble(listOperationCompteAdherent.get(compteurMvt).getMvMontant());
//                }else{
//                    TotalDeb= Double.parseDouble(listOperationCompteAdherent.get(compteurMvt).getMvMontant());
//                }
//                NewMtSolde=NewMtSolde+(TotalCred-TotalDeb);
//                i=i+1;
//                if ((NewMtSolde<TablMinMaxCpt[jour][1]) ||  (TablMinMaxCpt[jour][1]==0)) {
//                    TablMinMaxCpt[jour][1]=NewMtSolde;
//                }
//
//                if ((NewMtSolde>TablMinMaxCpt[jour][2]) || (TablMinMaxCpt[jour][2]==0)) {
//                    TablMinMaxCpt[jour][2]=NewMtSolde;
//                }
//
//                TablMinMaxCpt[jour][3]=SoldeInit+((TotalCred-TotalDeb)/i);
//
//                SoldeInit=NewMtSolde;
//
//                /*TxInterCpt, TypValInt(T,F) et ModeInter = {Requêe SQL comme indiqué ci-dessous pour les obtenir} */
//
//
//                //  Faire une Requête avec NumCompte= AdherDAV.AvNumero,
//                //  et prendre AvProduit pour Obtenir EpargneAVue.EvMtTxAgioPrelev à affecter à "TxInterCpt"
//                //  Si EvTypFrAgio(F/T/P) est de Type plage, alors, Faire une Requête sur la table PlageData
//                //  avec PdTypData=TIV et la clé du compte pour obtenir PdValTaux(affecter TxInterCpt) selon la plage,
//                //  ainsi que PdNature(T,F) Taux ou valeur fixe à affecter à TypValInt
//                //  Prendre aussi le champ EpargneAVue.EvModValInter /A1, ** à ajouter, pour le mode de calcul
//                //  du Taux d'intérêt qui sera Minimum/M par défaut ou Maximum/X, ou Moyenne/Y, à renvoyer
//                //  dans la variable "ModeInter".
//
//                // La valeur retournée est soit un Taux soit une valeur pour 1 An, ramené pour 1 jour. TxInterCpt=(TxInterCpt/MaxJours);
//                if (ModeInter.equals("M")) { // Cas de Valeur Minimale
//                    if (TypValInt.equals("T")) {
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                    }
//                }else if (ModeInter.equals("X")) { // Cas de Valeur Maximale
//                    if (TypValInt.equals("T")) { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;
//                    }
//                }else if (ModeInter.equals("Y")) { // Cas de Valeur Moyenne
//                    if (TypValInt.equals("T")) { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                    }
//                }
//
//
//                TablMinMaxCpt[jour][4]=TablMinMaxCpt[jour][4]/MaxJours;
//
//                MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][5];
//
//
//
//
//
//
//            } //fin		// Fin du Tant que l'on est sur la même DateJour
//
//            if (i!=0) { // Avancer si Seulement si jour traité ci-dessus,
//                Calendar c = Calendar.getInstance();
//                try{
//                    //Setting the date to the given date
//                    c.setTime(dateFormatter.parse(DateJr));
//                }catch(ParseException e){
//                    e.printStackTrace();
//                }
//
//                //Number of Days to add
//                c.add(Calendar.DAY_OF_MONTH, 1);
//                //Date after adding the days to the given date
//                String newDate = dateFormatter.format(c.getTime());
//                DateJr=DateJr+1;
//                jour=jour+1;
//                compteurMvt++; // MouvemDAV.EnregSuivant; // Avancer sur le mouvement suivant de ce compte Filtré.
//            }else{
//                while (compteurMvt<listOperationCompteAdherent.size() && (DateJr<listOperationCompteAdherent.get(compteurMvt).getMvDateDeValeur()) ) {
//                    // debut
//                    // Gestion des cas où il n'existe pas de mouvement pour ce jour sur la table "MouvemDAV", et calculer
//                    // les intérêts pour ces jours, pour forcément remplir le tableau pour les jours où il n'y a pas de mouvements
//                    // On verra si il faudra créer un param_tre dans EpargneAVue.IsCalcIntJrNonOuvres /A1 pour activer ces calculs ci-dessous...
//
//                    //  Initialiser SoldeInit du compte EAV NumCompte avec MtSolde au jour du DateJour
//                    //  Pas besoin de re-initialiser SoldeInit, car, la dernière valeur acant de rentrer dans cette boucle serait bonne
//
//
//                    // Initialisations du jour
//                    TablMinMaxCpt[jour][0]=jour;
//                    TablMinMaxCpt[jour][1]=SoldeInit; 	// Mt. SoldeMin
//                    TablMinMaxCpt[jour][2]=SoldeInit; 	// Mt. SoldeMax
//                    TablMinMaxCpt[jour][3]=SoldeInit; 	// Mt. SoldeMoyen
//
//                    if (ModeInter.equals("M")) { // Cas de Valeur Minimale
//                        if (TypValInt.equals("T")) { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                            TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                        }else{
//                            TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                        }
//
//                    }
//
//                    if (ModeInter.equals("X")) { // Cas de Valeur Maximale
//                        if (TypValInt.equals("T")) { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                            TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][2])*TxInterCpt)  ;
//                        }else{
//                            TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                        }
//
//                    }
//
//                    if (ModeInter.equals("Y")) { // Cas de Valeur Moyenne
//                        if (TypValInt.equals("T")) { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                            TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][3])*TxInterCpt)  ;
//                        }else{
//                            TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                        }
//
//                    }
//
//                    MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][4]/MaxJours;
//
//                    MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][4];
//
//                    DateJr=DateJr+1;
//                    jour=jour+1;
//
//
//
//
//                } //fin		// Fin boucle tant qu'on n'est pas sur un jour ayant des mouvements.
//
//            }
//
//
//        } // fin   // Fin de Parcours de la Table des Mouvements sur le compte "MouvemDAV" donc du traitement
//
//        // GERER LE CAS OU IL Y'A DES JOURs NON TRAITES APRES DERNIER ENREG DE "MouvemDAV"
//        if (compteurMvt>=listOperationCompteAdherent.size()) {
//            while (DateJr<=366){
//
//                //  Initialiser SoldeInit du compte EAV NumCompte avec MtSolde au jour du DateJour
//                //  Pas besoin de re-initialiser SoldeInit, car, la dernière valeur acant de rentrer dans cette boucle serait bonne
//
//                // Initialisations du jour
//                TablMinMaxCpt[jour][0]=jour;
//                TablMinMaxCpt[jour][1]=SoldeInit; 	// Mt. SoldeMin
//                TablMinMaxCpt[jour][2]=SoldeInit; 	// Mt. SoldeMax
//                TablMinMaxCpt[jour][3]=SoldeInit; 	// Mt. SoldeMoyen
//
//                if (ModeInter.equals("M")) { // Cas de Valeur Minimale
//                    if (TypValInt.equals("T")) { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                    }
//
//                }
//
//                if (ModeInter.equals("X")) { // Cas de Valeur Maximale
//                    if (TypValInt.equals("T")) { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][2])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                    }
//
//                }
//
//                if (ModeInter.equals("Y")) { // Cas de Valeur Moyenne
//                    if (TypValInt.equals("T")) { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][3])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                    }
//
//                }
//
//                MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][4]/MaxJours;
//
//                MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][4];
//
//                DateJr=DateJr+1;
//                jour=jour+1;
//
//            } // fin		// Fin boucle tant qu'on n'est pas sur un jour ayant des mouvements.
//
//        } //fin si
//
//        return MtTotalInter; //Calc_Frais_Tenue_Cpt_Annuel=MtTotalInter;	// Renvoyer le Résultat du Calcul total des frais à la fonction.
//
        return 0.0;
//        //FIN
    } //FIN  // FIN DE LA FONCTION

    /**
     * Fetches the list of operations or transactions's adherent from the server
     */
    private class FetchListOperationsOnCompteAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(CalculFraisTenueCompteAnnuel.this);
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
            httpParams.put(KEY_OC_DATE_INIT, "01-01-2020");
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_operation_eav_by_compte.php", "GET", httpParams);
            JSONObject jsonObject_solde_init = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_solde_init_operation_eav_by_compte.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
//                int success = 1;
                JSONArray movies;
                if (success == 1) {
                    //list opération on EAV's account
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
//                    for (int i = 0; i < 1; i++) {
                        JSONObject operation = movies.getJSONObject(i);
                        String typeOperation = operation.getString(KEY_TYPE_OPERATION_EAV);
                        String dateOperation = operation.getString(KEY_DATE_OPERATION);
                        String montantOperation = operation.getString(KEY_OC_MONTANT);
                        String nouveauSolde = operation.getString(KEY_OC_NEW_SOLDE);

//                        Record record = new Record("12","jurhdd","bfbfb","cbbbfb");
//                        Record record = new Record(typeOperation,dateOperation,defaultFormat.format(parseDouble(montantOperation)),
//                                defaultFormat.format(parseDouble(nouveauSolde)));
                        MouvementEAV mouvementEAV = new MouvementEAV(typeOperation,dateOperation,defaultFormat.format(parseDouble(montantOperation)),
                                defaultFormat.format(parseDouble(nouveauSolde)));
//                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
//                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
//                                "EAV","");
//                        listOperationCompteAdherent.add(record);
                        listOperationCompteAdherent.add(mouvementEAV);
                    }

                }
            } catch (JSONException e) {
//            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                int success = jsonObject_solde_init.getInt(KEY_SUCCESS);
//                int success = 1;
                JSONArray movies;
                if (success == 1) {
                    //list opération on EAV's account
                    movies = jsonObject_solde_init.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
//                    for (int i = 0; i < 1; i++) {
                        JSONObject operation = movies.getJSONObject(i);

                        SoldeInit = Double.parseDouble(operation.getString(KEY_OC_NEW_SOLDE));

//
                    }

                }
            } catch (JSONException e) {
//            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void run() {
                    //populateGuichetList();
                    loadRecyclerViewItem();
                }
            });
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadRecyclerViewItem() {


//        final ListView recordsView = (ListView) findViewById(R.id.records_view);
////        recordAdapter= new RecordAdapter(this, new ArrayList<Record>());
//        recordAdapter= new RecordAdapter(this, listOperationCompteAdherent);
//        recordsView.setAdapter(recordAdapter);
//        FraisTenueCompteAnnuel fraisTenueCompteAnnuel = new FraisTenueCompteAnnuel(listOperationCompteAdherent);
        try {
            double fraisCalculer = this.Calc_Frais_Tenue_Cpt_Annuel(2020);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    /**
     * Async task to get all food categories
     * */
    private class GetProduitEAVList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEavList = new ProgressDialog(CalculFraisTenueCompteAnnuel.this);
            pDialogFetchProduitEavList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEavList.setCancelable(false);
            pDialogFetchProduitEavList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_EV_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_eav_by_guichet.php", ServiceHandler.GET, httpParams);
           // String json = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET);



            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt(KEY_EAV_ID),
                                    catObj.getString(KEY_EAV_LIBELLE));
                            eavList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialogFetchProduitEavList.isShowing())
                pDialogFetchProduitEavList.dismiss();
            populateSpinner();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
     /*   Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();
        */
        eavID = eavListID.get(position);//pour recuperer l'ID du guichet selectionnée

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    /**
     * Checks whether all files are filled. If so then calls AddEavAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEavAdherent() {
        if (!STRING_EMPTY.equals(EavDepotMinEditText.getText().toString()) &&
            !STRING_EMPTY.equals(NumDossierEditText.getText().toString())
                 ) {
//String rr = compteSolde.replace(" FCFA","").replaceAll(",","\\.");
String rr = compteSolde.replace("FCFA","").trim().replaceAll(",","\\.").replaceAll(" ","");

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
            nf.setGroupingUsed(false);
            //nf.format(rr);
//if (natureOperation.equals("R")&&
//                    (Double.parseDouble(EavDepotMinEditText.getText().toString())<Double.parseDouble(rr))){
if (true){
//                Toast.makeText(OperationEAV.this,
//                        "Solde insuffisant!",
//                        Toast.LENGTH_LONG).show();
//                Toast.makeText(OperationEAV.this,
//                        rr.trim()+ "\n"+rr.length(),
//                        Toast.LENGTH_LONG).show();

   eavDepotMin = EavDepotMinEditText.getText().toString().replaceAll(",", "").trim();
//    eavDepotMin = EavDepotMinEditText.getText().toString();
    adNumDossier = NumDossierEditText.getText().toString();

    new AddEavAdherentAsyncTask().execute();
            }else
            {
//
//                eavDepotMin = EavDepotMinEditText.getText().toString();
//                adNumDossier = NumDossierEditText.getText().toString();
//
//                new AddEavAdherentAsyncTask().execute();
            }




        } else {
            Toast.makeText(CalculFraisTenueCompteAnnuel.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddEavAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CalculFraisTenueCompteAnnuel.this);
            pDialog.setMessage("Transaction en cours. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_EAV_ID, uxGuichetId);

            httpParams.put(KEY_CV_NUMERO, compteId);

            httpParams.put(KEY_CV_NUM_DOSSIER, adNumDossier);
            httpParams.put(KEY_CV_MT_SOLDE, eavDepotMin );
            httpParams.put(KEY_CV_NATURE_OPERATION, natureOperation );
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "operation_eav_adherent_new.php", "POST", httpParams);
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
                        Toast.makeText(CalculFraisTenueCompteAnnuel.this,
                                "Opération réussie !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CalculFraisTenueCompteAnnuel.this,
                                "Echec!\n Vérifiez votre solde ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}