package com.smsmode.unit.mapper;


import java.util.List;
import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.resource.inventory.InventoryGetResource.BedResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class BedMapper {

    public abstract BedResource toResource(BedEmbeddable bed);

    public abstract List<BedResource> toResourceList(List<BedEmbeddable> beds);

}
