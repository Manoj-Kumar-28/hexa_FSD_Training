package com.springboot.TaskManagement.repository;

import com.springboot.TaskManagement.model.Manager;
import com.springboot.TaskManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManaagerRepository extends JpaRepository<Manager,Long> {
    List<Manager> user(User user);

    @Query("""
        select m from Manager m where m.user.username=?1
""")
    Manager getManagerByUsername(String username);
}
