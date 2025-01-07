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
                users.put(user.getEmail().toLowerCase(), user);
                return true;
            }

            @Override
            public boolean updateUser(String email, String name, String role, boolean active, int age,
                                      String fitnessGoals, String dietaryPreferences, String dietaryRestrictions) {
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

        // Add baseline users and profiles for testing
        User user = new User(
                "John Doe",
                "john.doe@example.com",
                "Client",
                true,
                30,
                "Lose weight",
                "Vegan",
                "None"
        );
        mockUserService.addUser(user);

        List<Profile> baselineProfiles = new ArrayList<>();
        baselineProfiles.add(new Profile(
                "John Doe",
                30,
                "john.doe@example.com",
                "Lose weight",
                "Vegan",
                "None"
        ));
        PersistenceUtilMock.setProfiles(baselineProfiles);

        clientProfileService = new ClientProfileService();
    }

    @Test
    public void testConstructorWithEmail() {
        // Test the constructor that accepts an email
        clientProfileService = new ClientProfileService("john.doe@example.com");

        Profile loadedProfile = clientProfileService.viewProfile("john.doe@example.com");
        assertNotNull("Profile should not be null", loadedProfile);
        assertEquals("John Doe", loadedProfile.getName());
        assertEquals(30, loadedProfile.getAge());
    }

    @Test
    public void testCreateProfile_UserAlreadyExists() {
        Profile existingProfile = new Profile("John Doe", 30, "john.doe@example.com", "Lose weight", "Vegan", "None");

        clientProfileService.createProfile(existingProfile, mockUserService);

        // Ensure a warning is logged and no duplicate user is created
        User existingUser = mockUserService.getUser("john.doe@example.com");
        assertNotNull(existingUser);
        assertEquals("John Doe", existingUser.getName());
    }

    @Test
    public void testUpdateProfile_UserUpdateFails() {
        try {
            clientProfileService.updateProfile("John Updated", 35, "john.doe@example.com", "Build muscle",
                    "Keto", "None", new UserService() {
                        @Override
                        public boolean updateUser(String email, String name, String role, boolean active, int age,
                                                  String fitnessGoals, String dietaryPreferences, String dietaryRestrictions) {
                            return false; // Simulate failure
                        }
                    });
            fail("Expected IllegalStateException due to user update failure.");
        } catch (IllegalStateException e) {
            assertEquals("Failed to synchronize user data.", e.getMessage());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteProfile_ProfileDoesNotExist() {
        clientProfileService.deleteProfile("nonexistent@example.com", mockUserService);
    }

    @Test
    public void testDeleteProfile_UserRemovalFails() {
        clientProfileService = new ClientProfileService();

        // Simulate user removal failure
        mockUserService = new UserService() {
            @Override
            public boolean removeUserByName(String name) {
                return false; // Simulate failure
            }
        };

        clientProfileService.deleteProfile("john.doe@example.com", mockUserService);

        // Ensure the profile is still deleted
        Profile deletedProfile = clientProfileService.viewProfile("john.doe@example.com");
        assertNull("Profile should be deleted even if user removal fails", deletedProfile);
    }
}
