package com.altimetrik.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.altimetrik.currencyconversionservice.model.CurrencyConversionBean;

@FeignClient(name = "currency-exchange-service", url = "localhost:8001")
public interface CurrencyExchangeProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean getExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
