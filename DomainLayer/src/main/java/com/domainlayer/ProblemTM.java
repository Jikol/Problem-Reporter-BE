package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudUserTDG;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.problem.NewProblemDTO;
import com.domainlayer.dto.user.RegisterUserDTO;
import com.domainlayer.module.JwtToken;

import java.util.HashMap;
import java.util.Map;

public class ProblemTM {

    public Map<Object, Object> CreateNewProblem(final NewProblemDTO newProblemDTO) {
        if (newProblemDTO.getTitle() == null || newProblemDTO.getSummary() == null ||
            newProblemDTO.getActualBehavior() == null || newProblemDTO.getExpectedBehavior() == null) {
            return Map.of(
                    "status", 400,
                    "error", "One of the including attributes is not provided (title, summary, actualBehavior, expectedBehavior)"
            );
        }
        if (newProblemDTO.getRegisterUserDTO() != null) {
            Map message = new UserTM().RegisterUser(new RegisterUserDTO(
                    newProblemDTO.getRegisterUserDTO().getEmail(),
                    newProblemDTO.getRegisterUserDTO().getPasswd(),
                    newProblemDTO.getRegisterUserDTO().getName(),
                    newProblemDTO.getRegisterUserDTO().getSurname()
            ));
            if ((int) message.get("status") != 201) {
                return message;
            }
        }
        String email = null;
        try {
//            Map sub = (Map) JwtToken.DecodeToken(newProblemDTO.getNewProblemUserDTO().getToken()).get("payload");
//            email = (String) sub.get("sub");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(email);
        return null;
    }
}
