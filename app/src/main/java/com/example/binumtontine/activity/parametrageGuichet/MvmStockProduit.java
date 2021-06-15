package com.example.binumtontine.activity.parametrageGuichet;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.binumtontine.R;
import com.example.binumtontine.helper.BottomNavigationBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MvmStockProduit extends AppCompatActivity {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_title_list_mvm_stock_approvisionnement,R.string.tab_title_list_mvm_stock_entree, R.string.tab_title_list_mvm_stock_sortie, R.string.tab_title_list_mvm_stock_regularisation};
    private final Context mContext = this;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvm_stock_produit);
        Toolbar toolbar = findViewById(R.id.toolbar_mvm_stock_produit);
        setSupportActionBar(toolbar);
        //toolbar = getSupportActionBar();
        getSupportActionBar().setTitle("Produits à Approvisionner") ;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        //toolbar.setTitle("Shop");
        loadFragment(new StoreFragment());
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
//                    toolbar.setTitle("Shop");
                    getSupportActionBar().setTitle(mContext.getResources().getString(TAB_TITLES[0])) ;
                    fragment = new StoreFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_gifts:
//                    toolbar.setTitle("My Gifts");
                    getSupportActionBar().setTitle(mContext.getResources().getString(TAB_TITLES[1])) ;
//                    mContext.getResources().getString(TAB_TITLES[0]);
                    fragment = new GiftsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
//                    toolbar.setTitle("Cart");
                    getSupportActionBar().setTitle(mContext.getResources().getString(TAB_TITLES[2])) ;
                    fragment = new CartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
//                    toolbar.setTitle("Profile");
                    getSupportActionBar().setTitle(mContext.getResources().getString(TAB_TITLES[3])) ;
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}