package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.entity.UserEntity;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;

public class UserTDG {
    private static Database db = Database.getDatabase();

    public boolean Insert(UserEntity userEntity) {
        try {
            db.BeginTransaction();
            db.getSession().save(userEntity);
            db.EndTransaction();
        } catch (ConstraintViolationException ex) {
            db.Rollback();
            System.out.println("Constraint violation raised: " + ex.getCause());
            return false;
        }
        return true;
    }
}
