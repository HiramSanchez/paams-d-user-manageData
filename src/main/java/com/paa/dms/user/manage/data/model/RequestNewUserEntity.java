package com.paa.dms.user.manage.data.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestNewUserEntity {
    @NotBlank(message = "is mandatory")
    private String name;
    private String middleName;
    @NotBlank(message = "is mandatory")
    private String lastName;
    @NotBlank(message = "is mandatory")
    private String phone;
    @Email(message = "should be valid")
    @NotBlank(message = "is mandatory")
    private String email;
    @NotBlank(message = "is mandatory")
    private String address1;
    private String address2;
    @NotBlank(message = "is mandatory")
    private String city;
    @NotBlank(message = "is mandatory")
    private String state;
    @NotBlank(message = "is mandatory")
    private String zipCode;
}
