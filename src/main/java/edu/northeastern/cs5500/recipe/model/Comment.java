package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;

/** A super Class. * Used to save both comments and review from the user. * */
public class Comment {

    protected String userId;
    protected String date;
    protected String content;

    /**
     * Constructor. *
     *
     * @param userId User who give comment
     * @param date the date which user comment
     * @param content the content in a comment
     */
    public Comment(String userId, String date, String content) {

        this.userId = userId;
        this.date = date;
        this.content = content;
    }

    public Comment() {};

    public Comment(DBObject commentDB) {
        this.userId = (String) commentDB.get("userId");
        this.date = (String) commentDB.get("date");
        this.content = (String) commentDB.get("content");
    }

    /** @return userId */
    public String getUserId() {
        return userId;
    }

    /** @param userId */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** @return date */
    public String getDate() {
        return date;
    }

    /** @param date */
    public void setDate(String date) {
        this.date = date;
    }

    /** @return content */
    public String getContent() {
        return content;
    }

    /** @param content */
    public void setContent(String content) {
        this.content = content;
    }

    /** @return true if this comment is valid */
    @JsonIgnore
    public boolean isValid() {
        return this.userId != null;
    }
}
