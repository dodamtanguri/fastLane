package com.project.fastLane.contents.repository;

import com.project.fastLane.commons.enmuns.Status;
import com.project.fastLane.contents.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.status = :status")
    Optional<UserEntity> findByEmailAndStatus(String email, Status status);

}
