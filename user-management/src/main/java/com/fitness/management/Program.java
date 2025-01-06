package com.fitness.management;

import java.io.Serializable;
import java.util.List;

public class Program implements Serializable {
    private static final long serialVersionUID = 1L;

    // Fields
    private String title;
    private String duration;
    private String difficulty;
    private String goals;
    private double price;
    private String schedule;
    private List<String> videos;
    private List<String> documents;
    private int enrollment;
    private String progressSummary;
    private boolean isActive;

    // Private Constructor (Used by the Builder)
    private Program(Builder builder) {
        this.title = builder.title;
        this.duration = builder.duration;
        this.difficulty = builder.difficulty;
        this.goals = builder.goals;
        this.price = builder.price;
        this.schedule = builder.schedule;
        this.videos = builder.videos;
        this.documents = builder.documents;
        this.enrollment = builder.enrollment;
        this.progressSummary = builder.progressSummary;
        this.isActive = builder.isActive;
    }

    // Getters for fields (optional, if needed)
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

    public int getEnrollment() {
        return enrollment;
    }

    public String getProgressSummary() {
        return progressSummary;
    }

    public boolean isActive() {
        return isActive;
    }

    // Static Inner Builder Class
    public static class Builder {
        private String title;
        private String duration;
        private String difficulty;
        private String goals;
        private double price;
        private String schedule;
        private List<String> videos;
        private List<String> documents;
        private int enrollment = 0; // Default value
        private String progressSummary = ""; // Default value
        private boolean isActive = true; // Default value

        // Constructor for mandatory field(s)
        public Builder(String title) {
            this.title = title;
        }

        // Setter Methods
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

        public Builder setEnrollment(int enrollment) {
            this.enrollment = enrollment;
            return this;
        }

        public Builder setProgressSummary(String progressSummary) {
            this.progressSummary = progressSummary;
            return this;
        }

        public Builder setIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        // Build Method to Create the Program Instance
        public Program build() {
            return new Program(this);
        }
    }
}
