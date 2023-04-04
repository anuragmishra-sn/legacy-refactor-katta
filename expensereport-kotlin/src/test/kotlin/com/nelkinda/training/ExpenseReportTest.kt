package com.nelkinda.training

import com.nelkinda.training.ExpenseType.BREAKFAST
import com.nelkinda.training.ExpenseType.CAR_RENTAL
import com.nelkinda.training.ExpenseType.DINNER
import org.junit.jupiter.api.Test
import java.util.Date

class ExpenseReportTest {
    private val expenseReport = ExpenseReport()

    private val now = Date(123, 3, 4, 10, 2, 33)

    @Test
    fun `expenses over their limits are marked with an X`() {
        val expenses = listOf(
            Expense(BREAKFAST,  1000),
            Expense(BREAKFAST,  1001),
            Expense(DINNER,     5000),
            Expense(DINNER,     5001),
            Expense(CAR_RENTAL, 4   ),
        )
        val expected = """Expenses Tue Apr 04 10:02:33 IST 2023
Breakfast	1000	 
Breakfast	1001	X
Dinner	5000	 
Dinner	5001	X
Car Rental	4	 
Meal expenses: 12002
Total expenses: 12006
"""
        assertReport(expected) { expenseReport.printReport(expenses, now) }
    }

    fun assertReport(expected: String, block: () -> Unit) {
        assertStdout(expected, block)
    }
}