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
import java.time.LocalDateTime;

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

}
