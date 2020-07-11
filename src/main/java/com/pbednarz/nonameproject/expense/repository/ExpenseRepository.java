package com.pbednarz.nonameproject.expense.repository;

import com.pbednarz.nonameproject.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository  extends JpaRepository<Expense, Long> {

    @Query(nativeQuery = true, value = "SELECT e.* FROM Expense e JOIN User u ON u.user_id = e.id_user WHERE u.username = :username")
    List<Expense> findAllByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT e.* FROM Expense e JOIN User u On u.user_id = e.id_user WHERE e.id = :id AND u.username = :username")
    Expense findByIdAndUsername(long id, String username);
}
