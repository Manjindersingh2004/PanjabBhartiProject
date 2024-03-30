package com.example.panjabbharti.Classes;

import java.time.LocalDate;

public class JobStatus {
    LocalDate startDate,endDate;
    String job_title,notificationURL,formURL;

    public JobStatus(LocalDate startDate, LocalDate endDate, String job_title) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.job_title = job_title;
    }

    public JobStatus(LocalDate startDate, LocalDate endDate, String job_title, String notificationURL, String formURL) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.job_title = job_title;
        this.notificationURL = notificationURL;
        this.formURL = formURL;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getNotificationURL() {
        return notificationURL;
    }

    public void setNotificationURL(String notificationURL) {
        this.notificationURL = notificationURL;
    }

    public String getFormURL() {
        return formURL;
    }

    public void setFormURL(String formURL) {
        this.formURL = formURL;
    }
}
