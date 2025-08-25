package com.spring.boot.Controller.Vm;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ProductOrderItemVm", description = "عنصر طلب يحتوي على معرف المنتج والكمية المطلوبة / Order item containing product ID and quantity")
public class ProductOrderItemVm {

    @Schema(description = "معرف المنتج / Product ID", example = "5")
    private Long productId;

    @Schema(description = "الكمية المطلوبة من المنتج / Requested quantity", example = "3")
    private Integer quantity;
}