package com.traingin.shoppingCartTask.services;

import com.traingin.shoppingCartTask.dtos.ItemDto;
import com.traingin.shoppingCartTask.entities.Item;
import com.traingin.shoppingCartTask.exceptions.EntityNotFoundException;
import com.traingin.shoppingCartTask.mappers.ItemMapper;
import com.traingin.shoppingCartTask.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    public ResponseEntity<Item> getById(String itemId){

        // Find the item and then check if is it founded or not
        Item itemToFind = itemRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundException(HttpStatus.NOT_FOUND.value(),"Item with this id: " + itemId +" not found"));

        return ResponseEntity.ok(itemToFind);
    }

    public Page<Item> getAll(Pageable pageable){
        Page<Item> items = itemRepository.findAll(pageable);

        if (items.isEmpty()){
            throw  new EntityNotFoundException(HttpStatus.NOT_FOUND.value(), "No Items Founded");
        }

        return items;
    }

    public ResponseEntity<List<Item>> getAllNonDeleted(){
        List<Item> items = itemRepository.findByDeletedFalse();

        if (items.isEmpty()){
            throw  new EntityNotFoundException(HttpStatus.NOT_FOUND.value(), "No Items Founded");
        }

        return ResponseEntity.ok(items);
    }

    public ResponseEntity<Item> add(ItemDto itemDto){

        // map dto to entity
        Item mappedItem = itemMapper.toItemEntity(itemDto);

        // Insert the item
        Item savedItem = itemRepository.save(mappedItem);

        return ResponseEntity.ok(savedItem);
    }

    public ResponseEntity<Item> update(ItemDto itemDto , String itemId){

        // find the item to update
        Item itemToUpdate = itemRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundException(HttpStatus.NOT_FOUND.value(),"Item with this id: {id} not found"));

        // store new data in itemToUpdate
        itemToUpdate.setItemId(itemToUpdate.getItemId());
        itemToUpdate.setName(itemDto.getName());
        itemToUpdate.setQuantity(itemDto.getQuantity());

        // Update the item
        Item savedItem = itemRepository.save(itemToUpdate);

        return ResponseEntity.ok(savedItem);
    }

    public ResponseEntity<String> delete(String itemId){

        // Find the item and then check if is it founded or not
        Item itemToDelete = itemRepository.findById(itemId).orElseThrow(
                () -> new EntityNotFoundException(HttpStatus.NOT_FOUND.value(),"Item with this id: {id} not found"));

        // Delete the item
        itemRepository.delete(itemToDelete);

        // return success
        return ResponseEntity.ok( "Item deleted successfully");
    }

    public ResponseEntity<String> softDeleteItem(String productId) {
        Item item = itemRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND.value(),"Item not found"));
        item.setDeleted(true);
        item.setDeletedAt(new Date());
        itemRepository.save(item);
        return ResponseEntity.ok("Product soft deleted successfully");
    }

}
