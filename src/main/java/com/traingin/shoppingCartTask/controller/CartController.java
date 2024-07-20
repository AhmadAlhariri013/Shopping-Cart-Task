package com.traingin.shoppingCartTask.controller;

import com.traingin.shoppingCartTask.dtos.CartDto;
import com.traingin.shoppingCartTask.dtos.CartItem;
import com.traingin.shoppingCartTask.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/find/{userId}")
    public ResponseEntity<CartDto> findCartByUserId(@PathVariable int userId){
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<String> createCart(@PathVariable int userId, @RequestBody HashMap<String, CartItem> items){
        return cartService.createCart(userId, items);
    }

    @PostMapping("/{userId}/addItemToCart")
    public ResponseEntity<String> addItemToCart(@PathVariable int userId, @RequestBody CartItem sentCartItem){
        return cartService.addItemToCart(userId, sentCartItem);
    }

    @PostMapping("/{userId}/items/{itemId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable int userId, @PathVariable String itemId, @RequestParam int quantityDelta) {
        return cartService.updateCartItemQuantity(userId, itemId, quantityDelta);
    }

    @DeleteMapping("/{userId}/items/delete/{itemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable int userId, @PathVariable String itemId){
        return cartService.deleteCartItem(userId, itemId);
    }
}
