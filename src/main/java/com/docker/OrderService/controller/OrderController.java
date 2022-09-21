package com.docker.OrderService.controller;

import com.docker.OrderService.common.Payment;
import com.docker.OrderService.common.TransactionRequest;
import com.docker.OrderService.common.TransactionResponse;
import com.docker.OrderService.entity.Order;
import com.docker.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService service;

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request){
        return service.saveOrder(request);
    }


}
