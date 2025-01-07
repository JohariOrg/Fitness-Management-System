package com.fitness.management;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Program implements Serializable {
    private static final long serialVersionUID = 1L;

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

    // Private Constructor for Builder
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

    public int getEnrollment() {
        return enrollment;
    }

    public String getProgressSummary() {
        return progressSummary;
    }

    public boolean isActive() {
        return isActive;
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
        private int enrollment = 0;
        private String progressSummary = "";
        private boolean isActive = true;

        public Builder(String title) {
            this.title = title;
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

        public Program build() {
            return new Program(this);
        }
    }
    
    @Override
    public String toString() {
        return String.format(
            "Program Title: %s\nDuration: %s\nDifficulty: %s\nGoals: %s\nPrice: %.2f\nSchedule: %s\nEnrollment: %d\nProgress: %s\nActive: %s\nVideos: %s\nDocuments: %s",
            title,
            duration,
            difficulty,
            goals,
            price,
            schedule,
            enrollment,
            progressSummary,
            isActive ? "Yes" : "No",
            videos != null ? String.join(", ", videos) : "None",
            documents != null ? String.join(", ", documents) : "None"
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Program program = (Program) obj;

        return Double.compare(program.price, price) == 0 &&
                enrollment == program.enrollment &&
                isActive == program.isActive &&
                title.equals(program.title) &&
                Objects.equals(duration, program.duration) &&
                Objects.equals(difficulty, program.difficulty) &&
                Objects.equals(goals, program.goals) &&
                Objects.equals(schedule, program.schedule) &&
                Objects.equals(videos, program.videos) &&
                Objects.equals(documents, program.documents) &&
                Objects.equals(progressSummary, program.progressSummary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration, difficulty, goals, price, schedule, videos, documents, enrollment, progressSummary, isActive);
    }


}
