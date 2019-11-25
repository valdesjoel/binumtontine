package com.example.binumtontine.activity.parametrageGuichet;

import android.content.Intent;
import android.os.Bundle;

import com.example.binumtontine.activity.parametreGenerauxOF.ParametreGenerauxOFActivity;
import com.example.binumtontine.activity.parametreGenerauxOF.PieceToFournirActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.binumtontine.R;

public class ParamGuichetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_param_guichet);
        Toolbar toolbar = findViewById(R.id.toolbar_param_home_guichet);
        setSupportActionBar(toolbar);


    }
    public void onButtonClick(View v){
        switch (v.getId()) {

            case R.id.produit_guichet_card:
                // do your code
                Intent myIntent = new Intent(getBaseContext(),   ProduitGuichetFragment.class);
                startActivity(myIntent);
                break;

            case R.id.piece_frais_card:
                // do your code
                Intent piece_to_fournir_intent = new Intent(getBaseContext(),   ParametreGenerauxOFActivity.class);
                startActivity(piece_to_fournir_intent);
                break;



            default:
                break;
        }

    }

}
