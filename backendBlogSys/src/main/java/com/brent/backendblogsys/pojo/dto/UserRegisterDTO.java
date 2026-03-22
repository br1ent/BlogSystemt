package com.brent.backendblogsys.pojo.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String userName;
    private String email;
    private String password;
    private String confirmedPassword;
}