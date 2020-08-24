package com.depromeet.domain.record;

import com.depromeet.domain.BaseTimeEntity;
import com.depromeet.domain.common.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long scheduleId;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String question;

    private String answer;

    @Builder
    public Record(Long memberId, Long scheduleId, LocalDateTime startDateTime, LocalDateTime endDateTime, Category category, String question, String answer) {
        this.memberId = memberId;
        this.scheduleId = scheduleId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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

    public static LocalDateTime makeStartDateTime(LocalDate date) {
        return LocalDateTime.of(date.minusDays(1), LocalTime.of(0, 0, 0));
    }

    public static LocalDateTime makeEndDateTime(LocalDate date) {
        return LocalDateTime.of(date.plusMonths(1).minusDays(1), LocalTime.of(23, 59, 59));
    }
}
