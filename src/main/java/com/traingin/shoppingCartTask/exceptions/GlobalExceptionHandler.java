package com.traingin.shoppingCartTask.exceptions;

import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                             WebRequest request) {
        CustomErrorResponse message = new CustomErrorResponse(
                ex.getErrorCode(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<CustomErrorResponse>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse> handleEntityAlreadyExistException(EntityAlreadyExistException ex,
                                                                             WebRequest request) {
        CustomErrorResponse message = new CustomErrorResponse(
                ex.getErrorCode(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<CustomErrorResponse>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                               HttpHeaders headers,
                                                                               HttpStatus status,
                                                                               WebRequest request){


        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        CustomErrorResponse message = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                request.getDescription(false),
                errors);

        return new ResponseEntity<CustomErrorResponse>(message, HttpStatus.BAD_REQUEST);

    }
}
