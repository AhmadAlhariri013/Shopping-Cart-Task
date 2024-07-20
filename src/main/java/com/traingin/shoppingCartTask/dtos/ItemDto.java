package com.traingin.shoppingCartTask.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class ItemDto {

    @NotNull(message = "The Name of item can not be null")
    @NotBlank(message = "The Name of item can not be blank")
    @NotEmpty(message = "The Name of item can not be Empty")
    private String name;

    @NotNull(message = "The quantity of item can not be null")
    @Min(value = 1,message = "The item quantity must be greater than 0")
    private int quantity;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
