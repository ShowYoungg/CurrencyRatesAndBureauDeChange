package com.soyinka.soyombo.currencyratesandbureaudechange;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SHOW on 2/18/2019.
 */

public class CountryAndRates implements Parcelable {
    private String countryName;
    private String currencyName;
    private String currencyCode;

    private String rate;
    private double crossRates;
    private int countryFlag;
    private String currencyCode2;

    public CountryAndRates(){}

    public CountryAndRates(String countryName, String currencyName, String currencyCode,
                           String rate, int countryFlag, double crossRates, String currencyCode2 ){

        this.countryName = countryName;
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.rate = rate;
        this.countryFlag = countryFlag;
        this.crossRates = crossRates;
        this.currencyCode2 = currencyCode2;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public double getCrossRate() {
        return crossRates;
    }

    public void setCrossRates(double crossRates) {
        this.crossRates = crossRates;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode2() {
        return currencyCode2;
    }

    public void setCurrencyCode2(String currencyCode2) {
        this.currencyCode2 = currencyCode2;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.countryName);
        dest.writeString(this.currencyName);
        dest.writeString(this.currencyCode);
        dest.writeString(this.rate);
        dest.writeInt(this.countryFlag);
        dest.writeDouble(this.crossRates);
        dest.writeString(this.currencyCode2);
    }

    protected CountryAndRates(Parcel in) {
        this.countryName = in.readString();
        this.currencyName = in.readString();
        this.currencyCode = in.readString();
        this.rate = in.readString();
        this.countryFlag = in.readInt();
        this.crossRates = in.readDouble();
        this.currencyCode2 = in.readString();
    }

    public static final Parcelable.Creator<CountryAndRates> CREATOR = new Parcelable.Creator<CountryAndRates>() {
        @Override
        public CountryAndRates createFromParcel(Parcel source) {
            return new CountryAndRates(source);
        }

        @Override
        public CountryAndRates[] newArray(int size) {
            return new CountryAndRates[size];
        }
    };
}
