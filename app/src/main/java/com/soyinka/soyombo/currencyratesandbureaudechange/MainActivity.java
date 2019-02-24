package com.soyinka.soyombo.currencyratesandbureaudechange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soyinka.soyombo.currencyratesandbureaudechange.Utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ImageView baseImage, convertedImage;
    private EditText baseEditText, convertedEditText, screen;
    private TextView conversionRateA, conversionRateB, rate;
    private TextView one, two, three, four, five, six, seven,
            eight, nine, zero, doubleZero, enter, delete, minus, addition, multiplication, division;
    private SharedPreferences sharedPreferences78;
    private SharedPreferences sharedPreferences;
    private String screenValue = null, updatedUSDEUR;
    private int operator, flag, flag2, downloadSwitch = -1;
    private double crossRate, crossRate2;
    private String screenAppendedValue = null, secondAppendedValue = null;
    //private ProgressBar progressBar;
    private Uri uri;
    private List<MyPojo> myPojoList;
    private String json, country, cRate, country2, cRate2, code, code2;
    final String uriString = "https://script.google.com/macros/s/AKfycbx-HRaWIKu1zvHZ83Sn_eufl6Jpbxmjetrb2uMkilEYwOMXK1c/exec?id=1fVIF4dUoZtpAw3vByzrLIa5tFxME5yfcwAJyUNPwtU0&sheet=Sheet1";

    private List<String> stringList;
    private List<String> stringList2;
    private List<Country> countryList;
    private List<String[]> combinedStrings;
    private ArrayList<Country> countryArrayList;
    private ArrayList<Country> countryArrayList1;
    private List<CountryAndRates> countryAndRatesList;
    private CurrencyPairAdapter currencyPairAdapter;


    //ca-app-pub-2081307953269103~1978018585 app id
    //ca-app-pub-2081307953269103/3786758426 unit id banner
    //ca-app-pub-2081307953269103/6388871033 unit id interstitial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences78 = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        combinedStrings = new ArrayList<>();
        countryList = new ArrayList<>();
        countryArrayList = new ArrayList<>();
        countryArrayList1 = new ArrayList<>();
        countryAndRatesList = new ArrayList<>();

        countryArrayList1 = bindObject();

        //progressBar = findViewById(R.id.progress_bar1);
        final ImageView welcome = findViewById(R.id.welcome);
        baseImage = findViewById(R.id.base_currency_flag);
        convertedImage = findViewById(R.id.convert_to_flag);
        baseEditText = findViewById(R.id.base_edittext);
        convertedEditText = findViewById(R.id.convert_edittext);
        conversionRateA = findViewById(R.id.conversion_rateA);
        conversionRateB = findViewById(R.id.conversion_rateB);
        rate = findViewById(R.id.rate);

        ////////////////////CALCULATOR LAYOUT////////////////////////////////////
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        doubleZero = findViewById(R.id.hundreds);
        enter = findViewById(R.id.enter);
        delete = findViewById(R.id.delete);
        screen = findViewById(R.id.screen);
        addition = findViewById(R.id.plus);
        multiplication = findViewById(R.id.multiplication);
        minus = findViewById(R.id.minus);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        doubleZero.setOnClickListener(this);
        enter.setOnClickListener(this);
        delete.setOnClickListener(this);
        addition.setOnClickListener(this);
        multiplication.setOnClickListener(this);
        minus.setOnClickListener(this);
//        flag = R.drawable.usa;
//        flag2 = R.drawable.france;
//        crossRate = 0.882185;
//        crossRate2 = 1.13355;

        Intent i = getIntent();
        if (i != null && i.hasExtra("ParcelableObject")) {
            CountryAndRates countryAndRates = new CountryAndRates();
            countryAndRates = i.getParcelableExtra("ParcelableObject");
            String identifier = i.getStringExtra("Image");

            country = countryAndRates.getCountryName();
            cRate = countryAndRates.getRate();
            flag = countryAndRates.getCountryFlag();
            crossRate = countryAndRates.getCrossRate();
            code = countryAndRates.getCurrencyCode2();


            if (identifier.equals("baseImage")) {
                downloadSwitch = 500;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Country1a", country);
                editor.putString("Country2a", cRate);
                editor.putInt("Country3a", flag);
                editor.putString("Country4a", String.valueOf(crossRate));
                editor.putString("Country5a", code);
                editor.apply();

            } else if (identifier.equals("convertedImage")) {
                downloadSwitch = 500;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Country1b", country);
                editor.putString("Country2b", cRate);
                editor.putInt("Country3b", flag);
                editor.putString("Country4b", String.valueOf(crossRate));
                editor.putString("Country5b", code);
                editor.apply();
            }
        }

        if (downloadSwitch == -1) {
            if (isNetworkAvailable()) {
                downloadJSON();
            } else if (sharedPreferences78 != null) {
                json = sharedPreferences78.getString("CurrencyPairs", "");
                if (!json.equals("")) {
                    parseJson(json);
                } else {
                    Toast.makeText(this, "Enable network to update data", Toast.LENGTH_SHORT).show();
                    parseJson(StringUtils.hy);
                }
            }
        } else if (sharedPreferences78 != null) {
            json = sharedPreferences78.getString("CurrencyPairs", "");
            if (!json.equals("")) {
                parseJson(json);
            } else {
                Toast.makeText(this, "Enable network to update data", Toast.LENGTH_SHORT).show();
                parseJson(StringUtils.hy);
            }
        }

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                welcome.setVisibility(View.GONE);

            }
        }, 4000);

