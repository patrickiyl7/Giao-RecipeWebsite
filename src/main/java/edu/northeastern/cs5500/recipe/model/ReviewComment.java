package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;
import lombok.Data;

/**
 * A subclass of Comment, used to save ReviewComment Object, which created for comment each review
 * under each recipes.
 */
@Data
public class ReviewComment extends Comment {
    // private ObjectId reviewId;
    private String reviewId;

    public ReviewComment(String userId, String date, String content, String reviewId) {
        super(userId, date, content);
        this.reviewId = reviewId;
    }

    public ReviewComment() {};

    /**
     * Constructor used to creating DBObject for connecting MongoDB. *
     *
     * @param reviewCommentDB
     */
    public ReviewComment(DBObject reviewCommentDB) {
        this.reviewId = (String) reviewCommentDB.get("_id");
        this.userId = (String) reviewCommentDB.get("userId");
        this.date = (String) reviewCommentDB.get("date");
        this.content = (String) reviewCommentDB.get("content");
    }

    /** @return true if this reviewComment is valid */
    @JsonIgnore
    public boolean isValid() {
        return this.userId != null;
    }

    @Override
    public String toString() {
        return "ReviewComment []";
    }
}
