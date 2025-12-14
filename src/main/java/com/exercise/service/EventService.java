package com.exercise.service;

import com.exercise.dto.CreateEventRequest;
import com.exercise.dto.EventDto;
import com.exercise.entity.Event;
import com.exercise.entity.EventRegistration;
import com.exercise.entity.User;
import com.exercise.repository.EventRegistrationRepository;
import com.exercise.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRegistrationRepository registrationRepository;

    @Autowired
    private UserService userService;

    public List<EventDto> getAllEvents(Long userId) {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(event -> convertToDto(event, userId))
                .collect(Collectors.toList());
    }

    public List<EventDto> getUserRegisteredEvents(Long userId) {
        List<EventRegistration> registrations = registrationRepository.findByUserId(userId);
        return registrations.stream()
                .map(registration -> convertToDto(registration.getEvent(), userId))
                .collect(Collectors.toList());
    }

    @Transactional
    public EventDto createEvent(CreateEventRequest request) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());
        event.setMaxParticipants(request.getMaxParticipants());

        event = eventRepository.save(event);
        return convertToDto(event, null);
    }

    @Transactional
    public EventDto registerForEvent(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (registrationRepository.existsByUserIdAndEventId(userId, eventId)) {
            throw new RuntimeException("User is already registered for this event");
        }

        long currentParticipants = registrationRepository.countByEventId(eventId);
        if (currentParticipants >= event.getMaxParticipants()) {
            throw new RuntimeException("Event is full");
        }

        User user = userService.findById(userId);

        EventRegistration registration = new EventRegistration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());

        registrationRepository.save(registration);

        return convertToDto(event, userId);
    }

    @Transactional
    public void unregisterFromEvent(Long userId, Long eventId) {
        EventRegistration registration = registrationRepository
                .findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        registrationRepository.delete(registration);
    }

    private EventDto convertToDto(Event event, Long userId) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setEventDate(event.getEventDate());
        dto.setLocation(event.getLocation());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setCurrentParticipants((int) registrationRepository.countByEventId(event.getId()));

        if (userId != null) {
            dto.setIsRegistered(registrationRepository.existsByUserIdAndEventId(userId, event.getId()));
        } else {
            dto.setIsRegistered(false);
        }

        return dto;
    }
}

