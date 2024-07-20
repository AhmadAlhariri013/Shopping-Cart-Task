package com.traingin.shoppingCartTask.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomErrorResponse {

    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
    private List<String> errors;


    public CustomErrorResponse(int statusCode, Date timestamp, String description, List<String> errors) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.description = description;
        this.errors = errors;
    }

    public CustomErrorResponse(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }
}
