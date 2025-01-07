package com.fitness.management;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ClientProfileServiceTest {

    private ClientProfileService clientProfileService;
    private UserService mockUserService;
    private List<Profile> mockProfiles;

    @Before
    public void setUp() {
        mockUserService = new UserService() {
            private Map<String, User> users = new HashMap<>();

            @Override
            public User getUser(String email) {
                return users.get(email.toLowerCase());
            }

            @Override
            public boolean addUser(User user) {
                if (user.getName() == null || user.getName().isEmpty() ||
                    user.getEmail() == null || user.getEmail().isEmpty() ||
                    user.getRole() == null || user.getRole().isEmpty()) {
                    return false;
                }

                if (users.containsKey(user.getEmail().toLowerCase())) {
                    return false;
                }

                users.put(user.getEmail().toLowerCase(), user);
                return true;
            }

            @Override
            public boolean updateUser(String email, String name, String role, boolean active,
                                      int age, String fitnessGoals, String dietaryPreferences, String dietaryRestrictions) {
                User existingUser = getUser(email);
                if (existingUser != null) {
                    existingUser.setName(name);
                    existingUser.setAge(age);
                    existingUser.setFitnessGoals(fitnessGoals);
                    existingUser.setDietaryPreferences(dietaryPreferences);
                    existingUser.setDietaryRestrictions(dietaryRestrictions);
                    return true;
                }
                return false;
            }

            @Override
            public boolean removeUserByName(String name) {
                return users.entrySet().removeIf(entry -> entry.getValue().getName().equalsIgnoreCase(name));
            }
        };

        mockProfiles = new ArrayList<>();
        mockProfiles.add(new Profile("John Doe", 30, "john@example.com", "Lose weight", "Vegan", "None"));
        mockProfiles.add(new Profile("Jane Doe", 25, "jane@example.com", "Gain muscle", "Vegetarian", "None"));
    }

    @Test
    public void testCreateProfile() {
    	Profile newProfile = new Profile("Sam Smith", 28, "sam@example.com", "Stay fit", "Keto", "None");

        clientProfileService = new ClientProfileService();
        clientProfileService.createProfile(newProfile, mockUserService);

        User createdUser = mockUserService.getUser("sam@example.com");
        assertNotNull(createdUser);
        assertEquals("Sam Smith", createdUser.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProfile_NullProfile() {
        clientProfileService = new ClientProfileService();
        clientProfileService.createProfile(null, mockUserService);
    }

    @Test
    public void testViewProfile() {
        // Set up the mock profiles in the PersistenceUtilMock
        List<Profile> mockProfiles = new ArrayList<>();
        mockProfiles.add(new Profile("John Doe", 30, "john@example.com", "Lose weight", "Vegan", "None"));

        // Ensure PersistenceUtilMock has the profiles
        PersistenceUtilMock.setProfiles(mockProfiles);

        // Initialize the ClientProfileService (it uses PersistenceUtilMock instead of PersistenceUtil)
        clientProfileService = new ClientProfileService();

        // Attempt to view the profile
        Profile profile = clientProfileService.viewProfile("john@example.com");

        // Validate that the correct profile is returned
        assertNotNull("Profile should not be null", profile);
        assertEquals("John Doe", profile.getName());
        assertEquals(30, profile.getAge());
        assertEquals("john@example.com", profile.getEmail());
    }


    @Test
    public void testUpdateProfile() {
        clientProfileService = new ClientProfileService();

        clientProfileService.updateProfile("John Updated", 35, "john@example.com", "Build muscle",
                "Keto", "None", mockUserService);

        User updatedUser = mockUserService.getUser("john@example.com");
        assertNotNull(updatedUser);
        assertEquals("John Updated", updatedUser.getName());
    }

    @Test
    public void testDeleteProfile() {
        clientProfileService = new ClientProfileService();

        clientProfileService.deleteProfile("john@example.com", mockUserService);

        User deletedUser = mockUserService.getUser("john@example.com");
        assertNull(deletedUser);
    }
}
