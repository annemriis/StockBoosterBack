package com.example.back.ito03022021backend.repositories;

import com.example.back.ito03022021backend.model.Stock;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

    Stock findStockBySymbol(String symbol);
}
