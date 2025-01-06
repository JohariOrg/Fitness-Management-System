package com.fitness.management.steps;

import com.fitness.management.Program;
import com.fitness.management.ProgramService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

public class ProgramMonitoringSteps {

    private ProgramService programService;
    private List<String> popularPrograms; 
    private Map<String, Object> generatedReport; 
    private Map<String, List<Program>> categorizedPrograms; 

    public ProgramMonitoringSteps() {
        this.programService = new ProgramService(); 
    }

    @Given("the admin dashboard is loaded")
    public void the_admin_dashboard_is_loaded() {
        System.out.println("Admin dashboard loaded.");
    }

    
    @When("the admin views program enrollment statistics")
    public void the_admin_views_program_enrollment_statistics() {
        popularPrograms = programService.getMostPopularPrograms()
                                        .stream()
                                        .map(Program::getTitle) 
                                        .collect(Collectors.toList());
    }

    @Then("the system should display the most popular programs by enrollment")
    public void the_system_should_display_the_most_popular_programs_by_enrollment() {
        assertNotNull("Popular programs list should not be null", popularPrograms);
        System.out.println("Most popular programs: " + popularPrograms);
    }
    
    @When("the admin generates a report on program revenue")
    public void the_admin_generates_a_report_on_program_revenue() {
        
        Map<String, Double> rawRevenueReport = programService.generateRevenueReport();
        generatedReport = new HashMap<>(rawRevenueReport); 
    }

    @Then("the system should display total revenue for all active and completed programs")
    public void the_system_should_display_total_revenue_for_all_active_and_completed_programs() {
        assertNotNull("Revenue report should not be null", generatedReport);
        generatedReport.forEach((program, revenue) -> {
            System.out.println("Program: " + program + ", Revenue: $" + revenue);
        });
    }


    
    @When("the admin generates a report on program attendance")
    public void the_admin_generates_a_report_on_program_attendance() {
        Map<String, Integer> rawReport = programService.generateAttendanceReport();
        generatedReport = new HashMap<>(rawReport);
    }

    @Then("the system should display attendance data for active and completed programs")
    public void the_system_should_display_attendance_data_for_active_and_completed_programs() {
        assertNotNull("Attendance report should not be null", generatedReport);
        generatedReport.forEach((key, value) -> {
            System.out.println("Program: " + key + ", Attendance: " + value);
        });
    }

    
    @When("the admin generates a report on client progress")
    public void the_admin_generates_a_report_on_client_progress() {
        Map<String, String> rawReport = programService.generateClientProgressReport();
        generatedReport = new HashMap<>(rawReport); 
    }

    @Then("the system should display client progress statistics for all programs")
    public void the_system_should_display_client_progress_statistics_for_all_programs() {
        assertNotNull("Client progress report should not be null", generatedReport);
        generatedReport.forEach((key, value) -> {
            System.out.println("Program: " + key + ", Progress: " + value); 
        });
    }

   
    @When("the admin views the list of active and completed programs")
    public void the_admin_views_the_list_of_active_and_completed_programs() {
        categorizedPrograms = programService.getActiveAndCompletedPrograms();
    }

    @Then("the system should display a list of programs categorized as active or completed")
    public void the_system_should_display_a_list_of_programs_categorized_as_active_or_completed() {
        assertNotNull("List of active and completed programs should not be null", categorizedPrograms);
        
        System.out.println("Active Programs:");
        categorizedPrograms.getOrDefault("active", List.of()).forEach(System.out::println);

        System.out.println("Completed Programs:");
        categorizedPrograms.getOrDefault("completed", List.of()).forEach(System.out::println);
    }
}
