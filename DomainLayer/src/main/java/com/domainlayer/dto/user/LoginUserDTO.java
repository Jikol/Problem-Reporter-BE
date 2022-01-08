package com.domainlayer.dto.user;

import com.domainlayer.dto.SuperUserDTO;

public class LoginUserDTO extends SuperUserDTO {
    public LoginUserDTO(String email, String passwd) {
        super(email, passwd, null, null);
    }

    public String getEmail() {
        return email;
    }
    public String getPasswd() {
        return passwd;
    }

    @Override
    public String toString() {
        return "LoginUserDTO{" +
                "email='" + email + '\'' +
                ", password='" + passwd + '\'' +
                '}';
    }
}
