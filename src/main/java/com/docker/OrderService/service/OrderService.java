package com.docker.OrderService.service;

import com.docker.OrderService.common.Payment;
import com.docker.OrderService.common.TransactionRequest;
import com.docker.OrderService.common.TransactionResponse;
import com.docker.OrderService.entity.Order;
import com.docker.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponse saveOrder(TransactionRequest request) {
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        Payment paymentResponse = restTemplate.postForObject("http://payment-service/payment/doPayment",payment, Payment.class);
        String response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successfull and order placed" : "there is a failure in payment api, order added to cart";
        orderRepository.save(order);

        return new TransactionResponse(order, paymentResponse.getTransactionId(), paymentResponse.getAmount(), response);
    }

}
