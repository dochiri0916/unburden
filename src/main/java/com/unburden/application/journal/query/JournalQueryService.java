package com.unburden.application.journal.query;

import com.unburden.domain.journal.Journal;
import com.unburden.domain.journal.JournalNotFoundException;
import com.unburden.infrastructure.persistence.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JournalQueryService implements JournalLoader {

    private final JournalRepository journalRepository;

    @Override
    public Journal loadById(Long id) {
        return journalRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new JournalNotFoundException(id));
    }

}