package com.example.binumtontine.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.binumtontine.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {
    private CircularProgressButton cirLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        setContentView(R.layout.activity_eav);
        /*cirLoginButton = (CircularProgressButton)findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cirLoginButton.startAnimation();
  //TODO_:- replace with background task
            }
        }); */
    }
}



