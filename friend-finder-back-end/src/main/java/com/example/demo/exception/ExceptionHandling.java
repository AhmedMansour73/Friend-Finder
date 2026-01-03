package com.example.demo.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.helper.ExceptionResponse;
import com.example.demo.exception.service.BundleService;

@ControllerAdvice
public class ExceptionHandling {
	
	private final BundleService bundleService;

//    @Autowired
    public ExceptionHandling(BundleService bundleService) {
        this.bundleService = bundleService;
    }

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex) {

	    ExceptionResponse response = new ExceptionResponse(
	    		bundleService.getBundleMessageEn(ex.getMessage()),
	    		bundleService.getBundleMessageAr(ex.getMessage()),
	            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
	            LocalDateTime.now()
	    );

	    return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(response);
	}


	// Validation Errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ExceptionResponse>> handleValidationException(
	        MethodArgumentNotValidException exception) {

	    List<ExceptionResponse> errorMessages =
	            exception.getBindingResult()
	                     .getFieldErrors()
	                     .stream()
	                     .map(fieldError -> new ExceptionResponse(
	                    		 bundleService.getBundleMessageEn(fieldError.getDefaultMessage()),
	                    		 bundleService.getBundleMessageAr(fieldError.getDefaultMessage()),
	                             HttpStatus.BAD_REQUEST.value(),
	                             LocalDateTime.now()
	                     ))
	                     .collect(Collectors.toList());

	    return ResponseEntity.badRequest().body(errorMessages);
	}
	
	

    
  
}
