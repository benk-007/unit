package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.projection.FlatUnitBedProjection;
import com.smsmode.unit.dao.projection.UnitSubCountProjection;
import com.smsmode.unit.dao.service.RoomDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.exception.InternalServerException;
import com.smsmode.unit.exception.enumeration.InternalServerExceptionTitleEnum;
import com.smsmode.unit.mapper.UnitMapper;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.base.AbstractBaseModel;
import com.smsmode.unit.resource.inventory.PriceCalculationPostResource;
import com.smsmode.unit.resource.inventory.get.AvailabilityGetResource;
import com.smsmode.unit.resource.inventory.get.UnitInventoryGetResource;
import com.smsmode.unit.resource.inventory.post.InventoryPostResource;
import com.smsmode.unit.service.InventoryService;
import com.smsmode.unit.service.feign.BookingFeignService;
import com.smsmode.unit.service.feign.PricingFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final BookingFeignService bookingFeignService;
    private final UnitDaoService unitDaoService;
    private final PricingFeignService pricingFeignService;
    private final RoomDaoService roomDaoService;
    private final UnitMapper unitMapper;

    @Override
    public ResponseEntity<Page<UnitInventoryGetResource>> getInventory(InventoryPostResource inventoryPostResource, Pageable pageable) {
        log.info("Fetching reserved units from booking service...");

        // Step 1: Call booking service
        List<String> reservedUnitIds;
        try {
            ResponseEntity<List<String>> response = bookingFeignService.getBookedUnits(inventoryPostResource.getCheckinDate(), inventoryPostResource.getCheckoutDate());
            reservedUnitIds = response.getBody();
            if (reservedUnitIds == null) reservedUnitIds = Collections.emptyList();
            log.info("Reserved unit IDs: {}", reservedUnitIds);
        } catch (Exception e) {
            log.error("Failed to fetch reserved units", e);
            throw new InternalServerException(InternalServerExceptionTitleEnum.SERVICE_UNAVAILABLE, "Booking service unavailable");
        }
        log.debug("Constructing array from reserved unit IDs ...");
        String[] reservedUnitIdsArray = reservedUnitIds.toArray(new String[0]);
        log.debug("Fetching available units from database based on readiness and reserved unit Ids array ...");
        Page<UnitModel> availableUnits = unitDaoService.findAvailableUnits(reservedUnitIdsArray, pageable);
        log.info("Retrieved {} units available", availableUnits.getTotalElements());
        log.debug("Mapping models to unit inventory get resource ...");
        Page<UnitInventoryGetResource> inventoryGetResources = availableUnits.map(unitMapper::modelToInventoryGetResource);
        log.info("Mapping successful. Result contains: {}", inventoryGetResources.getTotalElements());
        log.debug("Grouping reserved unit ids retrieved from booking service with count ...");
        Map<String, Long> reservedUnitCountGrouped = reservedUnitIds.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));
        log.info("Grouping result: {}", reservedUnitCountGrouped);
        log.debug("Filtering the available units to keep only ids of those with MULTI_UNIT nature ...");
        List<String> multiUnitIds = availableUnits.getContent().stream()
                .filter(unit -> UnitNatureEnum.MULTI_UNIT.equals(unit.getNature()))
                .map(UnitModel::getId)
                .toList();
        log.info("Multi unit ids from your available units are: {}", multiUnitIds);
        log.debug("Will retrieve how many sub-units each multi unit has from database ...");
        List<UnitSubCountProjection> unitSubCountProjections = unitDaoService.countSubUnitsForMultiUnits(multiUnitIds);
        log.info("Result of subUnitCount is: {}", unitSubCountProjections);
        Map<String, Long> subUnitCountMap = unitSubCountProjections.stream()
                .collect(Collectors.toMap(UnitSubCountProjection::getUnitId, UnitSubCountProjection::getSubUnitCount));
        log.debug("Filter available unit models to retrieve list of their ids ...");
        List<String> availableUnitIds = availableUnits.map(AbstractBaseModel::getId).stream().toList();
        log.debug("Retrieve beds related to each unit of the resource from database ...");
        List<FlatUnitBedProjection> flatResults = unitDaoService.findUnitBeds(availableUnitIds, Stream.of(RoomTypeEnum.BEDROOM, RoomTypeEnum.LIVING, RoomTypeEnum.GENERAL).toList());
        log.info("Retrieved unit beds as flat results");
        log.debug("Grouping beds by unitId ...");
        Map<String, List<BedEmbeddable>> groupedBedsByUnit = flatResults.stream()
                .collect(Collectors.groupingBy(
                        FlatUnitBedProjection::getUnitId,
                        Collectors.mapping(FlatUnitBedProjection::getBed, Collectors.toList())
                ));
        log.debug("Enrich each resource in the page with availability and beds ...");
        inventoryGetResources.forEach(resource -> {
            log.debug("Resource with id:{} and name: {} ...", resource.getId(), resource.getName());
            String unitId = resource.getId();
            AvailabilityGetResource availability = new AvailabilityGetResource();

            if (UnitNatureEnum.MULTI_UNIT.equals(resource.getNature())) {
                log.debug("Resource is of type MULTI_UNIT ...");
                Long totalSubUnits = subUnitCountMap.getOrDefault(unitId, 0L);
                Long reservedCount = reservedUnitCountGrouped.getOrDefault(unitId, 0L);
                availability.setQuantity(totalSubUnits);
                availability.setAvailable(Math.max(totalSubUnits - reservedCount, 0));
                log.info("Availability after calculation is: {}", availability);
            } else {
                log.debug("Resource is of type SINGLE ...");
                availability.setQuantity(1L);
                availability.setAvailable(1L);
                log.info("Availability is: {}", availability);
            }
            log.debug("Setting availability to resource ...");
            resource.setAvailability(availability);
            log.debug("Setting beds ...");
            resource.setBeds(groupedBedsByUnit.getOrDefault(unitId, null));
        });
        // Step 3: Call pricing service
        /*List<String> unitIdsToPrice = availableUnits.stream().map(UnitModel::getId).toList();
        PriceCalculationPostResource pricingRequest = buildPricingRequest(inventoryPostResource, unitIdsToPrice);

        ResponseEntity<List<UnitPricingGetResource>> pricingResponse = pricingFeignService.calculatePricing(pricingRequest);
        List<UnitPricingGetResource> pricedUnits = pricingResponse.getBody();
        if (pricedUnits == null) pricedUnits = Collections.emptyList();

        // Step 4: Index pricing results
        Map<String, UnitPricingGetResource> pricingMap = pricedUnits.stream()
                .collect(Collectors.toMap(UnitPricingGetResource::getId, p -> p));

        // Step 5: Map to InventoryGetResource
        List<UnitInventoryGetResource> finalResults = availableUnits.stream()
                .map(unit -> {
                    UnitPricingGetResource pricing = pricingMap.get(unit.getId());
                    if (pricing == null) return null;

                    UnitInventoryGetResource resource = new UnitInventoryGetResource();
                    resource.setId(unit.getId());
                    resource.setName(unit.getName());

                    UnitInventoryGetResource.Inventory inventory = new UnitInventoryGetResource.Inventory();
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

                    UnitInventoryGetResource.Price price = new UnitInventoryGetResource.Price();
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
                .toList();*/

        return ResponseEntity.ok(inventoryGetResources);

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
