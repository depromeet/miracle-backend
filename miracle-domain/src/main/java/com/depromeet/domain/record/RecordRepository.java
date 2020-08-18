package com.depromeet.domain.record;

import com.depromeet.domain.record.repository.RecordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom {

}
