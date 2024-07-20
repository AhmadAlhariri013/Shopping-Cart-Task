package com.traingin.shoppingCartTask.mappers;

import com.traingin.shoppingCartTask.dtos.ItemDto;
import com.traingin.shoppingCartTask.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toItemEntity (ItemDto itemDto);

    ItemDto toItemDto(Item item);
}
