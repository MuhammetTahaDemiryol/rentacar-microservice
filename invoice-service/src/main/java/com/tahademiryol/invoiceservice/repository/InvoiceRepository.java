package com.tahademiryol.invoiceservice.repository;

import com.tahademiryol.invoiceservice.entities.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {

}
