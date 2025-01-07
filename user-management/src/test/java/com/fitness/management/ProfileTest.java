package com.fitness.management;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProfileTest {

    @Test
    public void testProfileConstructorAndGetters() {
        // Create a Profile instance
        Profile profile = new Profile(
            "John Doe",
            30,
            "john.doe@example.com",
            "Lose weight",
            "Vegan",
            "None"
        );

        // Validate constructor values using getters
        assertEquals("John Doe", profile.getName());
        assertEquals(30, profile.getAge());
        assertEquals("john.doe@example.com", profile.getEmail());
        assertEquals("Lose weight", profile.getFitnessGoals());
        assertEquals("Vegan", profile.getDietaryPreferences());
        assertEquals("None", profile.getDietaryRestrictions());
    }

    @Test
    public void testSettersAndToString() {
        // Create a Profile instance
        Profile profile = new Profile(
            "John Doe",
            30,
            "john.doe@example.com",
            "Lose weight",
            "Vegan",
            "None"
        );

        // Update profile fields using setters
        profile.setName("Jane Doe");
        profile.setAge(28);
        profile.setEmail("jane@example.com");
        profile.setFitnessGoals("Gain muscle");
        profile.setDietaryPreferences("Vegetarian");
        profile.setDietaryRestrictions("Gluten-free");

        // Validate updated values using getters
        assertEquals("Jane Doe", profile.getName());
        assertEquals(28, profile.getAge());
        assertEquals("jane@example.com", profile.getEmail());
        assertEquals("Gain muscle", profile.getFitnessGoals());
        assertEquals("Vegetarian", profile.getDietaryPreferences());
        assertEquals("Gluten-free", profile.getDietaryRestrictions());

        // Validate toString output
        String expectedToString = "Profile{name='Jane Doe', age=28, email='jane@example.com', fitnessGoals='Gain muscle', dietaryPreferences='Vegetarian', dietaryRestrictions='Gluten-free'}";
        assertEquals(expectedToString, profile.toString());
    }

    @Test
    public void testSerialization() throws Exception {
        // Create a Profile instance
        Profile profile = new Profile(
            "John Doe",
            30,
            "john.doe@example.com",
            "Lose weight",
            "Vegan",
            "None"
        );

        // Serialize the Profile object to a byte array
        java.io.ByteArrayOutputStream byteOut = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(byteOut);
        out.writeObject(profile);
        out.close();

        // Deserialize the Profile object from the byte array
        java.io.ByteArrayInputStream byteIn = new java.io.ByteArrayInputStream(byteOut.toByteArray());
        java.io.ObjectInputStream in = new java.io.ObjectInputStream(byteIn);
        Profile deserializedProfile = (Profile) in.readObject();
        in.close();

        // Validate that the deserialized object matches the original
        assertEquals(profile.getName(), deserializedProfile.getName());
        assertEquals(profile.getAge(), deserializedProfile.getAge());
        assertEquals(profile.getEmail(), deserializedProfile.getEmail());
        assertEquals(profile.getFitnessGoals(), deserializedProfile.getFitnessGoals());
        assertEquals(profile.getDietaryPreferences(), deserializedProfile.getDietaryPreferences());
        assertEquals(profile.getDietaryRestrictions(), deserializedProfile.getDietaryRestrictions());
    }
}
