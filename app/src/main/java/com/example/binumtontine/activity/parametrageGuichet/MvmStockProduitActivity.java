package com.example.binumtontine.activity.parametrageGuichet;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MvmStockProduitActivity extends AppCompatActivity {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_title_list_mvm_stock_entree, R.string.tab_title_list_mvm_stock_sortie};
    private final Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvm_stock_produit_new);

        Toolbar toolbar = findViewById(R.id.toolbar_mvm_stock_produit);
        setSupportActionBar(toolbar);

        Resources resources=getResources();
        String title = resources.getString(R.string.tab_title_mvm_stock_produit);
        getSupportActionBar().setTitle(title) ;

        //setSupportActionBar(toolbar);

        ViewPager vp_param_guichet= (ViewPager) findViewById(R.id.view_pager_guichet);
        PagerAdapter pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        vp_param_guichet.setAdapter(pagerAdapter);

        TabLayout tbl_mvm_stock_produit= (TabLayout) findViewById(R.id.tabs_mvm_stock_produit);
        tbl_mvm_stock_produit.setupWithViewPager(vp_param_guichet);
        tbl_mvm_stock_produit.setTabMode(TabLayout.MODE_SCROLLABLE); // to make tabs_param_produit_caisse scrollable

       /* SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_user_caisse);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_user_caisse);
        tabs.setupWithViewPager(viewPager);*/

        FloatingActionButton fab = findViewById(R.id.fab_guichet);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un mouvement", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    /* To manage Menu*/
   /* public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_type_membre, menu);
        return true;
    } */

    /* public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all:
                // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, About.class));
                return true;
            case R.id.action_to_affect:
               // action_to_affect = false;
               // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_already_affect:
               // action_to_affect = true;
               // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    } */




    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new PieceToFournirListGxFragment();
                case 1:
                    return new FraisToPayerListGxFragment();
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