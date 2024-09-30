package com.paa.dms.user.manage.data.repository;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import com.paa.dms.user.manage.data.model.MongoUsersNameEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UsersNameRepository extends MongoRepository <MongoUsersNameEntity, String> {
    @Query(value = MongoConstants.FIND_BY_UID_QUERY)
    Optional<MongoUsersNameEntity> findUserNameByUid(String uid);
}
