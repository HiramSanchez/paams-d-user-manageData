package com.paa.dms.user.manage.data.controller;

import com.paa.dms.user.manage.data.constants.APIConstants;
import com.paa.dms.user.manage.data.model.*;
import com.paa.dms.user.manage.data.service.UserManageDataService;
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
    @DeleteMapping(path = APIConstants.DELETE_USER_DATA_ENDPOINT)
    public ResponseEntity<String> deleteUser(@Valid @RequestBody RequestDeleteUserEntity userRequest, @RequestHeader HttpHeaders httpHeaders) {
        log.debug(apiConstants.getLOG_DELETE_USER_ENDPOINT());
        return userManageDataService.deleteUser(userRequest,httpHeaders);
    }

}
