package com.example.filterservice.business.concretes;

import com.example.filterservice.business.abstracts.FilterService;
import com.example.filterservice.business.dto.responses.GetAllFiltersResponses;
import com.example.filterservice.business.dto.responses.GetFilterResponse;
import com.example.filterservice.entities.Filter;
import com.example.filterservice.repositories.FilterRepository;
import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FilterManager implements FilterService {
    private final FilterRepository repository;
    private ModelMapperService mapper;

    @Override
    public List<GetAllFiltersResponses> getAll() {
        var filters = repository.findAll();

        return filters.stream()
                .map(filter -> mapper.forResponse().map(filter, GetAllFiltersResponses.class))
                .toList();
    }

    @Override
    public GetFilterResponse getById(UUID id) {
        var filter = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(filter, GetFilterResponse.class);
    }

    @Override
    public void add(Filter filter) {
        filter.setId(UUID.randomUUID());
        repository.save(filter);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByBrandId(UUID brandId) {

    }

    @Override
    public void deleteAllByModelId(UUID modelId) {

    }
}
