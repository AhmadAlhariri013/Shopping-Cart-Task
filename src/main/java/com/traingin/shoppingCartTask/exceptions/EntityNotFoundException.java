package com.traingin.shoppingCartTask.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
public class EntityNotFoundException extends RuntimeException {

    private final int errorCode;

    public EntityNotFoundException(int errorCode, String message) {
        super(message);
        this.errorCode =errorCode;
    }

}
