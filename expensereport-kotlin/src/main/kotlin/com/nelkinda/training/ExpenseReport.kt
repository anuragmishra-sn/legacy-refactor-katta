package com.nelkinda.training

import java.io.BufferedReader
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.Temporal
import java.util.Date

@JvmInline
value class MonetaryAmount(val value: Int): Comparable<MonetaryAmount> {
    operator fun plus(other: MonetaryAmount) = MonetaryAmount(value + other.value)
    operator fun minus(other: MonetaryAmount) = MonetaryAmount(value - other.value)
    override operator fun compareTo(other: MonetaryAmount): Int = value.compareTo(other.value)
    override fun toString() = "$value"
}

val Int.INR get() = MonetaryAmount(this)
val Int.`₹` get() = INR


enum class ExpenseType(
    val displayName: String,
    val isMeal: Boolean,
    private val limit: MonetaryAmount? = null,
) {
    DINNER("Dinner", true, 5_000.`₹`),
    BREAKFAST("Breakfast", true, 1_000.INR),
    CAR_RENTAL("Car Rental", false),
    ;
    fun isOverLimit(amount: MonetaryAmount) = limit != null && amount > limit
}

data class Expense(
    val type: ExpenseType,
    val amount: MonetaryAmount,
) {
    fun isMeal() = type.isMeal
    fun name() = type.displayName
    fun isOverLimit() = type.isOverLimit(amount)
}

class ExpenseReport(
    private val clock: Clock = Clock.systemUTC(),
) {
    /** Generate a report of the expenses.
     * @param expenses The expenses for which to generate the report.
     * @return The report for the expenses.
     */
    fun generateReport(expenses: List<Expense>, date: Temporal = LocalDateTime.now(clock)) =
        header(date) + expenses.body() + expenses.summary()

    private fun header(date: Temporal) = "Expenses $date\n"

    private fun List<Expense>.body() = joinToString("") { it.detail() }
    private fun Expense.detail() = "${name()}\t$amount\t$overLimitMarker\n"
    private val Expense.overLimitMarker: String get() = if (isOverLimit()) "X" else " "

    private fun List<Expense>.summary() = "Meal expenses: ${sumMeals()}\nTotal expenses: ${sumTotal()}\n"
    private fun List<Expense>.sumMeals() = MonetaryAmount(filter { it.isMeal() }.sumOf { it.amount.value })
    private fun List<Expense>.sumTotal() = MonetaryAmount(sumOf { it.amount.value })
}
