package com.depromeet.service.alarm;

import com.depromeet.domain.alarm.Alarm;
import com.depromeet.domain.alarm.AlarmCreator;
import com.depromeet.domain.alarm.AlarmRepository;
import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleCreator;
import com.depromeet.domain.alarm.AlarmScheduleScheduleRepository;
import com.depromeet.domain.alarm.AlarmType;
import com.depromeet.domain.alarm.DayOfTheWeek;
import com.depromeet.service.MemberSetup;
import com.depromeet.service.alarm.dto.request.AlarmRequest;
import com.depromeet.service.alarm.dto.request.CreateAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.request.DeleteAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.request.RetrieveAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.request.UpdateAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.response.AlarmScheduleInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class AlarmServiceTest extends MemberSetup {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private AlarmScheduleScheduleRepository alarmScheduleScheduleRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @AfterEach
    void cleanUp() {
        super.cleanup();
        alarmRepository.deleteAllInBatch();
        alarmScheduleScheduleRepository.deleteAllInBatch();
    }

    @Test
    void 새로운_알림을_설정하면_알림_스케쥴이_생성된다() {
        // given
        String description = "기상 알림";
        AlarmType type = AlarmType.WAKE_UP;

        CreateAlarmScheduleRequest request = CreateAlarmScheduleRequest.testBuilder()
            .description(description)
            .type(type)
            .alarms(Collections.emptyList())
            .build();

        // when
        alarmService.createAlarmSchedule(request, memberId);

        // then
        List<AlarmSchedule> alarmSchedules = alarmScheduleScheduleRepository.findAll();
        assertThat(alarmSchedules).hasSize(1);
        assertAlarmSchedule(alarmSchedules.get(0), description, type);
    }

    @Test
    void 새로운_알림을_설정하면_알림_스케쥴에_알림이_생성된다() {
        // given
        DayOfTheWeek dayOfTheWeek = DayOfTheWeek.MON;
        LocalTime reminderTime = LocalTime.of(9, 0);

        AlarmRequest alarmRequest = AlarmRequest.testBuilder()
            .dayOfTheWeek(dayOfTheWeek)
            .reminderTime(reminderTime)
            .build();

        CreateAlarmScheduleRequest request = CreateAlarmScheduleRequest.testBuilder()
            .type(AlarmType.WAKE_UP)
            .alarms(Collections.singletonList(alarmRequest))
            .build();

        // when
        alarmService.createAlarmSchedule(request, memberId);

        // then
        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms).hasSize(1);
        assertAlarm(alarms.get(0), dayOfTheWeek, reminderTime);
    }

    @Test
    void 회원의_알림_스케쥴_리스트_정보를_불러온다() {
        // given
        String description = "알림 스케쥴";
        AlarmType type = AlarmType.WAKE_UP;

        AlarmSchedule alarmSchedule = AlarmScheduleCreator.createAlarmSchedule(memberId, type, description);
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(9, 0));
        alarmSchedule.addAlarms(Arrays.asList(alarm1, alarm2));
        alarmScheduleScheduleRepository.save(alarmSchedule);

        // when
        List<AlarmScheduleInfoResponse> responses = alarmService.retrieveAlarmSchedules(memberId);

        // then
        assertAll(
            () -> assertThat(responses).hasSize(1),
            () -> assertAlarmScheduleInfoResponse(responses.get(0), alarmSchedule.getId(), type, description),
            () -> assertThat(responses.get(0).getAlarms()).hasSize(2),
            () -> assertThat(responses.get(0).getAlarms()).extracting("dayOfTheWeek").containsExactly(DayOfTheWeek.MON, DayOfTheWeek.TUE),
            () -> assertThat(responses.get(0).getAlarms()).extracting("reminderTime").containsExactly(LocalTime.of(8, 0), LocalTime.of(9, 0))
        );
    }

    @Test
    void 특정_알림_스케쥴의_정보를_불러온다() {
        // given
        String description = "알림 스케쥴";
        AlarmType type = AlarmType.WAKE_UP;

        AlarmSchedule alarmSchedule = AlarmScheduleCreator.createAlarmSchedule(memberId, type, description);
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(9, 0));
        alarmSchedule.addAlarms(Arrays.asList(alarm1, alarm2));
        alarmScheduleScheduleRepository.save(alarmSchedule);

        RetrieveAlarmScheduleRequest request = RetrieveAlarmScheduleRequest.of(alarmSchedule.getId());

        // when
        AlarmScheduleInfoResponse response = alarmService.retrieveAlarmSchedule(request, memberId);

        // then
        assertAll(
            () -> assertAlarmScheduleInfoResponse(response, alarmSchedule.getId(), type, description),
            () -> assertThat(response.getAlarms()).hasSize(2),
            () -> assertThat(response.getAlarms()).extracting("dayOfTheWeek").containsExactly(DayOfTheWeek.MON, DayOfTheWeek.TUE),
            () -> assertThat(response.getAlarms()).extracting("reminderTime").containsExactly(LocalTime.of(8, 0), LocalTime.of(9, 0))
        );
    }

    @Test
    void 자신의_알림_스케쥴에_대한_정보만_가져올_수있다() {
        // given
        AlarmSchedule alarmSchedule = AlarmScheduleCreator.createAlarmSchedule(memberId, AlarmType.WAKE_UP, "알림 스케쥴");
        alarmSchedule.addAlarms(Collections.emptyList());
        alarmScheduleScheduleRepository.save(alarmSchedule);

        RetrieveAlarmScheduleRequest request = RetrieveAlarmScheduleRequest.of(alarmSchedule.getId());

        // when & then
        assertThatThrownBy(() -> {
            alarmService.retrieveAlarmSchedule(request, 999L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 알림설정을_변경한다() {
        // given
        String description = "description";
        AlarmType type = AlarmType.WAKE_UP;
        DayOfTheWeek dayOfTheWeek = DayOfTheWeek.MON;
        LocalTime reminderTime = LocalTime.of(8, 0);

        AlarmSchedule alarmSchedule = AlarmScheduleCreator.createAlarmSchedule(memberId, AlarmType.WAKE_UP, "알림 스케쥴");
        alarmSchedule.addAlarms(Collections.emptyList());
        alarmScheduleScheduleRepository.save(alarmSchedule);

        AlarmRequest alarmRequest = AlarmRequest.testBuilder()
            .dayOfTheWeek(dayOfTheWeek)
            .reminderTime(reminderTime)
            .build();

        UpdateAlarmScheduleRequest request = UpdateAlarmScheduleRequest.testBuilder()
            .alarmScheduleId(alarmSchedule.getId())
            .description(description)
            .type(type)
            .alarms(Collections.singletonList(alarmRequest))
            .build();

        // when
        alarmService.updateAlarmSchedule(request, memberId);

        // then
        List<AlarmSchedule> alarmSchedules = alarmScheduleScheduleRepository.findAll();
        assertThat(alarmSchedules).hasSize(1);
        assertAlarmSchedule(alarmSchedules.get(0), description, type);

        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms).hasSize(1);
        assertAlarm(alarms.get(0), dayOfTheWeek, reminderTime);
    }

    @Test
    void 특정_알림_스케쥴을_삭제한다() {
        // given
        AlarmSchedule alarmSchedule = AlarmScheduleCreator.createAlarmSchedule(memberId, AlarmType.WAKE_UP, "description");
        Alarm alarm1 = AlarmCreator.createAlarm(DayOfTheWeek.MON, LocalTime.of(8, 0));
        Alarm alarm2 = AlarmCreator.createAlarm(DayOfTheWeek.TUE, LocalTime.of(9, 0));
        alarmSchedule.addAlarms(Arrays.asList(alarm1, alarm2));
        alarmScheduleScheduleRepository.save(alarmSchedule);

        DeleteAlarmScheduleRequest request = DeleteAlarmScheduleRequest.testInstance(alarmSchedule.getId());

        // when
        alarmService.deleteAlarmSchedule(request, memberId);

        // then
        List<AlarmSchedule> alarmSchedules = alarmScheduleScheduleRepository.findAll();
        assertThat(alarmSchedules).isEmpty();

        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms).isEmpty();
    }

    private void assertAlarmSchedule(AlarmSchedule alarmSchedule, String description, AlarmType type) {
        assertAll(
            () -> assertThat(alarmSchedule.getDescription()).isEqualTo(description),
            () -> assertThat(alarmSchedule.getType()).isEqualTo(type)
        );
    }

    private void assertAlarm(Alarm alarm, DayOfTheWeek dayOfTheWeek, LocalTime reminderTime) {
        assertAll(
            () -> assertThat(alarm.getDayOfTheWeek()).isEqualTo(dayOfTheWeek),
            () -> assertThat(alarm.getReminderTime()).isEqualTo(reminderTime)
        );
    }

    private void assertAlarmScheduleInfoResponse(AlarmScheduleInfoResponse response, Long alarmScheduleId, AlarmType type, String description) {
        assertThat(response.getId()).isEqualTo(alarmScheduleId);
        assertThat(response.getType()).isEqualTo(type);
        assertThat(response.getDescription()).isEqualTo(description);
    }

}
