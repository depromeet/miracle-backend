package com.depromeet.domain.schedule.record

import org.springframework.data.jpa.repository.Query


interface RecordRepositoryCustom {


    @Query(
        value = "SELECT * FROM record  WHERE member_id = ?1 and schedule_id = ?2 and year = ?3 and month = ?4 and day = ?5 ",
        nativeQuery = true
    )
    fun findByMemberIdAndScheduleIdAndYearAndMonthAndDay(
        memberId: Long,
        scheduleId: Long,
        year: Int,
        month: Int,
        day: Int
    ): MutableList<Record>?

}
