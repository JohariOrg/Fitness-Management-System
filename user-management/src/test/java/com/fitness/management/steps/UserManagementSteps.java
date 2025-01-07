package com.fitness.management.steps;

import com.fitness.management.User;
import com.fitness.management.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UserManagementSteps {
    private UserService userService;
    private Map<String, Boolean> activityData;

    @Given("the admin dashboard is loaded")
    public void the_admin_dashboard_is_loaded() {
        userService = new UserService();
        activityData = new HashMap<>();
    }

    @When("the admin adds a new user with name {string}, email {string}, role {string}, status {string}, age {int}, fitness goals {string}, dietary preferences {string}, and dietary restrictions {string}")
    public void the_admin_adds_a_new_user_with_name_email_role_status_age_fitness_goals_dietary_preferences_and_dietary_restrictions(
        String name, String email, String role, String status, int age, String fitnessGoals, String dietaryPreferences, String dietaryRestrictions) {
        boolean isActive = status.equalsIgnoreCase("active");
        User user = new User(name, email, role, isActive, age, fitnessGoals, dietaryPreferences, dietaryRestrictions);
        boolean result = userService.addUser(user);
        assertTrue("User should be added successfully", result);
    }

    @Then("the user should be added successfully")
    public void the_user_should_be_added_successfully() {
        assertNotNull("User should exist", userService.getUser("Rand@email.com"));
    }

    @Then("the user with email {string} should exist in the system")
    public void the_user_with_email_should_exist_in_the_system(String email) {
        User user = userService.getUser(email);
        assertNotNull("User should exist in the system", user);
    }

    @When("a new instructor registration with email {string} is pending approval")
    public void a_new_instructor_registration_with_email_is_pending_approval(String email) {
        User user = new User("New Instructor", email, "Instructor", false, 0, "", "", "");
        boolean added = userService.addUser(user);
        assertTrue("Instructor registration should be added for approval", added);
    }

    @When("the admin approves the registration")
    public void the_admin_approves_the_registration() {
        boolean approved = userService.approveUser("instructor@example.com");
        assertTrue("Instructor should be approved", approved);
    }

    @Then("the instructor should be marked as approved")
    public void the_instructor_should_be_marked_as_approved() {
        User user = userService.getUser("instructor@example.com");
        assertTrue("User should be active", user.isActive());
    }

    @When("the admin requests user activity statistics")
    public void the_admin_requests_user_activity_statistics() {
        activityData = userService.getUserActivityStats();
    }

    @Then("the system should display user activity and engagement data")
    public void the_system_should_display_user_activity_and_engagement_data() {
        assertNotNull("Activity data should not be null", activityData);
        assertTrue("Activity data should contain engagement stats", activityData.size() > 0);
    }
}
