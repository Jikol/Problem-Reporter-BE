package com.domainlayer;

import com.dataaccesslayer.dao.DataAccessClass;

public class DomainClass {
    public static String getName() {
        return "Data from DomainLayer";
    }

    public static String getNameFromDataAccessLayer() {
        return DataAccessClass.getName();
    }
}
