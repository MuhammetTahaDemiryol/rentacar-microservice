package com.tahademiryol.inventoryservice.business.concretes;

import com.tahademiryol.commonpackage.events.inventory.BrandDeletedEvent;
import com.tahademiryol.commonpackage.utils.kafka.producer.KafkaProducer;
import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.inventoryservice.business.abstracts.BrandService;
import com.tahademiryol.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.tahademiryol.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.tahademiryol.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.tahademiryol.inventoryservice.business.dto.responses.update.UpdateBrandResponse;
import com.tahademiryol.inventoryservice.business.rules.BrandBusinessRules;
import com.tahademiryol.inventoryservice.entities.Brand;
import com.tahademiryol.inventoryservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
    private final BrandRepository repository;
    private final ModelMapperService mapper;
    private final BrandBusinessRules rules;
    private final KafkaProducer producer;

    @Override
    public List<GetAllBrandsResponse> getAll() {
        var Brands = repository.findAll();
        return Brands
                .stream()
                .map(Brand -> mapper.forResponse().map(Brand, GetAllBrandsResponse.class))
                .toList();
    }

    @Override
    public GetBrandResponse getById(UUID id) {
        rules.checkIfBrandExists(id);
        var Brand = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(Brand, GetBrandResponse.class);
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        var Brand = mapper.forRequest().map(request, Brand.class);
        Brand.setId(null);
        repository.save(Brand);
        return mapper.forResponse().map(Brand, CreateBrandResponse.class);
    }

    @Override
    public UpdateBrandResponse update(UUID id, UpdateBrandRequest request) {
        rules.checkIfBrandExists(id);
        var Brand = mapper.forRequest().map(request, Brand.class);
        Brand.setId(id);
        repository.save(Brand);
        return mapper.forResponse().map(Brand, UpdateBrandResponse.class);
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfBrandExists(id);
        repository.deleteById(id);
        producer.sendMessage(new BrandDeletedEvent(id), "brand-deleted");
    }
}
