package com.example.binumtontine.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DigitFormatWatcher implements TextWatcher {

    EditText et_filed;

    String processed = "";


    public DigitFormatWatcher(EditText et_filed) {
        this.et_filed = et_filed;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        String initial = editable.toString();

        if (et_filed == null) return;
        if (initial.isEmpty()) return;
        String cleanString = initial.replace(",", "");

        //NumberFormat formatter = new DecimalFormat("#,###");
       // NumberFormat formatter = new DecimalFormat("#,###");
        DecimalFormat formatter = new DecimalFormat("###,###,###");

      //  double myNumber = new Double(cleanString);

        try{
            //int i = Integer.parseInt(input);
            //double myNumber = new Double(cleanString);
            int myNumber = Integer.parseInt(cleanString);

            processed = formatter.format(myNumber);
        } catch(NumberFormatException ex){ // handle your exception

        }



        //Remove the listener
        et_filed.removeTextChangedListener(this);

        //Assign processed text
        et_filed.setText(processed);

        try {
            et_filed.setSelection(processed.length());
        } catch (Exception e) {
            // TODO: handle exception
        }

        //Give back the listener
        et_filed.addTextChangedListener(this);

    }
}
