package com.vason.springdemo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    private String username;
    private String password;
    private String confirmPassword;
}
