package com.example.panjabbharti.Activities;
public class Department_Model {
    public String department_name;
    public int course_image;
    public Department_Model(String department_name, int course_image) {
        this.department_name = department_name;
        this.course_image = course_image;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getCourse_image() {
        return course_image;
    }

    public void setCourse_image(int course_image) {
        this.course_image = course_image;
    }
}