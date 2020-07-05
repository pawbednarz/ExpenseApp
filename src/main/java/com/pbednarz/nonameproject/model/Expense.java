package com.pbednarz.nonameproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pbednarz.nonameproject.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Entity
public class Expense {

    // TODO add proper validation and maybe some more fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal value;

    // TODO maybe make table UserCategories for custom categories for every user?
    private Category category;

    @Past
    private Date date;

    // TODO modification date
    @Past
    private Date modificationDate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;

    public Expense() {
    }

    public Expense(BigDecimal value, Category category) {
        this.value = value;
        this.category = category;
        this.date = Date.from(Instant.now());
    }

    public Expense(BigDecimal value, Category category, Date date) {
        this.value = value;
        this.category = category;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", value=" + value +
                ", category=" + category +
                ", date=" + date +
                '}';
    }
}
