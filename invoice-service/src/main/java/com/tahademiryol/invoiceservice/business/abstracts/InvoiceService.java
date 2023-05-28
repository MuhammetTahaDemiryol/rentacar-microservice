package com.tahademiryol.invoiceservice.business.abstracts;

import com.tahademiryol.invoiceservice.business.dto.responses.get.GetAllInvoicesResponse;
import com.tahademiryol.invoiceservice.business.dto.responses.get.GetInvoiceResponse;
import com.tahademiryol.invoiceservice.entities.Invoice;

import java.util.List;

public interface InvoiceService {

    List<GetAllInvoicesResponse> getAll();

    GetInvoiceResponse getById(String id);

    void add(Invoice invoice);

    //void update(String id, Invoice invoice);

    void delete(String id);
}
