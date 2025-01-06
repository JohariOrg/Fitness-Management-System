package com.fitness.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Predicate;


public class MainMenu {
	  private static final Logger logger = LoggerFactory.getLogger(MainMenu.class);
	    private static UserService userService = new UserService();
	    private static ProgramService programService = new ProgramService();
	    private static ClientProfileService clientProfileService = new ClientProfileService();
	    private static Map<String, Integer> activityData = new HashMap<>();
	    private static final String ENTER_YOUR_CHOICE = "Enter your choice: ";
	    private static final String INVALID_CHOICE = "Invalid choice. Please try again.";
	    private static final String USER_NOT_FOUND = "User not found.";
	    private static final String NO_PROGRAMS_FOUND = "No programs found";
	    private static final String ACTIVE = "active";
	    private static final String ENTER_YOUR_EMAIL = "Enter your email:";
	    private static final String BACK_TO_MAIN_MENU = "Back to Main Menu";
	    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        
        loadAllData();

        
        while (!exit) {
            exit = handleMenu(scanner, "\n=== Main Menu ===",
                List.of("Admin Menu", "Instructor Menu", "Client Menu", "Exit"),
                choice -> {
                    switch (choice) {
                        case 1 -> manageAdminMenu(scanner);
                        case 2 -> manageInstructorMenu(scanner);
                        case 3 -> manageClientMenu(scanner);
                        case 4 -> {
                            saveAllData();
                            logger.info("Exiting the system...");
                            return true;
                        }
                        default -> logger.warn(INVALID_CHOICE);
                    }
                    return false;
                });
        }
        scanner.close();
    }

    private static boolean handleMenu(Scanner scanner, String menuTitle, List<String> options, Predicate<Integer> menuHandler) {
        boolean exit = false;
        while (!exit) {
            logger.info(menuTitle);
            for (int i = 0; i < options.size(); i++) {
                String option = options.get(i); 
                logger.info("{}. {}", i + 1, option);
            }
            logger.info(ENTER_YOUR_CHOICE);

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice < 1 || choice > options.size()) {
                    logger.warn(INVALID_CHOICE);
                } else {
                    exit = menuHandler.test(choice); 
                }
            } catch (Exception e) {
                logger.error("Invalid input. Please enter a valid number.", e);
                scanner.nextLine(); 
            }
        }
        return exit;
    }


    
    private static void loadAllData() {
        logger.info("Loading all data...");

        
        List<User> loadedUsers = PersistenceUtil.loadUserData();
        if (loadedUsers.isEmpty()) {
            logger.warn("No users found in persistent storage.");
        } else {
            loadedUsers.forEach(userService::addUser);
            logger.info("Loaded {} users from persistent storage.", loadedUsers.size());
        }

        
        List<Program> loadedPrograms = PersistenceUtil.loadProgramData();
        if (loadedPrograms.isEmpty()) {
            logger.warn("No programs found in persistent storage.");
        } else {
            loadedPrograms.forEach(programService::addProgram);
            logger.info("Loaded {} programs from persistent storage.", loadedPrograms.size());
        }
    }


    private static void manageAdminMenu(Scanner scanner) {
        handleMenu(scanner, "\n=== Admin Menu ===",
            List.of("User Management", "Program Monitoring Menu", BACK_TO_MAIN_MENU),
            choice -> {
                switch (choice) {
                    case 1 -> manageUserAccountsMenu(scanner);
                    case 2 -> manageProgramMonitoringMenu(scanner);
                    case 3 -> {
                        return true;
                    }
                    default -> logger.warn(INVALID_CHOICE);
                }
                return false;
            });
    }


        
    private static void manageUserAccountsMenu(Scanner scanner) {
        handleMenu(
            scanner,
            "\n=== User Management Menu ===",
            List.of(
                "Add User",
                "Update User",
                "Deactivate User",
                "Approve Instructor",
                "View User Activity Statistics",
                "View All Users with Status",
                "Back to Admin Menu"
            ),
            choice -> {
                switch (choice) {
                    case 1 -> addUser(scanner);
                    case 2 -> updateUser(scanner);
                    case 3 -> deactivateUser(scanner);
                    case 4 -> approveInstructor(scanner);
                    case 5 -> viewUserActivity();
                    case 6 -> viewAllUsersWithStatus();
                    case 7 -> {
                        return true; 
                    }
                    default -> logger.warn(INVALID_CHOICE);
                }
                return false; 
            }
        );
    }

        
    private static void manageProgramMonitoringMenu(Scanner scanner) {
        handleMenu(
            scanner,
            "\n=== Program Monitoring Menu ===",
            List.of(
                "View Most Popular Programs by Enrollment",
                "Generate Revenue Report",
                "Generate Attendance Report",
                "Generate Client Progress Report",
                "View Active and Completed Programs",
                "Back to Admin Menu"
            ),
            choice -> {
                switch (choice) {
                    case 1 -> viewMostPopularPrograms();
                    case 2 -> generateRevenueReport();
                    case 3 -> generateAttendanceReport();
                    case 4 -> generateClientProgressReport();
                    case 5 -> viewActiveAndCompletedPrograms();
                    case 6 -> {
                        return true; 
                    }
                    default -> logger.warn(INVALID_CHOICE);
                }
                return false; 
            }
        );
    }

        
        private static void manageInstructorMenu(Scanner scanner) {
            handleMenu(scanner, "\n=== Instructor Menu ===",
                List.of("Create Program", "Update Program", "Delete Program", "View All Programs", BACK_TO_MAIN_MENU),
                choice -> {
                    switch (choice) {
                        case 1 -> createProgram(scanner);
                        case 2 -> updateProgram(scanner);
                        case 3 -> deleteProgram(scanner);
                        case 4 -> viewAllPrograms();
                        case 5 -> {
                            return true;
                        }
                        default -> logger.warn(INVALID_CHOICE);
                    }
                    return false;
                });
        }

      

        private static void manageClientMenu(Scanner scanner) {
            handleMenu(scanner, "\n=== Client Menu ===",
                List.of("Manage Client Profile", BACK_TO_MAIN_MENU),
                choice -> {
                    switch (choice) {
                        case 1 -> manageClientProfile(scanner);
                        case 2 -> {
                            return true;
                        }
                        default -> logger.warn(INVALID_CHOICE);
                    }
                    return false;
                });
        }


        private static void manageClientProfile(Scanner scanner) {
            handleMenu(
                scanner,
                "\n=== Manage Client Profile Menu ===",
                List.of(
                    "Create Profile",
                    "View Profile",
                    "Update Profile",
                    "Delete Profile",
                    "Back to Client Menu"
                ),
                choice -> {
                    switch (choice) {
                        case 1 -> createClientProfile(scanner);
                        case 2 -> viewClientProfile(scanner);
                        case 3 -> updateClientProfile(scanner);
                        case 4 -> deleteClientProfile(scanner);
                        case 5 -> {
                            return true; 
                        }
                        default -> logger.warn(INVALID_CHOICE);
                    }
                    return false; 
                }
            );
        }

        private static void saveAllData() {
            PersistenceUtil.saveUserData(new ArrayList<>(userService.getAllUsers()));
            PersistenceUtil.saveProgramData(new ArrayList<>(programService.getAllPrograms())); 
        }



    private static void incrementActivity(String email) {
        activityData.put(email, activityData.getOrDefault(email, 0) + 1);
    }
    
    
    
    private static void addUser(Scanner scanner) {
        String name = getName(scanner);
        String email = getEmail(scanner);
        String role = getRole(scanner);
        String status = getStatus(scanner);
        boolean isActive = status.equalsIgnoreCase(ACTIVE);
        User user = new User(name, email, role, isActive);
        if (userService.addUser(user)) {
            logger.info("User added successfully.");
            activityData.put(email, 0);
            incrementActivity(user.getEmail());
        } else {
            logger.warn("User with this email already exists.");
        }
    }

    private static void updateUser(Scanner scanner) {
        String email = getEmail(scanner);

        if (userService.getUser(email) == null) {
            logger.warn(USER_NOT_FOUND);
            return;
        }

        String name = getName(scanner);
        String role = getRole(scanner);

        if (userService.updateUser(email, name, role)) {
            logger.info("User updated successfully.");
            incrementActivity(email);
        } else {
            logger.warn("Failed to update user.");
        }
    }

    private static void deactivateUser(Scanner scanner) {
        String email = getEmail(scanner);
        if (userService.detectiveUser(email)) {
        	logger.info("User deactivated successfully.");
            incrementActivity(email);
        } else {
        	logger.warn(USER_NOT_FOUND);
        }
    }
    
   

    private static void approveInstructor(Scanner scanner) {
        String email = getEmail(scanner);
        User user = userService.getUser(email);

        if (user != null && user.getRole().equalsIgnoreCase("Instructor") && !user.isActive()) {
            user.setActive(true);
            logger.info("Instructor approved successfully."); 
        } else if (user == null) {
            logger.warn(USER_NOT_FOUND); 
        } else {
            logger.warn("This user is not eligible for approval."); 
        }
    }

    
    private static void viewUserActivity() {
        logger.info("\n=== User Activity Statistics ===");
        if (activityData.isEmpty()) {
            logger.info("No activity data available.");
        } else {
            activityData.forEach((email, activityCount) -> 
                logger.info("Email: {}, Activity Count: {}", email, activityCount)
            );
        }
    }

    private static void viewAllUsersWithStatus() {
        logger.info("\n=== List of Users ===");
        if (userService.getAllUsers().isEmpty()) {
            logger.info("No users found.");
        } else {
            for (User user : userService.getAllUsers()) {
                String status = user.isActive() ? "Active" : "Inactive";
                logger.info("Name: {}, Email: {}, Role: {}, Status: {}", user.getName(), user.getEmail(), user.getRole(), status);
            }
        }
    }
    private static String getName(Scanner scanner) {
        String name;
        while (true) {
            logger.info("Enter name: ");
            name = scanner.nextLine();
            if (!name.trim().isEmpty() && name.matches("^[a-zA-Z\\s]+$")) {
                break;
            } else {
                logger.warn("Invalid name. Please enter a valid name (only letters and spaces).");
            }
        }
        return name;
    }

    private static String getEmail(Scanner scanner) {
        String email;
        while (true) {
            logger.info("Enter email: ");
            email = scanner.nextLine();
            if (email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                break;
            } else {
                logger.warn("Invalid email format. Please try again.");
            }
        }
        return email;
    }

    private static String getRole(Scanner scanner) {
        String role;
        while (true) {
            logger.info("Enter role (Instructor/Client): ");
            role = scanner.nextLine();
            if (role.equalsIgnoreCase("Instructor") || role.equalsIgnoreCase("Client")) {
                break;
            } else {
                logger.warn("Invalid role. Please enter 'Instructor' or 'Client'.");
            }
        }
        return role;
    }

    private static String getStatus(Scanner scanner) {
        String status;
        while (true) {
            logger.info("Enter status (active/inactive): ");
            status = scanner.nextLine();
            if (status.equalsIgnoreCase(ACTIVE) || status.equalsIgnoreCase("inactive")) {
                break;
            } else {
                logger.warn("Invalid status. Please enter 'active' or 'inactive'.");
            }
        }
        return status;
    }


    private static void createProgram(Scanner scanner) {
        logger.info("Enter Program Title: ");
        String title = scanner.nextLine().trim();

        logger.info("Enter Duration (e.g., 6 weeks): ");
        String duration = scanner.nextLine().trim();

        logger.info("Enter Difficulty Level (e.g., Beginner/Intermediate): ");
        String difficulty = scanner.nextLine().trim();

        logger.info("Enter Goals: ");
        String goals = scanner.nextLine().trim();

        logger.info("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        logger.info("Enter Schedule (e.g., Online/In-Person): ");
        String schedule = scanner.nextLine().trim();

        logger.info("Enter Video Tutorials (comma-separated): ");
        List<String> videos = List.of(scanner.nextLine().split(","));

        logger.info("Enter Documents (comma-separated): ");
        List<String> documents = List.of(scanner.nextLine().split(","));

        
        Program program = new Program.Builder(title)
            .setDuration(duration)
            .setDifficulty(difficulty)
            .setGoals(goals)
            .setPrice(price)
            .setSchedule(schedule)
            .setVideos(videos)
            .setDocuments(documents)
            .build();

        if (programService.addProgram(program)) {
            
            PersistenceUtil.saveProgramData(new ArrayList<>(programService.getAllPrograms()));
            logger.info("Program created successfully.");
        } else {
            logger.warn("Failed to create program. A program with this title may already exist.");
        }
    }



    private static void updateProgram(Scanner scanner) {
        logger.info("Enter Program Title to Update: ");
        String title = scanner.nextLine().trim();

        logger.info("Enter New Duration: ");
        String duration = scanner.nextLine().trim();

        logger.info("Enter New Difficulty Level: ");
        String difficulty = scanner.nextLine().trim();

        logger.info("Enter New Goals: ");
        String goals = scanner.nextLine().trim();

        logger.info("Enter New Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        logger.info("Enter New Schedule: ");
        String schedule = scanner.nextLine().trim();

        logger.info("Enter New Video Tutorials (comma-separated): ");
        List<String> videos = List.of(scanner.nextLine().split(",")).stream()
                                  .map(String::trim)
                                  .toList();

        logger.info("Enter New Documents (comma-separated): ");
        List<String> documents = List.of(scanner.nextLine().split(",")).stream()
                                     .map(String::trim)
                                     .toList();

        
        Program updatedProgram = new Program.Builder(title)
            .setDuration(duration)
            .setDifficulty(difficulty)
            .setGoals(goals)
            .setPrice(price)
            .setSchedule(schedule)
            .setVideos(videos)
            .setDocuments(documents)
            .build();

        if (programService.updateProgram(updatedProgram)) {
            
            PersistenceUtil.saveProgramData(new ArrayList<>(programService.getAllPrograms()));
            logger.info("Program updated successfully.");
        } else {
            logger.warn("Failed to update program. Program may not exist.");
        }
    }




    private static void deleteProgram(Scanner scanner) {
        logger.info("Enter Program Title to Delete: ");
        String title = scanner.nextLine();

        if (programService.deleteProgram(title)) {
            
            PersistenceUtil.saveProgramData(new ArrayList<>(programService.getAllPrograms()));
            logger.info("Program deleted successfully.");
        } else {
            logger.warn("Failed to delete program. Program may not exist.");
        }
    }



    private static void viewAllPrograms() {
        List<Program> programs = programService.getAllPrograms();
        if (programs.isEmpty()) {
            logger.info(NO_PROGRAMS_FOUND);
        } else {
            for (Program program : programs) {
                logger.info("{}", program);
            }
        }
    }
    
    private static void viewMostPopularPrograms() {
        List<Program> popularPrograms = programService.getMostPopularPrograms(); 
        if (popularPrograms.isEmpty()) {
            logger.warn(NO_PROGRAMS_FOUND);
        } else {
            popularPrograms.forEach(program -> logger.info("Title: {}", program.getTitle()));
        }
    }

    private static void generateRevenueReport() {
        Map<String, Double> revenueReport = programService.generateRevenueReport(); 
        if (revenueReport.isEmpty()) {
            logger.warn("No revenue data found.");
        } else {
            revenueReport.forEach((title, revenue) -> logger.info("Program: {}, Revenue: ${}", title, revenue));
        }
    }

    private static void generateAttendanceReport() {
        Map<String, Integer> attendanceReport = programService.generateAttendanceReport(); 
        if (attendanceReport.isEmpty()) {
            logger.warn("No attendance data found.");
        } else {
            attendanceReport.forEach((title, attendance) -> logger.info("Program: {}, Attendance: {}", title, attendance));
        }
    }

    private static void generateClientProgressReport() {
        Map<String, String> clientProgressReport = programService.generateClientProgressReport(); 
        if (clientProgressReport.isEmpty()) {
            logger.warn("No client progress data found.");
        } else {
            clientProgressReport.forEach((title, progress) -> logger.info("Program: {}, Progress: {}", title, progress));
        }
    }

    private static void viewActiveAndCompletedPrograms() {
        Map<String, List<Program>> categorizedPrograms = programService.getActiveAndCompletedPrograms(); 
        if (categorizedPrograms.isEmpty()) {
            logger.warn(NO_PROGRAMS_FOUND);
        } else {
            logger.info("Active Programs:");
            categorizedPrograms.getOrDefault(ACTIVE, new ArrayList<>())
                               .forEach(program -> logger.info(" - {}", program.getTitle()));
            logger.info("Completed Programs:");
            categorizedPrograms.getOrDefault("completed", new ArrayList<>())
                               .forEach(program -> logger.info(" - {}", program.getTitle()));
        }
    }
    
    private static void createClientProfile(Scanner scanner) {
        logger.info("\n=== Create Client Profile ===");

        logger.info("Enter Name: ");
        String name = scanner.nextLine();

        logger.info("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        logger.info("Enter Email: ");
        String email = scanner.nextLine();

        logger.info("Enter Fitness Goals: ");
        String fitnessGoals = scanner.nextLine();

        logger.info("Enter Dietary Preferences: ");
        String dietaryPreferences = scanner.nextLine();

        logger.info("Enter Dietary Restrictions: ");
        String dietaryRestrictions = scanner.nextLine();

        Profile createdProfile = new Profile(name, age, email, fitnessGoals, dietaryPreferences, dietaryRestrictions);

        clientProfileService.createProfile(createdProfile, userService);
        logger.info("Profile created successfully: {}", createdProfile);
    }




    private static void viewClientProfile(Scanner scanner) {
        logger.info("\n=== View Client Profile ===");
        logger.info(ENTER_YOUR_EMAIL);
        String email = scanner.nextLine();

        Profile profile = clientProfileService.viewProfile(email);

        if (profile == null) {
            logger.warn("No profile found for the given email.");
        } else {
            logger.info("Name: {}", profile.getName());
            logger.info("Age: {}", profile.getAge());
            logger.info("Email: {}", profile.getEmail());
            logger.info("Fitness Goals: {}", profile.getFitnessGoals());
            logger.info("Dietary Preferences: {}", profile.getDietaryPreferences());
            logger.info("Dietary Restrictions: {}", profile.getDietaryRestrictions());
        }
    }

    private static void updateClientProfile(Scanner scanner) { 
        logger.info("\n=== Update Client Profile ===");
        logger.info(ENTER_YOUR_EMAIL);
        String email = scanner.nextLine();

        Profile profile = clientProfileService.viewProfile(email);
        if (profile == null) {
            logger.warn("No profile found for the given email.");
        } else {
            profile.setName(getUpdatedInput(scanner, "Name", profile.getName()));

            while (true) {
                logger.info("Enter New Age ({}): ", profile.getAge());
                String ageInput = scanner.nextLine();

                if (!ageInput.trim().isEmpty()) {
                    try {
                        int age = Integer.parseInt(ageInput);
                        profile.setAge(age);
                        break;
                    } catch (NumberFormatException e) {
                        logger.error("Invalid input. Please enter a valid number for age.");
                    }
                } else {
                    logger.warn("Age cannot be blank. Please enter a valid number.");
                }
            }

            profile.setEmail(getUpdatedInput(scanner, "Email", profile.getEmail()));
            profile.setFitnessGoals(getUpdatedInput(scanner, "Fitness Goals", profile.getFitnessGoals()));
            profile.setDietaryPreferences(getUpdatedInput(scanner, "Dietary Preferences", profile.getDietaryPreferences()));
            profile.setDietaryRestrictions(getUpdatedInput(scanner, "Dietary Restrictions", profile.getDietaryRestrictions()));

            clientProfileService.updateProfile(
                profile.getName(),
                profile.getAge(),
                profile.getEmail(),
                profile.getFitnessGoals(),
                profile.getDietaryPreferences(),
                profile.getDietaryRestrictions()
            );

            logger.info("Profile updated successfully.");
        }
    }

    


    private static void deleteClientProfile(Scanner scanner) {
        logger.info("\n=== Delete Client Profile ===");
        logger.info(ENTER_YOUR_EMAIL);
        String email = scanner.nextLine();

        Profile profile = clientProfileService.viewProfile(email);

        if (profile == null) {
            logger.warn("No profile found.");
        } else {
            clientProfileService.deleteProfile(userService);
            logger.info("Profile deleted successfully.");
        }
    }

    private static String getUpdatedInput(Scanner scanner, String field, String currentValue) {
        logger.info("Enter New {} ({}): ", field, currentValue);
        return scanner.nextLine();
    }
}