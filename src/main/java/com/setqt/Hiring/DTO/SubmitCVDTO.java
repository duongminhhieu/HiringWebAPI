package com.setqt.Hiring.DTO;

import java.util.Date;

public class SubmitCVDTO {

    private String introLetter;
    private String name;

    public SubmitCVDTO(String introLetter, String name) {
        this.introLetter = introLetter;
        this.name = name;
    }

    public String getIntroLetter() {
        return introLetter;
    }

    public void setIntroLetter(String introLetter) {
        this.introLetter = introLetter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
