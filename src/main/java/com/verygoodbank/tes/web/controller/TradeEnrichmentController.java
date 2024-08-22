package com.verygoodbank.tes.web.controller;


import com.verygoodbank.tes.web.domain.model.TradeDTO;
import com.verygoodbank.tes.web.service.TradeService;
import com.verygoodbank.tes.web.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TradeEnrichmentController {

    private final TradeService tradeService;

    @PostMapping(value = "/enrich")
    public ResponseEntity<String> enrichTradeData(@RequestParam("file") MultipartFile file) {
        List<TradeDTO> enrichedData = tradeService.enrichTrades(file);

        return new ResponseEntity<>(ResponseUtils.getString(enrichedData), HttpStatus.OK);
    }

}


