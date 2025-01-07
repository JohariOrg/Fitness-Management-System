package com.fitness.management;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceUtil {

	private static final Logger logger = LoggerFactory.getLogger(PersistenceUtil.class);

    // Default file paths
    private static String usersFile = "users_data.txt";
    private static String programsFile = "programs.dat";
    private static String clientProfileFilePath = "client_profile.dat";

    private PersistenceUtil() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }
    public static void setClientProfileFilePath(String path) {
        clientProfileFilePath = path;
    }

    // Setter for usersFile (for testability)
    public static void setUsersFilePath(String path) {
        usersFile = path;
    }

    // Setter for programsFile (for testability)
    public static void setProgramsFilePath(String path) {
        programsFile = path;
    }
  
    public static void saveUserData(List<User> users) {
        if (users == null || users.isEmpty()) {
            logger.warn("No user data to save.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile))) {
            for (User user : users) {
                String status = user.isActive() ? "Active" : "Inactive";
                writer.write(String.format("%s,%s,%s,%s,%d,%s,%s,%s",
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        status,
                        user.getAge(),
                        user.getFitnessGoals(),
                        user.getDietaryPreferences(),
                        user.getDietaryRestrictions()));
                writer.newLine();
            }
            logger.info("User data saved successfully to {}", usersFile);
        } catch (IOException e) {
            logger.error("Error saving user data: {}", e.getMessage(), e);
        }
    }
       
    
            public static List<User> loadUserData() {
                List<User> users = new ArrayList<>();
                File file = new File(usersFile);
                if (!file.exists()) {
                    logger.warn("User data file not found: {}", usersFile);
                    return users;
                }
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 8) {
                            String name = parts[0];
                            String email = parts[1];
                            String role = parts[2];
                            boolean isActive = "Active".equalsIgnoreCase(parts[3]);
                            int age = Integer.parseInt(parts[4]);
                            String fitnessGoals = parts[5];
                            String dietaryPreferences = parts[6];
                            String dietaryRestrictions = parts[7];
                            users.add(new User(name, email, role, isActive, age, fitnessGoals, dietaryPreferences, dietaryRestrictions));
                        }
                    }
                } catch (IOException e) {
                    logger.error("Error loading user data: {}", e.getMessage(), e);
                }
                return users;
            }
   
    public static void saveProgramData(List<Program> programs) {
        if (programs == null || programs.isEmpty()) {
            logger.warn("No program data to save.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(programsFile))) {
            oos.writeObject(programs); 
            logger.info("Program data saved successfully.");
        } catch (IOException e) {
            logger.error("Error saving program data: {}", e.getMessage(), e);
        }
    }


   
    @SuppressWarnings("unchecked")
    public static List<Program> loadProgramData() {
        List<Program> programs = new ArrayList<>();
        File file = new File(programsFile);
        if (!file.exists()) {
            logger.warn("Program data file not found: {}", programsFile);
            return programs;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            programs = (List<Program>) ois.readObject(); 
            logger.info("Program data loaded successfully.");
        } catch (IOException e) {
            logger.error("Error loading program data: {}", e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            logger.error("Error reading program data: Class not found - {}", e.getMessage(), e);
        }
        return programs;
    }



    public static void saveClientProfileData(List<Profile> profiles) {
        if (profiles == null || profiles.isEmpty()) {
            logger.warn("No client profiles to save.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("client_profiles.txt"))) {
            for (Profile profile : profiles) {
                writer.write(String.format("%s,%d,%s,%s,%s,%s",
                        profile.getName(),
                        profile.getAge(),
                        profile.getEmail(),
                        profile.getFitnessGoals(),
                        profile.getDietaryPreferences(),
                        profile.getDietaryRestrictions()));
                writer.newLine();
            }
            logger.info("Client profiles saved successfully to client_profiles.txt.");
        } catch (IOException e) {
            logger.error("Error saving client profiles: {}", e.getMessage(), e);
        }
    }


    public static List<Profile> loadClientProfileDataList() {
        List<Profile> profiles = new ArrayList<>();
        File file = new File("client_profiles.txt");
        if (!file.exists()) {
            logger.warn("Client profile data file not found: client_profiles.txt");
            return profiles;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String email = parts[2];
                    String fitnessGoals = parts[3];
                    String dietaryPreferences = parts[4];
                    String dietaryRestrictions = parts[5];
                    profiles.add(new Profile(name, age, email, fitnessGoals, dietaryPreferences, dietaryRestrictions));
                }
            }
            logger.info("Client profiles loaded successfully from client_profiles.txt.");
        } catch (IOException e) {
            logger.error("Error loading client profiles: {}", e.getMessage(), e);
        }
        return profiles;
    }


    public static void deleteClientProfileData() {
        Path filePath = Paths.get(clientProfileFilePath);
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
                logger.info("Client profile data deleted successfully.");
            } catch (IOException e) {
                logger.error("Failed to delete client profile data: " + e.getMessage(), e);
            }
        } else {
            logger.warn("Client profile data does not exist.");
        }
    }
}
