
package com.spring.boot.Controller;
import com.spring.boot.Controller.Vm.RequestOrderVm;
import com.spring.boot.Service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Tag(name = "Order Management", description = "APIs for managing customer orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/order/create-order")
    @PreAuthorize("hasAuthority(T(com.spring.boot.enums.RoleName).USER.toString()) or hasAuthority(T(com.spring.boot.enums.RoleName).ADMIN.toString())")
    @Operation(
            summary = "Create a new order",
            description = "This endpoint allows the user to create a new order by providing product IDs, quantities, and total price."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request or one or more products are invalid"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity< String> createOrder(@RequestBody @Valid RequestOrderVm requestOrderVm) {
        orderService.createOrder(requestOrderVm);
        return ResponseEntity.created(URI.create("/order/create-order")).body("Order created");


    }

}
