package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface UserManageDataService {
    ResponseEntity<String> saveUser(RequestNewUserEntity userRequest, HttpHeaders httpHeaders);
    ResponseEntity<ResponseUserDataEntity> findUserData(HttpHeaders httpHeaders);
    ResponseEntity<String> updateUser(RequestUpdateUserEntity userRequest, HttpHeaders httpHeaders);
    ResponseEntity<String> deleteUser(RequestDeleteUserEntity userRequest, HttpHeaders httpHeaders);
    ResponseEntity<ResponseUserAccess> findUserTokens(RequestLogInCheck userRequest);
}
