package com.fitness.management.steps;

import com.fitness.management.Program;
import com.fitness.management.ProgramService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgramManagementSteps {

    private final ProgramService programService;
    private Program currentProgram;
    private static final Logger logger = LoggerFactory.getLogger(ProgramManagementSteps.class);

    public ProgramManagementSteps() {
        this.programService = new ProgramService();
    }

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
            .setDuration(data.get("duration").trim())
            .setDifficulty(data.get("difficulty").trim())
            .setGoals(data.get("goals").trim())
            .setPrice(Double.parseDouble(data.get("price").trim()))
            .setSchedule(data.get("schedule").trim())
            .setVideos(videos)
            .setDocuments(documents)
            .build();

        boolean result = programService.addProgram(currentProgram);
        assertTrue("Program should be created successfully", result);
        logger.info("Program '{}' created successfully.", currentProgram.getTitle());
    }

    @Then("the fitness program should be created successfully")
    public void the_fitness_program_should_be_created_successfully() {
        Program existingProgram = programService.getProgram(currentProgram.getTitle());
        assertNotNull("Program should exist in the system", existingProgram);
        logger.info("Program '{}' exists in the system.", currentProgram.getTitle());
    }

    @Then("the program with title {string} should exist in the system")
    public void the_program_with_title_should_exist_in_the_system(String title) {
        Program existingProgram = programService.getProgram(title.trim());
        assertNotNull("The program with title '" + title + "' should exist in the system", existingProgram);
        logger.info("Verified that the program '{}' exists in the system.", title);
    }

    @When("the instructor updates the program titled {string} with:")
    public void the_instructor_updates_the_program_titled_with(String title, DataTable dataTable) {
        logger.info("Attempting to update program with title '{}'.", title);

        Map<String, String> data = dataTable.asMap(String.class, String.class);

        List<String> videos = List.of(data.get("videos").split(", "));
        List<String> documents = List.of(data.get("documents").split(", "));

        Program updatedProgram = new Program.Builder(title.trim())
            .setDuration(data.get("duration").trim())
            .setDifficulty(data.get("difficulty").trim())
            .setGoals(data.get("goals").trim())
            .setPrice(Double.parseDouble(data.get("price").trim()))
            .setSchedule(data.get("schedule").trim())
            .setVideos(videos)
            .setDocuments(documents)
            .build();

        boolean updated = programService.updateProgram(updatedProgram);

        if (!updated) {
            logger.error("Update failed: Program '{}' does not exist or data is invalid.", title);
        }

        assertTrue("Program should be updated successfully", updated);

        currentProgram = programService.getProgram(title.trim());
        assertNotNull("Program should exist after update", currentProgram);
        logger.info("Program '{}' updated successfully.", title);
    }

    @Then("the fitness program should be updated successfully")
    public void the_fitness_program_should_be_updated_successfully() {
        assertNotNull("Updated program should exist", currentProgram);
        logger.info("Verified updated program '{}' exists.", currentProgram.getTitle());
    }

    @When("the instructor deletes the program titled {string}")
    public void the_instructor_deletes_the_program_titled(String title) {
        logger.info("Attempting to delete program with title '{}'.", title);
        boolean deleted = programService.deleteProgram(title.trim());
        if (!deleted) {
            logger.error("Failed to delete program with title '{}'. Ensure the program exists before deletion.", title);
        }
        assertTrue("Program should be deleted successfully", deleted);
        currentProgram = null;
    }

    @Then("the fitness program should no longer exist in the system")
    public void the_fitness_program_should_no_longer_exist_in_the_system() {
        Program deletedProgram = programService.getProgram(currentProgram != null ? currentProgram.getTitle() : "");
        assertNull("Program should no longer exist in the system", deletedProgram);
        logger.info("Verified program no longer exists in the system.");
    }
}
