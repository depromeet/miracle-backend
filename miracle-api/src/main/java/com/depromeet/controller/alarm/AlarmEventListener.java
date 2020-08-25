package com.depromeet.controller.alarm;

import com.depromeet.event.alarm.NewMemberRegisteredEvent;
import com.depromeet.event.alarm.WakeUpTimeUpdatedEvent;
import com.depromeet.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlarmEventListener {

    private final AlarmService alarmService;

    @EventListener
    public void createDefaultWakeUpAlarm(NewMemberRegisteredEvent event) {
        alarmService.createDefaultWakeUpAlarmSchedule(event.getMemberId(), event.getWakeUpTime());
    }

    @EventListener
    public void updateWakeUpAlarm(WakeUpTimeUpdatedEvent event) {
        alarmService.updateDefaultWakeUpAlarmSchedule(event.getMemberId(), event.getWakeUpTime());
    }

}
