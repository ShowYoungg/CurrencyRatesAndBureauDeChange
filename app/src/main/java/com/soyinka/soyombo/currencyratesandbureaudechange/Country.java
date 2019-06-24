package com.soyinka.soyombo.currencyratesandbureaudechange;

/**
 * Created by SHOW on 2/17/2019.
 */

public class Country {
    private String mCountryName, mCurrencyName, mCurrencyCode;
    private int mCountryFlag;

    public Country(String countryName, String currencyName, String currencyCode, int countryFlag ){
        this.mCountryName = countryName;
        this.mCurrencyName = currencyName;
        this.mCurrencyCode = currencyCode;
        this.mCountryFlag = countryFlag;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public void setCountryName(String countryName) {
        this.mCountryName = mCountryName;
    }

    public String getCurrencyName() {
        return mCurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.mCurrencyName = mCurrencyName;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.mCurrencyCode = mCurrencyCode;
    }

    public int getCountryFlag() {
        return mCountryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.mCountryFlag = mCountryFlag;
    }
}
