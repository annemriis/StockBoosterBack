package com.example.back.ito03022021backend;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.google.gson.Gson;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class StockUnitListBuilder {

    public static List<StockUnit> readFromFile(String filename) throws IOException {
        Gson gson = new Gson();
        StockUnit[] stockUnitsArray = {};
        InputStream is = new ClassPathResource(filename + ".json").getInputStream();
        String contents = new String(FileCopyUtils.copyToByteArray(is), StandardCharsets.UTF_8);
        stockUnitsArray = gson.fromJson(contents, StockUnit[].class);
        return Arrays.asList(stockUnitsArray);
    }
}
