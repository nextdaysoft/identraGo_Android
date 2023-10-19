package com.project.identranaccess.model;

public class UserListModel {
    private String Name;
    private String lastName;
    private String email;
    private String password;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserListModel(String name, String lastname, String email, String pass) {
        this.Name=name;
        this.lastName=lastname;
        this.email=email;
        this.password=pass;
    }
}
