package com.smsmode.unit.service.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "booking", path = "/bookings")
public interface BookingFeignService {

    @GetMapping("/reserved-units")
    ResponseEntity<List<String>> getReservedUnits(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );
}
