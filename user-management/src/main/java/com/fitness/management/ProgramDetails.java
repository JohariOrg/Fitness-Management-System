package com.fitness.management;

import java.util.List;
import java.util.Objects;

public class ProgramDetails {
    private String title;
    private String duration;
    private String difficulty;
    private String goals;
    private double price;
    private String schedule;
    private List<String> videos;
    private List<String> documents;

    
    public ProgramDetails(String title, String duration, String difficulty, String goals, double price, String schedule,
                          List<String> videos, List<String> documents) {
        this.title = Objects.requireNonNullElse(title, "No Title");
        this.duration = Objects.requireNonNullElse(duration, "No Duration");
        this.difficulty = Objects.requireNonNullElse(difficulty, "No Difficulty");
        this.goals = Objects.requireNonNullElse(goals, "No Goals");
        this.price = price > 0 ? price : 0.0;
        this.schedule = Objects.requireNonNullElse(schedule, "No Schedule");
        this.videos = Objects.requireNonNullElse(videos, List.of());
        this.documents = Objects.requireNonNullElse(documents, List.of());
    }

    
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

    
    @Override
    public String toString() {
        return "ProgramDetails{" +
               "title='" + title + '\'' +
               ", duration='" + duration + '\'' +
               ", difficulty='" + difficulty + '\'' +
               ", goals='" + goals + '\'' +
               ", price=" + price +
               ", schedule='" + schedule + '\'' +
               ", videos=" + videos +
               ", documents=" + documents +
               '}';
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramDetails that = (ProgramDetails) o;
        return Double.compare(that.price, price) == 0 &&
               title.equals(that.title) &&
               duration.equals(that.duration) &&
               difficulty.equals(that.difficulty) &&
               goals.equals(that.goals) &&
               schedule.equals(that.schedule) &&
               videos.equals(that.videos) &&
               documents.equals(that.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration, difficulty, goals, price, schedule, videos, documents);
    }
}
