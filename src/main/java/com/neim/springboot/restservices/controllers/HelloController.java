package com.neim.springboot.restservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @GetMapping("/welcome")
    public String helloWorld() {
        return "Hello Nestor";
    }

    @GetMapping("/user-bean")
    public UserDetails userDetails() {
        return new UserDetails("Nestor Mazariego", "neim1996@hotmail.com", "San Jose");
    }

    @GetMapping("/hello-int")
    public String getMessagesI18nFormat(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        return messageSource.getMessage("label.hello", null, new Locale(locale));
    }

    @GetMapping("/hello-int2")
    public String getMessagesI18nFormat2() {
        return messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale());
    }
}
