package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.constants.APIConstants;
import com.paa.dms.user.manage.data.model.*;
import com.paa.dms.user.manage.data.repository.UsersContactInfoRepository;
import com.paa.dms.user.manage.data.repository.UsersNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;
import java.util.function.Supplier;

import java.util.Optional;
@Slf4j
@Service
public class UserManageDataService {

    @Autowired
    private UsersContactInfoRepository usersContactInfoRepository;

    @Autowired
    private UsersNameRepository usersNameRepository;

    ///////////////////////////
    //  Create User Service  //
    ///////////////////////////
    public ResponseEntity<String> saveUser(HttpHeaders httpHeaders, RequestNewUserEntity userRequest) {

        log.debug("REQUEST >>> " + userRequest.toString());
        String uid = httpHeaders.getFirst("uid").toString();

        MongoUsersContactInfoEntity userInfo = new MongoUsersContactInfoEntity();
        userInfo.setUid(uid);
        userInfo.setEmail(userRequest.getEmail());
        userInfo.setPhone(userRequest.getPhone());
        userInfo.setAddress1(userRequest.getAddress1());
        userInfo.setAddress2(userRequest.getAddress2());
        userInfo.setCity(userRequest.getCity());
        userInfo.setState(userRequest.getState());
        userInfo.setZipCode(userRequest.getZipCode());
        MongoUsersNameEntity userName = new MongoUsersNameEntity();
        userName.setUid(uid);
        userName.setFirstName(userRequest.getName());
        userName.setMiddleName(userRequest.getMiddleName());
        userName.setLastName(userRequest.getLastName());

        usersContactInfoRepository.save(userInfo);
        usersNameRepository.save(userName);
        ResponseEntity response = ResponseEntity.ok("User Created");
        log.debug(APIConstants.RESPONSE_STRING_HTTP_EMPTY + response.getStatusCode().toString());
        return response;
    }


    ///////////////////////////
    //   Read Data Service   //
    ///////////////////////////
    public ResponseEntity<ResponseUserDataEntity> findUserData(HttpHeaders httpHeaders) {

        String uid = httpHeaders.getFirst("uid").toString();
        log.debug("REQUEST >>> " + uid + " data requested");
        var userInfo = findUsersInfoByUid(uid);;
        var userName = findUserNameByUid(uid);

        if (userInfo.isPresent() && userName.isPresent()) {
            ResponseUserDataEntity response = new ResponseUserDataEntity();
            response.setEmail(userInfo.get().getEmail());
            response.setPhone(userInfo.get().getPhone());
            response.setAddress1(userInfo.get().getAddress1());
            response.setAddress2(userInfo.get().getAddress2());
            response.setTown(userInfo.get().getCity());
            response.setState(userInfo.get().getState());
            response.setZipCode(userInfo.get().getZipCode());
            response.setName(userName.get().getFirstName());
            response.setMiddleName(userName.get().getMiddleName());
            response.setLastName(userName.get().getLastName());
            log.debug("RESPONSE >>> " + response.toString());
            return ResponseEntity.ok(response);
        }
        log.debug(APIConstants.RESPONSE_STRING_HTTP404);
        return ResponseEntity.notFound().build();
    }

    public Optional<MongoUsersContactInfoEntity> findUsersInfoByUid(String uid) {
        return usersContactInfoRepository.findUserInfoByUid(uid);
    }

    public Optional<MongoUsersNameEntity> findUserNameByUid(String uid) {
        return usersNameRepository.findUserNameByUid(uid);
    }


