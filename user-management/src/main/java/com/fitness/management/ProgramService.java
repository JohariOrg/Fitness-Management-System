package com.fitness.management;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramService {
    private Map<String, Program> programs = new HashMap<>();

    public ProgramService() {
        List<Program> persistedPrograms = PersistenceUtil.loadProgramData();
        for (Program program : persistedPrograms) {
            programs.put(program.getTitle().trim(), program);
        }
    }


    public boolean addProgram(Program program) {
        if (program == null || program.getTitle() == null || program.getTitle().trim().isEmpty()) {
            System.err.println("Invalid program: Missing or empty title.");
            return false;
        }

        String trimmedTitle = program.getTitle().trim();
        Program existingProgram = programs.get(trimmedTitle);

        if (existingProgram != null) {
            // Check if the program details are identical
            if (existingProgram.equals(program)) {
                System.err.println("Program already exists with title: '" + trimmedTitle + "'.");
                return false;
            }
        }

        programs.put(trimmedTitle, program);

        // Save to persistence
        List<Program> allPrograms = new ArrayList<>(programs.values());
        PersistenceUtil.saveProgramData(allPrograms);

        System.out.println("Program added successfully: " + program);
        return true;
    }



    public boolean updateProgram(Program updatedProgram) {
        if (updatedProgram == null || updatedProgram.getTitle() == null || updatedProgram.getTitle().trim().isEmpty()) {
            System.err.println("Invalid updated program: Missing or empty title.");
            return false;
        }

        String trimmedTitle = updatedProgram.getTitle().trim();
        Program existingProgram = programs.get(trimmedTitle);

        if (existingProgram == null) {
            System.err.println("Update failed: No program found with title '" + trimmedTitle + "'.");
            return false;
        }

        
        Program mergedProgram = new Program.Builder(trimmedTitle)
            .setDuration(updatedProgram.getDuration() != null ? updatedProgram.getDuration() : existingProgram.getDuration())
            .setDifficulty(updatedProgram.getDifficulty() != null ? updatedProgram.getDifficulty() : existingProgram.getDifficulty())
            .setGoals(updatedProgram.getGoals() != null ? updatedProgram.getGoals() : existingProgram.getGoals())
            .setPrice(updatedProgram.getPrice() > 0 ? updatedProgram.getPrice() : existingProgram.getPrice())
            .setSchedule(updatedProgram.getSchedule() != null ? updatedProgram.getSchedule() : existingProgram.getSchedule())
            .setVideos(updatedProgram.getVideos() != null ? updatedProgram.getVideos() : existingProgram.getVideos())
            .setDocuments(updatedProgram.getDocuments() != null ? updatedProgram.getDocuments() : existingProgram.getDocuments())
            .setEnrollment(existingProgram.getEnrollment()) 
            .setProgressSummary(existingProgram.getProgressSummary()) 
            .setIsActive(existingProgram.isActive()) 
            .build();

        programs.put(trimmedTitle, mergedProgram);

        
        PersistenceUtil.saveProgramData(getAllPrograms());
        System.out.println("Program '" + trimmedTitle + "' updated successfully.");
        return true;
    }

    public boolean deleteProgram(String title) {
        String trimmedTitle = title.trim();
        if (!programs.containsKey(trimmedTitle)) {
            System.out.println("Program with title '" + trimmedTitle + "' not found in programs.");
            return false;
        }
        programs.remove(trimmedTitle);

        
        PersistenceUtil.saveProgramData(getAllPrograms());
        return true;
    }

    public Program getProgram(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        return programs.get(title.trim());
    }

    public List<Program> getAllPrograms() {
        return new ArrayList<>(programs.values());
    }

    public List<Program> getMostPopularPrograms() {
        return programs.values()
                       .stream()
                       .sorted(Comparator.comparingInt(Program::getEnrollment).reversed())
                       .toList();
    }

    public Map<String, Double> generateRevenueReport() {
        Map<String, Double> revenueReport = new HashMap<>();
        for (Program program : programs.values()) {
            double revenue = program.getPrice() * program.getEnrollment();
            revenueReport.put(program.getTitle(), revenue);
        }
        return revenueReport;
    }

    public Map<String, Integer> generateAttendanceReport() {
        Map<String, Integer> attendanceReport = new HashMap<>();
        for (Program program : programs.values()) {
            attendanceReport.put(program.getTitle(), program.getEnrollment());
        }
        return attendanceReport;
    }

    public Map<String, String> generateClientProgressReport() {
        Map<String, String> progressReport = new HashMap<>();
        for (Program program : programs.values()) {
            progressReport.put(program.getTitle(), program.getProgressSummary());
        }
        return progressReport;
    }

    public Map<String, List<Program>> getActiveAndCompletedPrograms() {
        Map<String, List<Program>> categorizedPrograms = new HashMap<>();
        categorizedPrograms.put("Active", new ArrayList<>());
        categorizedPrograms.put("Completed", new ArrayList<>());

        for (Program program : programs.values()) {
            if (program.isActive()) {
                categorizedPrograms.get("Active").add(program);
            } else {
                categorizedPrograms.get("Completed").add(program);
            }
        }
        return categorizedPrograms;
    }
}
