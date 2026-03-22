package com.brent.backendblogsys.service.user;

import com.brent.backendblogsys.pojo.dto.UserResetPasswordDTO;
import com.brent.backendblogsys.pojo.result.Result;

public interface ForgetPwdService {
    Result<String> reset(UserResetPasswordDTO dto);
}
