package com.brent.backendblogsys.pojo.dto;

import lombok.Data;

@Data
public class UserResetPasswordDTO {
    private String username;
    private String email;
    private String newPassword;
    private String confirmPassword;
}