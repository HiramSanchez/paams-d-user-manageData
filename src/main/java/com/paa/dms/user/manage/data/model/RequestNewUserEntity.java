package com.paa.dms.user.manage.data.model;

import lombok.Data;

@Data
public class RequestNewUserEntity {
    private String name;
    private String middleName;
    private String lastName;
    private String phone;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
}
