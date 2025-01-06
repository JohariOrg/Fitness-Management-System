Feature: Program Management

  Scenario: Create a new fitness program
    Given the instructor dashboard is loaded
    When the instructor creates a new fitness program with:
      | title       | Yoga Basics                        |
      | duration    | 4 weeks                            |
      | difficulty  | Beginner                           |
      | goals       | Flexibility and stress relief      |
      | price       | 100                                |
      | schedule    | Online                             |
      | videos      | intro.mp4, session1.mp4, session2.mp4 |
      | documents   | guide.pdf, faq.pdf                 |
    Then the fitness program should be created successfully
    And the program with title "Yoga Basics" should exist in the system

  Scenario: Update an existing fitness program
  Given the instructor dashboard is loaded
  When the instructor updates the program titled "Yoga Basics" with:
    | duration   | 6 weeks                     |
    | difficulty | Intermediate                |
    | goals      | Enhanced flexibility        |
    | price      | 150                         |
    | schedule   | Hybrid                      |
    | videos     | intro.mp4, session1.mp4, session2.mp4, advanced.mp4 |
    | documents  | guide.pdf, faq.pdf, tips.pdf |
  Then the fitness program should be updated successfully



  Scenario: Delete a fitness program
    Given the instructor dashboard is loaded
    When the instructor deletes the program titled "Yoga Basics"
    Then the fitness program should no longer exist in the system
