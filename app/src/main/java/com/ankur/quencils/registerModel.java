package com.ankur.quencils;

public class registerModel {
    String name,email,mobile,DOB;
    public registerModel()
    {

    }

    public registerModel(String name, String email, String mobile, String DOB) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.DOB = DOB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
