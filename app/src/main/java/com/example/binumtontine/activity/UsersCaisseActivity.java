package com.example.binumtontine.activity;

import android.content.Context;
import android.os.Bundle;

import com.example.binumtontine.R;
import com.example.binumtontine.fragment.CreateUserCaisseFragment;
import com.example.binumtontine.fragment.ListUserCaisseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.binumtontine.activity.ui.main.SectionsPagerAdapter;

public class UsersCaisseActivity extends AppCompatActivity {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_title_create_user_caisse, R.string.tab_title_list_user_caisse};
    private final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_caisse);

        ViewPager vp_pages_user_caisse= (ViewPager) findViewById(R.id.view_pager_user_caisse);
        PagerAdapter pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        vp_pages_user_caisse.setAdapter(pagerAdapter);

        TabLayout tbl_pages= (TabLayout) findViewById(R.id.tabs_user_caisse);
        tbl_pages.setupWithViewPager(vp_pages_user_caisse);

       /* SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_user_caisse);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_user_caisse);
        tabs.setupWithViewPager(viewPager);*/

        FloatingActionButton fab = findViewById(R.id.fab_user_caisse);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Modifier un utilisateur", Snackbar.LENGTH_LONG)
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
                    return new CreateUserCaisseFragment();
                case 1:
                    return new ListUserCaisseFragment();
               // case 2:
                   // return new YourFragment3();
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
           /* switch (position){
                //
                //Your tab titles
                //
                case 0:return "Profile";
                case 1:return "Search";
                case 2: return "Contacts";
                default:return null;
            }*/
        }
    }


}