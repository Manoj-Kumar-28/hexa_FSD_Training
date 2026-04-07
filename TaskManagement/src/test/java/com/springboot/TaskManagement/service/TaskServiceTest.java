package com.springboot.TaskManagement.service;

import com.springboot.TaskManagement.dto.TaskRespDto;
import com.springboot.TaskManagement.enums.TaskPriority;
import com.springboot.TaskManagement.enums.TaskStatus;
import com.springboot.TaskManagement.exception.ResourceNotFoundException;
import com.springboot.TaskManagement.model.Task;
import com.springboot.TaskManagement.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;

    @Test
    public void getTaskByIdTestIfExists(){
        Assertions.assertNotNull(taskService);
        Task task=new Task();
        task.setTaskName("test");
        task.setTaskId(1L);
        task.setTaskDetails("Test details");
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        task.setTaskPriority(TaskPriority.HIGH);
        task.setDueDate(LocalDate.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskRespDto dto=new TaskRespDto(
                task.getTaskName(),
                task.getTaskDetails(),
                task.getDueDate(),
                task.getTaskStatus(),
                task.getTaskPriority()
        );

        TaskRespDto dto1=new TaskRespDto(
                task.getTaskName(),
                task.getTaskDetails(),
                task.getDueDate(),
                TaskStatus.COMPLETED,
                task.getTaskPriority()
        );

        Assertions.assertEquals(dto,taskService.getTaskById(1));
        Assertions.assertNotEquals(dto1,taskService.getTaskById(1));

        Mockito.verify(taskRepository, Mockito.times(2)).findById(1L);
    }

    @Test
    public void getTaskByIdTestIfNotExists(){
        //Mocking  if 10L passes return the empty object
        when(taskRepository.findById(10L)).thenReturn(Optional.empty());

        //checking if exception is thrown
        Exception e=Assertions.assertThrows(ResourceNotFoundException.class,()->{
            taskService.getTaskById(10);
        });
        //Validating the error message
        Assertions.assertEquals("task id not found...",e.getMessage());
    }

    @Test
    public void getAllTasksTest(){
        Task task1=new Task();

        task1.setTaskName("test");
        task1.setTaskId(1L);
        task1.setTaskDetails("Test details");
        task1.setTaskStatus(TaskStatus.IN_PROGRESS);
        task1.setTaskPriority(TaskPriority.HIGH);
        task1.setDueDate(LocalDate.now());

        Task task2=new Task();

        task2.setTaskName("test");
        task2.setTaskId(1L);
        task2.setTaskDetails("Test details");
        task2.setTaskStatus(TaskStatus.IN_PROGRESS);
        task2.setTaskPriority(TaskPriority.HIGH);
        task2.setDueDate(LocalDate.now());

        List<Task> list= List.of(task1,task2);

        //Creating a pageTicket for task page

        Page<Task> pageTicket1=new PageImpl<>(list);
        int page=0;
        int size=2;
        Pageable pageable= PageRequest.of(page,size);
        when(taskRepository.findAll(pageable)).thenReturn(pageTicket1);
        Assertions.assertEquals(2,taskService.getAllTasks(0,2).totalRecords());



        /*
        Page<Task> pageTicket2=new PageImpl<>(list.subList(0,1));
        int page=0;
        int size=2;
        Pageable pageable2= PageRequest.of(page,size);
        when(taskRepository.findAll(pageable2)).thenReturn(pageTicket2);
        Assertions.assertEquals(1,taskService.getAllTasks(0,1).totalRecords());
        */

    }
}
