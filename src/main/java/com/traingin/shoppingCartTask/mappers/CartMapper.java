package com.traingin.shoppingCartTask.mappers;

import com.traingin.shoppingCartTask.dtos.CartDto;
import com.traingin.shoppingCartTask.dtos.ItemDto;
import com.traingin.shoppingCartTask.entities.Cart;
import com.traingin.shoppingCartTask.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart toCartEntity (CartDto cartDto);

    CartDto toCartDto(Cart cart);
}
