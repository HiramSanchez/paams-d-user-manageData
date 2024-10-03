package com.paa.dms.user.manage.data.model;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = MongoConstants.USER_NAME_COLLECTION)
/**
 * Entity representing user's name info stored in MongoDB.
 * This class maps to user's name info collection in the database.
 */
public class MongoUsersNameEntity {
    /** Unique identifier for the order document in MongoDB. */
    private ObjectId _id;
    /** The unique identifier (UID) of the user who placed the order. */
    private String uid;
    /** The user's first name */
    private String firstName;
    /** The user's (Optional) middle name */
    private String middleName;
    /** The user's last name */
    private String lastName;
}
