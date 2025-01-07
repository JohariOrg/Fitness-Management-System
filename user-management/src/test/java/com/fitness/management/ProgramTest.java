package com.fitness.management;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class ProgramTest {

    @Test
    public void testEquals_SameObject() {
        Program program = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Enhanced flexibility")
                .setPrice(150.0)
                .setSchedule("Hybrid")
                .setVideos(Arrays.asList("intro.mp4", "session1.mp4"))
                .setDocuments(Arrays.asList("guide.pdf", "faq.pdf"))
                .build();

        // Test equality with the same object
        assertTrue("Program should be equal to itself", program.equals(program));
    }

    @Test
    public void testEquals_DifferentObject_SameValues() {
        Program program1 = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Enhanced flexibility")
                .setPrice(150.0)
                .setSchedule("Hybrid")
                .setVideos(Arrays.asList("intro.mp4", "session1.mp4"))
                .setDocuments(Arrays.asList("guide.pdf", "faq.pdf"))
                .build();

        Program program2 = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Enhanced flexibility")
                .setPrice(150.0)
                .setSchedule("Hybrid")
                .setVideos(Arrays.asList("intro.mp4", "session1.mp4"))
                .setDocuments(Arrays.asList("guide.pdf", "faq.pdf"))
                .build();

        // Test equality with a different object with the same values
        assertTrue("Programs with identical values should be equal", program1.equals(program2));
    }

    @Test
    public void testEquals_DifferentValues() {
        Program program1 = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Enhanced flexibility")
                .setPrice(150.0)
                .setSchedule("Hybrid")
                .setVideos(Arrays.asList("intro.mp4", "session1.mp4"))
                .setDocuments(Arrays.asList("guide.pdf", "faq.pdf"))
                .build();

        Program program2 = new Program.Builder("Yoga Basics")
                .setDuration("4 weeks")
                .setDifficulty("Beginner")
                .setGoals("Flexibility")
                .setPrice(100.0)
                .setSchedule("Online")
                .setVideos(Arrays.asList("intro.mp4"))
                .setDocuments(Arrays.asList("guide.pdf"))
                .build();

        // Test equality with a different object with different values
        assertFalse("Programs with different values should not be equal", program1.equals(program2));
    }

    @Test
    public void testEquals_Null() {
        Program program = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .build();

        // Test equality with null
        assertFalse("Program should not be equal to null", program.equals(null));
    }

    @Test
    public void testEquals_DifferentClass() {
        Program program = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .build();

        String differentObject = "Not a Program";

        // Test equality with a different class
        assertFalse("Program should not be equal to an object of a different class", program.equals(differentObject));
    }

    @Test
    public void testHashCode() {
        Program program1 = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Enhanced flexibility")
                .setPrice(150.0)
                .setSchedule("Hybrid")
                .setVideos(Arrays.asList("intro.mp4", "session1.mp4"))
                .setDocuments(Arrays.asList("guide.pdf", "faq.pdf"))
                .build();

        Program program2 = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Enhanced flexibility")
                .setPrice(150.0)
                .setSchedule("Hybrid")
                .setVideos(Arrays.asList("intro.mp4", "session1.mp4"))
                .setDocuments(Arrays.asList("guide.pdf", "faq.pdf"))
                .build();

        // Test hashCode for objects with identical values
        assertEquals("Hash codes of programs with identical values should match", program1.hashCode(), program2.hashCode());
    }

    @Test
    public void testHashCode_DifferentValues() {
        Program program1 = new Program.Builder("Yoga Basics")
                .setDuration("6 weeks")
                .setDifficulty("Intermediate")
                .setGoals("Enhanced flexibility")
                .setPrice(150.0)
                .setSchedule("Hybrid")
                .setVideos(Arrays.asList("intro.mp4", "session1.mp4"))
                .setDocuments(Arrays.asList("guide.pdf", "faq.pdf"))
                .build();

        Program program2 = new Program.Builder("Yoga Advanced")
                .setDuration("8 weeks")
                .setDifficulty("Advanced")
                .setGoals("Mastery")
                .setPrice(200.0)
                .setSchedule("In-Person")
                .setVideos(Arrays.asList("session3.mp4", "session4.mp4"))
                .setDocuments(Arrays.asList("mastery_guide.pdf"))
                .build();

        // Test hashCode for objects with different values
        assertNotEquals("Hash codes of programs with different values should not match", program1.hashCode(), program2.hashCode());
    }
}
