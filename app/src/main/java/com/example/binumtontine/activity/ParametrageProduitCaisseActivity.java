package com.example.binumtontine.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.binumtontine.R;
import com.example.binumtontine.fragment.CreateUserCaisseFragment;
import com.example.binumtontine.fragment.ListUserCaisseFragment;
import com.example.binumtontine.fragment.ParamProduitEAPFragment;
import com.example.binumtontine.fragment.ParamProduitEAVFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class ParametrageProduitCaisseActivity extends AppCompatActivity {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_title_param_produit_eav_caisse, R.string.tab_title_param_produit_eap_caisse,R.string.tab_title_param_produit_eat_caisse,R.string.tab_title_param_produit_cc_caisse,R.string.tab_title_param_produit_transfert_caisse,R.string.tab_title_param_produit_credit_caisse};
    private final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrage_produit_caisse);

        ViewPager vp_param_produit_caisse= (ViewPager) findViewById(R.id.view_pager_param_produit_caisse);
        PagerAdapter pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        vp_param_produit_caisse.setAdapter(pagerAdapter);

        TabLayout tbl_param_produit_caisse= (TabLayout) findViewById(R.id.tabs_param_produit_caisse);
        tbl_param_produit_caisse.setupWithViewPager(vp_param_produit_caisse);
        tbl_param_produit_caisse.setTabMode(TabLayout.MODE_SCROLLABLE); // to make tabs_param_produit_caisse scrollable

       /* SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_user_caisse);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_user_caisse);
        tabs.setupWithViewPager(viewPager);*/

        FloatingActionButton fab = findViewById(R.id.fab_param_produit_caisse);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Modifier un produit", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }




    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ParamProduitEAVFragment();
                case 1:
                    return new ParamProduitEAPFragment();
                case 2:
                    return new CreateUserCaisseFragment();
                case 3:
                    return new CreateUserCaisseFragment();
                case 4:
                    return new CreateUserCaisseFragment();
                case 5:
                    return new CreateUserCaisseFragment();
                default: break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);

        }
    }


}