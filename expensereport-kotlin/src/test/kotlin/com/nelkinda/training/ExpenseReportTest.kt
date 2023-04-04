package com.nelkinda.training

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.Date

class ExpenseReportTest {
    @Test
    fun `empty report`() {
        val interceptedStdout = ByteArrayOutputStream()
        System.setOut(PrintStream(interceptedStdout))
        val expenseReport = ExpenseReport()
        expenseReport.printReport(emptyList(), Date(123, 3, 4, 10, 2, 33))
        val expected = """Expenses Tue Apr 04 10:02:33 IST 2023
Meal expenses: 0
Total expenses: 0
"""
        assertEquals(expected, interceptedStdout.toString())
    }

    @Test
    fun `single expense`() {
        val interceptedStdout = ByteArrayOutputStream()
        System.setOut(PrintStream(interceptedStdout))
        val expenseReport = ExpenseReport()
        val expense = Expense()
        expense.type = ExpenseType.BREAKFAST
        expense.amount = 100
        val expenses = listOf(expense)
        expenseReport.printReport(expenses, Date(123, 3, 4, 10, 2, 33))
        val expected = """Expenses Tue Apr 04 10:02:33 IST 2023
Breakfast	100	 
Meal expenses: 100
Total expenses: 100
"""
        assertEquals(expected, interceptedStdout.toString())
    }

    @Test
    fun `two expenses`() {
        val interceptedStdout = ByteArrayOutputStream()
        System.setOut(PrintStream(interceptedStdout))
        val expenseReport = ExpenseReport()
        val expense1 = Expense()
        expense1.type = ExpenseType.BREAKFAST
        expense1.amount = 100
        val expense2 = Expense()
        expense2.type = ExpenseType.DINNER
        expense2.amount = 200
        val expenses = listOf(expense1, expense2)
        expenseReport.printReport(expenses, Date(123, 3, 4, 10, 2, 33))
        val expected = """Expenses Tue Apr 04 10:02:33 IST 2023
Breakfast	100	 
Dinner	200	 
Meal expenses: 300
Total expenses: 300
"""
        assertEquals(expected, interceptedStdout.toString())
    }
}