package com.brent.backendblogsys.controller.user;

import com.brent.backendblogsys.pojo.User;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.user.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        return loginService.login(email, password);
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        return loginService.logout();
    }
}
