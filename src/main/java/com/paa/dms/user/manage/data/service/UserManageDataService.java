package com.paa.dms.user.manage.data.service;

import com.paa.dms.user.manage.data.constants.APIConstants;
import com.paa.dms.user.manage.data.exception.custom.ForbiddenException;
import com.paa.dms.user.manage.data.exception.custom.NoDataFoundException;
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

    /**
     * Create User Service
     *
     * Saves a new user in the database using the provided user request data and HTTP headers.
     * @param userRequest the details of the new user
     * @param httpHeaders HTTP headers containing the user's unique identifier (UID)
     * @return ResponseEntity with a success message
     */    public ResponseEntity<String> saveUser(RequestNewUserEntity userRequest, HttpHeaders httpHeaders) {

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
        log.debug("User Created");
        log.debug(APIConstants.RESPONSE_STRING_HTTP_EMPTY + response.getStatusCode().toString());
        return response;
    }

    /**
     * Read Data Service
     *
     * Retrieves user data based on the provided UID from the HTTP headers.
     * @param httpHeaders HTTP headers containing the user's unique identifier (UID)
     * @return ResponseEntity containing the user's data
     */
    public ResponseEntity<ResponseUserDataEntity> findUserData(HttpHeaders httpHeaders) {

        String uid = httpHeaders.getFirst("uid").toString();
        log.debug("REQUEST >>> " + uid + " data requested");
        var userInfo = findUsersInfoByUid(uid).orElseThrow(() -> new NoDataFoundException());
        var userName = findUserNameByUid(uid).orElseThrow(() -> new NoDataFoundException());

            ResponseUserDataEntity response = new ResponseUserDataEntity();
            response.setEmail(userInfo.getEmail());
            response.setPhone(userInfo.getPhone());
            response.setAddress1(userInfo.getAddress1());
            response.setAddress2(userInfo.getAddress2());
            response.setCity(userInfo.getCity());
            response.setState(userInfo.getState());
            response.setZipCode(userInfo.getZipCode());
            response.setName(userName.getFirstName());
            response.setMiddleName(userName.getMiddleName());
            response.setLastName(userName.getLastName());
            log.debug("RESPONSE >>> " + response);
            return ResponseEntity.ok(response);
    }

    /**
     * Finds user contact information by UID.
     *
     * @param uid the unique identifier for the user
     * @return Optional containing the user's contact info entity
     */
    public Optional<MongoUsersContactInfoEntity> findUsersInfoByUid(String uid) {
        return usersContactInfoRepository.findUserInfoByUid(uid);
    }

    /**
     * Finds user name information by UID.
     *
     * @param uid the unique identifier for the user
     * @return Optional containing the user's name entity
     */
    public Optional<MongoUsersNameEntity> findUserNameByUid(String uid) {
        return usersNameRepository.findUserNameByUid(uid);
    }

    /**
     * Update Data Service
     *
     * Updates existing user data with the provided new user request data.
     * @param userRequest the new user data for the update
     * @param httpHeaders HTTP headers containing the user's unique identifier (UID)
     * @return ResponseEntity with a success message
     */
    public ResponseEntity<String> updateUser(RequestUpdateUserEntity userRequest,HttpHeaders httpHeaders) {
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
                MongoUsersContactInfoEntity existingUserInfo = usersContactInfoRepository.findUserInfoByUid(uid).orElseThrow(() -> new NoDataFoundException());
                MongoUsersContactInfoEntity foundUserInfo = existingUserInfo;
                updateFieldIfNotEmpty(userRequest.getEmail(), foundUserInfo::setEmail, foundUserInfo::getEmail);
                updateFieldIfNotEmpty(userRequest.getPhone(), foundUserInfo::setPhone, foundUserInfo::getPhone);
                updateFieldIfNotEmpty(userRequest.getAddress1(), foundUserInfo::setAddress1, foundUserInfo::getAddress1);
                updateFieldIfNotEmpty(userRequest.getAddress2(), foundUserInfo::setAddress2, foundUserInfo::getAddress2);
                updateFieldIfNotEmpty(userRequest.getCity(), foundUserInfo::setCity, foundUserInfo::getCity);
                updateFieldIfNotEmpty(userRequest.getState(), foundUserInfo::setState, foundUserInfo::getState);
                updateFieldIfNotEmpty(userRequest.getZipCode(), foundUserInfo::setZipCode, foundUserInfo::getZipCode);
                usersContactInfoRepository.save(foundUserInfo);
            } else {
                log.debug("No contact data updated");
            }
        // Checks if any name fields are provided for update
        if (!userRequest.getName().isEmpty()
                || !userRequest.getMiddleName().isEmpty()
                || !userRequest.getLastName().isEmpty())
            {   // Fetch existing user name info and update fields if necessary
                MongoUsersNameEntity existingUserName = usersNameRepository.findUserNameByUid(uid).orElseThrow(() -> new NoDataFoundException());

                    MongoUsersNameEntity foundUserName = existingUserName;
                    updateFieldIfNotEmpty(userRequest.getName(), foundUserName::setFirstName, foundUserName::getFirstName);
                    updateFieldIfNotEmpty(userRequest.getMiddleName(), foundUserName::setMiddleName, foundUserName::getMiddleName);
                    updateFieldIfNotEmpty(userRequest.getLastName(), foundUserName::setLastName, foundUserName::getLastName);
                    usersNameRepository.save(foundUserName);

            } else {
                log.debug("No name data updated");
            }

        ResponseEntity response = ResponseEntity.ok("User Updated");
        log.debug("User Updated");
        log.debug(APIConstants.RESPONSE_STRING_HTTP_EMPTY + response.getStatusCode().toString());
        return response;
    }

    /**
     * Updates a specific field of a user entity if the provided new value is not empty.
     *
     * @param newValue the new value for the field
     * @param setter function to set the new value
     * @param getter function to get the current value
     */
    private void updateFieldIfNotEmpty(String newValue, Consumer<String> setter, Supplier<String> getter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }

    /**
     * Delete User Service
     *
     * Deletes a user from the database based on the provided request and HTTP headers.
     * @param userRequest contains the email of the user to be deleted
     * @param httpHeaders HTTP headers containing the user's unique identifier (UID)
     * @return ResponseEntity with a success message
     */
    public ResponseEntity<String> deleteUser(RequestDeleteUserEntity userRequest, HttpHeaders httpHeaders) {

        String uid = httpHeaders.getFirst("uid");
        log.debug("REQUEST >>> " + uid + " delete data requested with this email: " + userRequest.getEmail().toString());
        // Fetch stored user data to validate deletion
        MongoUsersContactInfoEntity storedUserData = findUsersInfoByUid(uid).orElseThrow(() -> new NoDataFoundException());
        MongoUsersNameEntity storedUserName = findUserNameByUid(uid).orElseThrow(() -> new NoDataFoundException());
        // Validate email before deleting the user
        if (userRequest.getEmail().equals(storedUserData.getEmail())) {
                usersContactInfoRepository.deleteById(storedUserData.get_id().toString());
                usersNameRepository.deleteById(storedUserName.get_id().toString());
                ResponseEntity response = ResponseEntity.ok("User deleted successfully");
                log.debug("User deleted successfully");
                log.debug(APIConstants.RESPONSE_STRING_HTTP_EMPTY + response.getStatusCode().toString());
                return response;
            }else{
                throw new ForbiddenException();
            }
    }
}
