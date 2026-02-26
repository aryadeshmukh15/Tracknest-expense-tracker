package com.arya.tracknest.controller;

import com.arya.tracknest.dto.CategorySummaryDTO;
import com.arya.tracknest.entity.Expense;
import com.arya.tracknest.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense createExpense(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    // ✅ Pagination version (REPLACES old List method)
    @GetMapping
    public Page<Expense> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return expenseService.getAllExpenses(page, size);
    }

    @GetMapping("/monthly-total")
    public Double getMonthlyTotal(@RequestParam int year,
                                  @RequestParam int month) {
        return expenseService.getMonthlyTotal(year, month);
    }

    @GetMapping("/category-summary")
    public List<CategorySummaryDTO> getCategorySummary(
            @RequestParam int year,
            @RequestParam int month) {

        return expenseService.getCategorySummary(year, month);
    }

    @GetMapping("/date-range-total")
    public Double getTotalByDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return expenseService.getTotalByDateRange(start, end);
    }
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id,
                                 @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "Expense deleted successfully";
    }
}