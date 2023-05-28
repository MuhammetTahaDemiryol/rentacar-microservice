package com.tahademiryol.paymentservice.business.concretes;

import com.tahademiryol.commonpackage.utils.dto.ClientResponse;
import com.tahademiryol.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.tahademiryol.commonpackage.utils.exceptions.BusinessException;
import com.tahademiryol.commonpackage.utils.kafka.producer.KafkaProducer;
import com.tahademiryol.commonpackage.utils.mappers.ModelMapperService;
import com.tahademiryol.paymentservice.business.abstracts.PaymentService;
import com.tahademiryol.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.tahademiryol.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.tahademiryol.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.tahademiryol.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.tahademiryol.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.tahademiryol.paymentservice.business.dto.responses.update.UpdatePaymentResponse;
import com.tahademiryol.paymentservice.business.rules.PaymentBusinessRules;
import com.tahademiryol.paymentservice.entities.Payment;
import com.tahademiryol.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final ModelMapperService mapper;
    private final KafkaProducer producer;
    private final PaymentRepository repository;
    private final PaymentBusinessRules rules;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        var payments = repository.findAll();

        return payments
                .stream()
                .map(payment -> mapper.forResponse().map(payment, GetAllPaymentsResponse.class))
                .toList();
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        rules.checkIfPaymentExists(id);
        var payment = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(payment, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        var payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(null);
        repository.save(payment);

        var response = mapper.forResponse().map(payment, CreatePaymentResponse.class);
        return response;
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        rules.checkIfPaymentExists(id);
        var payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(id);
        repository.save(payment);

        var response = mapper.forResponse().map(payment, UpdatePaymentResponse.class);
        return response;
    }


    @Override
    public void delete(UUID id) {
        rules.checkIfPaymentExists(id);
        repository.deleteById(id);
    }


    @Override
    public ClientResponse processRentalPayment(CreateRentalPaymentRequest request) {
        var response = new ClientResponse();
        try {
            rules.checkIfPaymentIsValid(request);
            Payment payment = repository.findByCardNumber(request.getCardNumber());
            rules.checkIfBalanceIfEnough(payment.getBalance(), request.getPrice());
            payment.setBalance(payment.getBalance() - request.getPrice());

            repository.save(payment);
            response.setSuccess(true);
        } catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }
        return response;
    }
}
