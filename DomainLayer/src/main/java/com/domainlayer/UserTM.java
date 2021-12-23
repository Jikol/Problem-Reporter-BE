package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudUserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.*;

public class UserTM {
    public static int userCreated = 0;
    private CrudUserTDG userDataGateway;

    public UserTM() {
        userDataGateway = new CrudUserTDG();
    }

    public int RegisterUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity(userDTO.getEmail(), userDTO.getPasswd(), userDTO.getName(), userDTO.getSurname());
//        if (userDataGateway.Insert(userEntity)) {
//            userCreated++;
//        }
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
                        userEntity.getName(),
                        userEntity.getSurname());
                users.add(user);
            });
        }
        return users;
    }

    public String GetJWTToken(UserDTO user) {
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
