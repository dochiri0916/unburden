package com.unburden.domain.journal;

import com.unburden.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "journals", uniqueConstraints =
    @UniqueConstraint(columnNames = {"user_id", "written_date"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Journal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate writtenDate;

    @Enumerated(EnumType.STRING)
    private JournalStatus status;

    public static Journal write(final Long userId, final String content) {
        Journal journal = new Journal();
        journal.userId = requireNonNull(userId);
        journal.content = requireNonNull(content);
        journal.status = JournalStatus.WRITTEN;
        return journal;
    }

}