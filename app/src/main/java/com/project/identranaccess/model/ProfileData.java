package com.project.identranaccess.model;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class ProfileData {

    public String  image;
    private String Name;
    private String Location;
    private String userId;

    private String email;
    private String mobile;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getLocation() {
        return Location;
    }

    public void setLocation(String pos) {
        Location = pos;
    }

   /* public ProfileData(Uri img, String name, String email, String mobile, String pos) {
        this.image= String.valueOf(img);
        this.Name=name;
        this.email=email;
        this.mobile=mobile;
        this.Pos=pos;
    }*/
}
