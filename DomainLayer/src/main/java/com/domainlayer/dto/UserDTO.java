package com.domainlayer.dto;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {
    private final String email;
    private final String name;
    private final String surname;

    public UserDTO(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO entity = (UserDTO) o;
        return Objects.equals(this.email, entity.email) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.surname, entity.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, surname);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "email = " + email + ", " +
                "name = " + name + ", " +
                "surname = " + surname + ")";
    }
}
