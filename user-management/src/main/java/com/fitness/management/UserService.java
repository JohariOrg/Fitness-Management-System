package com.fitness.management;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class UserService {
	private Map<String, User> users = new HashMap<>();
	 private static final Logger logger = LoggerFactory.getLogger(UserService.class); // Logger definition
	
	 public boolean addUser(User user) {
	        if (users.containsKey(user.getEmail())) {
	            logger.warn("User with this email already exists: {}", user.getEmail()); // Replacing System.out.println
	            return false;
	        }
	        users.put(user.getEmail(), user);
	        logger.info("User added successfully: {}", user.getEmail()); // Optional: Log user addition
	        return true;
	    }


	
public boolean updateUser(String email, String name, String role) {	
User user = users.get(email);
if(user == null) {
	return false;
}
user.setName(name);
user.setRole(role);
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

	    public void setUsers(Map<String, User> users) {
	        this.users = users;
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
	            return true;
	        }
	        return false; 
	    }
	    

	
} 