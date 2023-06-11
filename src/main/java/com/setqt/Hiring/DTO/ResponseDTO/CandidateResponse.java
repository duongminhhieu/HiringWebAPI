package com.setqt.Hiring.DTO.ResponseDTO;

import java.util.Date;

public class CandidateResponse {
    private Long id;
    private String fullName;
    private String gender;
    private String phone;
    private String address;
    private Date dob;
    private String[] skill;
    private String experience;

    public CandidateResponse(Long id, String fullName, String gender, String phone, String address, Date dob, String[] skill, String experience) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.skill = skill;
        this.experience = experience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String[] getSkill() {
        return skill;
    }

    public void setSkill(String[] skill) {
        this.skill = skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
