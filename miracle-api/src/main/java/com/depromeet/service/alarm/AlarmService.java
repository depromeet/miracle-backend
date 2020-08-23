package com.depromeet.service.alarm;

import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleRepository;
import com.depromeet.service.alarm.dto.request.CreateAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.request.DeleteAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.request.RetrieveAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.request.UpdateAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.response.AlarmScheduleInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmScheduleRepository alarmScheduleRepository;

    @Transactional
    public void createDefaultWakeUpAlarmSchedule(Long memberId, LocalTime wakeUpTime) {
        if (wakeUpTime == null) {
            return;
        }
        alarmScheduleRepository.save(AlarmSchedule.defaultWakeUpAlarmScheduleInstance(memberId, wakeUpTime));
    }

    @Transactional
    public AlarmScheduleInfoResponse createAlarmSchedule(CreateAlarmScheduleRequest request, Long memberId) {
        AlarmSchedule alarmSchedule = alarmScheduleRepository.save(request.toEntity(memberId));
        return AlarmScheduleInfoResponse.of(alarmSchedule);
    }

    @Transactional(readOnly = true)
    public List<AlarmScheduleInfoResponse> retrieveAlarmSchedules(Long memberId) {
        List<AlarmSchedule> alarmSchedules = alarmScheduleRepository.findAlarmSchedulesByMemberId(memberId);
        return alarmSchedules.stream()
            .map(AlarmScheduleInfoResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlarmScheduleInfoResponse retrieveAlarmSchedule(RetrieveAlarmScheduleRequest request, Long memberId) {
        AlarmSchedule alarmSchedule = AlarmServiceUtils.findAlarmScheduleByIdAndMemberId(alarmScheduleRepository, request.getAlarmScheduleId(), memberId);
        return AlarmScheduleInfoResponse.of(alarmSchedule);
    }

    @Transactional
    public AlarmScheduleInfoResponse updateAlarmSchedule(UpdateAlarmScheduleRequest request, Long memberId) {
        AlarmSchedule findAlarmSchedule = AlarmServiceUtils.findAlarmScheduleByIdAndMemberId(alarmScheduleRepository, request.getAlarmScheduleId(), memberId);
        findAlarmSchedule.updateAlarmScheduleInfo(request.getType(), request.getDescription(), request.toAlarmsEntity());
        return AlarmScheduleInfoResponse.of(findAlarmSchedule);
    }

    @Transactional
    public void deleteAlarmSchedule(DeleteAlarmScheduleRequest request, Long memberId) {
        AlarmSchedule alarmSchedule = AlarmServiceUtils.findAlarmScheduleByIdAndMemberId(alarmScheduleRepository, request.getAlarmScheduleId(), memberId);
        alarmScheduleRepository.delete(alarmSchedule);
    }

}
