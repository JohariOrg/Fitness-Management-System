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
        this.profile = profile;
        logger.info("Profile created: {}", profile); 

        String email = profile.getEmail();
        User user = new User(profile.getName(), email, "client", true);
        if (!userService.addUser(user)) {
            logger.warn("User with this email already exists."); 
        }

        // Save the profile persistently
        List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();
        profiles.add(profile);
        PersistenceUtil.saveClientProfileData(profiles);
    }

    public Profile viewProfile(String email) {
        List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();
        Profile loadedProfile = profiles.stream()
                                        .filter(p -> p.getEmail().equalsIgnoreCase(email))
                                        .findFirst()
                                        .orElse(null);

        if (loadedProfile != null) {
            logger.info("Profile found: {}", loadedProfile); 
            return loadedProfile;
        } else {
            logger.warn("No profile found for the given email."); 
            return null;
        }
    }

    public void updateProfile(String name, int age, String email, String fitnessGoals, String dietaryPreferences, String dietaryRestrictions) {
        if (profile == null) {
            throw new IllegalStateException("No profile exists to update.");
        }

        profile.setName(name);
        profile.setAge(age);
        profile.setEmail(email);
        profile.setFitnessGoals(fitnessGoals);
        profile.setDietaryPreferences(dietaryPreferences);
        profile.setDietaryRestrictions(dietaryRestrictions);

        
        List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();
        profiles.removeIf(p -> p.getEmail().equalsIgnoreCase(email));
        profiles.add(profile);
        PersistenceUtil.saveClientProfileData(profiles);

        logger.info("Profile updated and saved: {}", profile); 
    }

    public void deleteProfile(UserService userService) {
        if (profile == null) {
            logger.warn("No profile exists to delete."); 
            return;
        }
        logger.info("Profile deleted: {}", profile); 
        userService.removeUserByName(profile.getName());
        PersistenceUtil.deleteClientProfileData();
        this.profile = null;
    }
}
