package com.joelhelkala.watcherServer.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ConfirmationToken c WHERE c.appUser.id = :id")
    int deleteByUserId(@Param("id") Long id);

    @Query("SELECT c " +
            "FROM ConfirmationToken c " +
            "WHERE c.appUser.id = ?1")
    Optional<ConfirmationToken> existsByUserId(Long id);
}
