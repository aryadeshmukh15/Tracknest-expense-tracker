package com.arya.tracknest.service;

import com.arya.tracknest.dto.CategorySummaryDTO;
import com.arya.tracknest.entity.Expense;
import com.arya.tracknest.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.*;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }



    public Double getMonthlyTotal(int year, int month) {
        Double total = expenseRepository.getMonthlyTotal(year, month);
        return total != null ? total : 0.0;
    }
    public List<CategorySummaryDTO> getCategorySummary(int year, int month) {
        List<Object[]> results = expenseRepository.getCategorySummary(year, month);

        return results.stream()
                .map(obj -> new CategorySummaryDTO(
                        (String) obj[0],
                        (Double) obj[1]
                ))
                .toList();
    }
    public Double getTotalByDateRange(LocalDate start, LocalDate end) {
        return expenseRepository.getTotalByDateRange(start, end);
    }
    public Page<Expense> getAllExpenses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return expenseRepository.findAll(pageable);
    }
    public Expense updateExpense(Long id, Expense updatedExpense) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDate(updatedExpense.getDate());

        return expenseRepository.save(expense);
    }
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
