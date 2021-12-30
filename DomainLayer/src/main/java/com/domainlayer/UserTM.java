package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudUserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.user.LoginUserDTO;
import com.domainlayer.dto.user.RegisterUserDTO;
import com.domainlayer.dto.user.UserDTO;
import com.domainlayer.module.JwtToken;
import com.domainlayer.module.PasswordEncryption;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserTM {
    private static int userRegistered;

    public UserTM() {
        userRegistered = 0;
    }

    public Map<Object, Object> RegisterUser(final RegisterUserDTO registerUserDTO) {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        String token = null;
        if (registerUserDTO.getEmail() != null && registerUserDTO.getPasswd() != null) {
            String hashedPassword = PasswordEncryption.GetHashedPassword(registerUserDTO.getPasswd());
            crudUserTDG.RegisterNew(new UserEntity(registerUserDTO.getEmail(), hashedPassword, registerUserDTO.getName(), registerUserDTO.getSurname()));
            try {
                userRegistered += crudUserTDG.Commit();
            } catch (Exception ex) {
                return Map.of(
                        "status", 409,
                        "error", ex.getLocalizedMessage()
                );
            }
            token = JwtToken.GenerateToken(registerUserDTO.getEmail());
        } else {
            return Map.of(
                    "status", 400,
                    "error", "Email and Password are mandatory attributes"
            );
        }
        return Map.of(
                "status", 201,
                "created", userRegistered,
                "token", token
        );
    }

    public Map<Object, Object> RegisterUsers(final List<RegisterUserDTO> registerUserDTOS) {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        AtomicBoolean problemFound = new AtomicBoolean(false);
        registerUserDTOS.forEach(registerUserDTO -> {
            if (registerUserDTO.getEmail() != null && registerUserDTO.getPasswd() != null) {
                crudUserTDG.RegisterNew(new UserEntity(registerUserDTO.getEmail(), registerUserDTO.getPasswd(),
                        registerUserDTO.getName(), registerUserDTO.getSurname()));
            } else {
                problemFound.set(true);
            }
        });
        try {
            userRegistered += crudUserTDG.Commit();
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

    public Map<Object, Object> loginUser(final LoginUserDTO loginUserDTO) {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        if (loginUserDTO.getEmail() == null || loginUserDTO.getPassword() == null) {
            return Map.of(
                    "status", 400,
                    "error", "Email and Password are mandatory attributes"
            );
        }
        UserEntity userEntity = crudUserTDG.SelectByEmail(loginUserDTO.getEmail());
        if (userEntity != null) {
            if (PasswordEncryption.AuthenticatePassword(loginUserDTO.getPassword(), userEntity.getPasswd())) {
                String token = JwtToken.GenerateToken(loginUserDTO.getEmail());
                return Map.of(
                        "status", 200,
                        "token", token
                );
            } else {
                return Map.of(
                        "status", 403,
                        "error", "Incorrect email or password"
                );
            }
        } else {
            return Map.of(
                    "status", 404,
                    "error", "User with provided credentials does not exist"
            );
        }
    }

    public List<UserDTO> listAllUsers() {
        CrudUserTDG crudUserTDG = new CrudUserTDG();
        List<UserDTO> userDTOList = new ArrayList<>();
        List<UserEntity> userEntities = crudUserTDG.SelectAll();
        userEntities.forEach(userEntity -> {
            userDTOList.add(new UserDTO(userEntity.getEmail(), userEntity.getName(), userEntity.getSurname()));
        });
        return userDTOList;
    }

    public int GetRegisteredUser() {
        return userRegistered;
    }
}
