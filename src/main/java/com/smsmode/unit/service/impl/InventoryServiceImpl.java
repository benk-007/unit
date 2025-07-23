package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.RoomDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;

import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.mapper.BedMapper;
import com.smsmode.unit.model.RoomModel;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.inventory.InventoryGetResource;
import com.smsmode.unit.resource.inventory.InventoryPostResource;
import com.smsmode.unit.resource.inventory.PriceCalculationPostResource;
import com.smsmode.unit.resource.inventory.UnitPricingGetResource;
import com.smsmode.unit.service.InventoryService;
import com.smsmode.unit.service.feign.BookingFeignService;
import com.smsmode.unit.service.feign.PricingFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final BookingFeignService bookingFeignService;
    private final UnitDaoService unitDaoService;
    private final PricingFeignService pricingFeignService;
    private final RoomDaoService roomDaoService;
    private final BedMapper bedMapper;



    @Override
    public ResponseEntity<Page<InventoryGetResource>> getInventory(InventoryPostResource request, Pageable pageable) {
        log.info("Fetching reserved units from booking service...");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate checkin = LocalDate.parse(request.getCheckinDate(), formatter);
        LocalDate checkout = LocalDate.parse(request.getCheckoutDate(), formatter);

        // Step 1: Call booking service
        List<String> reservedUnitIds;
        try {
            ResponseEntity<List<String>> response = bookingFeignService.getReservedUnits(checkin, checkout);
            reservedUnitIds = response.getBody();
            if (reservedUnitIds == null) reservedUnitIds = Collections.emptyList();
            log.info("Reserved unit IDs: {}", reservedUnitIds);
        } catch (Exception e) {
            log.error("Failed to fetch reserved units", e);
            throw new RuntimeException("Booking service unavailable", e);
        }

        Map<String, Long> reservationCount = reservedUnitIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        // Step 2: Get top-level units only (no parent)
        List<UnitModel> topLevelUnits = unitDaoService.findAllBy(
                (root, query, cb) -> cb.isNull(root.get("parent")),
                Pageable.unpaged()
        ).getContent();

        List<UnitModel> availableUnits = new ArrayList<>();

        for (UnitModel unit : topLevelUnits) {
            if (unit.getNature() == UnitNatureEnum.SINGLE) {
                if (!reservedUnitIds.contains(unit.getId())) {
                    availableUnits.add(unit);
                }
            } else if (unit.getNature() == UnitNatureEnum.MULTI_UNIT) {
                List<UnitModel> subUnits = unitDaoService.findByParentUnit(unit);
                long reserved = reservationCount.getOrDefault(unit.getId(), 0L);
                if (subUnits.size() > reserved) {
                    availableUnits.add(unit);
                }
            }
        }

        log.info("Available units: {}", availableUnits.stream().map(UnitModel::getId).toList());

        // Step 3: Call pricing service
        List<String> unitIdsToPrice = availableUnits.stream().map(UnitModel::getId).toList();
        PriceCalculationPostResource pricingRequest = buildPricingRequest(request, unitIdsToPrice);

        ResponseEntity<List<UnitPricingGetResource>> pricingResponse = pricingFeignService.calculatePricing(pricingRequest);
        List<UnitPricingGetResource> pricedUnits = pricingResponse.getBody();
        if (pricedUnits == null) pricedUnits = Collections.emptyList();

        // Step 4: Index pricing results
        Map<String, UnitPricingGetResource> pricingMap = pricedUnits.stream()
                .collect(Collectors.toMap(UnitPricingGetResource::getId, p -> p));

        // Step 5: Map to InventoryGetResource
        List<InventoryGetResource> finalResults = availableUnits.stream()
                .map(unit -> {
                    UnitPricingGetResource pricing = pricingMap.get(unit.getId());
                    if (pricing == null) return null;

                    InventoryGetResource resource = new InventoryGetResource();
                    resource.setId(unit.getId());
                    resource.setName(unit.getName());

                    InventoryGetResource.Inventory inventory = new InventoryGetResource.Inventory();
                    if (unit.getNature() == UnitNatureEnum.SINGLE) {
                        // SINGLE units
                        inventory.setTotalCount(1);
                        inventory.setAvailableCount(1);
                    } else {
                        // MULTI_UNIT
                        List<UnitModel> subUnits = unitDaoService.findByParentUnit(unit);
                        int total = subUnits.size();
                        long reserved = reservationCount.getOrDefault(unit.getId(), 0L);
                        int available = Math.max(0, total - (int) reserved);

                        inventory.setTotalCount(total);
                        inventory.setAvailableCount(available);
                    }
                    resource.setInventory(inventory);

                    InventoryGetResource.Price price = new InventoryGetResource.Price();
                    price.setNightRates(pricing.getNightRates());
                    price.setNightlyRate(pricing.getNightlyRate());
                    price.setTotalAmount(pricing.getTotalAmount());
                    price.setMinStay(pricing.getMinStay());
                    price.setMaxStay(pricing.getMaxStay());
                    resource.setPrice(price);


                    List<RoomModel> rooms = roomDaoService.findByUnit(unit.getId());
                    List<BedEmbeddable> beds = rooms.stream()
                            .flatMap(room -> room.getBeds().stream())
                            .toList();
                    resource.setBedding(bedMapper.toResourceList(beds));


                    resource.setOccupancy(0);

                    return resource;
                })
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(new PageImpl<>(finalResults, pageable, finalResults.size()));

    }

    private PriceCalculationPostResource buildPricingRequest(InventoryPostResource originalRequest, List<String> unitIds) {
        PriceCalculationPostResource pricingRequest = new PriceCalculationPostResource();
        pricingRequest.setCheckinDate(originalRequest.getCheckinDate());
        pricingRequest.setCheckoutDate(originalRequest.getCheckoutDate());
        pricingRequest.setSegmentId(originalRequest.getSegmentId());
        pricingRequest.setSubSegmentId(originalRequest.getSubSegmentId());
        pricingRequest.setGuests(originalRequest.getGuests());
        pricingRequest.setUnits(unitIds);
        return pricingRequest;
    }
}
