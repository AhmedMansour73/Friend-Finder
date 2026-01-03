package com.example.demo.exception.helper;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
	
	private String messageEn;
	private String messageAr;
	
	private int status;
	private LocalDateTime timestamp;
	
	
	public ExceptionResponse(String messageEn , int status) {
		super();
		this.messageEn = messageEn;
		this.status = status;
	}

	public ExceptionResponse(String messageEn, String messageAr) {
		super();
		this.messageEn = messageEn;
		this.messageAr = messageAr;
	}
	
	

}
