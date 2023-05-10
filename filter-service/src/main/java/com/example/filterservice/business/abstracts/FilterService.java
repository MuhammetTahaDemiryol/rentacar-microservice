package com.example.filterservice.business.abstracts;

import com.example.filterservice.business.dto.responses.GetAllFiltersResponses;
import com.example.filterservice.business.dto.responses.GetFilterResponse;
import com.example.filterservice.entities.Filter;

import java.util.List;
import java.util.UUID;

public interface FilterService {
    List<GetAllFiltersResponses> getAll();

    GetFilterResponse getById(String id);

    //! Will not be used outside the service layer
    void add(Filter filter);

    void delete(String id);

    void deleteByCarId(UUID id);

    void deleteAllByBrandId(UUID brandId); //! Bulk delete

    void deleteAllByModelId(UUID modelId); //! Bulk delete
}
