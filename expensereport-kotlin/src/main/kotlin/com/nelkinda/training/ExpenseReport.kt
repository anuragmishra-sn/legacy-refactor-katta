package com.nelkinda.training

import java.util.Date

enum class ExpenseType(
    val displayName: String,
    val isMeal: Boolean,
    private val limit: Int? = null,
) {
    DINNER("Dinner", true, 5000),
    BREAKFAST("Breakfast", true, 1000),
    CAR_RENTAL("Car Rental", false),;
    fun isOverLimit(amount: Int) = limit != null && amount > limit
}

data class Expense(
    val type: ExpenseType,
    val amount: Int,
) {
    val isMeal get() = type.isMeal
    val name get() = type.displayName
    fun isOverLimit() = type.isOverLimit(amount)
}

class ExpenseReport {
    @Suppress("unused")
    fun printReport(expenses: List<Expense>) {
        printReport(expenses, Date())
    }

    fun printReport(expenses: List<Expense>, date: Date) {
        println("Expenses $date")
        for (expense in expenses) {
            val mealOverExpensesMarker = if (expense.isOverLimit()) "X" else " "
            println("${expense.name}\t${expense.amount}\t$mealOverExpensesMarker")
        }

        println("Meal expenses: ${expenses.sumMeals()}")
        println("Total expenses: ${expenses.sumTotal()}")
    }

    private fun List<Expense>.sumMeals() = filter { it.isMeal }.sumOf { it.amount }
    private fun List<Expense>.sumTotal() = sumOf { it.amount }
}
