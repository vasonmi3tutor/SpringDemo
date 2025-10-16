package com.vason.springdemo.record;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "SELECT * FROM record WHERE user_id = :id", nativeQuery = true)
    List<Record> findAllByUserIdSql(@Param("id") Long userId);
}
