package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudUserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.UserDTO;

import java.util.*;

public class UserModule {
    public static int userCreated = 0;
    private CrudUserTDG userDataGateway;

    public int CreateUser(UserDTO user) {
        userDataGateway = new CrudUserTDG();
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
        userDataGateway = new CrudUserTDG();
        List<UserDTO> users = new ArrayList<>();
        List<UserEntity> selectedUsers = userDataGateway.Select();
        if (selectedUsers != null) {
            selectedUsers.forEach(userEntity -> {
                UserDTO user = new UserDTO(
                        userEntity.getEmail(),
                        userEntity.getName(),
                        userEntity.getSurname());
                users.add(user);
            });
        }
        return users;
    }
}
