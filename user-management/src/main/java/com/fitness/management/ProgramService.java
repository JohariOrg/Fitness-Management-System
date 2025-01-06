package com.fitness.management;


import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramService {
    private Map<String, Program> programs = new HashMap<>();

    public boolean addProgram(Program program) {
        if (programs.containsKey(program.getTitle())) {
            return false; 
        }
        programs.put(program.getTitle(), program);
        return true;
    }

    public boolean updateProgram(ProgramDetails programDetails) {
        Program program = programs.get(programDetails.getTitle().trim());
        if (program == null) {
            return false; 
        }

        program.setDuration(programDetails.getDuration());
        program.setDifficulty(programDetails.getDifficulty());
        program.setGoals(programDetails.getGoals());
        program.setPrice(programDetails.getPrice());
        program.setSchedule(programDetails.getSchedule());
        program.setVideos(programDetails.getVideos());
        program.setDocuments(programDetails.getDocuments());

        return true;
    }


    public boolean deleteProgram(String title) {
        return programs.remove(title.trim()) != null;
    }

    public Program getProgram(String title) {
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

