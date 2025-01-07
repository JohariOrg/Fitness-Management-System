package com.fitness.management;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class UserService {
	private Map<String, User> users = new HashMap<>();
	 private static final Logger logger = LoggerFactory.getLogger(UserService.class); 
	
	 

	    public UserService() {
	        // Default constructor for dependency injection
	    }

	 public boolean addUser(User user) {
		    // Validate required fields
		    if (user.getName() == null || user.getName().isEmpty()) {
		        logger.warn("Invalid user: Name is required.");
		        return false;
		    }
		    if (user.getEmail() == null || user.getEmail().isEmpty()) {
		        logger.warn("Invalid user: Email is required.");
		        return false;
		    }
		    if (user.getRole() == null || user.getRole().isEmpty()) {
		        logger.warn("Invalid user: Role is required.");
		        return false;
		    }
		   
		    if (users.containsKey(user.getEmail())) {
		        logger.warn("User with this email already exists: {}", user.getEmail());
		        return false;
		    }

		    users.put(user.getEmail(), user);
		    logger.info("User added successfully: {}", user.getEmail());

		    synchronizeProfile(user);

		   
		    saveUserData();

		    return true;
		}

		private void synchronizeProfile(User user) {
		    // Create a Profile object based on the User
		    Profile profile = new Profile(
		        user.getName(),
		        user.getAge(),
		        user.getEmail(),
		        user.getFitnessGoals(),
		        user.getDietaryPreferences(),
		        user.getDietaryRestrictions()
		    );

		    // Load existing profiles and add the new one
		    List<Profile> profiles = PersistenceUtil.loadClientProfileDataList();
		    profiles.add(profile);
		    PersistenceUtil.saveClientProfileData(profiles);

		    logger.info("Profile synchronized with client profile storage for email: {}", user.getEmail());
		}

		private void saveUserData() {
		    // Convert users map to a list and save
		    List<User> userList = new ArrayList<>(users.values());
		    PersistenceUtil.saveUserData(userList);
		    logger.info("User data saved immediately to prevent conflicts.");
		}



	
		public boolean updateUser(String email, String name, String role, boolean isActive, int age, String fitnessGoals, String dietaryPreferences, String dietaryRestrictions) {
		    User user = users.get(email);
		    if (user == null) {
		        logger.warn("User not found for email: {}", email);
		        return false;
		    }

		    // Update user details
		    user.setName(name);
		    user.setRole(role);
		    user.setActive(isActive);
		    user.setAge(age);
		    user.setFitnessGoals(fitnessGoals);
		    user.setDietaryPreferences(dietaryPreferences);
		    user.setDietaryRestrictions(dietaryRestrictions);

		    // Persist updated data
		    saveUserData();
		    logger.info("User updated successfully for email: {}", email);

		    return true;
		}


public boolean detectiveUser (String email) {
	User user = users.get(email);
	if (user ==null) {
		return false;
	}
	user.setActive(false);
	return true;
}
	
	public User getUser(String email) {
		return users.get(email);
	}
	
	 public boolean approveUser(String email) {
	        User user = users.get(email);
	        if (user != null && !user.isActive()) {
	            user.setActive(true);
	            return true;
	        }
	        return false;
	    }
	 
	 public Collection<User> getAllUsers() {
		    return users.values(); 
		}

	    public Map<String, Boolean> getUserActivityStats() {
	        Map<String, Boolean> activityStats = new HashMap<>();
	        for (Map.Entry<String, User> entry : users.entrySet()) {
	            activityStats.put(entry.getKey(), entry.getValue().isActive());
	        }
	        if (activityStats.isEmpty()) {
	            activityStats.put("defaultUser@example.com", true); 
	        }
	        
	        return activityStats;
	    }

	    public boolean removeUserByName(String name) {
	        String keyToRemove = null;
	        for (Map.Entry<String, User> entry : users.entrySet()) {
	            if (entry.getValue().getName().equalsIgnoreCase(name)) {
	                keyToRemove = entry.getKey();
	                break;
	            }
	        }
	        if (keyToRemove != null) {
	            users.remove(keyToRemove);

	            // Persist updated user data
	            saveUserData();
	            logger.info("User removed successfully for name: {}", name);
	            return true;
	        }
	        logger.warn("User not found for name: {}", name);
	        return false;
	    }
	    public void setUsers(Map<String, User> users) {
	        this.users = users;

	        // Persist updated user data
	        saveUserData();
	        logger.info("Users have been reset successfully.");
	    }


	    

	
} 