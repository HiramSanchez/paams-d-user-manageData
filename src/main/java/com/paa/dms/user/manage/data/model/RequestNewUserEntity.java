package com.paa.dms.user.manage.data.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestNewUserEntity {
    /** The user's first name */
    @NotBlank(message = " is mandatory")
    private String name;
    /** The user's (Optional) middle name */
    private String middleName;
    /** The user's last name */
    @NotBlank(message = " is mandatory")
    private String lastName;
    /** The user's phone number. */
    @NotBlank(message = " is mandatory")
    private String phone;
    /** The user's email address. */
    @Email(message = " should be valid")
    @NotBlank(message = " is mandatory")
    private String email;
    /** The primary address line of the user. */
    @NotBlank(message = " is mandatory")
    private String address1;
    /** The secondary address line of the user */
    private String address2;
    /** The city where the user resides */
    @NotBlank(message = " is mandatory")
    private String city;
    /** The state where the user resides */
    @NotBlank(message = " is mandatory")
    private String state;
    /** The postal code for the user's address */
    @NotBlank(message = " is mandatory")
    private String zipCode;
}
