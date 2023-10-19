package com.project.identranaccess.model;

public class VisitorData {
    private String Name;
    private String lastName;
    private String visitDate;
    private String reasonofvisit;
    private String comment;
    private String code;
    private String id;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitData) {
        this.visitDate = visitData;
    }

    public String getReasonofvisit() {
        return reasonofvisit;
    }

    public void setReasonofvisit(String reasonofvisit) {
        this.reasonofvisit = reasonofvisit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VisitorData(String name, String lastname, String visitData, String reasonofvisit, String comment, String code, String id) {
        this.Name = name;
        this.lastName = lastname;
        this.visitDate = visitData;
        this.reasonofvisit = reasonofvisit;
        this.comment = comment;
        this.code = code;
        this.id = id;

    }


}
