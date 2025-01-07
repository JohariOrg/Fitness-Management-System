package com.fitness.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;



public class ClientProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ClientProfileService.class);
    private Profile profile;

    public ClientProfileService() {
        this.profile = null;
    }

    public ClientProfileService(String email) {
     
        List<Profile> profiles = PersistenceUtilMock.loadClientProfileDataList();
        this.profile = profiles.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public void createProfile(Profile profile, UserService userService) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile cannot be null.");
        }

        User existingUser = userService.getUser(profile.getEmail());
        if (existingUser != null) {
            logger.warn("User with email {} already exists. Skipping creation.", profile.getEmail());
            return;
        }

        User newUser = new User(
                profile.getName(),
                profile.getEmail(),
                "Client",
                true,
                profile.getAge(),
                profile.getFitnessGoals(),
                profile.getDietaryPreferences(),
                profile.getDietaryRestrictions()
        );
        userService.addUser(newUser);
        logger.info("Profile created successfully for email: {}", profile.getEmail());
    }

    public Profile viewProfile(String email) {
      
        List<Profile> profiles = PersistenceUtilMock.loadClientProfileDataList();

        Profile matchedProfile = profiles.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (matchedProfile != null) {
            logger.info("Profile found for email: {}", email);
            return matchedProfile;
        } else {
            logger.warn("No profile found for the given email: {}", email);
            return null;
        }
    }

    public void updateProfile(String name, int age, String email, String fitnessGoals,
                              String dietaryPreferences, String dietaryRestrictions,
                              UserService userService) {
        // --- CHANGE HERE: use the mock to load profiles ---
        List<Profile> profiles = PersistenceUtilMock.loadClientProfileDataList();

        Profile profileToUpdate = profiles.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (profileToUpdate == null) {
            logger.warn("No profile exists to update for email: {}", email);
            throw new IllegalStateException("Profile does not exist.");
        }

        // Update in-memory profile fields
        profileToUpdate.setName(name);
        profileToUpdate.setAge(age);
        profileToUpdate.setFitnessGoals(fitnessGoals);
        profileToUpdate.setDietaryPreferences(dietaryPreferences);
        profileToUpdate.setDietaryRestrictions(dietaryRestrictions);

        // Update the corresponding User
        boolean updateSuccess = userService.updateUser(
                email, name, "client", true, age,
                fitnessGoals, dietaryPreferences, dietaryRestrictions);

        if (!updateSuccess) {
            logger.warn("Failed to update user data for email: {}", email);
            throw new IllegalStateException("Failed to synchronize user data.");
        }

        // Replace the old Profile
        profiles.removeIf(p -> p.getEmail().equalsIgnoreCase(email));
        profiles.add(profileToUpdate);

        // --- CHANGE HERE: use the mock to save profiles ---
        PersistenceUtilMock.saveClientProfileData(profiles);

        logger.info("Profile updated successfully and synchronized for email: {}", email);
    }

    public void deleteProfile(String email, UserService userService) {
        // --- CHANGE HERE: use the mock to load profiles ---
        List<Profile> profiles = PersistenceUtilMock.loadClientProfileDataList();

        Profile profileToDelete = profiles.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (profileToDelete == null) {
            logger.warn("No profile exists to delete for email: {}", email);
            throw new IllegalStateException("Profile does not exist.");
        }

        profiles.removeIf(p -> p.getEmail().equalsIgnoreCase(email));

        // --- CHANGE HERE: use the mock to save profiles ---
        PersistenceUtilMock.saveClientProfileData(profiles);
        logger.info("Profile deleted successfully from persistence for email: {}", email);

        // Also remove the matching User
        boolean userRemoved = userService.removeUserByName(profileToDelete.getName());
        if (!userRemoved) {
            logger.warn("Failed to remove user from UserService for email: {}", email);
        } else {
            logger.info("User synchronized and removed from UserService for email: {}", email);
        }
    }
}
