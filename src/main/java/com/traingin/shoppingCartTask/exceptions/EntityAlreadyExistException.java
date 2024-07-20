package com.traingin.shoppingCartTask.exceptions;

import lombok.Getter;

@Getter
public class EntityAlreadyExistException extends RuntimeException {

    private final int errorCode;

    public EntityAlreadyExistException(int errorCode, String message) {
        super(message);
        this.errorCode =errorCode;
    }
}
