package com.springboot.TaskManagement.controller;

import com.springboot.TaskManagement.dto.TaskPageRespDto;
import com.springboot.TaskManagement.dto.TaskReqDto;
import com.springboot.TaskManagement.dto.TaskRespDto;
import com.springboot.TaskManagement.model.Task;
import com.springboot.TaskManagement.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskReqDto taskReqDto){
        taskService.addTask(taskReqDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/get-all")
    public TaskPageRespDto getAllTasks(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size){
        return taskService.getAllTasks(page,size);
    }

    @GetMapping("/get/{id}")
    public TaskRespDto getTaskById(@PathVariable int id){
        return taskService.getTaskById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@RequestBody TaskReqDto taskReqDto,@PathVariable int id,Principal principal){
        taskService.updateTask(taskReqDto,id,principal.getName());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id, Principal principal){
        taskService.deleteTask(id,principal.getName());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}

