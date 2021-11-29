package com.example.back.ito03022021backend;

import com.example.back.ito03022021backend.initialise.InitialiseStockDatabase;
import com.example.back.ito03022021backend.repositories.StockRepository;
import com.example.back.ito03022021backend.services.api.ApiService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@EntityScan
@SpringBootApplication()
public class Ito03022021BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ito03022021BackEndApplication.class, args);
    }

    @Bean
    public ApplicationRunner init(StockRepository repository, Environment environment) {
        ApiService apiService = new ApiService();
        apiService.initialiseApiService(environment.getProperty("api.key"));
        InitialiseStockDatabase initialiseStockDatabase = new InitialiseStockDatabase(apiService);

        try {
            InputStream is = new ClassPathResource("/symbols/symbols_list.txt").getInputStream();
            String contents = new String(FileCopyUtils.copyToByteArray(is), StandardCharsets.UTF_8);
            initialiseStockDatabase.initialiseStockDatabase(repository, contents);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.getLogger(getClass().getName()).info("Database has been initialised.");
        return args -> repository
                .findAll()
                .forEach(stock -> Logger.getLogger(getClass().getName()).info(stock.getSymbol()));
    }
}
