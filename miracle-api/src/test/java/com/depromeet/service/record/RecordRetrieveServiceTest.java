package com.depromeet.service.record;

import com.depromeet.domain.record.Record;
import com.depromeet.domain.record.RecordCreator;
import com.depromeet.domain.record.RecordRepository;
import com.depromeet.service.MemberSetup;
import com.depromeet.service.record.dto.request.DayRecordsRequest;
import com.depromeet.service.record.dto.request.MonthRecordsRequest;
import com.depromeet.service.record.dto.response.RecordResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecordRetrieveServiceTest extends MemberSetup {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private RecordRetrieveService recordRetrieveService;

    @AfterEach
    void cleanUp() {
        super.cleanup();
        recordRepository.deleteAll();
    }

    @Test
    void 한달동안의_레코드를_불러온다_앞쪽에_겹치는경우() {
        // given
        List<Record> records = Arrays.asList(
            RecordCreator.create(memberId, LocalDateTime.of(2020, 7, 31, 0, 0, 0), LocalDateTime.of(2020, 7, 31, 23, 59, 59)),
            RecordCreator.create(memberId, LocalDateTime.of(2020, 7, 31, 0, 0, 0), LocalDateTime.of(2020, 8, 1, 12, 0, 0)),
            RecordCreator.create(memberId, LocalDateTime.of(2020, 8, 1, 0, 0, 0), LocalDateTime.of(2020, 8, 1, 12, 0, 0))
        );
        recordRepository.saveAll(records);

        MonthRecordsRequest request = MonthRecordsRequest.testBuilder()
            .year(2020)
            .month(8)
            .build();

        // when
        List<RecordResponse> response = recordRetrieveService.retrieveMonthRecords(request, memberId);

        // then
        assertThat(response).hasSize(2);
        assertThat(response).extracting("startDateTime").containsExactly(
            LocalDateTime.of(2020, 7, 31, 0, 0, 0),
            LocalDateTime.of(2020, 8, 1, 0, 0, 0));
        assertThat(response).extracting("endDateTime").containsExactly(
            LocalDateTime.of(2020, 8, 1, 12, 0, 0),
            LocalDateTime.of(2020, 8, 1, 12, 0, 0));
    }

    @Test
    void 한달동안의_레코드를_불러온다_끝에_겹치는경우() {
        // given
        List<Record> records = Arrays.asList(
            RecordCreator.create(memberId, LocalDateTime.of(2020, 8, 30, 0, 0, 0), LocalDateTime.of(2020, 8, 30, 11, 59, 59)),
            RecordCreator.create(memberId, LocalDateTime.of(2020, 8, 31, 0, 0, 0), LocalDateTime.of(2020, 9, 1, 12, 0, 0)),
            RecordCreator.create(memberId, LocalDateTime.of(2020, 9, 1, 0, 0, 0), LocalDateTime.of(2020, 9, 1, 12, 0, 0))
        );
        recordRepository.saveAll(records);

        MonthRecordsRequest request = MonthRecordsRequest.testBuilder()
            .year(2020)
            .month(8)
            .build();

        // when
        List<RecordResponse> response = recordRetrieveService.retrieveMonthRecords(request, memberId);

        // then
        assertThat(response).hasSize(2);
        assertThat(response).extracting("startDateTime").containsExactly(
            LocalDateTime.of(2020, 8, 30, 0, 0, 0),
            LocalDateTime.of(2020, 8, 31, 0, 0, 0));
        assertThat(response).extracting("endDateTime").containsExactly(
            LocalDateTime.of(2020, 8, 30, 11, 59, 59),
            LocalDateTime.of(2020, 9, 1, 12, 0, 0));
    }

    @Test
    void 하루의_레코드를_불러오는기능_앞쪽에_겹치지_않는경우_포함되지_않는다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 14, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 15, 0, 0, 0);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    void 하루의_레코드를_불러오는기능_앞쪽에_겹치는_경우_포함된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 14, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 15, 1, 0, 0);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertRecordResponse(responses.get(0), startDateTime, endDateTime);
    }

    @Test
    void 하루의_레코드를_불러오는기능_뒷쪽에_겹치지_않는경우_포함되지_않는다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 16, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 16, 12, 0, 0);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    void 하루의_레코드를_불러오는기능_뒷쪽에_겹치는_경우_포함된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 15, 23, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 16, 1, 0, 0);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertRecordResponse(responses.get(0), startDateTime, endDateTime);
    }

    @Test
    void 하루의_레코드를_불러오는기_포함되서_겹치는경우_포함된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 15, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 15, 11, 59, 59);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertRecordResponse(responses.get(0), startDateTime, endDateTime);
    }

    private void assertRecordResponse(RecordResponse response, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        assertThat(response.getStartDateTime()).isEqualTo(startDateTime);
        assertThat(response.getEndDateTime()).isEqualTo(endDateTime);
    }

}
