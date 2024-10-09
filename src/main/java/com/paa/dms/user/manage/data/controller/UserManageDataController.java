package com.paa.dms.user.manage.data.controller;

import com.paa.dms.user.manage.data.constants.APIConstants;
import com.paa.dms.user.manage.data.model.*;
import com.paa.dms.user.manage.data.service.UserManageDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping(APIConstants.BASE_PATH)
public class UserManageDataController {

    @Autowired
    private UserManageDataService userManageDataService;
    @Autowired
    private APIConstants apiConstants;

    /**
     * Endpoint for creating a new user.
     *
     * @param httpHeaders Headers containing user information (uid)
     * @param userRequest Request body containing order details
     * @return ResponseEntity with status 200 OK when the user is successfully created
     */
    @Operation( //swagger config
            summary = "Create a new user",
            description = "Creates a new user based on the provided details.",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,name = "uid", description = "Header containing user id, string of 9 digit number", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details for the new user", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User created"),
                    @ApiResponse(responseCode = "400", description = "{field} + {validation error details}"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
            }
    )
    @PostMapping(path = APIConstants.CREATE_NEW_USER_ENDPOINT)
    public ResponseEntity<String> createUser(@Valid @RequestBody RequestNewUserEntity userRequest, @RequestHeader HttpHeaders httpHeaders) {
        log.debug(apiConstants.getLOG_NEW_USER_ENDPOINT());
        return userManageDataService.saveUser(userRequest,httpHeaders);
    }

    /**
     * Endpoint for retrieving users data.
     *
     * @param httpHeaders Headers containing user information (uid)
     * @return ResponseEntity with requested user's data
     */
    @Operation( //swagger config
            summary = "Retrieve user data",
            description = "Fetches user data using the provided headers containing user identification.",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,name = "uid", description = "Header containing user id, string of 9 digit number", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User data retrieve"),
                    @ApiResponse(responseCode = "404", description = "Resource not found in DB"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
            }
    )
    @GetMapping(path = APIConstants.READ_USER_DATA_ENDPOINT)
    public ResponseEntity<ResponseUserDataEntity> getUser(@RequestHeader HttpHeaders httpHeaders) {
        log.debug(apiConstants.getLOG_READ_USER_ENDPOINT());
        return userManageDataService.findUserData(httpHeaders);
    }

    /**
     * Endpoint for updating data to a specific user.
     *
     * @param userRequest Request body containing the data to update
     * @param httpHeaders Headers containing user information (uid)
     * @return ResponseEntity with status 200 OK upon successful update
     */
    @Operation( //swagger config
            summary = "Update user data",
            description = "Updates user data based on the provided information.",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,name = "uid", description = "Header containing user id, string of 9 digit number", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to update the user", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated"),
                    @ApiResponse(responseCode = "400", description = "{field} + {validation error details}"),
                    @ApiResponse(responseCode = "404", description = "Resource not found in DB"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
            }
    )
    @PutMapping(path = APIConstants.UPDATE_USER_DATA_ENDPOINT)
    public ResponseEntity<String> updateUser(@Valid @RequestBody RequestUpdateUserEntity userRequest, @RequestHeader HttpHeaders httpHeaders) {
        log.debug(apiConstants.getLOG_UPDATE_USER_ENDPOINT());
        return userManageDataService.updateUser(userRequest,httpHeaders);
    }

    /**
     * Endpoint for deleting a specific user permanently.
     *
     * @param userRequest Request body containing the email fo the user to delete
     * @param httpHeaders Headers containing user information (uid)
     * @return ResponseEntity with status 200 OK upon successful deletion
     */
    @Operation( //swagger config
            summary = "Delete user",
            description = "Permanently deletes a user based on the uid provided at headers, email for validation.",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,name = "uid", description = "Header containing user id, string of 9 digit number", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Email of the user to delete", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "{field} + {validation error details}"),
                    @ApiResponse(responseCode = "403", description = "Invalid Request due to data validation"),
                    @ApiResponse(responseCode = "404", description = "Resource not found in DB"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred")
            }
    )
    @DeleteMapping(path = APIConstants.DELETE_USER_DATA_ENDPOINT)
    public ResponseEntity<String> deleteUser(@Valid @RequestBody RequestDeleteUserEntity userRequest, @RequestHeader HttpHeaders httpHeaders) {
        log.debug(apiConstants.getLOG_DELETE_USER_ENDPOINT());
        return userManageDataService.deleteUser(userRequest,httpHeaders);
    }

}
