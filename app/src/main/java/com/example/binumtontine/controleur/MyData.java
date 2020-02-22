package com.example.binumtontine.controleur;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.binumtontine.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyData {
    public static int CAISSE_ID =0;
    public static String CAISSE_NAME ="";
    public static String GUICHET_NAME ="";
    public static String JOUR_OUVERT_DATE ="";
    public static String JOUR_OUVERT_IS_CLOSED ="";
    public static int GUICHET_ID =0;
    public static int TYPE_MEMBRE_ID =0;
    public static String TYPE_MEMBRE_NAME ="";
    public static int USER_ID =8;
    public static String USER_NOM ="";
    public static String USER_PRENOM ="";
    public static String USER_EMAIL ="";
    public static String TYPE_DE_FRAIS_PLAGE_DATA ="";
    //public static   String[] animallist;
   public static List<String> fruitList = new ArrayList<>();

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
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
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



}
