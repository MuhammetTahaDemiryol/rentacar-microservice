package com.tahademiryol.invoiceservice.api.controllers;

import com.tahademiryol.invoiceservice.business.abstracts.InvoiceService;
import com.tahademiryol.invoiceservice.business.dto.responses.get.GetAllInvoicesResponse;
import com.tahademiryol.invoiceservice.business.dto.responses.get.GetInvoiceResponse;
import com.tahademiryol.invoiceservice.entities.Invoice;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/invoices")
public class InvoicesController {
    private final InvoiceService service;

    // Will work only once for creating a mongo db
//    @PostConstruct
//    public void createDb() {
//        service.add(new Invoice());
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public List<GetAllInvoicesResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public void add(@RequestBody Invoice invoice) {
        service.add(invoice);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public GetInvoiceResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

}
