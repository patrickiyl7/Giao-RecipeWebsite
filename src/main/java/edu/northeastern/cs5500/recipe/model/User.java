package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;

public class User {
    private String userId;
    private String userName;
    private String passWord;
    private String emailAddress;
    private String slogan;
    // private String status;
    // public enum Status implements Serializable {
    // GUEST, VIP, CERTIFIED_CHEF, GOURMET;
    // }

    private String status;
    private String finalImage;

    public User() {}

    public User(DBObject userDB) {
        this.userId = (String) userDB.get("userId");
        this.userName = (String) userDB.get("username");
        this.passWord = (String) userDB.get("passWord");
        this.emailAddress = (String) userDB.get("emailAddress");
        this.slogan = (String) userDB.get("slogan");
        this.status = (String) userDB.get("status");
        this.finalImage = (String) userDB.get("finalImage");
    }

    public User(
            String userId,
            String userName,
            String passWord,
            String emailAddress,
            String slogan,
            String status,
            String finalImage) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.emailAddress = emailAddress;
        this.slogan = slogan;
        this.status = status;
        this.finalImage = finalImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getfinalImage() {
        return finalImage;
    }

    public void setfinalImage(String finalImage) {
        this.finalImage = finalImage;
    }

    /** @return true if this user is valid */
    @JsonIgnore
    public boolean isValid() {
        return this.userName != null && !this.userName.isEmpty();
    }
}
