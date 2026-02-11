package com.unburden.domain.letter;

import com.unburden.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "letters")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long journalId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private LetterStatus status;

    public static Letter create(final Long userId, final Long journalId, final String content) {
        Letter letter = new Letter();
        letter.userId = requireNonNull(userId);
        letter.journalId = requireNonNull(journalId);
        letter.content = requireNonNull(content);
        letter.status = LetterStatus.CREATED;
        return letter;
    }

    public void markSent() {
        this.status = LetterStatus.SENT;
    }

    public void markRead() {
        this.status = LetterStatus.READ;
    }

}