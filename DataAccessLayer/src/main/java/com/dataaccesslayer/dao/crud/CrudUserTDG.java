package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.entity.UserEntity;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class CrudUserTDG {
    private static Database db = Database.getDatabase();

    public boolean Insert(UserEntity userEntity) {
        try {
            db.BeginTransaction();
            // místo toho napsat normálně INSERT dotaz - implementace unit of work se udělá nad tímto
            // tato třída bude implementovat Unit of work interface
            //db.getSession().save(userEntity);
            db.EndTransaction();
        } catch (ConstraintViolationException ex) {
            System.out.println("Constraint violation raised: " + ex.getCause());
            db.Rollback();
            return false;
        }
        return true;
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
