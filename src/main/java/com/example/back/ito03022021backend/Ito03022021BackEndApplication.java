package com.example.back.ito03022021backend;

import com.example.back.ito03022021backend.initialise.InitialiseStockDatabase;
import com.example.back.ito03022021backend.model.StockRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@EntityScan
@SpringBootApplication
public class Ito03022021BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ito03022021BackEndApplication.class, args);
    }

    // Meetod on hetkel vales kohas ja tagastab midagi.
    // Hetkel on välja kommenteeritud, sest muidu back-endi käivitamine võtab liig kaua aega (sest 500 sümbolit on vaja andmebaasi lisada).
    // Kui keegi tahab katsetad (näha, mida kood teeb), siis võib kommentaarid ära võtta.
    /**
    @Bean
    public ApplicationRunner init(StockRepository repository) {
        InitialiseStockDatabase initialiseStockDatabase = new InitialiseStockDatabase();

        try {
            InputStream is = new ClassPathResource("/symbols/symbols_list.txt").getInputStream();
            String contents = new String(FileCopyUtils.copyToByteArray(is), StandardCharsets.UTF_8);
            initialiseStockDatabase.initialiseStockDatabase(repository, contents);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return args -> repository
                .findAll()
                .forEach(stock -> Logger.getLogger(getClass().getName()).info(stock.getSymbol()));
    }
    **/
}
