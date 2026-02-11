package com.unburden.application.journal.query;

import com.unburden.domain.journal.Journal;

public interface JournalLoader {

    Journal loadById(Long id);

}