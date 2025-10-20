package com.vason.springdemo.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "SELECT * FROM record WHERE user_id = :id", nativeQuery = true)
    List<Record> findAllByUserIdSql(@Param("id") Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE record SET value_change = :value_change, remark = :remark WHERE id = :id", nativeQuery = true)
    int updateByRecordIdSql(@Param("id") Long recordId, @Param("value_change") BigDecimal valueChange, @Param("remark") String remark);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM record WHERE record.id = :id", nativeQuery = true)
    int deleteByRecordIdSql(@Param("id") Long id);
}
