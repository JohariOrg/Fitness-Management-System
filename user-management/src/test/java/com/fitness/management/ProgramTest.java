package com.fitness.management;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProgramTest {

    @Test
    public void testBuilderCreatesProgramCorrectly() {
        // Arrange: Create a Program using the Builder
        List<String> videos = Arrays.asList("Video1.mp4", "Video2.mp4");
        List<String> documents = Arrays.asList("Doc1.pdf", "Doc2.pdf");

        Program program = new Program.Builder("Fitness Program")
                .setDuration("6 weeks")
                .setDifficulty("Beginner")
                .setGoals("Weight loss")
                .setPrice(59.99)
                .setSchedule("Weekly")
                .setVideos(videos)
                .setDocuments(documents)
                .setEnrollment(15)
                .setProgressSummary("50% completed")
                .setIsActive(true)
                .build();

        // Assert: Verify all fields are set correctly
        assertEquals("Fitness Program", program.getTitle());
        assertEquals("6 weeks", program.getDuration());
        assertEquals("Beginner", program.getDifficulty());
        assertEquals("Weight loss", program.getGoals());
        assertEquals(59.99, program.getPrice(), 0.01);
        assertEquals("Weekly", program.getSchedule());
        assertEquals(videos, program.getVideos());
        assertEquals(documents, program.getDocuments());
        assertEquals(15, program.getEnrollment());
        assertEquals("50% completed", program.getProgressSummary());
        assertTrue(program.isActive());
    }

    @Test
    public void testBuilderDefaultValues() {
        // Arrange: Create a Program using the Builder with default values
        Program program = new Program.Builder("Default Program").build();

        // Assert: Verify default values are applied
        assertEquals("Default Program", program.getTitle());
        assertNull(program.getDuration());
        assertNull(program.getDifficulty());
        assertNull(program.getGoals());
        assertEquals(0.0, program.getPrice(), 0.01);
        assertNull(program.getSchedule());
        assertNull(program.getVideos());
        assertNull(program.getDocuments());
        assertEquals(0, program.getEnrollment());
        assertEquals("", program.getProgressSummary());
        assertTrue(program.isActive());
    }

    @Test
    public void testToString() {
        // Arrange: Create a Program
        List<String> videos = Arrays.asList("Video1.mp4", "Video2.mp4");
        List<String> documents = Arrays.asList("Doc1.pdf", "Doc2.pdf");

        Program program = new Program.Builder("Test Program")
                .setDuration("4 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Build muscle")
                .setPrice(89.99)
                .setSchedule("Bi-weekly")
                .setVideos(videos)
                .setDocuments(documents)
                .setEnrollment(20)
                .setProgressSummary("20% completed")
                .setIsActive(false)
                .build();

        // Act: Call the toString method
        String programString = program.toString();

        // Assert: Verify the string representation
        assertTrue(programString.contains("Program Title: Test Program"));
        assertTrue(programString.contains("Duration: 4 weeks"));
        assertTrue(programString.contains("Difficulty: Intermediate"));
        assertTrue(programString.contains("Goals: Build muscle"));
        assertTrue(programString.contains("Price: 89.99"));
        assertTrue(programString.contains("Schedule: Bi-weekly"));
        assertTrue(programString.contains("Enrollment: 20"));
        assertTrue(programString.contains("Progress: 20% completed"));
        assertTrue(programString.contains("Active: No"));
        assertTrue(programString.contains("Videos: Video1.mp4, Video2.mp4"));
        assertTrue(programString.contains("Documents: Doc1.pdf, Doc2.pdf"));
    }

    @Test
    public void testBuilderWithPartialValues() {
        // Arrange: Create a Program with some fields not set
        Program program = new Program.Builder("Partial Program")
                .setDuration("3 weeks")
                .setGoals("Improve flexibility")
                .build();

        // Assert: Verify only the set fields are populated
        assertEquals("Partial Program", program.getTitle());
        assertEquals("3 weeks", program.getDuration());
        assertNull(program.getDifficulty());
        assertEquals("Improve flexibility", program.getGoals());
        assertEquals(0.0, program.getPrice(), 0.01);
        assertNull(program.getSchedule());
        assertNull(program.getVideos());
        assertNull(program.getDocuments());
        assertEquals(0, program.getEnrollment());
        assertEquals("", program.getProgressSummary());
        assertTrue(program.isActive());
    }
}
