package com.brent.backendblogsys.service.impl;

import com.brent.backendblogsys.common.utils.JwtUtil;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public Result<Map<String, String>> login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticate)) {
            return Result.fail("邮箱或密码错误");
        }

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        // 将完整的用户信息存入 Redis，设置 24 小时过期
        String redisKey = "login:" + userId;
        redisTemplate.opsForValue().set(redisKey, loginUser, 24, TimeUnit.HOURS);

        String jwt = JwtUtil.createJWT(userId);

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        map.put("photo", loginUser.getUser().getPhoto());
        map.put("id", userId);
        map.put("username", loginUser.getUser().getUsername());

        return Result.success("登录成功", map);
    }

    @Override
    public Result<String> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return Result.fail("当前未登录，无需退出");
        }

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();

        // 物理删除 Key 即可让该用户的 JWT 立即失效
        Boolean deleted = redisTemplate.delete("login:" + userId);

        if (Boolean.TRUE.equals(deleted)) {
            SecurityContextHolder.clearContext();
            return Result.success();
        }

        return Result.fail("退出失败或已在其他地方退出");
    }
}
