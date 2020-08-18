package com.depromeet.domain.record;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecordRepositoryTest {

    @Autowired
    private RecordRepository recordRepository;

    @AfterEach
    void cleanUp() {
        recordRepository.deleteAll();
    }

    @Test
    void test_findRecordById() {
        // given
        String question = "읽은 책은?";
        String answer = "책이름";

        Record record = Record.builder()
            .memberId(1L)
            .scheduleId(1L)
            .answer(answer)
            .question(question)
            .startDateTime(LocalDateTime.of(2020, 8, 18, 0, 0, 0))
            .endDateTime(LocalDateTime.of(2020, 8, 18, 0, 30, 0))
            .build();

        recordRepository.save(record);

        // when
        Record findRecord = recordRepository.findRecordById(record.getId());

        // then
        assertThat(findRecord.getQuestion()).isEqualTo(question);
        assertThat(findRecord.getAnswer()).isEqualTo(answer);
    }

}
