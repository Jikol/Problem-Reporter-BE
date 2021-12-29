package com.domainlayer.dto.user;

public class LoginUserDTO {
    private final String email;
    private final String passwd;

    public LoginUserDTO(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
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
