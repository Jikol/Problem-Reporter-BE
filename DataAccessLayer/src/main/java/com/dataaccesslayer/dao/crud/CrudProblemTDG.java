package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.dao.IUnitOfWork;
import com.dataaccesslayer.entity.ProblemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrudProblemTDG implements IUnitOfWork {
    private final Map<String, List<ProblemEntity>> context = new HashMap<>();
    private final Database db = Database.getDatabase();


    @Override
    public void RegisterNew(final Object entity) {

    }

    @Override
    public void RegisterModified(final Object entity) {

    }

    @Override
    public void RegisterDeleted(final Object entity) {

    }

    @Override
    public int Commit() throws Exception {
        return 0;
    }

    private void Register(final ProblemEntity problemEntity, final String operation) {
        List entitiesToPersistence = context.get(operation);
        if (entitiesToPersistence == null) {
            entitiesToPersistence = new ArrayList();
        }
        entitiesToPersistence.add(problemEntity);
        context.put(operation, entitiesToPersistence);
    }
}
