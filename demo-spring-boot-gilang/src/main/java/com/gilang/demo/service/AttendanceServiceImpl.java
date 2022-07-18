package com.gilang.demo.service;

import com.gilang.demo.dto.AttendanceDTO;
import com.gilang.demo.entity.Attendance;
import com.gilang.demo.entity.User;
import com.gilang.demo.exception.NotFoundExceprion;
import com.gilang.demo.repository.AttendanceRepository;
import com.gilang.demo.repository.UserRepository;
import com.gilang.demo.response.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Response getAllAttendanceByUsername(String username) {
        List<Attendance> arrAt = attendanceRepository.findByUsername(username).orElse(null);

        List<AttendanceDTO> output = new ArrayList<>();
        //convert ke DTO pake model mapper
        /*for (Attendance a : arrAt){
            AttendanceDTO dto = modelMapper.map(a,AttendanceDTO.class);
            output.add(dto);
        }*/

        for (Attendance a : arrAt){
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(a.getId());
            dto.setName(a.getUser().getName());
            dto.setCheck_in(a.getCheckIn());
            dto.setCheck_out(a.getCheckOut());
            dto.setApproved_by(a.getApprovedBy());
            dto.setApproved_at(a.getApprovedAt());
            dto.setStatus(a.getStatus());
            output.add(dto);
        }

        return new Response(output);
    }

    @Override
    public Response getAllAttendanceByUsernameAndMonth(String username, String month) {
        List<Attendance> arrAt = attendanceRepository.findByUsernameAndMonth(username,month).orElse(null);

        List<AttendanceDTO> output = new ArrayList<>();

        for (Attendance a : arrAt){
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(a.getId());
            dto.setName(a.getUser().getName());
            dto.setCheck_in(a.getCheckIn());
            dto.setCheck_out(a.getCheckOut());
            dto.setApproved_by(a.getApprovedBy());
            dto.setApproved_at(a.getApprovedAt());
            dto.setStatus(a.getStatus());
            output.add(dto);
        }

        return new Response(output);
    }

    @Override
    public Map<String, Object> getAllAttendanceByUsernameAndMonthPaging(String username, String month, Integer pageNo, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable paging = PageRequest.of(pageNo, pageSize, sort);

        Page<Attendance> attendances = attendanceRepository.findByUsernameAndMonthPagination(username, month, paging);
        Page<AttendanceDTO> output = attendances.map(new Function<Attendance, AttendanceDTO>() {
            @Override
            public AttendanceDTO apply(Attendance attendance) {
                AttendanceDTO dto = new AttendanceDTO();

                dto.setId(attendance.getId());
                dto.setName(attendance.getUser().getName());
                dto.setCheck_in(attendance.getCheckIn());
                dto.setCheck_out(attendance.getCheckOut());
                dto.setApproved_by(attendance.getApprovedBy());
                dto.setApproved_at(attendance.getApprovedAt());
                dto.setStatus(attendance.getStatus());

                return dto;
            }
        });

        Map<String, Object> result = new HashMap<>();
        if(output != null) {
            result.put("data", output.getContent());
            result.put("total_result", output.getTotalElements());
            result.put("total_page", output.getTotalPages());
        } else {
            result.put("data", output.getContent());
            result.put("total_result", 0);
            result.put("total_page", 0);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response checkIn(User user) {
        Response response = null;

        Attendance a = new Attendance();
        a.setCheckIn(attendanceRepository.sysDate().orElse(null));
        a.setStatus("submit");
        a.setIsDelete(false);
        a.setUser(user);
        a = attendanceRepository.save(a);

        response = a != null ? new Response("Success", a) : new Response(null);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response checkOut(User user) {
        Response response = null;

        Attendance a = attendanceRepository.findAttendanceByTodayCheckIn(user.getUserId()).orElseThrow(
                () -> new NotFoundExceprion("Attendance", "Please check in first.")
        );
        a.setCheckOut(attendanceRepository.sysDate().orElse(null));
        a = attendanceRepository.save(a);

        response = a != null ? new Response("Success", a) : new Response(null);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteAttendance(Long id){

        Attendance a = attendanceRepository.findById(id).orElseThrow(
                () -> new NotFoundExceprion("Attendance")
        );
        attendanceRepository.delete(a);

        return new Response("Success");
    }


}
