package com.traingin.shoppingCartTask.controller;

import com.traingin.shoppingCartTask.dtos.ItemDto;
import com.traingin.shoppingCartTask.entities.Item;
import com.traingin.shoppingCartTask.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

@Validated
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/findOne/{itemId}")
    public ResponseEntity<Item> findItemById(@PathVariable String itemId){
        return itemService.getById(itemId);
    }

    @GetMapping("")
    public Page<Item> findAllItems(Pageable pageable){
        return itemService.getAll(pageable);
    }

    @GetMapping("/findAllNonDeleted")
    public ResponseEntity<List<Item>> findAllNonDeleted(){
        return itemService.getAllNonDeleted();
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@Valid @RequestBody ItemDto itemDto){
        return itemService.add(itemDto);
    }

    @PutMapping("/editItem/{itemId}")
    public ResponseEntity<Item> editItem(@Valid @RequestBody ItemDto itemDto , @PathVariable String itemId){
        return itemService.update(itemDto,itemId);
    }

    @DeleteMapping("/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable String itemId){
        return itemService.delete(itemId);
    }

    @DeleteMapping("/softDeleteItem/{itemId}")
    public ResponseEntity<String> softDeleteItem(@PathVariable String itemId){
        return itemService.softDeleteItem(itemId);
    }

}
