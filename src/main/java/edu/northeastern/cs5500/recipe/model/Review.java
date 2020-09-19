package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;

// import lombok.Data;

/**
 * A subclass of comment, used to save review object, which contains recipeId and rating about
 * recipes.
 */
// @Data
public class Review extends Comment {
    private String recipeId;
    private Integer rating;
    public String reviewId;

    public Review() {};
    /**
     * @param userId userId
     * @param date date
     * @param content content
     * @param recipeId recipeId
     * @param rating rating
     */
    public Review(
            String userId,
            String date,
            String content,
            String recipeId,
            Integer rating,
            String reviewId) {
        super(userId, date, content);
        this.recipeId = recipeId;
        this.rating = rating;
        this.reviewId = recipeId + '/' + userId;
    };

    /**
     * Constructor for connecting MongoDB. *
     *
     * @param reviewDB
     */
    public Review(DBObject reviewDB) {
        this.userId = (String) reviewDB.get("userId");
        this.date = (String) reviewDB.get("date");
        this.content = (String) reviewDB.get("content");
        this.recipeId = (String) reviewDB.get("recipeId");
        this.rating = (Integer) reviewDB.get("rating");
        this.reviewId = (String) reviewDB.get("_id").toString();
    }

    /** @return recipeId */
    public String getRecipeId() {
        return this.recipeId;
    }

    public String getreviewId() {
        return this.reviewId;
    }

    /** @return recipeId */
    public String getUserId() {
        return this.userId;
    }

    /** @param recipeId recipeId */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    /** @return rating */
    public Integer getRating() {
        return this.rating;
    }

    /** @param rating */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /** @return content */
    public String getContent() {
        return super.getContent();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result + ((recipeId == null) ? 0 : recipeId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Review [rating=" + rating + ", recipeId=" + recipeId + "]";
    }

    /** @return true if this review is valid */
    @JsonIgnore
    public boolean isValid() {
        return this.recipeId != null && !this.recipeId.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Review other = (Review) obj;
        if (rating == null) {
            if (other.rating != null) return false;
        } else if (!rating.equals(other.rating)) return false;
        if (recipeId == null) {
            if (other.recipeId != null) return false;
        } else if (!recipeId.equals(other.recipeId)) return false;
        return true;
    }
}
