package com.nelkinda.training

import com.nelkinda.training.ExpenseType.BREAKFAST
import com.nelkinda.training.ExpenseType.CAR_RENTAL
import com.nelkinda.training.ExpenseType.DINNER
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant.parse

class ExpenseReportTest {
    val clock = Clock.fixed(parse("2023-04-04T04:32:33.00Z"), java.time.ZoneId.of("Asia/Kolkata"))
    private val expenseReport = ExpenseReport(clock)

    @Test
    fun `expenses over their limits are marked with an X`() {
        val expenses = listOf(
            Expense(BREAKFAST,  1_000.INR),
            Expense(BREAKFAST,  1_001.INR),
            Expense(DINNER,     5_000.INR),
            Expense(DINNER,     5_001.INR),
            Expense(CAR_RENTAL,     4.INR),
        )
        val expected = """Expenses 2023-04-04T10:02:33
Breakfast\t1000\t\s
Breakfast\t1001\tX
Dinner\t5000\t\s
Dinner\t5001\tX
Car Rental\t4\t\s
Meal expenses: 12002
Total expenses: 12006
""".translateEscapes()
        assertReport(expected) { expenseReport.generateReport(expenses) }
    }

    fun assertReport(expected: String, block: () -> String) {
        assertEquals(expected, block())
    }
}