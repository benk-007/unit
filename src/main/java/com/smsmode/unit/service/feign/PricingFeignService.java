package com.smsmode.unit.service.feign;


import com.smsmode.unit.resource.pricing.BookingPostResource;
import com.smsmode.unit.resource.pricing.UnitBookingRateGetResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "rate", path = "/calculate")
public interface PricingFeignService {

    @PostMapping
    ResponseEntity<Map<String, UnitBookingRateGetResource>> postCalculate(@RequestBody BookingPostResource bookingPostResource);
}