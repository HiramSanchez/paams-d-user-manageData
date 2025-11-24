package com.paa.dms.user.manage.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestLogInCheck {
    /** The user's username */
    @NotBlank(message = " is mandatory")
    private String tokenN;
    /** The user's passcode */
    @NotBlank(message = " is mandatory")
    private String tokenP;
}
