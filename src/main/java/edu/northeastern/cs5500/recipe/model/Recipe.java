package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import lombok.Data;

/**
 * A class to save recipe information, including user who post the recipe, recipeId, date when
 * posting, recipe title and description.
 */
@Data
public class Recipe {
    private String userName;
    private String userImage;
    private String recipeId;
    private String date;
    private String title;
    private String description;
    private String finalImage;

    public Recipe() {}

    /** Constructor for MongoObject, in order to connect MongoDB */
    public Recipe(DBObject recipeDB) {
        this.userName = (String) recipeDB.get("username");
        this.userImage = (String) recipeDB.get("userImage");
        this.recipeId = (String) recipeDB.get("_id");
        this.date = (String) recipeDB.get("date");
        this.title = (String) recipeDB.get("title");
        this.description = (String) recipeDB.get("description");
        this.finalImage = (String) recipeDB.get("finalImage");
    }

    /**
     * @param date date
     * @param title title
     * @param userName userName
     * @param description description
     */
    public Recipe(
            Date date,
            String title,
            String userName,
            String description,
            String finalImage,
            String userImage) {
        this.recipeId = userName.trim() + "_" + title.replaceAll("\\s", "_");
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        this.date = simpleDateFormat.format(date);
        this.title = title;
        this.description = description;
        this.userName = userName;
        this.finalImage = finalImage;
        this.userImage = userImage;
    }

    public Recipe(
            String date,
            String title,
            String userName,
            String description,
            String finalImage,
            String userImage) {
        this.recipeId = userName.trim() + "_" + title.replaceAll("\\s", "_");
        this.date = date;
        this.title = title;
        this.userName = userName;
        this.description = description;
        this.finalImage = finalImage;
        this.userImage = userImage;
    }

    /** @return userName */
    public String getUserName() {
        return this.userName;
    }

    /** @param userName */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** @return recipeId */
    public String getRecipeId() {
        return this.recipeId;
    }

    /** @param recipeId */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    /** @return date */
    public String getDate() {
        return this.date;
    }

    /** @param date */
    public void setDate(String date) {
        this.date = date;
    }

    /** @return title */
    public String getTitle() {
        return this.title;
    }

    /** @param title */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return description */
    public String getDescription() {
        return this.description;
    }

    /** @param description */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinalImage() {
        return this.finalImage;
    }

    public String getUserImage() {
        return this.userImage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Recipe)) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        return Objects.equals(recipeId, recipe.recipeId)
                && Objects.equals(date, recipe.date)
                && Objects.equals(title, recipe.title)
                && Objects.equals(description, recipe.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, date, title, description);
    }

    @Override
    public String toString() {
        return "{"
                + " recipeId='"
                + getRecipeId()
                + "'"
                + ", date='"
                + getDate()
                + "'"
                + ", title='"
                + getTitle()
                + "'"
                + ", description='"
                + getDescription()
                + "'"
                + "}";
    }

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return this.title != null
                && !this.title.isEmpty()
                && this.recipeId != null
                && !this.recipeId.isEmpty();
    }
}
