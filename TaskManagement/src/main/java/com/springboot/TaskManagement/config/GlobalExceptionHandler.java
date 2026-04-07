package com.springboot.TaskManagement.config;

import com.springboot.TaskManagement.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        //To print all the Errors as Key Value pairs
        Map<String,Object> map=new HashMap<>();

        //to print multiple field errors
        BindingResult bindingResult=e.getBindingResult();
        //to get the field errors during validation
        List<FieldError> list=bindingResult.getFieldErrors();
        for (FieldError error:list){
            map.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(map);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e
    ){
        Map<String,Object> map=new HashMap<>();
        map.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException e
    ){

        log.atLevel(Level.WARN).log(e.getMessage());

        log.atLevel(Level.INFO).log("Check the ID given to the API, Disable manual API calls..");
        Map<String,Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(map);
    }
}
