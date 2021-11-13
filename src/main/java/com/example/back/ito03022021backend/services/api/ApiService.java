package com.example.back.ito03022021backend.services.api;


import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import org.springframework.stereotype.Service;

@Service
public final class ApiService {

    private AlphaVantage alphaVantage = AlphaVantage.api();


    /**
     This function needs to be called,
     so that the external API will be connected to this object
     The time on api needs to be set by calling specific methods.
     */
        // https://github.com/crazzyghost/alphavantage-java
    public void initialiseApiService(String apiKey) {
        // QL2P2SF7O5SU8LD3
        Config cfg = Config.builder()
                .key(apiKey)
                .timeOut(10)
                .build();
        this.alphaVantage = AlphaVantage.api();
        alphaVantage.init(cfg);
        System.out.println("Alphavantage on");
    }


    public AlphaVantage getAlphaVantage() {
        return alphaVantage;
    }

    /**
     * Set apis calling to intraday
     */
    public void setApiToIntraday() {
        this.alphaVantage.timeSeries().intraday();
    }

}
