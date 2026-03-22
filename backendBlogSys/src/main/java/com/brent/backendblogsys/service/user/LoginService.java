package com.brent.backendblogsys.service.user;

import com.brent.backendblogsys.pojo.result.Result;

import java.util.Map;

public interface LoginService {
    public Result<Map<String, String>> login(String email, String password);

    public Result<String> logout();
}
