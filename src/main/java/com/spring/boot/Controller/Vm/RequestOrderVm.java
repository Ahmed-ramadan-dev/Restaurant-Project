package com.spring.boot.Controller.Vm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RequestOrderVm", description = "Request object for creating an order")
public class RequestOrderVm {

    @NotEmpty(message = "productOrderItemVmList.is.required")
    @Valid
    @Schema(
            description = "List of products and their quantities for the order",
            example = "[{\"productId\": 1, \"quantity\": 2}, {\"productId\": 5, \"quantity\": 1}]"
    )
    private List<ProductOrderItemVm> productOrderItemVmList;

    @NotNull(message = "totalPrice.is.required")
    @Min(value = 1, message = "totalPrice.must.be.greater.than.zero")
    @Schema(
            description = "Total price of the order",
            example = "250.75"
    )
    private Double totalPrice;

    @NotNull(message = "totalNumber.is.required")
    @Min(value = 1, message = "totalNumber.must.be.greater.than.zero")
    @Schema(
            description = "Total number of products in the order",
            example = "3"
    )
    private Integer totalNumber;

}