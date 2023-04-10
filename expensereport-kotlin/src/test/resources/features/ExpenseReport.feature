Feature: Expense Report
  Background:
    Given it is "2023-04-04T04:32:33.00Z" in "Asia/Kolkata"

  Scenario: Characterization test for preventing regression
    Given I have the following expenses:
      | type       | amount |
      | BREAKFAST  |  1_000 |
      | BREAKFAST  |  1_001 |
      | DINNER     |  5_000 |
      | DINNER     |  5_001 |
      | CAR_RENTAL |      4 |
    When generating the report
    Then the report MUST look like this:
      """
      Expenses 2023-04-04T10:02:33
      Breakfast\t1000\t\s
      Breakfast\t1001\tX
      Dinner\t5000\t\s
      Dinner\t5001\tX
      Car Rental\t4\t\s
      Meal expenses: 12002
      Total expenses: 12006
      """
