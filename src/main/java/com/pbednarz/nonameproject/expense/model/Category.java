package com.pbednarz.nonameproject.expense.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pbednarz.nonameproject.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 64)
    private String name;

    @Column(name = "is_default_category")
    @NotNull
    // TODO why that doesnt work?
    @JsonIgnore
    private Boolean isDefaultCategory;

    @ManyToMany
    @JsonIgnore
    private List<User> users;

    public Category() {
    }

    public Category(String name) {
        this(name, true);
    }

    public Category(String name, @NotNull Boolean isDefaultCategory) {
        this.name = name;
        this.isDefaultCategory = isDefaultCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDefaultCategory() {
        return isDefaultCategory;
    }

    public void setDefaultCategory(Boolean defaultCategory) {
        isDefaultCategory = defaultCategory;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
