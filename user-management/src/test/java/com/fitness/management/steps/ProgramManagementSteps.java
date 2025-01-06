package com.fitness.management.steps;
import com.fitness.management.ProgramDetails;
import com.fitness.management.Program;
import com.fitness.management.ProgramService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgramManagementSteps {

    private static ProgramService programService = new ProgramService(); 
    private Program currentProgram;
    private static final Logger logger = LoggerFactory.getLogger(ProgramManagementSteps.class);
    

    @Given("the instructor dashboard is loaded")
    public void the_instructor_dashboard_is_loaded() {
        logger.info("Instructor dashboard is now loaded.");
        
    }


    @When("the instructor creates a new fitness program with:") 
    public void the_instructor_creates_a_new_fitness_program_with(DataTable dataTable) {
        
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        List<String> videos = List.of(data.get("videos").split(", "));
        List<String> documents = List.of(data.get("documents").split(", "));

        
        currentProgram = new Program.Builder(data.get("title").trim())
            .setDuration(data.get("duration"))
            .setDifficulty(data.get("difficulty"))
            .setGoals(data.get("goals"))
            .setPrice(Double.parseDouble(data.get("price")))
            .setSchedule(data.get("schedule"))
            .setVideos(videos)
            .setDocuments(documents)
            .build();

        boolean result = programService.addProgram(currentProgram);
        assertTrue("Program should be created successfully", result);
    }


    @Then("the fitness program should be created successfully")
    public void the_fitness_program_should_be_created_successfully() {
        assertNotNull("Program should exist", programService.getProgram(currentProgram.getTitle()));
    }

    @Then("the program with title {string} should exist in the system")
    public void the_program_with_title_should_exist_in_the_system(String title) {
        Program existingProgram = programService.getProgram(title.trim());
        assertNotNull("The program with title " + title + " should exist in the system", existingProgram);
    }

    @When("the instructor updates the program titled {string} with:") 
    public void the_instructor_updates_the_program_titled_with(String title, DataTable dataTable) {
        
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        
        List<String> videos = List.of(data.get("videos").split(", ")).stream()
                                  .map(String::trim) 
                                  .toList();
        List<String> documents = List.of(data.get("documents").split(", ")).stream()
                                     .map(String::trim) 
                                     .toList();

        
        ProgramDetails programDetails = new ProgramDetails(
            title.trim(),
            data.get("duration").trim(),
            data.get("difficulty").trim(),
            data.get("goals").trim(),
            Double.parseDouble(data.get("price").trim()),
            data.get("schedule").trim(),
            videos,
            documents
        );
        
        boolean updated = programService.updateProgram(programDetails);
        assertTrue("Program should be updated successfully", updated);
        currentProgram = programService.getProgram(title.trim());
    }


    @Then("the fitness program should be updated successfully")
    public void the_fitness_program_should_be_updated_successfully() {
        Program updatedProgram = programService.getProgram(currentProgram.getTitle());
        assertNotNull("Updated program should exist", updatedProgram);
    }

    @When("the instructor deletes the program titled {string}")
    public void the_instructor_deletes_the_program_titled(String title) {
        boolean deleted = programService.deleteProgram(title.trim());
        assertTrue("Program should be deleted successfully", deleted);
        currentProgram = null;
    }

    @Then("the fitness program should no longer exist in the system")
    public void the_fitness_program_should_no_longer_exist_in_the_system() {
    	assertNull("Program should no longer exist", currentProgram);
     
    }

  }

