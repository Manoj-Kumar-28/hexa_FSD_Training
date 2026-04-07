package com.springboot.TaskManagement.mapper;

import com.springboot.TaskManagement.dto.TaskReqDto;
import com.springboot.TaskManagement.dto.TaskRespDto;
import com.springboot.TaskManagement.enums.TaskStatus;
import com.springboot.TaskManagement.model.Task;

public class TaskMapper {
    public static Task mapToEntity(TaskReqDto taskReqDto) {
        Task task = new Task();
        task.setTaskName(taskReqDto.taskName());
        task.setTaskDetails(taskReqDto.taskDetails());
        task.setTaskPriority(taskReqDto.taskPriority());
        task.setDueDate(taskReqDto.dueDate());
        task.setTaskStatus(TaskStatus.PENDING);
        return task;
    }

    public static TaskRespDto mapToDto(Task task) {
        return new TaskRespDto(
                task.getTaskName(),
                task.getTaskDetails(),
                task.getDueDate(),
                task.getTaskStatus(),
                task.getTaskPriority()
        );
    }

    public static Task updateEntity(Task task, TaskReqDto taskReqDto) {
        task.setTaskName(taskReqDto.taskName());
        task.setTaskDetails(taskReqDto.taskDetails());
        task.setTaskPriority(taskReqDto.taskPriority());
        task.setDueDate(taskReqDto.dueDate());
        task.setTaskStatus(TaskStatus.PENDING);
        return task;
    }
}
