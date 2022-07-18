package com.gilang.demo.payload;

import lombok.Data;

import java.util.Date;

@Data
public class AddLeaveRequest {
    private Date from_date;
    private Date to_date;
    private String reason;
    private Long backup_user_id;
}
