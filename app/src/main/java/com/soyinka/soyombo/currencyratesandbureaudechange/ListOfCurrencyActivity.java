package com.soyinka.soyombo.currencyratesandbureaudechange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class ListOfCurrencyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<String> stringList;
    private List<String> stringList2;
    private List<Country> countryList;
    private List<String[]> combinedStrings;
    private ArrayList<Country> countryArrayList;
    private ArrayList<Country> countryArrayList1;
    private List<CountryAndRates> countryAndRatesList;
    private CurrencyPairAdapter currencyPairAdapter;
    private SharedPreferences sharedPreferences78;
    private String identifier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_currency);

        countryAndRatesList = new ArrayList<>();
        recyclerView = findViewById(R.id.currency_recycler_view);
        sharedPreferences78 = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("Identifier")) {
                identifier = intent.getStringExtra("Identifier");
                countryAndRatesList = intent.getParcelableArrayListExtra("ArrayOfCountryAndRates");
            } else {
                countryAndRatesList = intent.getParcelableArrayListExtra("ArrayOfCountryAndRates");
                identifier = intent.getStringExtra("Image");
            }
        }

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        currencyPairAdapter = new CurrencyPairAdapter(ListOfCurrencyActivity.this, countryAndRatesList, identifier);
        recyclerView.setAdapter(currencyPairAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search by country, currency or symbol");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currencyPairAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}