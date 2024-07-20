package com.traingin.shoppingCartTask.services;

import com.traingin.shoppingCartTask.dtos.CartDto;
import com.traingin.shoppingCartTask.dtos.CartItem;
import com.traingin.shoppingCartTask.entities.Cart;
import com.traingin.shoppingCartTask.entities.Item;
import com.traingin.shoppingCartTask.exceptions.EntityAlreadyExistException;
import com.traingin.shoppingCartTask.exceptions.EntityNotFoundException;
import com.traingin.shoppingCartTask.mappers.CartMapper;
import com.traingin.shoppingCartTask.repositories.CartRepository;
import com.traingin.shoppingCartTask.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartMapper cartMapper;

    public ResponseEntity<CartDto> getCartByUserId(int userId){
        Cart cartToGet = cartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(HttpStatus.NOT_FOUND.value(), "No Cart For this user"));

        return ResponseEntity.ok(cartMapper.toCartDto(cartToGet));
    }


    // Not Finished
    public ResponseEntity<String> createCart(int userId, HashMap<String ,CartItem> items){

        // Check if this user has cart or not
        boolean isUserHasCart = cartRepository.findByUserId(userId).isPresent();

        // If the user has a cart throw exception
        if(isUserHasCart){
            throw  new EntityAlreadyExistException(HttpStatus.BAD_REQUEST.value(), "This User already has a cart");
        }else{
            // create cart for the user
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setItems(items);

            // Save the new cart
            cartRepository.save(newCart);

            return ResponseEntity.ok("Cart created successfully for user: " + userId);
        }


    }

    public ResponseEntity<String> addItemToCart(int userId, CartItem sentCartItem ){

        // Find the item and then check if is it founded or not
        Item itemToAdd = itemRepository.findById(sentCartItem.getItemId()).orElseThrow(
                () -> new EntityNotFoundException(HttpStatus.NOT_FOUND.value(),"Item with this id: " + sentCartItem.getItemId() +" not found"));

        // Check if the item quantity in the inventory greater than the quantity that user add
        if (itemToAdd.getQuantity() < sentCartItem.getCartItemQuantity()) {
             throw  new EntityNotFoundException(HttpStatus.BAD_REQUEST.value(), "Insufficient product stock");

        }

        // Retrieve or create shopping cart for the user if he doesn't have on
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setItems(new HashMap<>());
            return cartRepository.save(newCart);
        });


        // Update or add item to the cart
        // Check if the item already in the cart or not
        CartItem cartItem = cart.getItems().get(sentCartItem.getItemId());

        // if exist in the cart
        if (cartItem != null) {
            // update the quantity
            cartItem.setCartItemQuantity(cartItem.getCartItemQuantity() + sentCartItem.getCartItemQuantity());
        } else {
            // create new cartItem and assign values to it
            cartItem = new CartItem();
            cartItem.setItemId(sentCartItem.getItemId());
            cartItem.setCartItemQuantity(sentCartItem.getCartItemQuantity());
            
            // add it in the cart
            cart.getItems().put(cartItem.getItemId(), cartItem);
        }

        // Update Item stock
        itemToAdd.setQuantity(itemToAdd.getQuantity() - sentCartItem.getCartItemQuantity());
        itemRepository.save(itemToAdd);

        // Save the updated shopping cart
        cartRepository.save(cart);

        return ResponseEntity.ok("Product added to cart successfully");

    }

    public ResponseEntity<String> updateCartItemQuantity(int userId, String itemId, int quantityDelta) {


        //  Check if the item exist
         Item item = itemRepository.findById(itemId).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND.value(), "Item not found"));

        // Retrieve shopping cart
        Cart cart = cartRepository.findByUserId(userId).get();

        // Check if the cartItem exist in the cart
        CartItem cartItem = cart.getItems().get(itemId);
        // if not exist
        if (cartItem == null) {
            throw  new EntityNotFoundException(HttpStatus.NOT_FOUND.value(), "Item not found in cart");
        }

        // Update quantity (handle positive/negative deltas)
        int newQuantity = cartItem.getCartItemQuantity() + quantityDelta;


        // Check on the new Quantity
        if ( quantityDelta > item.getQuantity()) {
            throw  new EntityNotFoundException(HttpStatus.BAD_REQUEST.value(), "Insufficient product stock");

        } else if (newQuantity <= 0) {
            cart.getItems().remove(itemId); // Remove item if quantity reaches 0

        } else {
            cartItem.setCartItemQuantity(newQuantity);
            item.setQuantity(item.getQuantity() + (quantityDelta * -1));
            itemRepository.save(item);
        }

        // Save the updated cart
        cartRepository.save(cart);

        return ResponseEntity.ok("Cart item quantity updated successfully");
    }

    public ResponseEntity<String> deleteCartItem(int userId, String itemId) {


        // Retrieve shopping cart
        Cart cart = cartRepository.findByUserId(userId).get();

        // Check if item exists in cart
        CartItem cartItem = cart.getItems().get(itemId);
        if (cartItem == null) {
            throw  new EntityNotFoundException(HttpStatus.NOT_FOUND.value(), "Item not found in cart");
        }

        // Remove the item from the cart
        cart.getItems().remove(itemId);

        // Save the updated cart (optional, might be redundant with some configurations)
        cartRepository.save(cart);

        return ResponseEntity.ok("Item deleted from cart successfully");
    }
}
