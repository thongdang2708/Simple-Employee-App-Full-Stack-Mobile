package com.ltp.backend.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ltp.backend.entity.ConfirmationToken;

import jakarta.transaction.Transactional;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("Update ConfirmationToken c set c.confirmedAt = :date where c.token = :token")
    int updateConfirmedAtWithToken(@Param("date") LocalDateTime date, @Param("token") String token);
}
