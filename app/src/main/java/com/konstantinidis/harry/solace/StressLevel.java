package com.konstantinidis.harry.solace;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StressLevel implements Serializable {

    int initialStressRate;
    int finalStressRate;
    Date rateDate;

    public StressLevel() {

    }

    public StressLevel(int initialStressRate, int finalStressRate, Date rateDate) {
        this.initialStressRate = initialStressRate;
        this.finalStressRate = finalStressRate;
        this.rateDate = rateDate;
    }

    public int getInitialStressRate() {
        return initialStressRate;
    }

    public void setInitialStressRate(int initialStressRate) {
        this.initialStressRate = initialStressRate;
    }

    public int getFinalStressRate() {
        return finalStressRate;
    }

    public void setFinalStressRate(int finalStressRate) {
        this.finalStressRate = finalStressRate;
    }

    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }

    public String getDateString () {
        DateFormat formatter = new SimpleDateFormat("dd/MM");
        String date = formatter.format(rateDate);
        return date;
    }
}
