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
import com.example.binumtontine.fragment.CreateGuichetFragment;
import com.example.binumtontine.fragment.CreateUserCaisseFragment;
import com.example.binumtontine.fragment.ParamProduitEAPFragment;
import com.example.binumtontine.fragment.ParamProduitEAVFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class GuichetActivity extends AppCompatActivity {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_title_param1, R.string.tab_title_param2};
    private final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guichet);

        ViewPager vp_param_guichet= (ViewPager) findViewById(R.id.view_pager_guichet);
        PagerAdapter pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        vp_param_guichet.setAdapter(pagerAdapter);

        TabLayout tbl_guichet= (TabLayout) findViewById(R.id.tabs_guichet);
        tbl_guichet.setupWithViewPager(vp_param_guichet);
        tbl_guichet.setTabMode(TabLayout.MODE_SCROLLABLE); // to make tabs_param_produit_caisse scrollable

       /* SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_user_caisse);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_user_caisse);
        tabs.setupWithViewPager(viewPager);*/

        FloatingActionButton fab = findViewById(R.id.fab_guichet);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Modifier un guichet", Snackbar.LENGTH_LONG)
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
                    return new CreateGuichetFragment();
                case 1:
                    return new CreateGuichetFragment();
                default: break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);

        }
    }


}