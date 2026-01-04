package com.exercise.controller;

import com.exercise.dto.CreateMeetupGroupRequest;
import com.exercise.dto.MeetupGroupDto;
import com.exercise.service.MeetupGroupService;
import com.exercise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetup-groups")
@CrossOrigin(origins = "*")
public class MeetupGroupController {

    @Autowired
    private MeetupGroupService meetupGroupService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<MeetupGroupDto>> getAllMeetupGroups(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        List<MeetupGroupDto> groups = meetupGroupService.getAllMeetupGroups(userId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetupGroupDto> getMeetupGroupById(@PathVariable Long id, Authentication authentication) {
        try {
            Long userId = getCurrentUserId(authentication);
            MeetupGroupDto group = meetupGroupService.getMeetupGroupById(id, userId);
            return ResponseEntity.ok(group);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createMeetupGroup(@Valid @RequestBody CreateMeetupGroupRequest request) {
        try {
            MeetupGroupDto group = meetupGroupService.createMeetupGroup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(group);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private Long getCurrentUserId(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userService.findByUsername(username).getId();
    }
}

