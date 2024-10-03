package com.paa.dms.user.manage.data.model;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = MongoConstants.USER_INFO_COLLECTION)
/**
 * Entity representing a user contact info stored in MongoDB.
 * This class maps to the user contact info collection in the database.
 */
public class MongoUsersContactInfoEntity {
    /** Unique identifier for the order document in MongoDB. */
    private ObjectId _id;
    /** The unique identifier (UID) of the user who placed the order. */
    private String uid;
    /** The user's email address. */
    private String email;
    /** The user's phone number. */
    private String phone;
    /** The primary address line of the user. */
    private String address1;
    /** The secondary address line of the user */
    private String address2;
    /** The city where the user resides */
    private String city;
    /** The state where the user resides */
    private String state;
    /** The postal code for the user's address */
    private String zipCode;
}
