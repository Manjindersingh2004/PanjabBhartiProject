package com.example.panjabbharti.Classes;

import com.google.firebase.Timestamp;

import java.util.HashMap;

public class DataModal {
    int AgeMin,AgeMax;
    String NotificationURL,FormURL;
    Timestamp StartDate,EndDate;

    boolean PanjabiRequired;
    HashMap<String,String> Qualification;

    public int getAgeMin() {
        return AgeMin;
    }

    public void setAgeMin(int ageMin) {
        AgeMin = ageMin;
    }

    public int getAgeMax() {
        return AgeMax;
    }

    public void setAgeMax(int ageMax) {
        AgeMax = ageMax;
    }

    public String getNotificationURL() {
        return NotificationURL;
    }

    public void setNotificationURL(String notificationURL) {
        NotificationURL = notificationURL;
    }

    public String getFormURL() {
        return FormURL;
    }

    public void setFormURL(String formURL) {
        FormURL = formURL;
    }

    public Timestamp getStartDate() {
        return StartDate;
    }

    public void setStartDate(Timestamp startDate) {
        StartDate = startDate;
    }

    public Timestamp getEndDate() {
        return EndDate;
    }

    public void setEndDate(Timestamp endDate) {
        EndDate = endDate;
    }

    public HashMap<String, String> getQualification() {
        return Qualification;
    }

    public void setQualification(HashMap<String, String> qualification) {
        Qualification = qualification;
    }

    public boolean isPanjabiRequired() {
        return PanjabiRequired;
    }

    public void setPanjabiRequired(boolean panjabiRequired) {
        PanjabiRequired = panjabiRequired;
    }
}
