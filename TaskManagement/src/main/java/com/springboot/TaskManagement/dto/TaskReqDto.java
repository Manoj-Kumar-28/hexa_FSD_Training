package com.springboot.TaskManagement.dto;

import com.springboot.TaskManagement.enums.TaskPriority;

import java.time.LocalDate;

public record TaskReqDto(
        String taskName,
        String taskDetails,
        LocalDate dueDate,
        TaskPriority taskPriority
) {
}
