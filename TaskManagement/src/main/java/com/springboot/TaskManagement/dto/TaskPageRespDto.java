package com.springboot.TaskManagement.dto;

import java.util.List;

public record TaskPageRespDto(
        List<TaskRespDto> tasks,
        long totalRecords,
        int totalPages
) {
}
