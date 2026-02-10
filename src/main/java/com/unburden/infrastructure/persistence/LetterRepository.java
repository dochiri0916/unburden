package com.unburden.infrastructure.persistence;

import com.unburden.domain.letter.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {

}