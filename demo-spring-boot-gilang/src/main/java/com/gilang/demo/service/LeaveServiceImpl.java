package com.gilang.demo.service;

import com.gilang.demo.entity.Leaves;
import com.gilang.demo.entity.User;
import com.gilang.demo.payload.AddLeaveRequest;
import com.gilang.demo.repository.LeaveRepository;
import com.gilang.demo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    UserService userService;

    @Override
    public Response addLeave(User user, AddLeaveRequest request) {
        Leaves l = new Leaves();
        l.setUser(user);
        l.setFromDate(request.getFrom_date());
        l.setToDate(request.getTo_date());
        l.setReason(request.getReason());

        User userBackup = userService.findById(request.getBackup_user_id());
        l.setUserBackup(userBackup);

        l.setStatus("submit");
        l.setIsDelete(false);

        l = leaveRepository.save(l);

        return new Response(l);
    }

    @Override
    public Response getAll(){
        List<Leaves> l = leaveRepository.findAll();

        return new Response(l);
    }
}
