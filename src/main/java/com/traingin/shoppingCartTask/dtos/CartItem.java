package com.traingin.shoppingCartTask.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItem {

    @NotNull
    private String itemId;

    @Min(1)
    private int cartItemQuantity;
}
