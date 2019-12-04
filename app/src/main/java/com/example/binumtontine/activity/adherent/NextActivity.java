package com.example.binumtontine.activity.adherent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.binumtontine.R;
import com.example.binumtontine.adapter.CustomAdapterListViewCheckbox;

public class NextActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        tv = (TextView) findViewById(R.id.tv);

        for (int i = 0; i < CustomAdapterListViewCheckbox.modelArrayList.size(); i++){
            if(CustomAdapterListViewCheckbox.modelArrayList.get(i).getSelected()) {
                tv.setText(tv.getText() + " " + CustomAdapterListViewCheckbox.modelArrayList.get(i).getAnimal());
            }
        }
    }
}
