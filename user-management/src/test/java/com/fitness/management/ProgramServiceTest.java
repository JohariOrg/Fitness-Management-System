package com.fitness.management;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ProgramServiceTest {

    private ProgramService programService;

    @Before
    public void setUp() {
        
        PersistenceUtil.saveProgramData(new ArrayList<>()); 
        programService = new ProgramService();
    }
    
    


    @Test
    public void testAddProgram() {
        Program program = new Program.Builder("Yoga Basics")
            .setDuration("6 weeks")
            .setDifficulty("Intermediate")
            .setGoals("Enhanced flexibility")
            .setPrice(150.0)
            .setSchedule("Hybrid")
            .setVideos(List.of("intro.mp4", "session1.mp4", "session2.mp4"))
            .setDocuments(List.of("guide.pdf", "faq.pdf"))
            .build();

        boolean result = programService.addProgram(program);
        assertTrue("Program should be added successfully", result);

        Program duplicateProgram = new Program.Builder("Yoga Basics")
            .setDuration("6 weeks")
            .setDifficulty("Intermediate")
            .setGoals("Enhanced flexibility")
            .setPrice(150.0)
            .setSchedule("Hybrid")
            .setVideos(List.of("intro.mp4", "session1.mp4", "session2.mp4"))
            .setDocuments(List.of("guide.pdf", "faq.pdf"))
            .build();

        boolean duplicateResult = programService.addProgram(duplicateProgram);
        assertFalse("Duplicate program should not be added", duplicateResult);

        Program variationProgram = new Program.Builder("Yoga Basics")
            .setDuration("8 weeks")  // Different duration
            .setDifficulty("Advanced")  // Different difficulty
            .setGoals("Strength training")  // Different goals
            .setPrice(200.0)  // Different price
            .setSchedule("Online")  // Different schedule
            .setVideos(List.of("intro.mp4", "session1.mp4", "session3.mp4"))  // Different videos
            .setDocuments(List.of("guide.pdf", "faq.pdf", "manual.pdf"))  // Different documents
            .build();

        boolean variationResult = programService.addProgram(variationProgram);
        assertTrue("Program variation should be added successfully", variationResult);
    }





    @Test
    public void testAddDuplicateProgram() {
        Program program = new Program.Builder("Yoga Basics")
                .setDuration("4 weeks")
                .setDifficulty("Beginner")
                .setGoals("Flexibility")
                .setPrice(100)
                .setSchedule("Online")
                .build();

        programService.addProgram(program);
        boolean result = programService.addProgram(program);
        assertFalse("Duplicate program should not be added", result);
    }

    @Test
    public void testUpdateProgram() {
        Program program = new Program.Builder("Yoga Basics")
                .setDuration("4 weeks")
                .setDifficulty("Beginner")
                .setGoals("Flexibility")
                .setPrice(100)
                .setSchedule("Online")
                .build();

        programService.addProgram(program);

        Program updatedProgram = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Enhanced Flexibility")
                .setPrice(150)
                .setSchedule("Hybrid")
                .build();

        boolean result = programService.updateProgram(updatedProgram);
        assertTrue("Program should be updated successfully", result);

        Program retrievedProgram = programService.getProgram("Yoga Basics");
        assertNotNull("Updated program should exist", retrievedProgram);
        assertEquals("Updated duration should match", "6 weeks", retrievedProgram.getDuration());
        assertEquals("Updated difficulty should match", "Intermediate", retrievedProgram.getDifficulty());
    }

    @Test
    public void testUpdateNonExistentProgram() {
        Program updatedProgram = new Program.Builder("Nonexistent Program")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .build();

        boolean result = programService.updateProgram(updatedProgram);
        assertFalse("Updating a non-existent program should fail", result);
    }

    @Test
    public void testDeleteProgram() {
        Program program = new Program.Builder("Yoga Basics")
                .setDuration("4 weeks")
                .setDifficulty("Beginner")
                .setGoals("Flexibility")
                .setPrice(100)
                .setSchedule("Online")
                .build();

        programService.addProgram(program);

        boolean result = programService.deleteProgram("Yoga Basics");
        assertTrue("Program should be deleted successfully", result);

        Program retrievedProgram = programService.getProgram("Yoga Basics");
        assertNull("Deleted program should no longer exist", retrievedProgram);
    }

    @Test
    public void testDeleteNonExistentProgram() {
        boolean result = programService.deleteProgram("Nonexistent Program");
        assertFalse("Deleting a non-existent program should fail", result);
    }

    @Test
    public void testPersistenceIntegration() {
        Program program = new Program.Builder("Yoga Basics")
                .setDuration("4 weeks")
                .setDifficulty("Beginner")
                .setGoals("Flexibility")
                .setPrice(100)
                .setSchedule("Online")
                .build();

        programService.addProgram(program);

        
        ProgramService newProgramService = new ProgramService();

        Program retrievedProgram = newProgramService.getProgram("Yoga Basics");
        assertNotNull("Program should persist across service instances", retrievedProgram);
        assertEquals("Persisted program title should match", "Yoga Basics", retrievedProgram.getTitle());
    }
}
