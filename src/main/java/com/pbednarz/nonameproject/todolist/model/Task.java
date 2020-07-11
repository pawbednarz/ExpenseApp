package com.pbednarz.nonameproject.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pbednarz.nonameproject.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 1024)
    @NotBlank
    private String task;
    @Future
    private Date executionDate;
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    User user;


    public Task() {
    }

    public Task(String task, Date executionDate) {
        this.task = task;
        this.executionDate = executionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }
}
