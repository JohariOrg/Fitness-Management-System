package com.fitness.management;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersistenceUtilTest {

    private static final String TEST_USERS_FILE = "test_users_data.txt";
    private static final String TEST_PROGRAMS_FILE = "test_programs_data.dat";
    private static final String TEST_CLIENT_PROFILE_FILE = "test_client_profile.dat";
    @Before
    public void setUp() {
     
        PersistenceUtil.setClientProfileFilePath(TEST_CLIENT_PROFILE_FILE);

        PersistenceUtil.setUsersFilePath(TEST_USERS_FILE);
        PersistenceUtil.setProgramsFilePath(TEST_PROGRAMS_FILE);

        deleteFile(TEST_USERS_FILE);
        deleteFile(TEST_PROGRAMS_FILE);
        deleteFile(TEST_CLIENT_PROFILE_FILE);
    }


    @After
    public void tearDown() {
        
        deleteFile(TEST_USERS_FILE);
        deleteFile(TEST_PROGRAMS_FILE);
        deleteFile(TEST_CLIENT_PROFILE_FILE);
        
        PersistenceUtil.setUsersFilePath("users_data.txt");
        PersistenceUtil.setProgramsFilePath("programs.dat");
    }

    private void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (!file.delete()) {
                System.err.println("Failed to delete file: " + path);
            }
        }
    }
    

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<PersistenceUtil> constructor = PersistenceUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
            fail("Expected UnsupportedOperationException to be thrown");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof UnsupportedOperationException);
            assertEquals("Utility class - cannot be instantiated", e.getCause().getMessage());
        }
    }

    @Test
    public void testLoadUserDataFileDoesNotExist() {
        
        List<User> users = PersistenceUtil.loadUserData();

        
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testLoadUserDataWithValidData() throws IOException {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_USERS_FILE))) {
            writer.write("John Doe,john.doe@example.com,User,Active,30,Lose weight,Vegetarian,None");
            writer.newLine();
            writer.write("Jane Smith,jane.smith@example.com,Admin,Inactive,25,Build muscle,Omnivore,None");
            writer.newLine();
        }

        
        List<User> users = PersistenceUtil.loadUserData();

        
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testLoadUserDataIOException() {
        
        PersistenceUtil.setUsersFilePath("/invalid_path/test_users_data.txt");

        
        List<User> users = PersistenceUtil.loadUserData();

        
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testSaveProgramDataWithValidPrograms() throws IOException, ClassNotFoundException {
        
        List<Program> programs = new ArrayList<>();
        programs.add(new Program.Builder("Program 1")
                .setDuration("4 weeks")
                .setGoals("Weight loss")
                .setPrice(99.99)
                .setDifficulty("Intermediate")
                .build());
        programs.add(new Program.Builder("Program 2")
                .setDuration("8 weeks")
                .setGoals("Muscle building")
                .setPrice(149.99)
                .setDifficulty("Advanced")
                .build());

        
        PersistenceUtil.saveProgramData(programs);

        
        File file = new File(TEST_PROGRAMS_FILE);
        assertTrue(file.exists());

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEST_PROGRAMS_FILE))) {
            List<Program> loadedPrograms = (List<Program>) ois.readObject();
            assertNotNull(loadedPrograms);
            assertEquals(2, loadedPrograms.size());
        }
    }

    @Test
    public void testSaveProgramDataWithEmptyList() {
        
        List<Program> programs = new ArrayList<>();

        
        PersistenceUtil.saveProgramData(programs);

        
        File file = new File(TEST_PROGRAMS_FILE);
        assertFalse(file.exists());
    }

    @Test
    public void testSaveProgramDataIOException() {
        
        PersistenceUtil.setProgramsFilePath("/invalid_path/test_programs_data.dat");

        
        PersistenceUtil.saveProgramData(new ArrayList<>());

        
        File file = new File("/invalid_path/test_programs_data.dat");
        assertFalse(file.exists());
    }

    @Test
    public void testLoadProgramDataFileDoesNotExist() {
        
        List<Program> programs = PersistenceUtil.loadProgramData();

        
        assertNotNull(programs);
        assertTrue(programs.isEmpty());
    }

    @Test
    public void testLoadProgramDataWithValidData() throws IOException, ClassNotFoundException {
        
        List<Program> programs = new ArrayList<>();
        programs.add(new Program.Builder("Program 1")
                .setDuration("4 weeks")
                .setGoals("Weight loss")
                .setPrice(99.99)
                .setDifficulty("Intermediate")
                .build());
        programs.add(new Program.Builder("Program 2")
                .setDuration("8 weeks")
                .setGoals("Muscle building")
                .setPrice(149.99)
                .setDifficulty("Advanced")
                .build());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEST_PROGRAMS_FILE))) {
            oos.writeObject(programs);
        }

        
        List<Program> loadedPrograms = PersistenceUtil.loadProgramData();

        
        assertNotNull(loadedPrograms);
        assertEquals(2, loadedPrograms.size());
    }

    @Test
    public void testLoadProgramDataIOException() {
        
        PersistenceUtil.setProgramsFilePath("/invalid_path/test_programs_data.dat");

        
        List<Program> programs = PersistenceUtil.loadProgramData();

        
        assertNotNull(programs);
        assertTrue(programs.isEmpty());
    }
    
    @Test
    public void testDeleteClientProfileDataFileExists() throws IOException {
        
        Path clientProfilePath = Paths.get(TEST_CLIENT_PROFILE_FILE);
        Files.createFile(clientProfilePath);

        
        PersistenceUtil.deleteClientProfileData();

        
        assertFalse("File should be deleted", Files.exists(clientProfilePath));
    }
    @Test
    public void testDeleteClientProfileDataFileDoesNotExist() {
        Path clientProfilePath = Paths.get(TEST_CLIENT_PROFILE_FILE);
        deleteFile(TEST_CLIENT_PROFILE_FILE);
        PersistenceUtil.deleteClientProfileData();
        assertFalse(Files.exists(clientProfilePath)); 
    }

    @Test
    public void testDeleteClientProfileDataIOException() throws IOException {
        Path clientProfilePath = Paths.get(TEST_CLIENT_PROFILE_FILE);
        Files.createFile(clientProfilePath);
        File clientProfileFile = clientProfilePath.toFile();
        clientProfileFile.setWritable(false);
        try {
            PersistenceUtil.deleteClientProfileData();
            fail("Expected an IllegalStateException due to read-only file");
        } catch (IllegalStateException e) {
            
        }
        assertTrue("File should not be deleted", Files.exists(clientProfilePath));
        clientProfileFile.setWritable(true);
    }

}
