package com.gilang.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AttendanceDTO {
    private Long id;
    private String name;
    private Timestamp check_in;
    private Timestamp check_out;
    private String approved_by;
    private Timestamp approved_at;
    private String status;
}
