package com.fitness.management;

import java.util.ArrayList;
import java.util.List;

public class PersistenceUtilMock {
    private static List<Profile> profiles = new ArrayList<>();

    public static void setProfiles(List<Profile> mockProfiles) {
        profiles = new ArrayList<>(mockProfiles);
    }

    public static List<Profile> loadClientProfileDataList() {
        return new ArrayList<>(profiles);
    }

    public static void saveClientProfileData(List<Profile> updatedProfiles) {
        profiles = new ArrayList<>(updatedProfiles);
    }
}
