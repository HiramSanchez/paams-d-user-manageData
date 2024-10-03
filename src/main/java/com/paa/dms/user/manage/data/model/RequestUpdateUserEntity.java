package com.paa.dms.user.manage.data.model;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RequestUpdateUserEntity {
    private String name;
    private String middleName;
    private String lastName;
    private String phone;
    @Email(message = "should be valid")
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
}
