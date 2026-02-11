package com.unburden.infrastructure.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface JournalKeywordRepository extends ElasticsearchRepository<JournalKeywordDocument, String> {

    List<JournalKeywordDocument> findByUserIdAndKeywordsIn(Long userId, List<String> keywords);

}
