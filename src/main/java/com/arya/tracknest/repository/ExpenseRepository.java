package com.arya.tracknest.repository;

import com.arya.tracknest.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDate;
import org.springframework.data.domain.*;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    Double getMonthlyTotal(@Param("year") int year,
                           @Param("month") int month);

    @Query("SELECT e.category, SUM(e.amount) FROM Expense e " +
            "WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month " +
            "GROUP BY e.category")
    List<Object[]> getCategorySummary(@Param("year") int year,
                                      @Param("month") int month);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.date BETWEEN :start AND :end")
    Double getTotalByDateRange(@Param("start") LocalDate start,
                               @Param("end") LocalDate end);



}