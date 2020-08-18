package com.depromeet.domain.schedule.record

import com.depromeet.domain.schedule.Schedule
import com.depromeet.domain.schedule.repository.ScheduleRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecordRepository : JpaRepository<Record?, Long?>,
    RecordRepositoryCustom
