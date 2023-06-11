package com.setqt.Hiring.DTO.ResponseDTO;

import jakarta.persistence.Column;

public class CompanyResponse {
    private Long id;
    private String name;
    private String taxCode;
    private String address;
    private String domain;
    private String logo;
    private String companySize;
    private String 	workTime;
    private String description;
    private Double rate;


    public CompanyResponse(Long id, String name, String taxCode, String address, String domain, String logo, String companySize, String workTime, String description, Double rate) {
        this.id = id;
        this.name = name;
        this.taxCode = taxCode;
        this.address = address;
        this.domain = domain;
        this.logo = logo;
        this.companySize = companySize;
        this.workTime = workTime;
        this.description = description;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
