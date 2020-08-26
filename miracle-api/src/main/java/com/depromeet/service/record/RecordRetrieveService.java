package com.depromeet.service.record;

import com.depromeet.domain.record.Record;
import com.depromeet.domain.record.RecordRepository;
import com.depromeet.service.record.dto.request.DayRecordsRequest;
import com.depromeet.service.record.dto.request.MonthRecordsRequest;
import com.depromeet.service.record.dto.response.RecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordRetrieveService {

    private final RecordRepository recordRepository;

    @Transactional(readOnly = true)
    public List<RecordResponse> retrieveMonthRecords(MonthRecordsRequest request, Long memberId) {
        List<Record> records = recordRepository.findRecordBetween(memberId, request.convertFirstDayOfTheMonth(), request.convertEndDayOfTheMonth());
        return records.stream()
            .map(RecordResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecordResponse> retrieveDayRecords(DayRecordsRequest request, Long memberId) {
        List<Record> records = recordRepository.findRecordBetween(memberId, request.convertMinLocalDateTime(), request.convertMaxLocalDateTime());
        return records.stream()
            .map(RecordResponse::of)
            .collect(Collectors.toList());
    }

}
