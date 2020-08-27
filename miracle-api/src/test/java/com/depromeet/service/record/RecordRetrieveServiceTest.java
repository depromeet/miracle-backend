package com.depromeet.service.record;

import com.depromeet.domain.record.RecordCreator;
import com.depromeet.domain.record.RecordRepository;
import com.depromeet.service.MemberSetup;
import com.depromeet.service.record.dto.request.DayRecordsRequest;
import com.depromeet.service.record.dto.request.MonthRecordsRequest;
import com.depromeet.service.record.dto.response.RecordForCalendarResponse;
import com.depromeet.service.record.dto.response.RecordResponse;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Description("7월 31일 23:50 ~ 8월 1일 00:10의 레코드 기록이 있을때, 8월의 레코드 정보를 불러오면 포함되어야 한다.")
    @Test
    void 달의_레코드_조회_테스트_레코드가_앞쪽_경계에_걸쳐있는경우_포함되서_조회된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 7, 31, 23, 50);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 1, 0, 10);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        MonthRecordsRequest request = MonthRecordsRequest.testBuilder()
            .year(2020)
            .month(8)
            .build();

        // when
        RecordForCalendarResponse response = recordRetrieveService.retrieveMonthRecords(request, memberId);

        // then
        assertThat(response.getDateList()).hasSize(1);
      //  assertRecordResponse(response.getDateList().get(0), startDateTime, endDateTime);
    }

    @Description("7월 31일 23:50 ~ 8월 1일 00:00 의 레코드 기록이 있을때, 8월의 레코드 정보를 불러오면 포함되지 않아야 한다.")
    @Test
    void 달의_레코드_조회_테스트_레코드가_앞쪽_경계에_겹치지_않는경우_포함되지_않는다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 7, 31, 23, 50);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 1, 0, 0);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        MonthRecordsRequest request = MonthRecordsRequest.testBuilder()
            .year(2020)
            .month(8)
            .build();

        // when
      //  List<RecordResponse> response = recordRetrieveService.retrieveMonthRecords(request, memberId);

        // then
   //     assertThat(response).isEmpty();
    }

    @Description("8월 31일 23:50 ~ 9월 1일 00:10 의 레코드 기록이 있을때, 8월의 레코드 정보를 불러오면 포함되어야 한다.")
    @Test
    void 달의_레코드_조회_테스트_레코드가_뒷쪽_경계에_걸쳐있는경우_포함되서_조회된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 31, 23, 50);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 9, 1, 0, 10);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        MonthRecordsRequest request = MonthRecordsRequest.testBuilder()
            .year(2020)
            .month(8)
            .build();

        // when
     //   List<RecordResponse> response = recordRetrieveService.retrieveMonthRecords(request, memberId);

        // then
//        assertThat(response).hasSize(1);
//        assertRecordResponse(response.get(0), startDateTime, endDateTime);
    }

    @Description("9월1일 00:00 ~ 9월 1일 00:10 의 레코드 기록이 있을때, 8월의 레코드 정보를 불러오면 포함되지 않아야한다.")
    @Test
    void 달의_레코드_조회_테스트_레코드가_뒷쪽_경계에_겹치지_않는경우_포함되지_않는다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 9, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 9, 1, 0, 10);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        MonthRecordsRequest request = MonthRecordsRequest.testBuilder()
            .year(2020)
            .month(8)
            .build();

        // when
       // List<RecordResponse> response = recordRetrieveService.retrieveMonthRecords(request, memberId);

        // then
      //  assertThat(response).isEmpty();
    }

    @Description("8월 14일 00:00 ~ 15일 00:00의 레코드 기록이 있을때, 8월 15일의 레코드 정보를 불러오면 포함되지 않아야한다.")
    @Test
    void 하루의_레코드를_불러오는기능_앞쪽에_겹치지_않는경우_포함되지_않는다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 14, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 15, 0, 0);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).isEmpty();
    }

    @Description("8월 14일 00:00 ~ 15일 00:10의 레코드 기록이 있을때, 8월 15일의 레코드 정보를 불러오면 포함되어야 한다.")
    @Test
    void 하루의_레코드를_불러오는기능_앞쪽에_겹치는_경우_포함된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 14, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 15, 0, 10);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertRecordResponse(responses.get(0), startDateTime, endDateTime);
    }

    @Description("8월 16일 00:00 ~ 16일 00:10의 레코드 기록이 있을때, 8월 15일의 레코드 정보를 불러오면 포함되지 않아야 한다.")
    @Test
    void 하루의_레코드를_불러오는기능_뒷쪽에_겹치지_않는경우_포함되지_않는다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 16, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 16, 0, 10);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).isEmpty();
    }

    @Description("8월 15일 23:50 ~ 16일 00:10의 레코드 기록이 있을때, 8월 15일의 레코드 정보를 불러오면 포함되어야 한다.")
    @Test
    void 하루의_레코드를_불러오는기능_뒷쪽에_겹치는_경우_포함된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 15, 23, 50);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 16, 1, 10);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertRecordResponse(responses.get(0), startDateTime, endDateTime);
    }

    @Description("8월 15일 00:00 ~ 16일 00:00의 레코드 기록이 있을때, 8월 15일의 레코드 정보를 불러오면 포함되어야 한다.")
    @Test
    void 하루의_레코드를_불러오는기_포함되서_겹치는경우_포함된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 15, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 16, 0, 0);
        recordRepository.save(RecordCreator.create(memberId, startDateTime, endDateTime));

        DayRecordsRequest request = DayRecordsRequest.testInstance(LocalDate.of(2020, 8, 15));

        // when
        List<RecordResponse> responses = recordRetrieveService.retrieveDayRecords(request, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertRecordResponse(responses.get(0), startDateTime, endDateTime);
    }

    @Description("8월 14일 00:00 ~ 16일 00:00의 레코드 기록이 있을때, 8월 15일의 레코드 정보를 불러오면 포함되어야 한다.")
    @Test
    void 하루의_레코드를_불러오는기_아예_포함되어_지는경우_포함된다() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2020, 8, 14, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2020, 8, 16, 0, 0);
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
