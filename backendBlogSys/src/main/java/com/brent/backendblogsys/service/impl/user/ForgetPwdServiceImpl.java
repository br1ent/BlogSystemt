package com.brent.backendblogsys.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brent.backendblogsys.mapper.UserMapper;
import com.brent.backendblogsys.pojo.User;
import com.brent.backendblogsys.pojo.dto.UserResetPasswordDTO;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.user.ForgetPwdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForgetPwdServiceImpl implements ForgetPwdService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Result<String> reset(UserResetPasswordDTO dto) {
        log.info("收到的请求：{}", dto);

        String username = dto.getUsername();
        String email = dto.getEmail();
        String newPwd = dto.getNewPassword();
        String confirmPwd = dto.getConfirmPassword();

        if (username == null || email == null || newPwd == null) {
            return Result.fail("信息填写不完整!");
        }

        if (!newPwd.equals(confirmPwd)) {
            return Result.fail("两次输入的密码不一致!");
        }

        if (newPwd.length() > 32) {
            return Result.fail("密码长度不能超过32位!");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username.trim())
                .eq("email", email.trim());

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            return Result.fail("用户名与邮箱不匹配，请重新确认!");
        }

        String encodedPassword = passwordEncoder.encode(newPwd);
        user.setPassword(encodedPassword);

        int rows = userMapper.updateById(user);

        if (rows <= 0) {
            return Result.fail("数据库更新失败，请稍后再试!");
        }

        log.info("用户尝试重置密码：{}, 邮箱地址: {}", dto.getUsername(), dto.getEmail());

        return Result.success("密码重置成功!", null);
    }
}
