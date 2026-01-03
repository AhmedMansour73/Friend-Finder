package com.example.demo.exception.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.example.demo.exception.helper.ExceptionResponse;

@Component
public class BundleService {

    private final MessageSource messageSource;

    public BundleService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getBundleMessageAr(String key){
        return messageSource.getMessage(key, null, Locale.forLanguageTag("ar"));
    }

    public String getBundleMessageEn(String key){
        return messageSource.getMessage(key, null, Locale.forLanguageTag("en"));
    }

    public ExceptionResponse getBundleMessage(String key){
        return new ExceptionResponse(
                getBundleMessageEn(key),
                getBundleMessageAr(key)
        );
    }

	

}
