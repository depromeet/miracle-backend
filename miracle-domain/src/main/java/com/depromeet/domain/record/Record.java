package com.depromeet.domain.record;

import com.depromeet.domain.BaseTimeEntity;
import com.depromeet.domain.common.Category;
import com.depromeet.domain.common.DateTimeInterval;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    indexes = @Index(name = "idx_record_1", columnList = "memberId,scheduleId")
)
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long scheduleId;

    @Embedded
    private DateTimeInterval dateTimeInterval;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false, length = 100)
    private String question;

    @Column(nullable = false, length = 100)
    private String answer;

    @Builder
    public Record(Long memberId, Long scheduleId, LocalDateTime startDateTime, LocalDateTime endDateTime, Category category, String question, String answer) {
        this.memberId = memberId;
        this.scheduleId = scheduleId;
        this.dateTimeInterval = DateTimeInterval.of(startDateTime, endDateTime);
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public static Record newInstance(Long memberId, Long scheduleId, Category category, LocalDateTime startDateTime, LocalDateTime endDateTime, String question, String answer) {
        return Record.builder()
            .memberId(memberId)
            .scheduleId(scheduleId)
            .category(category)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .question(question)
            .answer(answer)
            .build();
    }

    public LocalDateTime getStartDateTime() {
        return dateTimeInterval.getStartDateTime();
    }

    public LocalDateTime getEndDateTime() {
        return dateTimeInterval.getEndDateTime();
    }

}
