package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudUserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.user.LoginUserDTO;
import com.domainlayer.dto.user.NewUserDTO;
import com.domainlayer.module.JwtToken;
import com.domainlayer.module.PasswordEncryption;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserTM {
    private static int userRegistered;

    public UserTM() {
        userRegistered = 0;
    }

    public Map<Object, Object> RegisterUser(final NewUserDTO newUserDTO) {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        String token = null;
        if (newUserDTO.getEmail() != null && newUserDTO.getPasswd() != null) {
            String hashedPassword = PasswordEncryption.GetHashedPassword(newUserDTO.getPasswd());
            crudUserTDG.registerNew(new UserEntity(newUserDTO.getEmail(), hashedPassword, newUserDTO.getName(), newUserDTO.getSurname()));
            try {
                userRegistered += crudUserTDG.commit();
            } catch (Exception ex) {
                return Map.of(
                        "status", 409,
                        "error", ex.getLocalizedMessage()
                );
            }
            token = JwtToken.GenerateToken(newUserDTO.getEmail());
        } else {
            return Map.of(
                    "status", 400,
                    "error", "Email and Password are mandatory attributes"
            );
        }
        return Map.of(
                "status", 201,
                "token", token
        );
    }

    public Map<Object, Object> RegisterUsers(final List<NewUserDTO> newUserDTOS) {
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

    public void loginUser(final LoginUserDTO loginUserDTO) {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        UserEntity userEntity = crudUserTDG.selectByEmail(loginUserDTO.getEmail());
        if (PasswordEncryption.AuthenticatePassword(loginUserDTO.getPassword(), userEntity.getPasswd())) {
            System.out.println("prihlasen");
        } else {
            System.out.println("spatne heslo");
        }
    }

//    public List<NewUserDTO> ListAllUsers() {
//        CrudUserTDG crudUserTDG = new CrudUserTDG();
//        List<NewUserDTO> users = new ArrayList<>();
//        List<UserEntity> selectedUsers = crudUserTDG.Select();
//        if (selectedUsers != null) {
//            selectedUsers.forEach(userEntity -> {
//                NewUserDTO user = new NewUserDTO(
//                        userEntity.getEmail(),
//                        userEntity.getName(),
//                        userEntity.getName(),
//                        userEntity.getSurname());
//                users.add(user);
//            });
//        }
//        return users;
//    }

    public int GetRegisteredUser() {
        return userRegistered;
    }
}
