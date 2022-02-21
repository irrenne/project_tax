package com.epam.tax.entities;

import java.sql.Date;
import java.util.Objects;

public class Report {

    private long id;
    private String type;
    private Date dateOfCreation;
    private int statusId;
    private byte[] document;
    private long userId;
    private Status status;
    private String comment;
    private long inspectorId;
    private String fileName;

    public Status getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public static Report createReport(String type, Date dateOfCreation, String filename) {
        Report report = new Report();
        report.type = type;
        report.dateOfCreation = dateOfCreation;
        report.fileName = filename;
        return report;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int status_id) {
        this.statusId = status_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && Objects.equals(type, report.type) && Objects.equals(dateOfCreation, report.dateOfCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, dateOfCreation);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                " userid =" + userId +
                ", filename=" + fileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(long inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
