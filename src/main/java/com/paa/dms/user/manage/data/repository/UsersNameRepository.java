package com.paa.dms.user.manage.data.repository;

import com.paa.dms.user.manage.data.constants.MongoConstants;
import com.paa.dms.user.manage.data.model.MongoUsersNameEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
/**
 * Repository interface for managing users name info.
 * Extends MongoRepository to leverage Spring Data MongoDB functionalities.
 */
public interface UsersNameRepository extends MongoRepository <MongoUsersNameEntity, String> {
    /**
     * Custom query to find a specific order by its order ID.
     *
     * @param uid The unique identifier of the user.
     * @return An Optional containing the found user, or empty if not found.
     */
    @Query(value = MongoConstants.FIND_BY_UID_QUERY)
    Optional<MongoUsersNameEntity> findUserNameByUid(String uid);
}
