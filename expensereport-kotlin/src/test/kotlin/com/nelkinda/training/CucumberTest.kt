package com.nelkinda.training

import io.cucumber.java.DataTableType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
class CucumberTest {
    private var clock = Clock.systemUTC()
    private lateinit var expenses: List<Expense>
    private lateinit var report: String

    @DataTableType
    fun toExpense(row: Map<String, String>): Expense {
        val expenseType = ExpenseType.valueOf(row["type"]!!)
        val amount = row["amount"]!!.replace(Regex("_"), "").toInt()
        return Expense(expenseType, amount.INR)
    }

    @Given("it is {string} in {string}")
    fun `it is timestamp in zoneId`(timestamp: String, zoneId: String) {
        clock = Clock.fixed(Instant.parse(timestamp), ZoneId.of(zoneId))
    }

    @Given("I have the following expenses:")
    fun `I have the following expenses`(expenses: List<Expense>) {
        this.expenses = expenses
    }

    @When("generating the report")
    fun `generate report`() {
        report = ExpenseReport(clock).generateReport(expenses)
    }

    @Then("the report MUST look like this:")
    fun `expect report`(expected: String) {
        assertEquals("$expected\n".translateEscapes(), report)
    }
}
