package com.smsmode.unit.service.feign;


import com.smsmode.unit.resource.inventory.PriceCalculationPostResource;
import com.smsmode.unit.resource.inventory.UnitPricingGetResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "rate", path = "/price-calculations")
public interface PricingFeignService {

    @PostMapping
    ResponseEntity<List<UnitPricingGetResource>> calculatePricing(@RequestBody PriceCalculationPostResource request);
}