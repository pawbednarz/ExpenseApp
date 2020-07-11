package com.pbednarz.nonameproject.todolist.repository;

import com.pbednarz.nonameproject.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
