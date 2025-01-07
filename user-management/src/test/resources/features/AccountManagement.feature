Feature: Account Management
  As a client
  I want to create and customize my profile
  So that I can provide my personal details, dietary preferences, and restrictions

  Scenario: Create a client profile
    Given the client dashboard is loaded
    When the client creates a new profile with the following details:
      | key                 | value                   |
      | Name                | John Doe               |
      | Age                 | 30                     |
      | Fitness Goals       | Weight Loss            |
      | Dietary Preferences | Vegetarian             |
      | Dietary Restrictions| Gluten-Free            |
    Then the profile should be created successfully

  Scenario: View client profile
    Given the client has already created a profile
    When the client views their profile
    Then the system should display the profile with the following details:
      | key                 | value                   |
      | Name                | John Doe               |
      | Age                 | 30                     |
      | Fitness Goals       | Weight Loss            |
      | Dietary Preferences | Vegetarian             |
      | Dietary Restrictions| Gluten-Free            |

  Scenario: Update client profile
    Given the client has already created a profile
    When the client updates their profile with the following details:
      | key                 | value                   |
      | Name                | John Doe               |
      | Age                 | 31                     |
      | Fitness Goals       | Muscle Building        |
      | Dietary Preferences | Vegan                  |
      | Dietary Restrictions| Nut-Free               |
    Then the profile should be updated successfully

  Scenario: Deleting a profile
  Given the client has already created a profile
  When the client deletes their profile
  Then the profile should be deleted successfully

