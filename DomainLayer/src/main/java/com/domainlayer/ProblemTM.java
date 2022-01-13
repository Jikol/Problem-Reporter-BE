package com.domainlayer;

import com.dataaccesslayer.dao.crud.CrudAttachmentTDG;
import com.dataaccesslayer.dao.crud.CrudDeploymentTDG;
import com.dataaccesslayer.dao.crud.CrudProblemTDG;
import com.dataaccesslayer.entity.AttachmentEntity;
import com.dataaccesslayer.entity.DeploymentEntity;
import com.dataaccesslayer.entity.ProblemEntity;
import com.dataaccesslayer.entity.UserEntity;
import com.domainlayer.dto.problem.NewProblemDTO;
import com.domainlayer.dto.user.RegisterUserDTO;
import io.jsonwebtoken.ExpiredJwtException;

import java.util.List;
import java.util.Map;

public class ProblemTM {
    private int problemReported = 0;
    private int attachmentCreated = 0;

    public int getProblemReported() {
        return problemReported;
    }
    public int getAttachmentCreated() {
        return attachmentCreated;
    }

    public Map<Object, Object> CreateNewProblem(final NewProblemDTO newProblemDTO) {
        if (newProblemDTO.getTitle() == null || newProblemDTO.getSummary() == null ||
            newProblemDTO.getActualBehavior() == null || newProblemDTO.getExpectedBehavior() == null ||
            newProblemDTO.getDeploymentDomain() == null) {
            return Map.of(
                    "status", 400,
                    "error", "One of the including attributes is not provided (title, summary, actualBehavior, expectedBehavior)"
            );
        }
        DeploymentEntity deploymentEntity = null;
        try {
            deploymentEntity = new CrudDeploymentTDG().SelectByDomain(newProblemDTO.getDeploymentDomain());
        } catch (NullPointerException ex) {
            return Map.of(
                    "status", 404,
                    "error", "Deployment does not exist for provided domain"
            );
        }
        UserEntity userEntity = null;
        Map registerUserCallback = null;
        if (newProblemDTO.getRegisterUserDTO() != null) {
            UserTM userTM = new UserTM();
            registerUserCallback = userTM.RegisterUser(new RegisterUserDTO(
                    newProblemDTO.getRegisterUserDTO().getEmail(),
                    newProblemDTO.getRegisterUserDTO().getPasswd(),
                    newProblemDTO.getRegisterUserDTO().getName(),
                    newProblemDTO.getRegisterUserDTO().getSurname()),
                    false
            );
            if ((int) registerUserCallback.get("status") != 201) {
                return registerUserCallback;
            }
            userEntity = new UserEntity(userTM.getUserRegisteredIds().get(userTM.getUserRegisteredIds().size() - 1));
        }
        boolean commitProblem = true;
        if (newProblemDTO.getAttachments() != null) {
            commitProblem = false;
        }
        CrudProblemTDG crudProblemTDG = new CrudProblemTDG();
        crudProblemTDG.RegisterNew(new ProblemEntity(
                newProblemDTO.getTitle(),
                newProblemDTO.getSummary(),
                newProblemDTO.getConfiguration(),
                newProblemDTO.getExpectedBehavior(),
                newProblemDTO.getActualBehavior(),
                userEntity,
                deploymentEntity
        ));
        try {
            problemReported += crudProblemTDG.Commit(commitProblem);
        } catch (Exception ex) {
            return Map.of(
                    "status", 409,
                    "error", ex.getLocalizedMessage()
            );
        }
        if (!commitProblem) {
            List attachments = newProblemDTO.getAttachments();
            CrudAttachmentTDG crudAttachmentTDG = new CrudAttachmentTDG();
            ProblemEntity problemEntity = new ProblemEntity(
                    crudProblemTDG.getInsertedIds().get(crudProblemTDG.getInsertedIds().size() - 1)
            );
            attachments.forEach(attachment -> {
                crudAttachmentTDG.RegisterNew(new AttachmentEntity((String) attachment, problemEntity));
            });
            try {
                attachmentCreated += crudAttachmentTDG.Commit(true);
            } catch (Exception ex) {
                return Map.of(
                        "status", 409,
                        "error", ex.getLocalizedMessage()
                );
            }
        }
        return Map.of(
                "status", 201,
                "created", problemReported + attachmentCreated,
                "token", registerUserCallback.get("token")
        );
    }
}
