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

    @PostMapping(path = APIConstants.CREATE_NEW_USER_ENDPOINT)
    public ResponseEntity<String> createUser(@Valid @RequestBody RequestNewUserEntity userRequest, @RequestHeader HttpHeaders httpHeaders) {
        log.debug(APIConstants.LOG_NEW_USER_ENDPOINT);
        return userManageDataService.saveUser(userRequest,httpHeaders);
    }

    @GetMapping(path = APIConstants.READ_USER_DATA_ENDPOINT)
    public ResponseEntity<ResponseUserDataEntity> getUser(@RequestHeader HttpHeaders httpHeaders) {
        log.debug(APIConstants.LOG_READ_USER_ENDPOINT);
        return userManageDataService.findUserData(httpHeaders);
    }

    @PutMapping(path = APIConstants.UPDATE_USER_DATA_ENDPOINT)
    public ResponseEntity<String> updateUser(@Valid @RequestBody RequestUpdateUserEntity userRequest, @RequestHeader HttpHeaders httpHeaders) {
        log.debug(APIConstants.LOG_UPDATE_USER_ENDPOINT);
        return userManageDataService.updateUser(userRequest,httpHeaders);
    }

    @DeleteMapping(path = APIConstants.DELETE_USER_DATA_ENDPOINT)
    public ResponseEntity<String> deleteUser(@Valid @RequestBody RequestDeleteUserEntity userRequest, @RequestHeader HttpHeaders httpHeaders) {
        log.debug(APIConstants.LOG_DELETE_USER_ENDPOINT);
        return userManageDataService.deleteUser(userRequest,httpHeaders);
    }

}
