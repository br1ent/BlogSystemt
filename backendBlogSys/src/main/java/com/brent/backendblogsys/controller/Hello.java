package com.brent.backendblogsys.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Hello {
    @RequestMapping("/hello")
    public String hello() {
        log.info("访问接口");
        return "Hello World!";
    }
}
