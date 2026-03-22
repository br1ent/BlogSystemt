package com.brent.backendblogsys.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HelloService {
    public String hello() {
        return "Hello World!";
    }
}
