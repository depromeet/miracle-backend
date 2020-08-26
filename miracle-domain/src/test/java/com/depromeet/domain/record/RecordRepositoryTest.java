package com.depromeet.domain.record;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    void findRecordBetween_날짜가_빠른것부터_정렬된다() {
        // given
        Record first = RecordCreator.create(1L, LocalDateTime.of(2020, 8, 1, 0, 0), LocalDateTime.of(2020, 8, 1, 1, 0));
        Record second = RecordCreator.create(1L, LocalDateTime.of(2020, 8, 2, 0, 0), LocalDateTime.of(2020, 8, 2, 1, 0));
        recordRepository.saveAll(Arrays.asList(first, second));

        // when
        List<Record> records = recordRepository.findRecordBetween(1L, first.getStartDateTime(), second.getEndDateTime());

        // then
        assertThat(records).hasSize(2);
        assertThat(records.get(0).getId()).isEqualTo(first.getId());
        assertThat(records.get(1).getId()).isEqualTo(second.getId());
    }

    @Test
    void findRecordBetween_해당하는_Record_가_없으면_null_이아닌_빈_리스트를_반환한다() {
        // when
        List<Record> records = recordRepository.findRecordBetween(1L, LocalDateTime.MIN, LocalDateTime.MAX);

        // then
        assertThat(records).isNotNull();
        assertThat(records).isEmpty();
    }

}
