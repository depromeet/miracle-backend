package com.depromeet.service.alarm;

import com.depromeet.domain.alarm.AlarmSchedule;
import com.depromeet.domain.alarm.AlarmScheduleScheduleRepository;
import com.depromeet.service.alarm.dto.request.CreateAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.request.RetrieveAlarmScheduleRequest;
import com.depromeet.service.alarm.dto.response.AlarmScheduleInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmScheduleScheduleRepository alarmScheduleRepository;

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
        AlarmSchedule alarmSchedule = AlarmServiceUtils.findAlarmScheduleById(alarmScheduleRepository, request.getAlarmScheduleId());
        alarmSchedule.validateMemberHasOwner(memberId);
        return AlarmScheduleInfoResponse.of(alarmSchedule);
    }

}
