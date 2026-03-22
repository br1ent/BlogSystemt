package com.brent.backendblogsys.controller.user;

import com.brent.backendblogsys.pojo.dto.UserResetPasswordDTO;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.user.ForgetPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ForgetPwdController {
    @Autowired
    private ForgetPwdService forgetPwdService;

    @PostMapping("/forgetpwd")
    public Result<String> reset(@RequestBody UserResetPasswordDTO dto) {
        return forgetPwdService.reset(dto);
    }
}
