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

    private static final String USERS_FILE = "users_data.txt";
    private static final String PROGRAMS_FILE = "programs.dat";

    // Private constructor to prevent instantiation
    private PersistenceUtil() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    // Method to save user data to a file
    public static void saveUserData(List<User> users) {
        if (users == null || users.isEmpty()) {
            logger.warn("No user data to save.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                String status = user.isActive() ? "Active" : "Inactive";
                writer.write(String.format("%s,%s,%s,%s",
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        status));
                writer.newLine();
            }
            logger.info("User data saved successfully.");
        } catch (IOException e) {
            logger.error("Error saving user data: {}", e.getMessage(), e);
        }
    }

    // Method to load user data from a file
    public static List<User> loadUserData() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            logger.warn("User data file not found: {}", USERS_FILE);
            return users;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String email = parts[1];
                    String role = parts[2];
                    boolean isActive = "Active".equalsIgnoreCase(parts[3]);
                    users.add(new User(name, email, role, isActive));
                }
            }
        } catch (IOException e) {
            logger.error("Error loading user data: {}", e.getMessage(), e);
        }
        return users;
    }

    // Method to save program data to a file
    public static void saveProgramData(List<ProgramDetails> programs) {
        if (programs == null || programs.isEmpty()) {
            logger.warn("No program data to save.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROGRAMS_FILE))) {
            oos.writeObject(programs);
            logger.info("Program data saved successfully.");
        } catch (IOException e) {
            logger.error("Error saving program data: {}", e.getMessage(), e);
        }
    }

    // Method to load program data from a file
    @SuppressWarnings("unchecked")
    public static List<ProgramDetails> loadProgramData() {
        List<ProgramDetails> programs = new ArrayList<>();
        File file = new File(PROGRAMS_FILE);
        if (!file.exists()) {
            logger.warn("Program data file not found: {}", PROGRAMS_FILE);
            return programs;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            programs = (List<ProgramDetails>) ois.readObject();
        } catch (IOException e) {
            logger.error("Error loading program data: {}", e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            logger.error("Error reading program data: Class not found - {}", e.getMessage(), e);
        }
        return programs;
    }


    public static void saveClientProfileData(List<Profile> profiles) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("client_profiles.dat"))) {
            oos.writeObject(profiles);
            logger.info("Client profiles saved successfully."); 
        } catch (IOException e) {
            logger.error("Error saving client profiles: {}", e.getMessage()); 
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Profile> loadClientProfileDataList() {
        File file = new File("client_profiles.dat");
        if (!file.exists()) {
            return new ArrayList<>(); 
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject(); 
            if (obj instanceof List) { 
                return (List<Profile>) obj; 
            } else {
                logger.error("Error: Invalid data format in file."); 
                return new ArrayList<>(); 
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error loading client profiles: {}", e.getMessage()); 
            return new ArrayList<>(); 
        }
    }

    public static void deleteClientProfileData() {
        Path filePath = Paths.get("client_profile.dat");
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
