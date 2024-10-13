package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.model.RequestDeleteUserEntity;
import com.paa.dms.user.manage.data.model.RequestNewUserEntity;
import com.paa.dms.user.manage.data.model.RequestUpdateUserEntity;
import com.paa.dms.user.manage.data.model.ResponseUserDataEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface UserManageDataService {
    ResponseEntity<String> saveUser(RequestNewUserEntity userRequest, HttpHeaders httpHeaders);
    ResponseEntity<ResponseUserDataEntity> findUserData(HttpHeaders httpHeaders);
    ResponseEntity<String> updateUser(RequestUpdateUserEntity userRequest, HttpHeaders httpHeaders);
    ResponseEntity<String> deleteUser(RequestDeleteUserEntity userRequest, HttpHeaders httpHeaders);
}
