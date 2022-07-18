package com.gilang.demo.controller;

import com.gilang.demo.dto.AttendanceDTO;
import com.gilang.demo.entity.User;
import com.gilang.demo.response.BaseResponse;
import com.gilang.demo.response.Response;
import com.gilang.demo.security.CurrentUser;
import com.gilang.demo.security.UserPrincipal;
import com.gilang.demo.service.AttendanceService;
import com.gilang.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/{childUsername}/all")
    @PreAuthorize("hasAuthority('APPROVER')")
    public ResponseEntity<?> findByManager(
            HttpServletRequest request,
            @PathVariable String childUsername
    ){
        Response resp = attendanceService.getAllAttendanceByUsername(childUsername);
        return  ResponseEntity.ok(resp);
    }

    @GetMapping(value = "/view-all/{month}")
    @PreAuthorize("hasAuthority('MAKER')")
    public ResponseEntity<?> findByMonth(
            HttpServletRequest request,
            @Parameter(hidden = true) @CurrentUser UserPrincipal currentUser,
            @PathVariable String month
    ){
        Response resp = attendanceService.getAllAttendanceByUsernameAndMonth(currentUser.getUsername(),month);
        return  ResponseEntity.ok(resp);
    }

    @GetMapping(value = "/all/paging/{month}")
    public ResponseEntity<?> findPaging(
            HttpServletRequest request,
            @Parameter(hidden = true) @CurrentUser UserPrincipal currentUser,
            @PathVariable String month,
            @RequestParam(name = "page_no", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize
    ){
        Map<String, Object> result = new HashMap<>();

        result = attendanceService.getAllAttendanceByUsernameAndMonthPaging(currentUser.getUsername(), month, pageNo, pageSize);

        return ResponseEntity.ok(new Response("Success",
                Long.valueOf(result.get("total_result").toString()), Long.valueOf(result.get("total_page").toString()),
                result.get("data")));
    }

    //create
    @PostMapping(value = "/check-in")
    public ResponseEntity<?> checkIn(
            @Parameter(hidden = true) @CurrentUser UserPrincipal currentUser
            ){
        User u = userService.findById(currentUser.getId());
        Response resp = attendanceService.checkIn(u);
        return ResponseEntity.ok(resp);
    }

    //update
    @PutMapping(value = "/check-out")
    public ResponseEntity<?> checkOut(
            @Parameter(hidden = true) @CurrentUser UserPrincipal currentUser
    ){
        User u = userService.findById(currentUser.getId());
        Response resp = attendanceService.checkOut(u);
        return ResponseEntity.ok(resp);
    }

    //delete
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteAttendance(
            @PathVariable Long id
    ){
        Response resp = attendanceService.deleteAttendance(id);
        return ResponseEntity.ok(resp);
    }

}
