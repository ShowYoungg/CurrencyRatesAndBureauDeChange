package com.soyinka.soyombo.currencyratesandbureaudechange;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class MultiCurrency extends AppCompatActivity implements View.OnClickListener {
    private ImageView image1, image2, image3, image4;
    private EditText editText1, editText2, editText3, editText4;
    private int flag1, flag2, flag3, flag4;
    private double rate1, rate2, rate3, rate4;
    private String code1, code2, code3, code4;
    private SharedPreferences shp;
    private SharedPreferences shp2;
    private List<CountryAndRates> countryAndRatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_currency);

        shp = getDefaultSharedPreferences(this);
        shp2 = getDefaultSharedPreferences(this);

        countryAndRatesList = new ArrayList<>();

        image1 = findViewById(R.id.base_currency_flaga);
        image1.setOnClickListener(this);
        editText1 = findViewById(R.id.base_edittexta);
        image2 = findViewById(R.id.convert_to_flag1a);
        image2.setOnClickListener(this);
        editText2 = findViewById(R.id.convert_edittext1a);
        image3 = findViewById(R.id.convert_to_flag2);
        image3.setOnClickListener(this);
        editText3 = findViewById(R.id.convert_edittext2);
        image4 = findViewById(R.id.convert_to_flag3);
        image4.setOnClickListener(this);
        editText4 = findViewById(R.id.convert_edittext3);

        Intent i = getIntent();
        if (i != null && i.hasExtra("ArrayOfCountryAndRates")) {
            countryAndRatesList = i.getParcelableArrayListExtra("ArrayOfCountryAndRates");
        }

        if (i != null && i.hasExtra("ParcelableObject")) {
            CountryAndRates c = new CountryAndRates();
            c = i.getParcelableExtra("ParcelableObject");
            String identifier = i.getStringExtra("Identifier");

            countryAndRatesList = i.getParcelableArrayListExtra("ArrayOfCountryAndRates");

            //String countryName = c.getCountryName();

            if (shp != null){
                code1 = shp.getString("onea", "");
                code2 = shp.getString("twoa", "");
                code3 = shp.getString("threea", "");
                code4 = shp.getString("foura", "");

                flag1 = shp.getInt("oneb", 0);
                flag2 = shp.getInt("twob", 0);
                flag3 = shp.getInt("threeb", 0);
                flag4 = shp.getInt("fourb", 0);

                rate1 = Double.parseDouble(shp.getString("onec", "0"));
                rate2 = Double.parseDouble(shp.getString("twoc", "0"));
                rate3 = Double.parseDouble(shp.getString("threec", "0"));
                rate4 = Double.parseDouble(shp.getString("fourc", "0"));

            }

            switch (identifier) {
                case "one":
                    flag1 = c.getCountryFlag();
                    code1 = c.getCurrencyCode2();
                    rate1 = c.getCrossRate();

                    if (rate2 == 0.0) {
                        rate2 = 1;
                        code2 = "USA";
                        flag2 = R.drawable.usa;
                    }

                    if (rate3 == 0.0) {
                        rate3 = 1;
                        code3 = "USA";
                        flag3 = R.drawable.usa;
                    }

                    if (rate4 == 0.0) {
                        rate4 = 1;
                        code4 = "USA";
                        flag4 = R.drawable.usa;
                    }


                    bindValues(flag1, code1, rate1, c, image1, "one");
                    break;

                case "two":
                    flag2 = c.getCountryFlag();
                    code2 = c.getCurrencyCode2();
                    rate2 = c.getCrossRate();

//                    code1 = shp.getString("onea", "");
//                    flag1 = shp.getInt("oneb", 0);
//                    rate1 = Double.parseDouble(shp.getString("onec", "0"));



                    if (rate1 == 0.0) {
                        rate1 = 1;
                        code1 = "USA";
                        flag1 = R.drawable.usa;
                    }

                    if (rate3 == 0.0) {
                        rate3 = 1;
                        code3 = "USA";
                        flag3 = R.drawable.usa;
                    }

                    if (rate4 == 0.0) {
                        rate4 = 1;
                        code4 = "USA";
                        flag4 = R.drawable.usa;
                    }

                    bindValues(flag2, code2, rate2, c, image2, "two");
                    break;

                case "three":
                    flag3 = c.getCountryFlag();
                    code3 = c.getCurrencyCode2();
                    rate3 = c.getCrossRate();

                    if (rate2 == 0.0) {
                        rate2 = 1;
                        code2 = "USA";
                        flag2 = R.drawable.usa;
                    }

                    if (rate1 == 0.0) {
                        rate1 = 1;
                        code1 = "USA";
                        flag1 = R.drawable.usa;
                    }

                    if (rate4 == 0.0) {
                        rate4 = 1;
                        code4 = "USA";
                        flag4 = R.drawable.usa;
                    }


                    bindValues(flag3, code3, rate3, c, image3, "three");
                    break;

                case "four":
                    flag4 = c.getCountryFlag();
                    code4 = c.getCurrencyCode2();
                    rate4 = c.getCrossRate();

                    if (rate2 == 0.0) {
                        rate2 = 1;
                        code2 = "USA";
                        flag2 = R.drawable.usa;
                    }

                    if (rate3 == 0.0) {
                        rate3 = 1;
                        code3 = "USA";
                        flag3 = R.drawable.usa;
                    }

                    if (rate1 == 0.0) {
                        rate1 = 1;
                        code1 = "USA";
                        flag1 = R.drawable.usa;
                    }

                    bindValues(flag4, code4, rate4, c, image4, "four");
                    break;
            }
        }

        if (shp != null) {
            code1 = shp.getString("onea", "");
            code2 = shp.getString("twoa", "");
            code3 = shp.getString("threea", "");
            code4 = shp.getString("foura", "");

            flag1 = shp.getInt("oneb", 0);
            flag2 = shp.getInt("twob", 0);
            flag3 = shp.getInt("threeb", 0);
            flag4 = shp.getInt("fourb", 0);

            rate1 = Double.parseDouble(shp.getString("onec", "0"));
            rate2 = Double.parseDouble(shp.getString("twoc", "0"));
            rate3 = Double.parseDouble(shp.getString("threec", "0"));
            rate4 = Double.parseDouble(shp.getString("fourc", "0"));

            if (rate1 == 0.0) {
                rate1 = 1;
                code1 = "USA";
                flag1 = R.drawable.usa;
            }

            if (rate2 == 0.0) {
                rate2 = 1;
                code2 = "USA";
                flag2 = R.drawable.usa;
            }

            if (rate3 == 0.0) {
                rate3 = 1;
                code3 = "USA";
                flag3 = R.drawable.usa;
            }

            if (rate4 == 0.0) {
                rate4 = 1;
                code4 = "USA";
                flag4 = R.drawable.usa;
            }


        } else {

            code1 = "USA";
            code2 = "USA";
            code3 = "USA";
            code4 = "USA";

            flag1 = R.drawable.usa;
            flag2 = R.drawable.usa;
            flag3 = R.drawable.usa;
            flag4 = R.drawable.usa;

            rate1 = 1;
            rate2 = 1;
            rate3 = 1;
            rate4 = 1;
        }


        image1.setImageResource(flag1);
        image2.setImageResource(flag2);
        image3.setImageResource(flag3);
        image4.setImageResource(flag4);

        //This watches for text being entered into the EditText
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String valueStrings = editText1.getText().toString().trim();
                if (!valueStrings.equals("")) {
                    try {
                        editText2.setText(String.valueOf(code2 + " " + Integer.parseInt(valueStrings) * (rate2 / rate1)));
                        editText3.setText(String.valueOf(code3 + " " + Integer.parseInt(valueStrings) * (rate3 / rate1)));
                        editText4.setText(String.valueOf(code4 + " " + Integer.parseInt(valueStrings) * (rate4 / rate1)));
                        //editText1.setText(String.valueOf(code1 + " " + Integer.parseInt(valueStrings)));
                    } catch (Exception e) {
                        Toast.makeText(MultiCurrency.this, "Error calculating cross rates", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * This method takes in the following parameters and persist data into SharedPreferences
     *
     * @param flag:   this is an integer that represents the drawable id of an image; a country flag
     * @param code:   this is the currency symbol of the selected country
     * @param rate:   the rate to USDollars
     * @param c:      the whole object returned
     * @param image:  ImageView into which flag will be passed
     * @param number: it serves as identifier
     */
    private void bindValues(int flag, String code, double rate, CountryAndRates c, ImageView image, String number) {

        String name = c.getCountryName();
        image.setImageResource(flag);
        if (shp != null) {
            SharedPreferences.Editor editor = shp.edit();
            editor.putString(number + "a", code);
            editor.putInt(number + "b", flag);
            editor.putString(number + "c", String.valueOf(rate));
            editor.apply();
        }
    }


    //OnClickListener implementation
    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid) {
            case R.id.base_currency_flaga:
                startIntent("one");
                break;

            case R.id.convert_to_flag1a:
                startIntent("two");
                break;

            case R.id.convert_to_flag2:
                startIntent("three");
                break;

            case R.id.convert_to_flag3:
                startIntent("four");
                break;
        }
    }

    //This method sends an intent to start ListOfCurrencyActitivity so that user can choose between a list of countries
    private void startIntent(String a) {

//        startActivity(new Intent(this, ListOfCurrencyActivity.class).putExtra("Identifier", a));


        startActivity(new Intent(this, ListOfCurrencyActivity.class).putExtra("Identifier", a)
                .putParcelableArrayListExtra("ArrayOfCountryAndRates",
                        (ArrayList<? extends Parcelable>) countryAndRatesList));
    }
}