    ///////////////////////////
    //  Update Data Service  //
    ///////////////////////////
    public ResponseEntity<String> updateUser(RequestNewUserEntity userRequest,HttpHeaders httpHeaders) {
        log.debug("REQUEST >>> " + userRequest.toString());
        String uid = httpHeaders.getFirst("uid").toString();
        //Checks which DB should be called according to request,then if needed updates data.
        if (!userRequest.getEmail().isEmpty()
                || !userRequest.getPhone().isEmpty()
                || !userRequest.getAddress1().isEmpty()
                || !userRequest.getAddress2().isEmpty()
                || !userRequest.getCity().isEmpty()
                || !userRequest.getState().isEmpty()
                || !userRequest.getZipCode().isEmpty())
            {
                Optional<MongoUsersContactInfoEntity> existingUserInfo = usersContactInfoRepository.findUserInfoByUid(uid);
                if (existingUserInfo.isPresent()) {
                    MongoUsersContactInfoEntity foundUserInfo = existingUserInfo.get();
                    updateFieldIfNotEmpty(userRequest.getEmail(), foundUserInfo::setEmail, foundUserInfo::getEmail);
                    updateFieldIfNotEmpty(userRequest.getPhone(), foundUserInfo::setPhone, foundUserInfo::getPhone);
                    updateFieldIfNotEmpty(userRequest.getAddress1(), foundUserInfo::setAddress1, foundUserInfo::getAddress1);
                    updateFieldIfNotEmpty(userRequest.getAddress2(), foundUserInfo::setAddress2, foundUserInfo::getAddress2);
                    updateFieldIfNotEmpty(userRequest.getCity(), foundUserInfo::setCity, foundUserInfo::getCity);
                    updateFieldIfNotEmpty(userRequest.getState(), foundUserInfo::setState, foundUserInfo::getState);
                    updateFieldIfNotEmpty(userRequest.getZipCode(), foundUserInfo::setZipCode, foundUserInfo::getZipCode);
                    usersContactInfoRepository.save(foundUserInfo);
                    } else {
                        log.debug(APIConstants.RESPONSE_STRING_HTTP404);
                        return ResponseEntity.notFound().build();
                    }
            } else {
                log.debug("No contact data updated");
            }

        if (!userRequest.getName().isEmpty()
                || !userRequest.getMiddleName().isEmpty()
                || !userRequest.getLastName().isEmpty())
            {
                Optional<MongoUsersNameEntity> existingUserName = usersNameRepository.findUserNameByUid(uid);
                if (existingUserName.isPresent()) {
                    MongoUsersNameEntity foundUserName = existingUserName.get();
                    updateFieldIfNotEmpty(userRequest.getName(), foundUserName::setFirstName, foundUserName::getFirstName);
                    updateFieldIfNotEmpty(userRequest.getMiddleName(), foundUserName::setMiddleName, foundUserName::getMiddleName);
                    updateFieldIfNotEmpty(userRequest.getLastName(), foundUserName::setLastName, foundUserName::getLastName);
                    usersNameRepository.save(foundUserName);
                    } else {
                        log.debug(APIConstants.RESPONSE_STRING_HTTP404);
                        return ResponseEntity.notFound().build();
                    }
            } else {
                log.debug("No name data updated");
            }

        ResponseEntity response = ResponseEntity.ok("User Updated");
        log.debug(APIConstants.RESPONSE_STRING_HTTP_EMPTY + response.getStatusCode().toString());
        return response;
    }

    private void updateFieldIfNotEmpty(String newValue, Consumer<String> setter, Supplier<String> getter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }

    ///////////////////////////
    //  Delete User Service  //
    ///////////////////////////
    public ResponseEntity<String> deleteUser(RequestDeleteUserEntity userRequest, HttpHeaders httpHeaders) {

        String uid = httpHeaders.getFirst("uid");
        log.debug("REQUEST >>> " + uid + " delete data requested with this email: " + userRequest.getEmail().toString());
        MongoUsersContactInfoEntity storedUserData = findUsersInfoByUid(uid).get();
        MongoUsersNameEntity storedUserName = findUserNameByUid(uid).get();
        if (userRequest.getEmail().equals(storedUserData.getEmail())) {
                usersContactInfoRepository.deleteById(storedUserData.get_id().toString());
                usersNameRepository.deleteById(storedUserName.get_id().toString());
            ResponseEntity response = ResponseEntity.ok("User deleted successfully");
            log.debug(APIConstants.RESPONSE_STRING_HTTP_EMPTY + response.getStatusCode().toString());
            return response;
            }else{
                ResponseEntity response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
                log.debug(APIConstants.RESPONSE_STRING_HTTP403);
                return response;
            }
    }
}
