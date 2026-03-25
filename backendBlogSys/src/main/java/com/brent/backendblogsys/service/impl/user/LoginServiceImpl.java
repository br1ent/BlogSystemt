package com.brent.backendblogsys.service.impl.user;

import com.brent.backendblogsys.common.utils.JwtUtil;
import com.brent.backendblogsys.pojo.result.Result;
import com.brent.backendblogsys.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public Result<Map<String, String>> login(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);

            Authentication authenticate =
                    authenticationManager.authenticate(authenticationToken);

            UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
            String userId = loginUser.getUser().getId().toString();

            // 存 Redis
            redisTemplate.opsForValue().set(
                    "login:" + userId,
                    loginUser,
                    24,
                    TimeUnit.HOURS
            );

            String jwt = JwtUtil.createJWT(userId);

            Map<String, String> map = new HashMap<>();
            map.put("token", jwt);
            map.put("photo", loginUser.getUser().getPhoto());
            map.put("id", userId);
            map.put("username", loginUser.getUser().getUsername());

            return Result.success("登录成功", map);

        } catch (BadCredentialsException e) {
            return Result.fail(401, "邮箱或密码错误");
        } catch (UsernameNotFoundException e) {
            return Result.fail(401, "邮箱不存在");
        } catch (Exception e) {
            return Result.fail(500, "服务器异常，请稍后再试");
        }
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
