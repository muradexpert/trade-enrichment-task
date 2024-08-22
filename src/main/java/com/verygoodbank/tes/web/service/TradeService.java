package com.verygoodbank.tes.web.service;

import com.opencsv.CSVReader;
import com.verygoodbank.tes.web.domain.model.Product;
import com.verygoodbank.tes.web.domain.model.Trade;
import com.verygoodbank.tes.web.domain.model.TradeDTO;
import com.verygoodbank.tes.web.util.CsvUtils;
import com.verygoodbank.tes.web.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TradeService {

    private static final String MISSING_PRODUCT_NAME = "Missing Product Name";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final Map<String, Product> productMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        loadProductData();
    }

    private void loadProductData() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass()
                .getClassLoader().getResourceAsStream("product.csv")))) {
            try (CSVReader csvReader = new CSVReader(reader)) {

                String[] line;

                csvReader.readNext();

                while ((line = csvReader.readNext()) != null) {
                    if (line.length >= 2) {
                        productMap.put(line[0], new Product(line[0], line[1]));
                    } else {
                        log.warn("Ignoring invalid line: {}", (Object) line);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error loading product data", e);
        }
    }

    public List<TradeDTO> enrichTrades(MultipartFile file) {
        try {
            List<Trade> trades = CsvUtils.parseTrades(file);
            return trades.stream()
                    .filter(this::isValidDate)
                    .map(this::enrichTrade)
                    .toList();
        } catch (Exception e) {
            log.error("Error processing trade CSV data", e);
            return List.of();
        }
    }

    private boolean isValidDate(Trade trade) {
        try {
            DateUtils.parseDate(trade.date(), DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            log.error("Invalid date format for trade: {}", trade);
            return false;
        }
    }

    public TradeDTO enrichTrade(Trade trade) {
        Product product = productMap.get(trade.productId());
        String productName = (product != null) ? product.name() : MISSING_PRODUCT_NAME;
        if (product == null) {
            log.warn("Missing product name for product ID: {}", trade.productId());
        }
        return new TradeDTO(
                trade.date(),
                productName,
                trade.currency(),
                trade.price()
        );
    }
}
