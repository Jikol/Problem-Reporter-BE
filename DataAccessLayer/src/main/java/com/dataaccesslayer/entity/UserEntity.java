package com.dataaccesslayer.entity;

public class UserEntity {
    private Integer id = null;
    private final String email;
    private final String passwd;
    private final String name;
    private final String surname;

    public UserEntity(final String email, final String passwd, final String name, final String surname) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.surname = surname;
    }

    public UserEntity(final Integer id, final String email, final String passwd, final String name, final String surname) {
        this.id = id;
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.surname = surname;
    }

    public Integer getId() {
        return id;
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
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
