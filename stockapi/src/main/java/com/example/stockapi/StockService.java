package com.example.stockapi;

import com.example.stockapi.Stock;
import com.example.stockapi.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    
    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockBySymbol(String symbol) {
        return stockRepository.findById(symbol);
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Optional<Stock> updateStock(String symbol, Stock stockDetails) {
        Optional<Stock> stock = stockRepository.findById(symbol);
        if (stock.isPresent()) {
            Stock updatedStock = stock.get();
            updatedStock.setCompanyName(stockDetails.getCompanyName());
            updatedStock.setPrice(stockDetails.getPrice());
            updatedStock.setVolume(stockDetails.getVolume());
            updatedStock.setMarketCap(stockDetails.getMarketCap());
            return Optional.of(stockRepository.save(updatedStock));
        }
        return Optional.empty();
    }

    public boolean deleteStock(String symbol) {
        if (stockRepository.existsById(symbol)) {
            stockRepository.deleteById(symbol);
            return true;
        }
        return false;
    }
}

