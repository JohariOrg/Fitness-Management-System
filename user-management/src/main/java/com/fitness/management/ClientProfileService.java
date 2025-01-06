package com.fitness.management;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ClientProfileService.class); // Add logger
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
        logger.info("Profile created: {}", profile); // Replaced System.out.println

        String email = profile.getEmail();
        User user = new User(profile.getName(), email, "client", true);
        if (!userService.addUser(user)) {
            logger.warn("User with this email already exists."); // Replaced System.out.println
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
            logger.info("Profile found: {}", loadedProfile); // Replaced System.out.println
            return loadedProfile;
        } else {
            logger.warn("No profile found for the given email."); // Replaced System.out.println
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

        // Save updated profile persistently
        List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();
        profiles.removeIf(p -> p.getEmail().equalsIgnoreCase(email));
        profiles.add(profile);
        PersistenceUtil.saveClientProfileData(profiles);

        logger.info("Profile updated and saved: {}", profile); // Replaced System.out.println
    }

    public void deleteProfile(UserService userService) {
        if (profile == null) {
            logger.warn("No profile exists to delete."); // Replaced System.out.println
            return;
        }
        logger.info("Profile deleted: {}", profile); // Replaced System.out.println
        userService.removeUserByName(profile.getName());
        PersistenceUtil.deleteClientProfileData();
        this.profile = null;
    }
}
