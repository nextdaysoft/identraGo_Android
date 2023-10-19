package com.project.identranaccess.model;

public class RegisterDataModel {
  public   String email;
  public   String mobile;
  public   String password;
  public   String  userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

   /* public RegisterDataModel(String email, String mobileNo, String password) {
        this.email =email;
        this.mobile=mobileNo;
        this.password=password;
    }*/

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
