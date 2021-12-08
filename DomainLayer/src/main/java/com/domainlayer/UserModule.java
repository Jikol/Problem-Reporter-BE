package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudUserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.UserDTO;

import java.util.*;

public class UserModule {
    public static int userCreated = 0;
    private final CrudUserTDG userDataGateway = new CrudUserTDG();

    public int CreateUser(UserDTO user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        if (userDataGateway.Insert(userEntity)) {
            userCreated++;
        }
        return userCreated;
    }

    public List<UserDTO> ListAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        userDataGateway.Select().forEach(userEntity -> {
            UserDTO user = new UserDTO(
                    userEntity.getEmail(),
                    userEntity.getName(),
                    userEntity.getSurname());
            users.add(user);
        });
        return users;
    }
}
