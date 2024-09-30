package com.paa.dms.user.manage.data.model;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = MongoConstants.USER_NAME_COLLECTION)
public class MongoUsersNameEntity {
    private ObjectId _id;
    private String uid;
    private String firstName;
    private String middleName;
    private String lastName;
}
