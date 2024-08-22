package com.verygoodbank.tes.web.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.verygoodbank.tes.web.domain.model.Trade;
import com.verygoodbank.tes.web.exception.InvalidCsvFormatException;
import com.verygoodbank.tes.web.exception.InvalidPriceFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvUtils {

    private CsvUtils() {
    }

    public static List<Trade> parseTrades(MultipartFile file) throws IOException, InvalidCsvFormatException, InvalidPriceFormatException, CsvValidationException {
        String csvData = new String(file.getBytes(), StandardCharsets.UTF_8);
        List<Trade> trades = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new StringReader(csvData))) {
            String[] line;
            boolean isFirstRow = true;
            while ((line = reader.readNext()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                if (line.length == 4) {
                    try {
                        trades.add(new Trade(line[0], line[1], line[2], Double.parseDouble(line[3])));
                    } catch (NumberFormatException e) {
                        throw new InvalidPriceFormatException("Invalid number format for price: " + line[3]);
                    }
                } else {
                    throw new InvalidCsvFormatException("Invalid CSV format, expected 4 columns but got: " + line.length);
                }
            }
        }
        return trades;
    }
}
