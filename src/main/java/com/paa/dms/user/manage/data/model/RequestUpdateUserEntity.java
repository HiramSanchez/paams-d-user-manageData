package com.paa.dms.user.manage.data.model;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RequestUpdateUserEntity {
    /** The user's first name */
    private String name;
    /** The user's (Optional) middle name */
    private String middleName;
    /** The user's last name */
    private String lastName;
    /** The user's phone number. */
    private String phone;
    /** The user's email address. */
    @Email(message = "should be valid")
    private String email;
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
