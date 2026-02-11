package com.unburden.infrastructure.elasticsearch;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "journal_keywords", createIndex = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JournalKeyword {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Long)
    private Long journalId;

    @Field(type = FieldType.Keyword)
    private List<String> keywords;

    @Field(type = FieldType.Date, format = DateFormat.date)
    private LocalDate writtenDate;

    @Builder
    public JournalKeyword(Long userId, Long journalId, List<String> keywords, LocalDate writtenDate) {
        this.userId = userId;
        this.journalId = journalId;
        this.keywords = keywords;
        this.writtenDate = writtenDate;
    }

}
