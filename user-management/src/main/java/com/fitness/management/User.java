package com.fitness.management;

public class User {
    private String name;
    private String email;
    private String role;
    private boolean active;
    private int age;
    private String fitnessGoals;
    private String dietaryPreferences;
    private String dietaryRestrictions;

    public User(String name, String email, String role, boolean active, int age, String fitnessGoals, String dietaryPreferences, String dietaryRestrictions) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = active;
        this.age = age;
        this.fitnessGoals = fitnessGoals;
        this.dietaryPreferences = dietaryPreferences;
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getFitnessGoals() { return fitnessGoals; }
    public void setFitnessGoals(String fitnessGoals) { this.fitnessGoals = fitnessGoals; }

    public String getDietaryPreferences() { return dietaryPreferences; }
    public void setDietaryPreferences(String dietaryPreferences) { this.dietaryPreferences = dietaryPreferences; }

    public String getDietaryRestrictions() { return dietaryRestrictions; }
    public void setDietaryRestrictions(String dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }
}
