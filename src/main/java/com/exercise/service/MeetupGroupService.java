package com.exercise.service;

import com.exercise.dto.CreateMeetupGroupRequest;
import com.exercise.dto.EventDto;
import com.exercise.dto.MeetupGroupDto;
import com.exercise.entity.Event;
import com.exercise.entity.MeetupGroup;
import com.exercise.repository.EventRepository;
import com.exercise.repository.MeetupGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetupGroupService {

    @Autowired
    private MeetupGroupRepository meetupGroupRepository;

    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private EventService eventService;

    @Transactional(readOnly = true)
    public List<MeetupGroupDto> getAllMeetupGroups(Long userId) {
        List<MeetupGroup> groups = meetupGroupRepository.findAllWithEvents();
        
        groups.forEach(group -> {
        	group.setEvents(new HashSet<Event>(eventRepository.findAllByMeetupGroupId(group.getId())));
        });
        return groups.stream()
                .map(group -> convertToDto(group, userId))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MeetupGroupDto getMeetupGroupById(Long id, Long userId) {
        MeetupGroup group = meetupGroupRepository.findByIdWithEvents(id)
                .orElseThrow(() -> new RuntimeException("Meetup group not found"));
        return convertToDto(group, userId);
    }

    @Transactional
    public MeetupGroupDto createMeetupGroup(CreateMeetupGroupRequest request) {
        if (meetupGroupRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Meetup group with this name already exists");
        }

        MeetupGroup group = new MeetupGroup();
        group.setName(request.getName());
        group.setDescription(request.getDescription());

        group = meetupGroupRepository.save(group);
        return convertToDto(group, null);
    }

    private MeetupGroupDto convertToDto(MeetupGroup group, Long userId) {
        MeetupGroupDto dto = new MeetupGroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());

        // Safely access events collection
        if (group.getEvents() != null && !group.getEvents().isEmpty()) {
            List<EventDto> events = group.getEvents().stream()
                    .map(event -> eventService.convertToDto(event, userId))
                    .collect(Collectors.toList());
            dto.setEvents(events);
        } else {
            dto.setEvents(new java.util.ArrayList<>());
        }

        return dto;
    }
}

