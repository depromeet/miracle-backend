package com.depromeet.service.record.dto.response;

import com.depromeet.domain.common.Category;
import com.depromeet.domain.record.Record;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@RequiredArgsConstructor
public class RecordResponse {

    private final Long id;

    private final Long memberId;

    private final Long scheduleId;

    private final LocalDateTime startDateTime;

    private final LocalDateTime endDateTime;

    private final Category category;

    private final String question;

    private final String answer;

    public static RecordResponse of(Record record) {
        return new RecordResponse(record.getId(), record.getMemberId(), record.getScheduleId(), record.getStartDateTime(),
            record.getEndDateTime(), record.getCategory(), record.getQuestion(), record.getAnswer());
    }

}