//        parseJson(StringUtils.hy);

        myPojoList = new ArrayList<>();
        baseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, ListOfCurrencyActivity.class)
                        .putExtra("Image", "baseImage")
                        .putParcelableArrayListExtra("ArrayOfCountryAndRates", (ArrayList<? extends Parcelable>) countryAndRatesList));
            }
        });

        convertedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, ListOfCurrencyActivity.class)
                        .putExtra("Image", "convertedImage")
                        .putParcelableArrayListExtra("ArrayOfCountryAndRates", (ArrayList<? extends Parcelable>) countryAndRatesList));
            }
        });

        screen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                baseEditText.setText(String.valueOf(s));
                //screenAppendedValue = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        baseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    String valueString = null;
                    String valueString2 = null;
                    try {
                        valueString = baseEditText.getText().toString().trim();
                        Log.i("VALUESTRING", crossRate + " " + crossRate2);
                        if (crossRate2 == 0.0 && crossRate != 0.0) {
                            convertedEditText.setText(String.valueOf(code2 + " " + Integer.parseInt(valueString) * (crossRate)));
                            baseEditText.setText(String.valueOf(code + " " + valueString));
                        } else if (crossRate == 0.0 && crossRate2 != 0.0) {

                            convertedEditText.setText(String.valueOf(code2 + " " + Integer.parseInt(valueString) * (crossRate2)));
                            baseEditText.setText(String.valueOf(code + " " + valueString));
                        } else if (crossRate == 0.0 && crossRate2 == 0.0) {
                            crossRate = 0.882185;
                            crossRate2 = 1.13355;
                            convertedEditText.setText(String.valueOf(code2 + " " + Integer.parseInt(valueString) * (crossRate2 / crossRate)));
                            baseEditText.setText(String.valueOf(code + " " + valueString));

                        } else {

                            convertedEditText.setText(String.valueOf(code2 + " " + Integer.parseInt(valueString) * (crossRate2 / crossRate)));
                            baseEditText.setText(String.valueOf(code + " " + valueString));
                        }


                    } catch (Exception e) {
                        try {
                            //String valueString = baseEditText.getText().toString().trim();
                            //convertedEditText.setText(code2 + String.valueOf(Double.parseDouble(valueString) * (crossRate2 / crossRate)));
                            //baseEditText.setText(String.valueOf(code + " " + valueString));
                        } catch (Exception ee) {
                            Toast.makeText(MainActivity.this, "Please input number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (sharedPreferences != null) {
            country = sharedPreferences.getString("Country1a", "");
            cRate = sharedPreferences.getString("Country2a", "");
            code = sharedPreferences.getString("Country5a", "");
            flag = sharedPreferences.getInt("Country3a", -123);
            crossRate = Double.parseDouble(sharedPreferences.getString("Country4a", "0"));

            country2 = sharedPreferences.getString("Country1b", "");
            cRate2 = sharedPreferences.getString("Country2b", "");
            code2 = sharedPreferences.getString("Country5b", "");
            flag2 = sharedPreferences.getInt("Country3b", -321);
            crossRate2 = Double.parseDouble(sharedPreferences.getString("Country4b", "0"));


            try {
                String newCode2 = null;
                String newCode = null;

                if (flag != -123) {
                    baseImage.setImageResource(flag);
                    if (!code2.equals("")) {
                        conversionRateA.setText(code + code2 + ":" + String.valueOf((crossRate2 / crossRate)));
                        conversionRateB.setText(code2 + code + ":" + String.valueOf((crossRate / crossRate2)));
                    } else if (code2.equals("")) {
                        newCode2 = "EUR";
                        code2 = newCode2;

                        crossRate = crossRate / Double.parseDouble(updatedUSDEUR);
                        crossRate = 1 / crossRate;
                        conversionRateA.setText(code + newCode2 + ":" + String.valueOf(crossRate));
                        conversionRateB.setText(newCode2 + code + ":" + String.valueOf(1 / crossRate));

                    }
                } else {
                    code = "USD";

                    if (code2.equals("")) {
                        newCode2 = "EUR";
                        code2 = newCode2;
                        //USDEUR\":0.882185
                        updatedUSDEUR = String.valueOf(0.882185);
                        conversionRateA.setText(code + newCode2 + ":" + updatedUSDEUR);
                        conversionRateB.setText(newCode2 + code + ":" + String.valueOf(1 / Double.parseDouble(updatedUSDEUR)));
                    }
                }

                if (flag2 != -321) {
                    convertedImage.setImageResource(flag2);
                    if (!code.equals("")) {
                        Log.i("ATEST", crossRate + " " + crossRate2);

                        if (crossRate == 0.0) {
                            conversionRateB.setText(code2 + code + ":"
                                    + String.valueOf(Double.parseDouble(String.valueOf(1/crossRate2))));
                            conversionRateA.setText(code + code2 + ":"
                                    + String.valueOf(Double.parseDouble(String.valueOf(crossRate2))));
                            //crossRate = 0.882185;
                            //crossRate = 1.13355;
                        } else {
                            conversionRateB.setText(code2 + code + ":"
                                    + String.valueOf(Double.parseDouble(String.valueOf(crossRate / crossRate2))));
                            conversionRateA.setText(code + code2 + ":"
                                    + String.valueOf(Double.parseDouble(String.valueOf(crossRate2 / crossRate))));
                        }
                    } else if (code.equals("")) {

                        newCode = "USD";
                        code = newCode;

                        Log.i("ABTEST", "" + crossRate2);


                        conversionRateB.setText(code2 + newCode + ":"
                                + String.valueOf(Double.parseDouble(String.valueOf(1 / crossRate2))));
                        conversionRateA.setText(newCode + code2 + ":" + String.valueOf(crossRate2));
                    }
                } else {
                    code2 = "EUR";
                    if (code.equals("")) {
                        newCode = "USD";
                        code = newCode;
                        Log.i("ABCTEST", updatedUSDEUR);
                        updatedUSDEUR = String.valueOf(0.882185);
                        conversionRateA.setText(newCode + code2 + ":" + updatedUSDEUR);
                        conversionRateB.setText(code2 + newCode + ":" + String.valueOf(1 / Double.parseDouble(updatedUSDEUR)));
                    }
                }

            } catch (Exception e) {

            }

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.multi_currency) {
            //startActivity(new Intent(this, MultiCurrency.class));
            startActivity(new Intent(this, MultiCurrency.class)
                    .putParcelableArrayListExtra("ArrayOfCountryAndRates",
                            (ArrayList<? extends Parcelable>) countryAndRatesList));
        } else if (id == R.id.trend) {
            startActivity(new Intent(this, TrendsActivity.class));

        } else if (id == R.id.share) {

        } else if (id == R.id.review) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * This method checks if there is network connection
     *
     * @return boolean
     */

    private boolean isNetworkAvailable() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = con.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }


    private void downloadJSON() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(Void... voids) {

                uri = Uri.parse(uriString);
                URL urii = null;
                try {
                    urii = new URL(uri.toString());
                } catch (MalformedURLException exception) {
                    Log.e(TAG, "Error creating URL", exception);
                }

                String jsonResponse = "";
                try {
                    jsonResponse = getResponseFromHttpUrl(urii);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("JSonRESPONSE", jsonResponse);

                return jsonResponse;
            }

            @Override
            protected void onPostExecute(String jsonResponse) {
                super.onPostExecute(jsonResponse);
                SharedPreferences.Editor editor = sharedPreferences78.edit();
                editor.putString("CurrencyPairs", jsonResponse);
                editor.apply();
                Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
                parseJson(jsonResponse);

            }
        }.execute();
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else return null;
        } finally {
            urlConnection.disconnect();
        }
    }

    @Override
    public void onClick(View v) {
        screenValue = screen.getText().toString().trim();
        switch (v.getId()) {
            case R.id.one:
                writeOnSreen("1");
                break;
            case R.id.two:
                writeOnSreen("2");
                break;
            case R.id.three:
                writeOnSreen("3");
                break;
            case R.id.four:
                writeOnSreen("4");
                break;
            case R.id.five:
                writeOnSreen("5");
                break;
            case R.id.six:
                writeOnSreen("6");
                break;
            case R.id.seven:
                writeOnSreen("7");
                break;
            case R.id.eight:
                writeOnSreen("8");
                break;
            case R.id.nine:
                writeOnSreen("9");
                break;
            case R.id.zero:
                writeOnSreen("0");
                break;
            case R.id.hundreds:
                writeOnSreen("00");
                break;

            case R.id.plus:
                if (!screenValue.equals("")) {
                    if (!screenValue.equals("0") && !screenValue.equals("00")) {
                        writeOnSreen(" + ");
                    }
                }
                break;

            case R.id.multiplication:
                if (!screenValue.equals("")) {
                    if (!screenValue.equals("0") && !screenValue.equals("00")) {
                        writeOnSreen(" X ");
                    }
                }
                break;
            case R.id.minus:
                if (!screenValue.equals("")) {
                    if (!screenValue.equals("0") && !screenValue.equals("00")) {
                        writeOnSreen(" - ");
                    }
                }
                break;
            case R.id.delete:
                if (!screenValue.equals("")) {
                    screenValue = screenValue.substring(0, screenValue.length() - 1);
                    screen.setText(screenValue);
                }
                break;
            case R.id.enter:
                if (screenValue.equals("")) {
                    baseEditText.setText("0");

                }

                String[] data = screenValue.split(" ", 3);
                int result = 0;

                if (data.length == 1) {
                    result = Integer.parseInt(data[0]);

                    screen.setText(String.valueOf(result));
                    baseEditText.setText(String.valueOf(result));

                } else {
                    try {
                        if (data[1].equals("+")) {
                            result = Integer.parseInt(data[0]) + Integer.parseInt(data[2]);
                        }

                        if (data[1].equals("-")) {
                            result = Integer.parseInt(data[0]) - Integer.parseInt(data[2]);
                        }

                        if (data[1].equals("X")) {
                            result = Integer.parseInt(data[0]) * Integer.parseInt(data[2]);
                        }

                        screen.setText(String.valueOf(result));
                        baseEditText.setText(String.valueOf(result));
                    } catch (Exception e) {

                    }
                }

                break;
        }
    }

    private void writeOnSreen(String e) {

        if (e.equals("+") || e.equals("-") || e.equals("X")) {

            if (!screenValue.contains(e) && !screenValue.equals("00")) {
                screen.append(e);
            }

        } else {

            if (screenValue.equals("0") || screenValue.equals("00")) {
                screen.setText(e);
            } else if (!screenValue.equals("") || screenValue.equals("")) {
                screen.append(e);
            }

        }
    }


    private void parseJson(String s) {

        String usd = null;
        try {
            JSONObject mainJSONObject = new JSONObject(s);
            JSONArray records = mainJSONObject.getJSONArray("records");

            List<String> list = new ArrayList<>(records.length());

            for (int i = 0; i < records.length(); i++) {
                list.add(records.getString(i));
            }

            for (String keyString : list) {
                Log.i("JSONSTRING", keyString + ":" + usd + "\n");

                JSONObject currency = new JSONObject(keyString);
                String keyss = currency.getString("key");

                JSONObject currencyPair = new JSONObject(keyss);
                JSONArray name = currencyPair.names();

                List<String> keys = new ArrayList<>(name.length());

                for (int i = 0; i < name.length(); i++) {
                    keys.add(name.getString(i));
                }

                stringList = new ArrayList<>();

                for (String k : keys) {
                    usd = currencyPair.getString(k);
                    stringList.add(k + ":" + usd);
                    if (k.equals("USDEUR")) {
                        updatedUSDEUR = usd;
                    }

                    Log.i("CURRENCYPAIRS", k + ":" + usd + "\n");

                    for (int j = 0; j < countryArrayList1.size(); j++) {
                        String currencyCode = countryArrayList1.get(j).getCurrencyCode();
                        Log.i("TOOMUCHTOAST", currencyCode + "\n");
//
                        if (currencyCode != null) {
                            if (k.endsWith(currencyCode)) {
                                CountryAndRates countryAndRates = new CountryAndRates(countryArrayList1.get(j).getCountryName(),
                                        countryArrayList1.get(j).getCurrencyName() + ":" + currencyCode,
                                        countryArrayList1.get(j).getCurrencyCode(), k + ":" + usd,
                                        countryArrayList1.get(j).getCountryFlag(), Double.parseDouble(usd), currencyCode);
                                countryAndRatesList.add(countryAndRates);
                                Log.i("NOTTOOMANY", countryAndRatesList.get(0).getRate() + "\n");
                            }
                        }

                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private ArrayList<Country> bindObject() {
        countryList.add(new Country("United Arab Emirate", "United Arab Emirates Dirham", "AED", R.drawable.ae));
        countryList.add(new Country("Afghanistan", "Afghan Afghani", "AFN", R.drawable.afganistan));
        countryList.add(new Country("Albania", "Albanian Lek", "ALL", R.drawable.albania));
        countryList.add(new Country("Armenian", "Armenian Dram", "AMD", R.drawable.armenia));
        countryList.add(new Country("Netherlands", "Netherlands Antillean Guilder", "ANG", R.drawable.netherlands));
        countryList.add(new Country("Angola", "Angolan Kwanza", "AOA", R.drawable.angola));
        countryList.add(new Country("Argentina", "Argentine Peso", "ARS", R.drawable.argentina));
        countryList.add(new Country("Australia", "Australian Dollar", "AUD", R.drawable.australia));
        countryList.add(new Country("Aruba", "Aruban Florin", "AWG", R.drawable.netherlands));
        countryList.add(new Country("Azerbaijan", "Azerbaijani Manat", "AZN", R.drawable.azerbaijan));
        countryList.add(new Country("Bosnia-Herzegovina", "Bosnia-Herzegovina Convertible Mark", "BAM", R.drawable.bosnia));
        countryList.add(new Country("Barbados", "Barbadian Dollar", "BBD", R.drawable.barbados));
        countryList.add(new Country("Bangladesh", "Bangladeshi Taka", "BDT", R.drawable.bangladesh));
        countryList.add(new Country("Bulgaria", "Bulgarian Lev", "BGN", R.drawable.bg));
        countryList.add(new Country("Bahraini", "Bahraini Dinar", "BHD", R.drawable.bahrain));
        countryList.add(new Country("Burundi", "Burundian Franc", "BIF", R.drawable.bi));
        countryList.add(new Country("Bermuda", "Bermudan Dollar", "BMD", R.drawable.ic_launcher_foreground));
        countryList.add(new Country("Brunei", "Brunei Dollar", "BND", R.drawable.bn));
        countryList.add(new Country("Bolivia", "Bolivian Boliviano", "BOB", R.drawable.bolivia));
        countryList.add(new Country("Brazil", "Brazilian Real", "BOB", R.drawable.brazil));
        countryList.add(new Country("Bahamas", "Bahamian Dollar", "BOB", R.drawable.bahamas));
        countryList.add(new Country("Bitcoin", "Bitcoin", "BTC", R.drawable.usa));
        countryList.add(new Country("Bhutan", "Bhutanese Ngultrum", "BTN", R.drawable.bhutan));
        countryList.add(new Country("Botswana", "Botswanan Pula", "BWP", R.drawable.botswana));
        countryList.add(new Country("Belarus", "New Belarusian Ruble", "BYN", R.drawable.belarus));
        countryList.add(new Country("Belarus", "Belarusian Ruble", "BYR", R.drawable.belarus));
        countryList.add(new Country("Belize", "Belize Dollar", "BZD", R.drawable.belize));
        countryList.add(new Country("Canada", "Canadian Dollar", "CAD", R.drawable.canada));
        countryList.add(new Country("Congo", "Congolese Franc", "CDF", R.drawable.congo_dr));
        countryList.add(new Country("Switzerland", "Swiss Franc", "CAF", R.drawable.ch));
        countryList.add(new Country("Chile", "Chilean Unit of Account (UF)", "CLF", R.drawable.chile));
        countryList.add(new Country("Chile", "Chilean Peso", "CLP", R.drawable.chile));
        countryList.add(new Country("China", "Chinese Yuan", "CNY", R.drawable.china));
        countryList.add(new Country("Colombia", "Colombian Peso", "COP", R.drawable.colombia));
        countryList.add(new Country("Costa Rica", "Costa Rican Col\u00f3n", "CRC", R.drawable.costa_rica));
        countryList.add(new Country("Cuba", "Cuban Convertible Peso", "CUC", R.drawable.cuba));
        countryList.add(new Country("Cuba", "Cuban Peso", "CUP", R.drawable.cuba));
        countryList.add(new Country("Cape Verde", "Cape Verdean Escudo", "CVE", R.drawable.cape_verde));
        countryList.add(new Country("Czech Republic", "Czech Republic Koruna", "CZK", R.drawable.czech));
        countryList.add(new Country("Djibouti", "Djiboutian Franc", "DJF", R.drawable.djibouti));
        countryList.add(new Country("Denmark", "Danish Krone", "DKK", R.drawable.denmark));
        countryList.add(new Country("Dominica Republic", "Dominican Peso", "DOP", R.drawable.domica_republic));
        countryList.add(new Country("Algeria", "Algerian Dinar", "DZD", R.drawable.dz));
        countryList.add(new Country("Egypt", "Egyptian Pound", "EGP", R.drawable.egypt));
        countryList.add(new Country("Eritrea", "Eritrean Nakfa", "ERN", R.drawable.eritea));
        countryList.add(new Country("Ethiopia", "Ethiopian Birr", "ETH", R.drawable.ethiopia));
        countryList.add(new Country("Euro Zone", "Euro", "EUR", R.drawable.france));
        countryList.add(new Country("Fiji", "Fijian Dollar", "FJD", R.drawable.fiji));
        countryList.add(new Country("Falkland Islands", "Falkland Islands Pound", "FKP", R.drawable.ic_launcher_foreground));
        countryList.add(new Country("The Great Britain", "British Pound Sterling", "GBP", R.drawable.great_britain));
        countryList.add(new Country("Georgia", "Georgian Lari", "GEL", R.drawable.georgia));
        countryList.add(new Country("Guernsey", "Guernsey Pound", "GGP", R.drawable.ic_launcher_foreground));
        countryList.add(new Country("Ghana", "Ghanaian Cedi", "GHS", R.drawable.ghana));
        countryList.add(new Country("Gibraltar", "Gibraltar Pound", "GIP", R.drawable.ic_launcher_background));
        countryList.add(new Country("Gambia", "Gambian Dalasi", "GMD", R.drawable.gambia));
        countryList.add(new Country("Guinea", "Guinean Franc", "GNF", R.drawable.guinea));
        countryList.add(new Country("Guatemala", "Guatemalan Quetzal", "GTQ", R.drawable.guatemala));
        countryList.add(new Country("Guyana", "Guyanaese Dollar", "GYD", R.drawable.guyana));
        countryList.add(new Country("Hong Kong", "Hong Kong Dollar", "HKD", R.drawable.ic_launcher_background));
        countryList.add(new Country("Honduras", "Honduran Lempira", "HNL", R.drawable.honduras));
        countryList.add(new Country("Croatia", "Croatian Kuna", "HRK", R.drawable.hr));
        countryList.add(new Country("Haiti", "Haitian Gourde", "HTG", R.drawable.haiti));
        countryList.add(new Country("Hungary", "Hungarian Forint", "HUF", R.drawable.hungary));
        countryList.add(new Country("Indonesian", "Indonesian Rupiah", "IDR", R.drawable.indonesia));
        countryList.add(new Country("Israel", "Israeli New Sheqel", "ILS", R.drawable.isreal));
        countryList.add(new Country("Manx pound", "Manx pound", "IMP", R.drawable.ic_launcher_background));
        countryList.add(new Country("India", "Indian Rupee", "INR", R.drawable.india));
        countryList.add(new Country("Iraq", "Iraqi Dinar", "IQD", R.drawable.iraq));
        countryList.add(new Country("Iran", "Iranian Rial", "IRR", R.drawable.iran));
        countryList.add(new Country("Iceland", "Icelandic Kr\\u00f3na", "ISK", R.drawable.iceland));
        countryList.add(new Country("Jersey", "Jersey Pound", "JEP", R.drawable.ic_launcher_background));
        countryList.add(new Country("Jamaica", "Jamaican Dollar", "JMD", R.drawable.jamaica));
        countryList.add(new Country("Jordan", "Jordanian Dinar", "JOD", R.drawable.jordan));
        countryList.add(new Country("Japanese", "Japanese Yen", "JPY", R.drawable.japan));
        countryList.add(new Country("Kenya", "Kenyan Shilling", "KES", R.drawable.kenya));
        countryList.add(new Country("Kyrgystan", "Kyrgystani Som", "KGS", R.drawable.kyrgyzstan));
        countryList.add(new Country("Cambodia", "Cambodian Riel", "KHR", R.drawable.kh));
        countryList.add(new Country("Comoros", "Comorian Franc", "KMF", R.drawable.km));
        countryList.add(new Country("North Korea", "North Korean Won", "KPW", R.drawable.kp));
        countryList.add(new Country("South Korea", "South Korean Won", "KRW", R.drawable.south_korea));
        countryList.add(new Country("Kuwait", "Kuwaiti Dinar", "KWD", R.drawable.kw));
        countryList.add(new Country("Cayman Islands", "Cayman Islands Dollar", "KYD", R.drawable.ic_launcher_background));
        countryList.add(new Country("Kazakhstan", "Kazakhstani Tenge", "KZT", R.drawable.kz));
        countryList.add(new Country("Laos", "Laotian Kip", "LAK", R.drawable.laos));
        countryList.add(new Country("Lebanese", "Lebanese Pound", "LBP", R.drawable.lebanon));
        countryList.add(new Country("Sri Lanka", "Sri Lankan Rupee", "LKR", R.drawable.sri_lanka));
        countryList.add(new Country("Liberia", "Liberian Dollar", "LRD", R.drawable.liberia));
        countryList.add(new Country("Lesotho", "Lesotho Loti", "LSL", R.drawable.lesotho));
        countryList.add(new Country("Lithuania", "Lithuanian Litas", "LTL", R.drawable.lithuana));
        countryList.add(new Country("Latvia", "Latvian Lats", "LVL", R.drawable.latvia));
        countryList.add(new Country("Libya", "Libyan Dinar", "LYD", R.drawable.libya));
        countryList.add(new Country("Morocco", "Moroccan Dirham", "MAD", R.drawable.morocco));
        countryList.add(new Country("Moldova", "Moldovan Leu", "MDL", R.drawable.moldova));
        countryList.add(new Country("Malagasy", "Malagasy Ariary", "MGA", R.drawable.madagascar));
        countryList.add(new Country("Macedonia", "Macedonian Denar", "MKD", R.drawable.macedonia));
        countryList.add(new Country("Myanma", "Myanma Kyat", "MMK", R.drawable.mm));
        countryList.add(new Country("Mongolia", "Mongolian Tugrik", "MNT", R.drawable.mongolia));
        countryList.add(new Country("Macao", "Macanese Pataca", "MOP", R.drawable.ic_launcher_background));
        countryList.add(new Country("Mauritania", "Mauritanian Ouguiya", "MRO", R.drawable.mauritania));
        countryList.add(new Country("Mauritius", "Mauritian Rupee", "MUR", R.drawable.mauritus));
        countryList.add(new Country("Maldives", "Maldivian Rufiyaa", "MVR", R.drawable.maldives));
        countryList.add(new Country("Malawi", "Malawian Kwacha", "MWK", R.drawable.malawi));
        countryList.add(new Country("Mexico", "Mexican Peso", "MXN", R.drawable.mexico));
        countryList.add(new Country("Malaysia", "Malaysian Ringgit", "MYR", R.drawable.malaysia));
        countryList.add(new Country("Mozambique", "Mozambican Metical", "MZN", R.drawable.mozambique));
        countryList.add(new Country("Namibia", "Namibian Dollar", "NAD", R.drawable.namibia));
        countryList.add(new Country("Nigeria", "Nigerian Naira", "NGN", R.drawable.nigeria));
        countryList.add(new Country("Nicaragua", "Nicaraguan C\\u00f3rdoba", "NIO", R.drawable.nicaragua));
        countryList.add(new Country("Norway", "Norwegian Krone", "NOK", R.drawable.norway));
        countryList.add(new Country("Nepal", "Nepalese Rupee", "NPR", R.drawable.nepal));
        countryList.add(new Country("New Zealand", "New Zealand Dollar", "NZD", R.drawable.new_zealand));
        countryList.add(new Country("Omani", "Omani Rial", "OMR", R.drawable.oman));
        countryList.add(new Country("Panama", "Panamanian Balboa", "PAB", R.drawable.panama));
        countryList.add(new Country("Peru", "Peruvian Nuevo Sol", "PEN", R.drawable.peru));
        countryList.add(new Country("Papua New Guinea", "Papua New Guinean Kina", "PGK", R.drawable.papua_new_guinea));
        countryList.add(new Country("The Philippines", "Philippine Peso", "PHP", R.drawable.philippines));
        countryList.add(new Country("Pakistan", "Pakistani Rupee", "PKR", R.drawable.pakistan));
        countryList.add(new Country("Poland", "Polish Zloty", "PLN", R.drawable.poland));
        countryList.add(new Country("Paraguay", "Paraguayan Guarani", "PYG", R.drawable.paraguay));
        countryList.add(new Country("Qatar", "Qatari Rial", "QAR", R.drawable.qatar));
        countryList.add(new Country("Romania", "Romanian Leu", "RON", R.drawable.romania));
        countryList.add(new Country("Serbia", "Serbian Dinar", "RSD", R.drawable.serbia));
        countryList.add(new Country("Russia", "Russian Ruble", "RUB", R.drawable.russia));
        countryList.add(new Country("Rwanda", "Rwandan Franc", "RWF", R.drawable.rwanda));
        countryList.add(new Country("Saudi Arabia", "Saudi Riyal", "SAR", R.drawable.saudi_arabia));
        countryList.add(new Country("Solomon Islands", "Solomon Islands Dollar", "SBD", R.drawable.solomon_island));
        countryList.add(new Country("Seychelles", "Seychellois Rupee", "SCR", R.drawable.seychelles));
        countryList.add(new Country("Sudan", "Sudanese Pound", "SDG", R.drawable.ic_launcher_background));
        countryList.add(new Country("Sweden", "Swedish Krona", "SEK", R.drawable.sweden));
        countryList.add(new Country("Singapore", "Singapore Dollar", "SGD", R.drawable.singapore));
        countryList.add(new Country("Saint Helena", "Saint Helena Pound", "SHP", R.drawable.ic_launcher_background));
        countryList.add(new Country("Sierra Leone", "Sierra Leonean Leone", "SLL", R.drawable.sierra_leone));
        countryList.add(new Country("Somali", "Somali Shilling", "SOS", R.drawable.somalia));
        countryList.add(new Country("Suriname", "Surinamese Dollar", "SRD", R.drawable.surinam));
        countryList.add(new Country("S\\u00e3o Tom\\u00e9 and Pr\\u00edncipe", "S\\u00e3o Tom\\u00e9 and Pr\\u00edncipe Dobra", "STD", R.drawable.sao_tome));
        countryList.add(new Country("Salvador", "Salvadoran Col\\u00f3n", "SVC", R.drawable.ic_launcher_background));
        countryList.add(new Country("Syria", "Syrian Pound", "SYP", R.drawable.syria));
        countryList.add(new Country("Swaziland", "Swazi Lilangeni", "SZL", R.drawable.swaziland));
        countryList.add(new Country("Thailand", "Thai Baht", "THB", R.drawable.thailand));
        countryList.add(new Country("Tajikistan", "Tajikistani Somoni", "TJS", R.drawable.tajikistan));
        countryList.add(new Country("Turkmenistan", "Turkmenistani Manat", "TMT", R.drawable.turkmenistan));
        countryList.add(new Country("Tunisia", "Tunisian Dinar", "TND", R.drawable.tn));
        countryList.add(new Country("Tonga", "Tongan Pa\\u02bbanga", "TOP", R.drawable.tonga));
        countryList.add(new Country("Turkey", "Turkish Lira", "TRY", R.drawable.turkey));
        countryList.add(new Country("Trinidad and Tobago", "Trinidad and Tobago Dollar", "TTD", R.drawable.trinidad));
        countryList.add(new Country("Taiwan", "New Taiwan Dollar", "TWD", R.drawable.taiwan));
        countryList.add(new Country("Tanzania", "Tanzanian Shilling", "TZS", R.drawable.tanzania));
        countryList.add(new Country("Ukraine", "Ukrainian Hryvnia", "UAH", R.drawable.ukraine));
        countryList.add(new Country("Uganda", "Ugandan Shilling", "UGX", R.drawable.uganda));
        countryList.add(new Country("United States", "United States Dollar", "USD", R.drawable.usa));
        countryList.add(new Country("Uruguay", "Uruguayan Peso", "UYU", R.drawable.uruguay));
        countryList.add(new Country("Uzbekistan", "Uzbekistan Som", "UZS", R.drawable.uzbekistan));
        countryList.add(new Country("Venezuela", "Venezuelan Bol\\u00edvar Fuerte", "VEF", R.drawable.venezuela));
        countryList.add(new Country("Vietnam", "Vietnamese Dong", "VND", R.drawable.vietnam));
        countryList.add(new Country("Vanuatu", "Vanuatu Vatu", "VUV", R.drawable.vu));
        countryList.add(new Country("Samoa", "Samoan Tala", "WST", R.drawable.samoa));
        countryList.add(new Country("CFA Franc BEAC", "CFA Franc BEAC", "XAF", R.drawable.ch));
        countryList.add(new Country("Silver (troy ounce)", "Silver (troy ounce)", "XAG", R.drawable.ic_launcher_background));
        countryList.add(new Country("Gold (troy ounce)", "Gold (troy ounce)", "XAU", R.drawable.ic_launcher_background));
        countryList.add(new Country("East Caribbean", "East Caribbean Dollar", "XCD", R.drawable.ic_launcher_background));
        countryList.add(new Country("Special Drawing Rights", "Special Drawing Rights", "XDR", R.drawable.ic_launcher_background));
        countryList.add(new Country("CFA Franc BCEAO", "CFA Franc BCEAO", "XOF", R.drawable.ch));
        countryList.add(new Country("CFP Franc", "CFP Franc", "XPF", R.drawable.ic_launcher_background));
        countryList.add(new Country("Yemen", "Yemeni Rial", "YER", R.drawable.yemen));
        countryList.add(new Country("South Africa", "South African Rand", "ZAR", R.drawable.south_africa));
        countryList.add(new Country("Zambia", "Zambian Kwacha (pre-2013)", "ZMK", R.drawable.zambia));
        countryList.add(new Country("Zambia", "Zambian Kwacha", "ZMW", R.drawable.zambia));
        countryList.add(new Country("Zimbabwe", "Zimbabwean Dollar", "ZWL", R.drawable.zimbabwe));

        countryArrayList.addAll(countryList);
        if (countryList.get(0).getCurrencyName() != null) {
            Log.i("EXAMPLEOFLIST", countryList.get(0).getCountryName());
        }
        return countryArrayList;
    }

}
