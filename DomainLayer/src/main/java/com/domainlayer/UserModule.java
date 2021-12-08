package com.domainlayer;

import com.dataaccesslayer.dao.crud.UserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.UserDTO;

import java.util.*;

public class UserModule {
    public static int userCreated = 0;

    public int CreateUser(UserDTO user) {
        UserTDG userDataGateway = new UserTDG();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        if (userDataGateway.Insert(userEntity)) {
            userCreated++;
        }
        return userCreated;
    }
}
