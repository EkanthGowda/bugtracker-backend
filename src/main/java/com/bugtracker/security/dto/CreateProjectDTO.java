package com.bugtracker.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDTO {
    private String name;
    private String description;
    private String key; // e.g., "JIR", "PROJ"
}
