package com.vti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private MessageSource messageSource;

    private String getMessage( Locale locale, String code, String... args) {
        String defaultMessage = "Hello from MessageController";
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    @GetMapping
    public String getMessage(@RequestParam String code) {
        return getMessage(LocaleContextHolder.getLocale(), code);
    }

    @GetMapping("/vi")
    public String getMessageVi(@RequestParam String code) {
        return getMessage(new Locale("vi"), code);
    }

    @GetMapping("/en")
    public String getMessageEn(@RequestParam String code) {
        return getMessage(Locale.ENGLISH, code);
    }


}
