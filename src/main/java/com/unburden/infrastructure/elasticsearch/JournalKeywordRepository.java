package com.unburden.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface JournalKeywordRepository extends ElasticsearchRepository<JournalKeyword, String> {

    List<JournalKeyword> findByUserIdAndKeywordsIn(Long userId, List<String> keywords);

}
