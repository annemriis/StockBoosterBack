package com.example.back.ito03022021backend.api;


import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;

public class APIImport {

    private AlphaVantage alphaVantage;



    /**
    This function needs to be called,
     so that the external API will be connected to this object
     The time on api needs to be set by calling specific methods.
     */
    // https://github.com/crazzyghost/alphavantage-java
    public void initialize() {
        Config cfg = Config.builder()
                .key("QL2P2SF7O5SU8LD3")
                .timeOut(10)
                .build();
        this.alphaVantage = AlphaVantage.api();
        alphaVantage.init(cfg);
    }

    public AlphaVantage getAlphaVantage() {
        return alphaVantage;
    }

    /**
     * Set apis calling to daily
     */
    public void setAPIToDaily() {
        this.alphaVantage.timeSeries().daily();
    }

    /**
     * Set apis calling to weekly
     */
    public void setAPIToWeekly() {
        this.alphaVantage.timeSeries().weekly();
    }

    /**
     * Set apis calling to intraday
     */
    public void setAPIToIntraday() {
        this.alphaVantage.timeSeries().intraday();
    }
}
