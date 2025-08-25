package com.spring.boot.Service;


import com.spring.boot.Controller.Vm.RequestOrderVm;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderService {
    void createOrder( RequestOrderVm requestOrderVm);
}
