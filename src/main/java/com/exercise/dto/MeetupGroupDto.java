package com.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetupGroupDto {
    private Long id;
    private String name;
    private String description;
    private List<EventDto> events;
}

