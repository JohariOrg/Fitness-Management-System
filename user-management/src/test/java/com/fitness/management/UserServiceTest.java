package com.fitness.management;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import java.util.Map;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();

        
        User user1 = new User("John Doe", "john@example.com", "Client", true, 30, "Lose weight", "Vegan", "None");
        User user2 = new User("Jane Doe", "jane@example.com", "Admin", false, 25, "Gain muscle", "Vegetarian", "None");

        Map<String, User> initialUsers = new HashMap<>();
        initialUsers.put(user1.getEmail(), user1);
        initialUsers.put(user2.getEmail(), user2);

        userService.setUsers(initialUsers);
    }

    @Test
    public void testAddUser() {
        User newUser = new User("Sam Smith", "sam@example.com", "Client", true, 28, "Stay fit", "Keto", "None");

        boolean result = userService.addUser(newUser);
        assertTrue("User should be added successfully", result);

        User addedUser = userService.getUser("sam@example.com");
        assertNotNull("Added user should not be null", addedUser);
        assertEquals("Sam Smith", addedUser.getName());
    }

    @Test
    public void testAddUserWithDuplicateEmail() {
        User duplicateUser = new User("Duplicate", "john@example.com", "Client", true, 30, "Lose weight", "Vegan", "None");

        boolean result = userService.addUser(duplicateUser);
        assertFalse("User with duplicate email should not be added", result);
    }

    @Test
    public void testUpdateUser() {
        boolean result = userService.updateUser("john@example.com", "John Updated", "Admin", false, 35, "Build muscle", "Vegetarian", "None");
        assertTrue("User should be updated successfully", result);

        User updatedUser = userService.getUser("john@example.com");
        assertNotNull("Updated user should not be null", updatedUser);
        assertEquals("John Updated", updatedUser.getName());
        assertEquals(35, updatedUser.getAge());
    }

    @Test
    public void testUpdateNonExistentUser() {
        boolean result = userService.updateUser("nonexistent@example.com", "Nonexistent", "Client", true, 30, "Stay fit", "Keto", "None");
        assertFalse("Updating a non-existent user should return false", result);
    }

    @Test
    public void testDetectiveUser() {
        boolean result = userService.detectiveUser("jane@example.com");
        assertTrue("User should be deactivated successfully", result);

        User deactivatedUser = userService.getUser("jane@example.com");
        assertFalse("User should be inactive", deactivatedUser.isActive());
    }

    @Test
    public void testDetectiveNonExistentUser() {
        boolean result = userService.detectiveUser("nonexistent@example.com");
        assertFalse("Deactivating a non-existent user should return false", result);
    }

    @Test
    public void testApproveUser() {
        boolean result = userService.approveUser("jane@example.com");
        assertTrue("User should be approved successfully", result);

        User approvedUser = userService.getUser("jane@example.com");
        assertTrue("User should be active", approvedUser.isActive());
    }

    @Test
    public void testApproveNonExistentUser() {
        boolean result = userService.approveUser("nonexistent@example.com");
        assertFalse("Approving a non-existent user should return false", result);
    }

    @Test
    public void testGetAllUsers() {
        assertEquals("There should be 2 users initially", 2, userService.getAllUsers().size());
    }

    @Test
    public void testGetUserActivityStats() {
        Map<String, Boolean> stats = userService.getUserActivityStats();
        assertEquals("There should be 2 activity stats initially", 2, stats.size());
        assertTrue("John should be active", stats.get("john@example.com"));
        assertFalse("Jane should be inactive", stats.get("jane@example.com"));
    }

    @Test
    public void testRemoveUserByName() {
        boolean result = userService.removeUserByName("John Doe");
        assertTrue("User should be removed successfully", result);

        User removedUser = userService.getUser("john@example.com");
        assertNull("Removed user should be null", removedUser);
    }

    @Test
    public void testRemoveNonExistentUserByName() {
        boolean result = userService.removeUserByName("Nonexistent User");
        assertFalse("Removing a non-existent user should return false", result);
    }
}
