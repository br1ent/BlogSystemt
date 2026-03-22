package com.brent.backendblogsys.service.user;

import com.brent.backendblogsys.pojo.result.Result;

public interface RegisterService {
    Result<String> register(String userName, String email, String password, String confirmPassword);
}
