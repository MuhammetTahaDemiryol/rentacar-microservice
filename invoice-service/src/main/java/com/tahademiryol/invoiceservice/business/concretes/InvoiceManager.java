package com.tahademiryol.invoiceservice.business.concretes;

import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.invoiceservice.business.abstracts.InvoiceService;
import com.tahademiryol.invoiceservice.business.dto.responses.get.GetAllInvoicesResponse;
import com.tahademiryol.invoiceservice.business.dto.responses.get.GetInvoiceResponse;
import com.tahademiryol.invoiceservice.business.rules.InvoiceBusinessRules;
import com.tahademiryol.invoiceservice.entities.Invoice;
import com.tahademiryol.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class InvoiceManager implements InvoiceService {
    private final InvoiceRepository repository;
    private final ModelMapperService mapper;
    private final InvoiceBusinessRules rules;


    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoices = repository.findAll();

        return invoices.stream()
                .map(invoice -> mapper.forResponse().map(invoice, GetAllInvoicesResponse.class))
                .toList();
    }

    @Override
    public GetInvoiceResponse getById(String id) {
        Invoice invoice = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(invoice, GetInvoiceResponse.class);
    }

    @Override
    public void add(Invoice invoice) {
        invoice.setId(UUID.randomUUID().toString());
        repository.save(invoice);

    }

//    @Override
//    public void update(String id, Invoice invoice) {
//        Invoice invoiceToUpdate = mapper.forResponse().map(invoice, Invoice.class);
//        // Invoice invoiceToUpdate = repository.findById(id).orElseThrow();
//        invoiceToUpdate.setId(id);
//        mapper.forResponse().map(invoice, invoiceToUpdate);
//        repository.save(invoiceToUpdate);
//    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
