package com.fitness.management.steps;

import com.fitness.management.ClientProfileService;
import com.fitness.management.UserService;
import com.fitness.management.Profile;
import com.fitness.management.User;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before; 
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountManagementSteps {

    private ClientProfileService clientProfileService;
    private UserService userService;
    private Profile createdProfile;
    private String currentClientEmail;
    private static final Logger logger = LoggerFactory.getLogger(AccountManagementSteps.class);

    @Before
    public void setup() {
        clientProfileService = new ClientProfileService();
        userService = new UserService();

        
        userService.setUsers(new HashMap<>());
    }


    @Given("the client dashboard is loaded")
    public void the_client_dashboard_is_loaded() {
        logger.info("Client dashboard loaded.");
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
        logger.info("Profile created successfully: {}", createdProfile);
    }

    @Given("the client has already created a profile")
    public void the_client_has_already_created_a_profile() {
        Profile existingProfile = new Profile("John Doe", 30, "john.doe@example.com", "Weight Loss", "Vegetarian", "Gluten-Free");
        currentClientEmail = "john.doe@example.com";
        clientProfileService.createProfile(existingProfile, userService);
    }

    @When("the client views their profile")
    public void the_client_views_their_profile() {
        createdProfile = clientProfileService.viewProfile(currentClientEmail);
        assertNotNull("Profile should not be null when viewed", createdProfile);
        logger.info("Profile successfully retrieved: {}", createdProfile);
    }


    @Then("the system should display the profile with the following details:")
    public void the_system_should_display_the_profile_with_the_following_details(DataTable dataTable) {
        Map<String, String> expectedData = dataTable.asMap(String.class, String.class);

        assertNotNull("Profile should not be null before checking details", createdProfile);

        assertEquals(expectedData.get("Name"), createdProfile.getName());
        assertEquals(Integer.parseInt(expectedData.get("Age")), createdProfile.getAge());
        assertEquals(expectedData.get("Fitness Goals"), createdProfile.getFitnessGoals());
        assertEquals(expectedData.get("Dietary Preferences"), createdProfile.getDietaryPreferences());
        assertEquals(expectedData.get("Dietary Restrictions"), createdProfile.getDietaryRestrictions());

        logger.info("Profile details match expected values: {}", createdProfile);
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
            updatedData.get("Dietary Restrictions"),
            userService 
        );
    }

    @Then("the profile should be updated successfully")
    public void the_profile_should_be_updated_successfully() {
        Profile updatedProfile = clientProfileService.viewProfile(currentClientEmail);
        assertNotNull("Updated profile should not be null", updatedProfile);

        logger.info("Updated Profile Details:");
        logger.info("Name: {}", updatedProfile.getName());
        logger.info("Age: {}", updatedProfile.getAge());
        logger.info("Email: {}", updatedProfile.getEmail());
        logger.info("Fitness Goals: {}", updatedProfile.getFitnessGoals());
        logger.info("Dietary Preferences: {}", updatedProfile.getDietaryPreferences());
        logger.info("Dietary Restrictions: {}", updatedProfile.getDietaryRestrictions());
    }

    @When("the client deletes their profile")
    public void the_client_deletes_their_profile() {
        try {
            clientProfileService.deleteProfile(currentClientEmail, userService);
            System.out.println("Profile deleted successfully for email: " + currentClientEmail);
        } catch (IllegalStateException e) {
            logger.error("Error during profile deletion: {}", e.getMessage());
        }
    }

    @Then("the profile should be deleted successfully")
    public void the_profile_should_be_deleted_successfully() {
        // Verify the profile is null after deletion
        Profile profile = clientProfileService.viewProfile(currentClientEmail);
        assertEquals("Profile should be null after deletion", null, profile);

        // Verify the user is also null in UserService
        User user = userService.getUser(currentClientEmail);
        assertEquals("User should also be removed from UserService", null, user);

        logger.info("Profile and associated user deleted successfully.");
    }



}
