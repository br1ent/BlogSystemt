package com.brent.backendblogsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brent.backendblogsys.mapper.UserMapper;
import com.brent.backendblogsys.pojo.User;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Result<String> register(String userName, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return Result.fail("密码不一致!");
        }

        if (userName == null || userName.trim().isEmpty()) {
            return Result.fail("用户名不能为空!");
        }

        if (userName.length() > 10) {
            return Result.fail("用户名长度不能超过10!");
        }

        if (email == null || email.trim().isEmpty()) {
            return Result.fail("邮箱不能为空!");
        }

        if (email.length() > 100) {
            return Result.fail("邮箱长度不能超过100!");
        }

        if (password == null || password.trim().isEmpty()) {
            return Result.fail("密码不能为空!");
        }

        if (password.length() > 32) {
            return Result.fail("密码长度不能超过32位!");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        if (userMapper.selectCount(queryWrapper) > 0) {
            return Result.fail("邮箱已存在!");
        }

        String encodedPwd = passwordEncoder.encode(password);


        User user = new User();
        user.setUsername(userName.trim());
        user.setEmail(email.trim());
        user.setPassword(encodedPwd);
        user.setPhoto("https://api.dicebear.com/7.x/avataaars/svg?seed=" + userName);
        user.setStatus(1);
        user.setIsValued(1);

        userMapper.insert(user);
        return Result.success();
    }
}
