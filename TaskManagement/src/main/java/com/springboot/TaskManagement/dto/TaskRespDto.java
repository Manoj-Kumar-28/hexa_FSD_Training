package com.springboot.TaskManagement.dto;

import com.springboot.TaskManagement.enums.TaskPriority;
import com.springboot.TaskManagement.enums.TaskStatus;

import java.time.LocalDate;

public record TaskRespDto(
    String taskName,
    String taskDetails,
    LocalDate dueDate,
    TaskStatus taskStatus,
    TaskPriority taskPriority
) {
}
