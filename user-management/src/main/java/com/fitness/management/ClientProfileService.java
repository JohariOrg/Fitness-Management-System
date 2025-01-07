package com.fitness.management;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ClientProfileService.class); 
    private Profile profile;

    public ClientProfileService() {
        this.profile = null;
    }

    public ClientProfileService(String email) {
        List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();
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
        // Always load fresh profile data from persistence
        List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();

        // Find the profile with the matching email
        Profile matchedProfile = profiles.stream()
                                          .filter(profile -> profile.getEmail().equalsIgnoreCase(email))
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


    public void updateProfile(String name, int age, String email, String fitnessGoals, String dietaryPreferences, String dietaryRestrictions, UserService userService) {
        
        List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();

        
        Profile profileToUpdate = profiles.stream()
                                           .filter(p -> p.getEmail().equalsIgnoreCase(email))
                                           .findFirst()
                                           .orElse(null);

        if (profileToUpdate == null) {
            logger.warn("No profile exists to update for email: {}", email);
            throw new IllegalStateException("Profile does not exist.");
        }

        
        profileToUpdate.setName(name);
        profileToUpdate.setAge(age);
        profileToUpdate.setFitnessGoals(fitnessGoals);
        profileToUpdate.setDietaryPreferences(dietaryPreferences);
        profileToUpdate.setDietaryRestrictions(dietaryRestrictions);

        
        boolean updateSuccess = userService.updateUser(email, name, "client", true, age, fitnessGoals, dietaryPreferences, dietaryRestrictions);
        if (!updateSuccess) {
            logger.warn("Failed to update user data for email: {}", email);
            throw new IllegalStateException("Failed to synchronize user data.");
        }

        
        profiles.removeIf(p -> p.getEmail().equalsIgnoreCase(email));
        profiles.add(profileToUpdate);
        PersistenceUtil.saveClientProfileData(profiles);

        logger.info("Profile updated successfully and synchronized for email: {}", email);
    }



    public void deleteProfile(String email, UserService userService) {
        // Load all profiles from persistent storage
        List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();

        // Find the profile to delete
        Profile profileToDelete = profiles.stream()
                                           .filter(p -> p.getEmail().equalsIgnoreCase(email))
                                           .findFirst()
                                           .orElse(null);

        if (profileToDelete == null) {
            logger.warn("No profile exists to delete for email: {}", email);
            throw new IllegalStateException("Profile does not exist.");
        }

        // Remove the profile from the list
        profiles.removeIf(p -> p.getEmail().equalsIgnoreCase(email));

        // Save the updated profiles list to persistence
        PersistenceUtil.saveClientProfileData(profiles);
        logger.info("Profile deleted successfully from persistence for email: {}", email);

        // Synchronize deletion with UserService
        boolean userRemoved = userService.removeUserByName(profileToDelete.getName());
        if (!userRemoved) {
            logger.warn("Failed to remove user from UserService for email: {}", email);
        } else {
            logger.info("User synchronized and removed from UserService for email: {}", email);
        }
    }

}
