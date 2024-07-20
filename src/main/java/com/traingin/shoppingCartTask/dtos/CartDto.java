package com.traingin.shoppingCartTask.dtos;

import com.traingin.shoppingCartTask.entities.Item;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Data
public class CartDto {
    private int userId;
    private HashMap<String,CartItem> items;
}
