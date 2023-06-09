package com.setqt.Hiring.DTO;

import jakarta.persistence.Column;

import java.io.Serializable;

public class EmployerDTO implements Serializable {
    private String phone;
    private String name;
    private String taxCode;
    private String address;
    private String domain;
    private String companySize;
    private String 	workTime;
    private String description;

    public EmployerDTO(String phone, String name, String taxCode, String address, String domain, String companySize, String workTime, String description) {
        this.phone = phone;
        this.name = name;
        this.taxCode = taxCode;
        this.address = address;
        this.domain = domain;
        this.companySize = companySize;
        this.workTime = workTime;
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
