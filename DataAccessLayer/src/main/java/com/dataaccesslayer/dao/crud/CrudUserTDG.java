package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.dao.IUnitOfWork;
import com.dataaccesslayer.entity.UserEntity;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrudUserTDG implements IUnitOfWork {
    private final Map<String, List<UserEntity>> context = null;
    private final Database db = Database.getDatabase();

    @Override
    public void registerNew(Object entity) {
        register((UserEntity) entity, IUnitOfWork.INSERT);
    }

    @Override
    public void registerModified(Object entity) {
        register((UserEntity) entity, IUnitOfWork.MODIFY);
    }

    @Override
    public void registerDeleted(Object entity) {
        register((UserEntity) entity, IUnitOfWork.DELETE);
    }

    private void register(UserEntity userEntity, String operation) {
        List entitiesToPersistence = context.get(operation);
        if (entitiesToPersistence == null) {
            entitiesToPersistence = new ArrayList();
        }
        entitiesToPersistence.add(userEntity);
        context.put(operation, entitiesToPersistence);
    }

    @Override
    public boolean commit() {
        if (context == null || context.size() == 0) {
            return false;
        }
        try {
            db.BeginTransaction();
            if (context.containsKey(IUnitOfWork.INSERT)) {
                commitInsert();
            }
            db.EndTransaction();
        } catch (Exception ex) {
            System.out.println("Error " + ex.getCause());
            db.Rollback();
            return false;
        }
        return true;
    }

    private void commitInsert() {
        List objectsToBePersisted = context.get(IUnitOfWork.INSERT);
        for (Object userEntity : objectsToBePersisted) {
            Insert((UserEntity) userEntity);
        }
    }

    private void Insert(UserEntity userEntity) {
        db.ExecuteQuery()
    }

    public List<UserEntity> Select() {
        List selectedUsers = new ArrayList();
        try {
            db.BeginTransaction();
            //selectedUsers = db.getSession().createQuery("FROM UserEntity").list();
            db.EndTransaction();
        } catch (Exception ex) {
            System.out.println("Error " + ex.getCause());
            db.Rollback();
            return null;
        }
        return selectedUsers;
    }
}
