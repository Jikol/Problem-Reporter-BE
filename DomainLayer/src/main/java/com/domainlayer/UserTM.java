package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudUserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.UserDTO;

import java.util.*;

public class UserTM {
    public static int userCreated = 0;
    private CrudUserTDG userDataGateway;

    public UserTM() {
        userDataGateway = new CrudUserTDG();
    }

    public int CreateUser(UserDTO user) {
        UserEntity userEntity = new UserEntity(user.getEmail(), user.getName(), user.getSurname());
        if (userDataGateway.Insert(userEntity)) {
            userCreated++;
        }
        return userCreated;
    }

    public List<UserDTO> ListAllUsers() {
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
