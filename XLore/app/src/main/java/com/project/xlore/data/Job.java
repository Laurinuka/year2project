package com.project.xlore.data;

import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4
@Entity(tableName = "job")
public class Job
{
    @ColumnInfo(name = "rowid")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user")
    private int userId;

    @ColumnInfo(name = "name")
    private String jobName;

    @ColumnInfo(name = "salary")
    private double salary;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "type")
    private String employmentType;

    @ColumnInfo(name = "closing_date")
    private String closingDate;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "skills")
    private ArrayList<String> requiredSkills;

    @ColumnInfo(name = "applicants")
    private ArrayList<Integer> applicantIds;

    public Job(int userId, String jobName, double salary, String location, String employmentType, String closingDate, String description, ArrayList<String> requiredSkills)
    {
        this.userId = userId;
        this.jobName = jobName;
        this.salary = salary;
        this.location = location;
        this.employmentType = employmentType;
        this.closingDate = closingDate;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.applicantIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(ArrayList<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public ArrayList<Integer> getApplicantIds() {
        return applicantIds;
    }

    public void setApplicantIds(ArrayList<Integer> applicantIds) {
        this.applicantIds = applicantIds;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", userId=" + userId +
                ", jobName='" + jobName + '\'' +
                ", salary=" + salary +
                ", location='" + location + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", closingDate='" + closingDate + '\'' +
                ", description='" + description + '\'' +
                ", requiredSkills=" + requiredSkills +
                ", applicantIds=" + applicantIds +
                '}';
    }
}
