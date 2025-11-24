package com.paa.dms.user.manage.data.constants;

public class MongoConstants {
    //Collections
    public static final String USER_INFO_COLLECTION = "users_contact_info";
    public static final String USER_NAME_COLLECTION = "users_name";
    public static final String ACCESS_COLLECTION = "access";

    //Querys
    public static final String FIND_BY_UID_QUERY = "{uid:'?0'}";
    public static final String FIND_BY_TOKENS = "{ 'tokenN': ?0, 'tokenP': ?1 }";
}
