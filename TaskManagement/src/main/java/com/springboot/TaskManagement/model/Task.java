package com.springboot.TaskManagement.model;

import com.springboot.TaskManagement.enums.TaskPriority;
import com.springboot.TaskManagement.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private long taskId;
    @Column(name = "task_name", nullable = false)
    private String taskName;
    @Column(name = "task_details")
    private String taskDetails;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

}
