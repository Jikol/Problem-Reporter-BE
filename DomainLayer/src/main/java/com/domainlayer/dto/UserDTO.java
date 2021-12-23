package com.domainlayer.dto;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {
    private final String email;
    private final String passwd;
    private final String name;
    private final String surname;

    public UserDTO(String email, String passwd, String name, String surname) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
