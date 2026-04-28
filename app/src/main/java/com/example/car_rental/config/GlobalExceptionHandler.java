package com.example.car_rental.config;

import com.example.car_rental.exception.ResourceNotFoundException;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    final static String key = "message";
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        log.warn("Validation failed for request: {}", e.getMessage());
        //To print all the Errors as Key Value pairs
        Map<String,Object> map=new HashMap<>();

        //to print multiple field errors
        BindingResult bindingResult=e.getBindingResult();
        //to get the field errors during validation
        List<FieldError> list=bindingResult.getFieldErrors();
        for (FieldError error:list){
            map.put(error.getField(), error.getDefaultMessage());
        }
        log.info("Validation error details prepared with {} field errors", list.size());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(map);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e
    ){
        log.error("Malformed request body received: {}", e.getMessage());
        Map<String,Object> map=new HashMap<>();
        map.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException e
    ){

        log.warn("Resource not found: {}", e.getMessage());

        log.info("Check request IDs and ensure valid resource references");
        Map<String,Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(map);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(
            RuntimeException e
    ){
        log.error("Unexpected runtime exception occurred", e);
        Map<String,Object> map = new HashMap<>();
        map.put(key, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(map);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleRuntimeException(
            IOException e
    ){
        log.error("IO exception occurred during file processing", e);
        Map<String,Object> map = new HashMap<>();
        map.put(key, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(map);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        log.warn("File upload rejected due to size limit");
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", "File size exceeds the allowed limit!"));
    }
}
