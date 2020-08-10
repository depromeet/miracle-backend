package com.depromeet.domain.alarm;

import com.depromeet.domain.alarm.repository.AlarmScheduleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmScheduleRepository extends JpaRepository<AlarmSchedule, Long>, AlarmScheduleRepositoryCustom {

}
