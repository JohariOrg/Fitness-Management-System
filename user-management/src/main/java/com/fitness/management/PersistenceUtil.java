package com.fitness.management;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceUtil {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceUtil.class); // Add logger
    private static final String USERS_FILE = "users_data.txt";
    private static final String PROGRAMS_FILE = "programs.dat";

    public static void saveUserData(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                String status = user.isActive() ? "Active" : "Inactive";
                writer.write(user.getName() + "," + user.getEmail() + "," + user.getRole() + "," + status);
                writer.newLine();
            }
            logger.info("User data saved successfully."); // Replaced System.out.println
        } catch (IOException e) {
            logger.error("Error saving user data: {}", e.getMessage()); // Replaced System.out.println
        }
    }

    public static List<User> loadUserData() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String email = parts[1];
                    String role = parts[2];
                    boolean isActive = parts[3].equalsIgnoreCase("Active");
                    users.add(new User(name, email, role, isActive));
                }
            }
        } catch (FileNotFoundException e) {
            logger.warn("User data file not found. Initializing empty data."); // Replaced System.out.println
        } catch (IOException e) {
            logger.error("Error loading user data: {}", e.getMessage()); // Replaced System.out.println
        }
        return users;
    }

    public static void saveProgramData(List<Program> programs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PROGRAMS_FILE))) {
            oos.writeObject(programs);
            logger.info("Programs data saved successfully."); // Uncommented and replaced System.out.println
        } catch (IOException e) {
            logger.error("Error saving programs: {}", e.getMessage()); // Replaced System.err.println
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Program> loadProgramData() {
        File file = new File(PROGRAMS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Program>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error loading programs: {}", e.getMessage()); // Replaced System.err.println
            return new ArrayList<>();
        }
    }

    public static void saveClientProfileData(List<Profile> profiles) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("client_profiles.dat"))) {
            oos.writeObject(profiles);
            logger.info("Client profiles saved successfully."); // Replaced System.out.println
        } catch (IOException e) {
            logger.error("Error saving client profiles: {}", e.getMessage()); // Replaced System.out.println
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Profile> loadClientProfileDataList() {
        File file = new File("client_profiles.dat");
        if (!file.exists()) {
            return new ArrayList<>(); // Return an empty list if the file doesn't exist
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject(); // Read the object
            if (obj instanceof List) { // Ensure it is a List
                return (List<Profile>) obj; // Cast safely after checking
            } else {
                logger.error("Error: Invalid data format in file."); // Replaced System.out.println
                return new ArrayList<>(); // Return an empty list if data is invalid
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error loading client profiles: {}", e.getMessage()); // Replaced System.out.println
            return new ArrayList<>(); // Return an empty list in case of an exception
        }
    }

    public static void deleteClientProfileData() {
        File file = new File("client_profile.dat");
        if (file.exists()) {
            if (file.delete()) {
                logger.info("Client profile data deleted successfully."); // Added logging for successful deletion
            } else {
                logger.error("Failed to delete client profile data."); // Added logging for failed deletion
            }
        }
    }
}
