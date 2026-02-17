package com.vason.springdemo.record;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "record")
public class Record {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id;
    private Long user_id;
    private BigDecimal value_change;
    private LocalDateTime timestamp;
    private String remark;

}
