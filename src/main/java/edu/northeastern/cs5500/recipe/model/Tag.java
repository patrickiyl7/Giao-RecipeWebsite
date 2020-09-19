package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;
import java.util.Objects;

public class Tag {
    private String name;
    private String tagId;
    private String recipeId;

    public Tag(DBObject tagDB) {
        this.tagId = (String) tagDB.get("_id");
        this.recipeId = (String) tagDB.get("recipeId");
        this.name = (String) tagDB.get("name");
    }

    public Tag(String name, String recipeId) {
        this.tagId = recipeId + "_" + name;
        this.name = name;
        this.recipeId = recipeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipeId() {
        return this.recipeId;
    }

    public Tag(String name, String tagId, String recipeId) {
        this.name = name;
        this.tagId = tagId;
        this.recipeId = recipeId;
    }

    public String getTagId() {
        return this.tagId;
    }

    public Tag name(String name) {
        this.name = name;
        return this;
    }

    public Tag tagId(String tagId) {
        this.tagId = tagId;
        return this;
    }

    public Tag recipeId(String recipeId) {
        this.recipeId = recipeId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name) && Objects.equals(recipeId, tag.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, recipeId);
    }

    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'" + ", recipeId='" + getRecipeId() + "'" + "}";
    }

    /** @return true if this tag is valid */
    @JsonIgnore
    public boolean isValid() {
        return this.name != null
                && !this.name.isEmpty()
                && this.recipeId != null
                && !this.recipeId.isEmpty();
    }
}
