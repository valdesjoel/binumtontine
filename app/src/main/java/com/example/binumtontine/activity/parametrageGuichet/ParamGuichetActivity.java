package com.example.binumtontine.activity.parametrageGuichet;

import android.content.Intent;
import android.os.Bundle;

import com.example.binumtontine.activity.adherent.InitialisationCaisseGuichet;
import com.example.binumtontine.activity.parametreGenerauxCx.ListTypeMembrePFActivity;
import com.example.binumtontine.activity.parametreGenerauxCx.ParametreGenerauxCxActivity;
import com.example.binumtontine.activity.parametreGenerauxOF.ParametreGenerauxOFActivity;
import com.example.binumtontine.activity.parametreGenerauxOF.PieceToFournirActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.binumtontine.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParamGuichetActivity extends AppCompatActivity {

    private  Button save;
    private  Button cancel;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    // This listview is just under above button.
    private ListView userDataListView = null;
    private View popupInputDialogView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_param_guichet);
        Toolbar toolbar = findViewById(R.id.toolbar_param_home_guichet);
        setSupportActionBar(toolbar);
        save = (Button)findViewById(R.id.button_save_user_data);
        cancel = (Button)findViewById(R.id.button_cancel_user_data);
        userNameEditText = (EditText)findViewById(R.id.userName);
        passwordEditText = (EditText)findViewById(R.id.password);
        emailEditText = (EditText)findViewById(R.id.email);



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
                Intent piece_to_fournir_intent = new Intent(getBaseContext(),   ListTypeMembrePFgxActivity.class);
                //Intent piece_to_fournir_intent = new Intent(getBaseContext(),   ParametreGenerauxGxActivity.class);
                startActivity(piece_to_fournir_intent);
                break;
            case R.id.type_membre_guichet_card:
                // do your code
                Intent type_membre_guichet_intent = new Intent(getBaseContext(),   TypeMembreGxActivity.class);
                startActivity(type_membre_guichet_intent);
                break;
            case R.id.init_caisse_guichet_card:
                // do your code
                Intent init_caisse_guichet_intent = new Intent(getBaseContext(),   InitialisationCaisseGuichet.class);
                startActivity(init_caisse_guichet_intent);
/*

                // When click the open input popup dialog button.


                        // Create a AlertDialog Builder.
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ParamGuichetActivity.this);
                        // Set title, icon, can not cancel properties.
                        alertDialogBuilder.setTitle("User Data Collection Dialog.");
                        alertDialogBuilder.setIcon(R.drawable.ic_launcher_background);
                        alertDialogBuilder.setCancelable(false);

                        // Init popup dialog view and it's ui controls.
                            //initPopupViewControls();

                        // Set the inflated layout view object to the AlertDialog builder.
                        alertDialogBuilder.setView(popupInputDialogView);

                        // Create AlertDialog and show.
                        final AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                        // When user click the save user data button in the popup dialog.
                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // Get user data from popup dialog editeext.
                                String userName = userNameEditText.getText().toString();
                                String password = passwordEditText.getText().toString();
                                String email = emailEditText.getText().toString();

                                // Create data for the listview.
                                String[] titleArr = { "User Name", "Password", "Email"};
                                String[] dataArr = {userName, password, email};

                                ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

                                int titleLen = titleArr.length;
                                for(int i =0; i < titleLen; i++) {
                                    Map<String,Object> listItemMap = new HashMap<String,Object>();
                                    listItemMap.put("title", titleArr[i]);
                                    listItemMap.put("data", dataArr[i]);
                                    itemDataList.add(listItemMap);
                                }

                                SimpleAdapter simpleAdapter = new SimpleAdapter(ParamGuichetActivity.this,itemDataList,android.R.layout.simple_list_item_2,
                                        new String[]{"title","data"},new int[]{android.R.id.text1,android.R.id.text2});

                                userDataListView.setAdapter(simpleAdapter);

                                alertDialog.cancel();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.cancel();
                            }
                        });

**/
                break;



            default:
                break;
        }

    }

}
