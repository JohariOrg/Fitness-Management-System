Feature: Admin User Management

Scenario: Add a new user
  Given the admin dashboard is loaded
  When the admin adds a new user with name "Rand", email "Rand@email.com", role "Instructor", status "active", age 30, fitness goals "Build muscle", dietary preferences "Vegetarian", and dietary restrictions "No dairy"
  Then the user should be added successfully
  And the user with email "Rand@email.com" should exist in the system

Scenario: Approve a new instructor registration
  Given the admin dashboard is loaded
  When a new instructor registration with email "instructor@example.com" is pending approval
  And the admin approves the registration
  Then the instructor should be marked as approved

Scenario: Monitor user activity and engagement statistics
  Given the admin dashboard is loaded
  When the admin requests user activity statistics
  Then the system should display user activity and engagement data
