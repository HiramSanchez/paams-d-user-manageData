package com.paa.dms.user.manage.data.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RequestDeleteUserEntity {

    @Email(message = "should be valid")
    @NotBlank(message = "is mandatory")
    private String email;
}
