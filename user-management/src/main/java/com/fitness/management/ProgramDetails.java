package com.fitness.management;

import java.util.List;
import java.util.Objects;

public class ProgramDetails {
    private final String title;
    private final String duration;
    private final String difficulty;
    private final String goals;
    private final double price;
    private final String schedule;
    private final List<String> videos;
    private final List<String> documents;

    // Private constructor to enforce the use of the builder
    private ProgramDetails(Builder builder) {
        this.title = Objects.requireNonNullElse(builder.title, "No Title");
        this.duration = Objects.requireNonNullElse(builder.duration, "No Duration");
        this.difficulty = Objects.requireNonNullElse(builder.difficulty, "No Difficulty");
        this.goals = Objects.requireNonNullElse(builder.goals, "No Goals");
        this.price = builder.price > 0 ? builder.price : 0.0;
        this.schedule = Objects.requireNonNullElse(builder.schedule, "No Schedule");
        this.videos = Objects.requireNonNullElse(builder.videos, List.of());
        this.documents = Objects.requireNonNullElse(builder.documents, List.of());
    }

    // Getters
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

    // Builder Class
    public static class Builder {
        private String title;
        private String duration;
        private String difficulty;
        private String goals;
        private double price;
        private String schedule;
        private List<String> videos;
        private List<String> documents;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public Builder setDifficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder setGoals(String goals) {
            this.goals = goals;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setSchedule(String schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setVideos(List<String> videos) {
            this.videos = videos;
            return this;
        }

        public Builder setDocuments(List<String> documents) {
            this.documents = documents;
            return this;
        }

        public ProgramDetails build() {
            return new ProgramDetails(this);
        }
    }
}
