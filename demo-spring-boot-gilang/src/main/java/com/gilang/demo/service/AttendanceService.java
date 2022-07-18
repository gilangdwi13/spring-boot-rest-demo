package com.gilang.demo.service;

import com.gilang.demo.dto.AttendanceDTO;
import com.gilang.demo.entity.User;
import com.gilang.demo.response.Response;

import java.util.List;
import java.util.Map;

public interface AttendanceService {
    Response getAllAttendanceByUsername(String username);
    Response getAllAttendanceByUsernameAndMonth(String username, String month);
    Map<String, Object> getAllAttendanceByUsernameAndMonthPaging(String username, String month, Integer pageNo, Integer pageSize);
    Response checkIn(User user);
    Response checkOut(User user);
    Response deleteAttendance(Long id);
}
