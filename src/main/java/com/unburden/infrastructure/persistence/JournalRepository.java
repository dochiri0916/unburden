package com.unburden.infrastructure.persistence;

import com.unburden.domain.journal.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    boolean existsByUserIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanAndDeletedAtIsNull(Long userId, LocalDateTime start, LocalDateTime end);

    Optional<Journal> findByIdAndDeletedAtIsNull(Long id);

}