package com.springboot.TaskManagement.service;

import com.springboot.TaskManagement.dto.TaskPageRespDto;
import com.springboot.TaskManagement.dto.TaskReqDto;
import com.springboot.TaskManagement.dto.TaskRespDto;
import com.springboot.TaskManagement.exception.ResourceNotFoundException;
import com.springboot.TaskManagement.mapper.TaskMapper;
import com.springboot.TaskManagement.model.Manager;
import com.springboot.TaskManagement.model.Task;
import com.springboot.TaskManagement.repository.ManaagerRepository;
import com.springboot.TaskManagement.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ManaagerRepository manaagerRepository;

    public void addTask(TaskReqDto taskReqDto) {
        //Mapping the given details to add the task
        //status updated in the mapper
        Task task= TaskMapper.mapToEntity(taskReqDto);

        log.atLevel(Level.INFO).log("Adding Task in the Db....");
        //save task through repository
        taskRepository.save(task);
    }

    public TaskPageRespDto getAllTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskRepository.findAll(pageable);
        long totalRecords=tasks.getTotalElements();
        int totalPages=tasks.getTotalPages();

        log.atLevel(Level.INFO).log("Getting All the Tasks from the Db....");
        List<TaskRespDto> listDto=tasks
                .toList()
                .stream()
                .map(TaskMapper::mapToDto)
                .toList();
        return new TaskPageRespDto(
                listDto,
                totalRecords,
                totalPages
        );

    }

    public TaskRespDto getTaskById(int id) {
        //getting task by id from repository
        Task task=taskRepository.findById((long)id)
                .orElseThrow(()->new ResourceNotFoundException("task id not found..."));
        //converting task to response dto
        TaskRespDto taskRespDto=TaskMapper.mapToDto(task);
        log.atLevel(Level.INFO).log("Getting a single Task from the Db....");
        return taskRespDto;
    }

    public void updateTask(TaskReqDto taskReqDto, int id, String username) {
        //getting task by id
        Task task=taskRepository.findById((long)id)
                .orElseThrow(()->new ResourceNotFoundException("task id not found..."));

        Manager manager=manaagerRepository.getManagerByUsername(username);
        if(!manager.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }
        //update the previous details with new details
        TaskMapper.updateEntity(task,taskReqDto);
        //save the updated task in repository
        log.atLevel(Level.INFO).log("Updating Task in the Db....");
        taskRepository.save(task);

    }

    public void deleteTask(int id, String username) {
        Task task=taskRepository.findById((long)id)
                .orElseThrow(()->new ResourceNotFoundException("task id not found..."));

        Manager manager=manaagerRepository.getManagerByUsername(username);
        if(!manager.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }
        taskRepository.delete(task);
        log.atLevel(Level.INFO).log("Deleting Task in the Db....");
    }
}
