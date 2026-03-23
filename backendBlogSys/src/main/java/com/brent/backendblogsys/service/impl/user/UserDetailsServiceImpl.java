package com.brent.backendblogsys.service.impl.user;

import com.brent.backendblogsys.mapper.UserMapper;
import com.brent.backendblogsys.pojo.User; // 确保导入的是你自己的实体类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.selectUserForAuth(email);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("邮箱不存在或账号错误");
        }

        return new UserDetailsImpl(user);
    }
}