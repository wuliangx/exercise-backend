package com.exercise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMeetupGroupRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}

