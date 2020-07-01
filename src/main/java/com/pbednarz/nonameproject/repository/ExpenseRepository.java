package com.pbednarz.nonameproject.repository;

import com.pbednarz.nonameproject.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository  extends JpaRepository<Expense, Long> {

    public List<Expense> findAllByUserId(Long id);

    @Query("SELECT e FROM Expense e JOIN User u ON e.id = u.id WHERE u.username = :username")
    public List<Expense> findAllByUsername(String username);
}
