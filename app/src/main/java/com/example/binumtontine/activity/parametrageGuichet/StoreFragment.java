package com.example.binumtontine.activity.parametrageGuichet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.example.binumtontine.R;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.modele.Produit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {
    private static final String TAG = StoreFragment.class.getSimpleName();
//    private static final String URL = "https://api.androidhive.info/json/movies_2017.json";
    private static final String URL = "http://binumt.diff-itc.net/binumTontine/fetch_all_produit_gx_for_volley.php"; //fetch_all_produit_gx_for_volley.php
    private static final String URL_GET = "http://binumt.diff-itc.net/binumTontine/fetch_all_produit_gx_for_volley.php?PdGuichet="+ MyData.GUICHET_ID; //fetch_all_produit_gx_for_volley.php

    private RecyclerView recyclerView;
    private List<Produit> movieList;
    private StoreAdapter mAdapter;

    public StoreFragment() {
        // Required empty public constructor
    }

    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        mAdapter = new StoreAdapter(getActivity(), movieList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        fetchStoreItems();
//        fetchStoreItems_new();

        return view;
    }

    private void fetchStoreItems() {
        try{

        JsonArrayRequest request = new JsonArrayRequest(URL_GET,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        List<Produit> items = new Gson().fromJson(response.toString(), new TypeToken<List<Produit>>() {
                        }.getType());

                        movieList.clear();
                        movieList.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplicationSingleton.getInstance().addToRequestQueue(request);
    } catch (Exception e) {
        e.printStackTrace();
    }
//        getInstance().addToRequestQueue(request);
    }
    private void fetchStoreItems_new() {
        Map<String, String> params = new HashMap<>();
        params.put("PdGuichet", "1");
//        params.put("USER_ID", "userId");
// try{
            MyJsonArrayRequest request = new MyJsonArrayRequest(URL, params,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response == null) {
                                Toast.makeText(getActivity(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                                return;
                            }

                            List<Produit> items = new Gson().fromJson(response.toString(), new TypeToken<List<Produit>>() {
                            }.getType());

                            movieList.clear();
                            movieList.addAll(items);

                            // refreshing recycler view
                            mAdapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error in getting json
                    Log.e(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            MyApplicationSingleton.getInstance().addToRequestQueue(request);
       /* } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    public class MyJsonArrayRequest extends JsonArrayRequest {

        private Map<String, String> mPostParams;


        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return mPostParams;
        }

        public MyJsonArrayRequest(String url, Map<String, String> postParams, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);

            this.mPostParams = postParams;
        }
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
        private Context context;
        private List<Produit> movieList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView PdNumero, name, price, PdStockMin, PdPrixUnit;
            public ImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);
                PdNumero = view.findViewById(R.id.PdNumero);
                name = view.findViewById(R.id.title);
                price = view.findViewById(R.id.price);
                PdStockMin = view.findViewById(R.id.PdStockMin);
                PdPrixUnit = view.findViewById(R.id.PdPrixUnit);
                thumbnail = view.findViewById(R.id.thumbnail);
            }
        }


        public StoreAdapter(Context context, List<Produit> movieList) {
            this.context = context;
            this.movieList = movieList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.store_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Produit movie = movieList.get(position);
            holder.name.setText(movie.getPdNom());
            holder.PdNumero.setText(movie.getPdNumero());
            holder.price.setText(holder.price.getText()+ movie.getPdQteStock());
            holder.PdStockMin.setText(holder.PdStockMin.getText()+ movie.getPdStockMin());
            holder.PdPrixUnit.setText(holder.PdPrixUnit.getText()+ movie.getPdPrixUnit());

            Glide.with(context)
                    .load(movie.getImage())
                    .into(holder.thumbnail);
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }
    }
}