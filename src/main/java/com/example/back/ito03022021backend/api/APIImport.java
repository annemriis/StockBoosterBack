package com.example.back.ito03022021backend.api;


import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;

public class APIImport {

    private AlphaVantage alphaVantage;



    /**
    This function needs to be called, so that the external API will be connected to this object
     */
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
}
