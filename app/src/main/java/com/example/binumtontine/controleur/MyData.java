package com.example.binumtontine.controleur;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.binumtontine.R;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class MyData {
//    public static int CAISSE_ID =0; //BON
    public static int CAISSE_ID =0; //provisoire
    public static String CAISSE_NAME ="";
    public static int COLLECTEUR_ID =0; //provisoire
    public static String COLLECTEUR_NAME ="";
    public static String GUICHET_NAME ="";
    public static String JOUR_OUVERT_DATE ="";
    public static String JOUR_OUVERT_IS_CLOSED ="";
    public static int GUICHET_ID =0;
    public static int TYPE_MEMBRE_ID =0;
    public static String TYPE_MEMBRE_NAME ="";
    public static int USER_ID =0;
    public static String USER_NOM ="";
    public static String USER_PRENOM ="";
    public static String USER_EMAIL ="";
    public static String USER_PROFIL ="";
    public static String TYPE_DE_FRAIS_PLAGE_DATA ="";
    public static String LIBELLE_PRODUIT_CPTE_COURANT ="";
    //public static   String[] animallist;
   public static List<String> fruitList = new ArrayList<>();
    public static NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    public static String normalizeSymbolsAndAccents(String str){
//        str = org.apache.commons.lang.StringUtils.defaultString(str);
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static void avertissement(Context context) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Avertissement !")
                .setMessage("Vous êtes en mode consultation unique !")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    /**
     * To format number in edittext after real time editing and
     * should provide a format for the number input type.
     * @param et_filed
     * @return
     */
    public static TextWatcher onTextChangedListener(final EditText et_filed) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et_filed.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
//                    if (originalString.contains(" ")) {
//                        originalString = originalString.replaceAll(" ", "");
//                    }
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRANCE);
                    formatter.applyPattern("#,###,###,###");
//                    formatter.applyPattern("# ### ### ###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    et_filed.setText(formattedString);
                    et_filed.setSelection(et_filed.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                et_filed.addTextChangedListener(this);
            }
        };
    }


    /**
     * To make a beep error if a mandadory field left empty
     */
    public static void bipError(){

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
    }


    public static void alreadyUpperCase(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    editText.setText(s);
                    editText.setSelection(editText.length()); //fix reverse texting
                }
            }
        });
    }



}
