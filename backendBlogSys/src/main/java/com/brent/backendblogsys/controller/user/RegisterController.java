package com.brent.backendblogsys.controller.user;

import com.brent.backendblogsys.pojo.dto.UserRegisterDTO;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.user.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDTO  user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String userName = user.getUserName();
        String confirmedPassword = user.getConfirmedPassword();
        log.info("用户注册：{}, 用户名：{}", email, userName);
        log.info("接收到的数据：password={}, confirm={}", password, confirmedPassword);

        return registerService.register(userName, email, password, confirmedPassword);
    }
}
