package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.dao.mapper.DeploymentMapper;
import com.dataaccesslayer.entity.DeploymentEntity;

import java.util.AbstractMap;
import java.util.HashMap;

public class CrudDeploymentTDG {
    private final Database db = Database.getDatabase();

    public DeploymentEntity SelectByDomain(final String domainParam) throws NullPointerException {
        db.BeginConnection();
        String query = "SELECT * FROM data.deployment WHERE domain LIKE ?";
        var parameters = new HashMap<>();
        parameters.put(1, new AbstractMap.SimpleEntry(String.class, domainParam));
        DeploymentEntity deploymentEntity = new DeploymentMapper().mapResultSingle(db.ExecutePreparedSelect(query, parameters));
        db.EndConnection();
        return deploymentEntity;
    }
}
