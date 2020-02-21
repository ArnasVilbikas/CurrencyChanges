package com.CurrencyChanges.currencyInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class currencyInfo {

    private String currencyName;
    private String currencyCode;
    private double currencyProportion;
    private String currencyDate;

    /*System.out.println("Before formatting: " + currencyDate);
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    String formattedDate = currencyDate.format(myFormatObj);
    System.out.println("After formatting: " + formattedDate);*/

    public currencyInfo(String currencyName, String currencyCode, double currencyProportion, String currencyDate){

        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.currencyProportion = currencyProportion;
        this.currencyDate = currencyDate;

    }
    public String getCurrencyName() {
        return currencyName;
    }
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public double getCurrencyProportion() {
        return currencyProportion;
    }
    public void setCurrencyProportion(double currencyProportion) {
        this.currencyProportion = currencyProportion;
    }
    public String getCurrencyDate() {
        return currencyDate;
    }
    public void setCurrencyDate(String currencyDate) {
        this.currencyDate = currencyDate;
    }

    @Override
    public String toString() {
        return "[ currencyName=" + currencyName + ", currencyCode=" + currencyCode + ", currencyProportion=" + currencyProportion + ", currencyDate=" + currencyDate +"]";
    }
}
