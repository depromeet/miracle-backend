package com.depromeet.service.schedule;

import com.depromeet.domain.schedule.Schedule;
import com.depromeet.domain.schedule.ScheduleRepository;
import com.deprommet.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleServiceUtils {

    public static Schedule findSchedule(ScheduleRepository scheduleRepository, Long scheduleId, Long memberId) {
        Schedule schedule = scheduleRepository.findScheduleByIdAndMemberId(scheduleId, memberId);
        if (schedule == null) {
            throw new NotFoundException(String.format("해당하는 멤버 (%s) 에게 스케쥴 (%s)은 존재하지 않습니다.", memberId, scheduleId), "멤버에게 해당하는 스케쥴이 존재하지 않습니다");
        }
        return schedule;
    }

}
