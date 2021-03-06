package com.altimetrik.currencyconversionservice.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.currencyconversionservice.CurrencyExchangeProxy;
import com.altimetrik.currencyconversionservice.model.CurrencyConversionBean;

@RestController
public class CurrencyConversionController {
    
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
            @PathVariable BigDecimal quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversionBean> entity = new RestTemplate().getForEntity(
                "http://localhost:8001/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
                uriVariables);
        CurrencyConversionBean response = entity.getBody();
        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());

    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
            @PathVariable BigDecimal quantity) {
        CurrencyConversionBean response = currencyExchangeProxy.getExchangeValue(from, to);
        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());

    }

}
