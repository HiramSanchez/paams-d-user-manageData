package com.paa.dms.user.manage.data.model;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = MongoConstants.ACCESS_COLLECTION)
public class MongoAccessEntity {
    /** Unique identifier for the tokens document in MongoDB. */
    private ObjectId _id;
    /** The unique identifier (UID) of the user. */
    private String uid;
    /** The user's username */
    @NotBlank(message = " is mandatory")
    private String tokenN;
    /** The user's passcode */
    @NotBlank(message = " is mandatory")
    private String tokenP;
}
