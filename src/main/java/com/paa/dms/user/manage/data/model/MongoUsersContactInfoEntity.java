package com.paa.dms.user.manage.data.model;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = MongoConstants.USER_INFO_COLLECTION)
public class MongoUsersContactInfoEntity {
    private ObjectId _id;
    private String uid;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
}
