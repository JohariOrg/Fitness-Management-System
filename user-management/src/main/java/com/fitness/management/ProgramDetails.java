package com.fitness.management;

import java.util.List;

public class ProgramDetails {
    private String title;
    private String duration;
    private String difficulty;
    private String goals;
    private double price;
    private String schedule;
    private List<String> videos;
    private List<String> documents;

    // Constructor
    public ProgramDetails(String title, String duration, String difficulty, String goals, double price, String schedule,
                          List<String> videos, List<String> documents) {
        this.title = title;
        this.duration = duration;
        this.difficulty = difficulty;
        this.goals = goals;
        this.price = price;
        this.schedule = schedule;
        this.videos = videos;
        this.documents = documents;
    }

    // Getters and setters (Optional if needed)
    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getGoals() {
        return goals;
    }

    public double getPrice() {
        return price;
    }

    public String getSchedule() {
        return schedule;
    }

    public List<String> getVideos() {
        return videos;
    }

    public List<String> getDocuments() {
        return documents;
    }
}
