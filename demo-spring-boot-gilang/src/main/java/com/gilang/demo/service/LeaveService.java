package com.gilang.demo.service;

import com.gilang.demo.entity.User;
import com.gilang.demo.payload.AddLeaveRequest;
import com.gilang.demo.response.Response;

public interface LeaveService {
    Response addLeave(User user, AddLeaveRequest request);
    Response getAll();
}
