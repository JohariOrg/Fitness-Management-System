package com.fitness.management.steps;

import com.fitness.management.UserService;
import com.fitness.management.ClientProfileService;
import com.fitness.management.Profile;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountManagementSteps {

    private ClientProfileService clientProfileService;
    private Profile createdProfile;
    private UserService userService;
    private String currentClientEmail; 

    public AccountManagementSteps(ClientProfileService clientProfileService, UserService userService) {
        this.clientProfileService = clientProfileService;
        this.userService = userService;
    }

    @Given("the client dashboard is loaded")
    public void the_client_dashboard_is_loaded() {
        System.out.println("Client dashboard loaded.");
    }

    @When("the client creates a new profile with the following details:")
    public void the_client_creates_a_new_profile_with_the_following_details(DataTable dataTable) {
        Map<String, String> profileData = dataTable.asMap(String.class, String.class);

        createdProfile = new Profile(
            profileData.get("Name"),
            Integer.parseInt(profileData.get("Age")),
            profileData.get("Email"),
            profileData.get("Fitness Goals"),
            profileData.get("Dietary Preferences"),
            profileData.get("Dietary Restrictions")
        );

        currentClientEmail = profileData.get("Email"); 
        clientProfileService.createProfile(createdProfile, userService);
    }

    @Then("the profile should be created successfully")
    public void the_profile_should_be_created_successfully() {
        assertNotNull("Profile should not be null", createdProfile);
        System.out.println("Profile created successfully: " + createdProfile);
    }

    @Given("the client has already created a profile")
    public void the_client_has_already_created_a_profile() {
        createdProfile = new Profile("John Doe", 30, "john.doe@example.com", "Weight Loss", "Vegetarian", "Gluten-Free");
        currentClientEmail = "john.doe@example.com"; 
        clientProfileService.createProfile(createdProfile, userService);
    }

    @When("the client views their profile")
    public void the_client_views_their_profile() {
        createdProfile = clientProfileService.viewProfile(currentClientEmail);
    }

    @Then("the system should display the profile with the following details:")
    public void the_system_should_display_the_profile_with_the_following_details(DataTable dataTable) {
        Map<String, String> expectedData = dataTable.asMap(String.class, String.class);

        assertEquals(expectedData.get("Name"), createdProfile.getName());
        assertEquals(Integer.parseInt(expectedData.get("Age")), createdProfile.getAge());
        assertEquals(expectedData.get("Fitness Goals"), createdProfile.getFitnessGoals());
        assertEquals(expectedData.get("Dietary Preferences"), createdProfile.getDietaryPreferences());
        assertEquals(expectedData.get("Dietary Restrictions"), createdProfile.getDietaryRestrictions());

        System.out.println("Profile details match expected values.");
    }

    @When("the client updates their profile with the following details:")
    public void the_client_updates_their_profile_with_the_following_details(DataTable dataTable) {
        Map<String, String> updatedData = dataTable.asMap(String.class, String.class);

        clientProfileService.updateProfile(
            updatedData.get("Name"),
            Integer.parseInt(updatedData.get("Age")),
            currentClientEmail,
            updatedData.get("Fitness Goals"),
            updatedData.get("Dietary Preferences"),
            updatedData.get("Dietary Restrictions")
        );

        createdProfile = clientProfileService.viewProfile(currentClientEmail);
        System.out.println("Profile updated successfully: " + createdProfile);
    }

    @Then("the profile should be updated successfully")
    public void the_profile_should_be_updated_successfully() {
        assertNotNull("Profile should not be null after update", createdProfile);
        System.out.println("Profile updated successfully: " + createdProfile);
    }

    @When("the client deletes their profile")
    public void the_client_deletes_their_profile() {
        clientProfileService.deleteProfile(userService);
    }

    @Then("the profile should be deleted successfully")
    public void the_profile_should_be_deleted_successfully() {
        Profile profile = clientProfileService.viewProfile(currentClientEmail);
        assertEquals("Profile should be null after deletion", null, profile);
        System.out.println("Profile deleted successfully.");
    }
}
