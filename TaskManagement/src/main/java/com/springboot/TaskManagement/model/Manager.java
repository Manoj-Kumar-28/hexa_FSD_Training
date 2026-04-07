package com.springboot.TaskManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, updatable = false)
    private String managerId;
    @Column(nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;
    private String city;


    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
