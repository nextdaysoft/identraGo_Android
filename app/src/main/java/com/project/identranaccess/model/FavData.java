package com.project.identranaccess.model;

public class FavData {
    private String Name;
    private String LastName;
    private String VisitDate;
    private String ReasonOfVisit;
    private String Comment;
    private String Code;
    private String Id;
    private Boolean clicked;

    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Boolean getClicked() {
        return clicked;
    }

    public String getReasonOfVisit() {
        return ReasonOfVisit;
    }

    public void setReasonOfVisit(String reasonOfVisit) {
        ReasonOfVisit = reasonOfVisit;
    }

    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }


    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getVisitDate() {
        return VisitDate;
    }

    public void setVisitDate(String visitData) {
        this.VisitDate = visitData;
    }



    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        this.Comment = comment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }


}
