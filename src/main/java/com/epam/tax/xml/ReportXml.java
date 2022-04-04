package com.epam.tax.xml;

import java.util.Objects;

public class ReportXml {
    private int salary;
    private String company;

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportXml reportXml = (ReportXml) o;
        return salary == reportXml.salary && Objects.equals(company, reportXml.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary, company);
    }

    @Override
    public String toString() {
        return "ReportXml{" +
                "salary=" + salary +
                ", company='" + company + '\'' +
                '}';
    }
}
