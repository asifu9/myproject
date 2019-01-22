package com.auth.oauth.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping(value = "/")
    public String index(){
        return "Hello world";
    }

    @PreAuthorize("#oauth2.hasScope('read-foo')")
    @GetMapping(value = "/private")
    public String privateArea(){
        return "Private area";
    }

}
