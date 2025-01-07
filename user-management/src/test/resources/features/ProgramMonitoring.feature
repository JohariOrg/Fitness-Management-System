Feature: Program Monitoring

  Scenario: View statistics on the most popular programs by enrollment
    Given the admin dashboard for program monitoring is loaded
    When the admin views program enrollment statistics
    Then the system should display the most popular programs by enrollment

  Scenario: Generate reports on revenue
    Given the admin dashboard for program monitoring is loaded
    When the admin generates a report on program revenue
    Then the system should display total revenue for all active and completed programs

  Scenario: Generate reports on attendance
    Given the admin dashboard for program monitoring is loaded
    When the admin generates a report on program attendance
    Then the system should display attendance data for active and completed programs

  Scenario: Generate reports on client progress
    Given the admin dashboard for program monitoring is loaded
    When the admin generates a report on client progress
    Then the system should display client progress statistics for all programs

  Scenario: Track active and completed programs
    Given the admin dashboard for program monitoring is loaded
    When the admin views the list of active and completed programs
    Then the system should display a list of programs categorized as active or completed
