package com.fitness.management;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProgramServiceTest {

    private ProgramService programService;

    @Before
    public void setUp() {
        programService = new ProgramService();
    }

    @Test
    public void testAddProgramSuccess() {
        Program program = new Program.Builder("Program 1")
                .setDuration("4 weeks")
                .setPrice(100.0)
                .setEnrollment(10)
                .build();

        boolean result = programService.addProgram(program);

        assertTrue(result);
        assertEquals(1, programService.getAllPrograms().size());
    }

    @Test
    public void testAddProgramDuplicateTitle() {
        Program program1 = new Program.Builder("Program 1").build();
        Program program2 = new Program.Builder("Program 1").build();

        programService.addProgram(program1);
        boolean result = programService.addProgram(program2);

        assertFalse(result);
        assertEquals(1, programService.getAllPrograms().size());
    }

    @Test
    public void testAddProgramNull() {
        boolean result = programService.addProgram(null);

        assertFalse(result);
        assertEquals(0, programService.getAllPrograms().size());
    }

    @Test
    public void testUpdateProgramSuccess() {
        Program program = new Program.Builder("Program 1")
                .setDuration("4 weeks")
                .setPrice(100.0)
                .build();
        programService.addProgram(program);

        Program updatedProgram = new Program.Builder("Program 1")
                .setDuration("6 weeks")
                .setPrice(150.0)
                .build();

        boolean result = programService.updateProgram(updatedProgram);

        assertTrue(result);
        Program updated = programService.getProgram("Program 1");
        assertEquals("6 weeks", updated.getDuration());
        assertEquals(150.0, updated.getPrice(), 0.01);
    }

    @Test
    public void testUpdateProgramDoesNotExist() {
        Program updatedProgram = new Program.Builder("Nonexistent Program").build();

        boolean result = programService.updateProgram(updatedProgram);

        assertFalse(result);
    }

    @Test
    public void testUpdateProgramInvalid() {
        boolean result = programService.updateProgram(null);

        assertFalse(result);
    }

    @Test
    public void testDeleteProgramSuccess() {
        Program program = new Program.Builder("Program 1").build();
        programService.addProgram(program);

        boolean result = programService.deleteProgram("Program 1");

        assertTrue(result);
        assertEquals(0, programService.getAllPrograms().size());
    }

    @Test
    public void testDeleteProgramDoesNotExist() {
        boolean result = programService.deleteProgram("Nonexistent Program");

        assertFalse(result);
    }

    @Test
    public void testGetProgramSuccess() {
        Program program = new Program.Builder("Program 1").build();
        programService.addProgram(program);

        Program result = programService.getProgram("Program 1");

        assertNotNull(result);
        assertEquals("Program 1", result.getTitle());
    }

    @Test
    public void testGetProgramDoesNotExist() {
        Program result = programService.getProgram("Nonexistent Program");

        assertNull(result);
    }

    @Test
    public void testGetAllPrograms() {
        programService.addProgram(new Program.Builder("Program 1").build());
        programService.addProgram(new Program.Builder("Program 2").build());

        List<Program> programs = programService.getAllPrograms();

        assertEquals(2, programs.size());
    }

    @Test
    public void testGetMostPopularPrograms() {
        programService.addProgram(new Program.Builder("Program 1").setEnrollment(5).build());
        programService.addProgram(new Program.Builder("Program 2").setEnrollment(15).build());
        programService.addProgram(new Program.Builder("Program 3").setEnrollment(10).build());

        List<Program> programs = programService.getMostPopularPrograms();

        assertEquals(3, programs.size());
        assertEquals("Program 2", programs.get(0).getTitle());
        assertEquals("Program 3", programs.get(1).getTitle());
        assertEquals("Program 1", programs.get(2).getTitle());
    }

    @Test
    public void testGenerateRevenueReport() {
        programService.addProgram(new Program.Builder("Program 1").setPrice(100.0).setEnrollment(10).build());
        programService.addProgram(new Program.Builder("Program 2").setPrice(200.0).setEnrollment(5).build());

        Map<String, Double> revenueReport = programService.generateRevenueReport();

        assertEquals(2, revenueReport.size());
        assertEquals(1000.0, revenueReport.get("Program 1"), 0.01);
        assertEquals(1000.0, revenueReport.get("Program 2"), 0.01);
    }

    @Test
    public void testGenerateAttendanceReport() {
        programService.addProgram(new Program.Builder("Program 1").setEnrollment(10).build());
        programService.addProgram(new Program.Builder("Program 2").setEnrollment(5).build());

        Map<String, Integer> attendanceReport = programService.generateAttendanceReport();

        assertEquals(2, attendanceReport.size());
        assertEquals(10, (int) attendanceReport.get("Program 1"));
        assertEquals(5, (int) attendanceReport.get("Program 2"));
    }

    @Test
    public void testGenerateClientProgressReport() {
        programService.addProgram(new Program.Builder("Program 1").setProgressSummary("50% completed").build());
        programService.addProgram(new Program.Builder("Program 2").setProgressSummary("80% completed").build());

        Map<String, String> progressReport = programService.generateClientProgressReport();

        assertEquals(2, progressReport.size());
        assertEquals("50% completed", progressReport.get("Program 1"));
        assertEquals("80% completed", progressReport.get("Program 2"));
    }

    @Test
    public void testGetActiveAndCompletedPrograms() {
        programService.addProgram(new Program.Builder("Program 1").setIsActive(true).build());
        programService.addProgram(new Program.Builder("Program 2").setIsActive(false).build());

        Map<String, List<Program>> categorizedPrograms = programService.getActiveAndCompletedPrograms();

        assertEquals(1, categorizedPrograms.get("Active").size());
        assertEquals(1, categorizedPrograms.get("Completed").size());
    }
}
