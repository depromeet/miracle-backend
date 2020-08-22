package com.depromeet.service.schedule.record

import com.depromeet.domain.schedule.record.RecordRepository
import com.depromeet.service.MemberSetup
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class RecordServiceTest(): MemberSetup(){

    @Autowired
    private lateinit var recordRepository:RecordRepository

    @Autowired
    private lateinit var service: RecordService


    @AfterEach
    fun cleanUp() {
        super.cleanup()
        recordRepository.deleteAll()
    }


//    "scheduleId": 1,
//    "time": "2020-08-06T17:00:00",
//    "content": "운동을 하자!"

    @Test
    fun test(){
       val request = CreateRecordRequest(
           scheduleId = 1,
           time = LocalDateTime.of(2020,8,11,23,0,0),
           content = "테스트입니다."
       )

        service.createRecord(memberId = memberId,request = request)


        val records = recordRepository.findAll()
        Assertions.assertThat(records).hasSize(1)
        Assertions.assertThat(records[0]?.content).isEqualTo("테스트입니다.")

    }

}
