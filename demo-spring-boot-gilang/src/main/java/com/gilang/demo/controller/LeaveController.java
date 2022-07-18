package com.gilang.demo.controller;

import com.gilang.demo.entity.User;
import com.gilang.demo.payload.AddLeaveRequest;
import com.gilang.demo.response.Response;
import com.gilang.demo.security.CurrentUser;
import com.gilang.demo.security.UserPrincipal;
import com.gilang.demo.service.LeaveService;
import com.gilang.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leave")
@Tag(name = "leave")
public class LeaveController {

    @Autowired
    LeaveService leaveService;

    @Autowired
    UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(
            @Parameter(hidden = true) @CurrentUser UserPrincipal currentUser,
            @RequestBody AddLeaveRequest req
    ){
        User u = userService.findById(currentUser.getId());
        Response resp = leaveService.addLeave(u, req);
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> all(){
        Response resp = leaveService.getAll();
        return ResponseEntity.ok(resp);
    }
}
