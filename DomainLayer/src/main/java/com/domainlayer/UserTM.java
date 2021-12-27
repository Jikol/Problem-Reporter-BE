package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudUserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.user.NewUserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserTM {
    private static int userRegistered;

    public UserTM() {
        userRegistered = 0;
    }

    public Map<Object, Object> RegisterUser(NewUserDTO newUserDTO) {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        if (newUserDTO.getEmail() != null && newUserDTO.getPasswd() != null) {
            crudUserTDG.registerNew(new UserEntity(newUserDTO.getEmail(), newUserDTO.getPasswd(), newUserDTO.getName(), newUserDTO.getSurname()));
            try {
                userRegistered += crudUserTDG.commit();
            } catch (Exception ex) {
                return Map.of(
                        "status", 409,
                        "error", ex.getLocalizedMessage()
                );
            }
        } else {
            return Map.of(
                    "status", 400,
                    "error", "Email and Password are mandatory attributes"
            );
        }
        return Map.of(
                "status", 201
        );
    }

    public Map<Object, Object> RegisterUsers(List<NewUserDTO> newUserDTOS) {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        AtomicBoolean problemFound = new AtomicBoolean(false);
        newUserDTOS.forEach(newUserDTO -> {
            if (newUserDTO.getEmail() != null && newUserDTO.getPasswd() != null) {
                crudUserTDG.registerNew(new UserEntity(newUserDTO.getEmail(), newUserDTO.getPasswd(), newUserDTO.getName(), newUserDTO.getSurname()));
            } else {
                problemFound.set(true);
            }
        });
        try {
            userRegistered += crudUserTDG.commit();
        } catch (Exception ex) {
            return Map.of(
                    "status", 409,
                    "error", ex.getLocalizedMessage()
            );
        }
        if (problemFound.get()) {
            return Map.of(
                    "status", 400,
                    "error", "Email and Password are mandatory attributes"
            );
        }
        return Map.of(
                "status", 201
        );
    }

    public List<NewUserDTO> ListAllUsers() {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        List<NewUserDTO> users = new ArrayList<>();
        List<UserEntity> selectedUsers = crudUserTDG.Select();
        if (selectedUsers != null) {
            selectedUsers.forEach(userEntity -> {
                NewUserDTO user = new NewUserDTO(
                        userEntity.getEmail(),
                        userEntity.getName(),
                        userEntity.getName(),
                        userEntity.getSurname());
                users.add(user);
            });
        }
        return users;
    }

    public int GetRegisteredUser() {
        return userRegistered;
    }

    public String GetJWTToken(NewUserDTO user) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = Jwts
                .builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(key)
                .compact();
        return "Bearer " + token;
    }
}
