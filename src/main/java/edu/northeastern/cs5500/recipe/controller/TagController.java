package edu.northeastern.cs5500.recipe.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.northeastern.cs5500.recipe.JavaMongoDBConnection;
import edu.northeastern.cs5500.recipe.exception.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.exception.InvalidException;
import edu.northeastern.cs5500.recipe.model.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * TagController used to build connection to MongoDB and have multiple method to add and edit tag
 * information on MongoDB.
 */
public class TagController {
    private static DBCollection collection = JavaMongoDBConnection.getDB().getCollection("tag");

    @Inject
    TagController() {}

    /**
     * Get tags by recipeId. *
     *
     * @param String recipeId
     * @return List tagList
     * @throws Exception DuplicateKeyException
     */
    @Nullable
    public List<Tag> getTagsByRecipeId(@Nonnull String recipeId) {
        List<Tag> tagList = new ArrayList<>();
        DBCursor tags = collection.find(new BasicDBObject("recipeId", recipeId));
        for (DBObject t : tags) {
            tagList.add(new Tag(t));
        }
        return tagList;
    }

    /**
     * Get all tags. *
     *
     * @return Map tagMap
     * @throws Exception DuplicateKeyException
     */
    @Nullable
    public Map<String, Integer> getAllTagsAndCount() {
        List<Tag> tagList = new ArrayList<>();
        DBCursor tags = collection.find(new BasicDBObject());
        for (DBObject t : tags) {
            tagList.add(new Tag(t));
        }
        Map<String, Integer> tagMap = new HashMap<>();
        for (Tag tag : tagList) {
            if (tagMap.containsKey(tag.getName())) {
                tagMap.put(tag.getName(), tagMap.get(tag.getName()) + 1);
            } else {
                tagMap.put(tag.getName(), 1);
            }
        }
        return tagMap;
    }

    /**
     * Adding a tag to collection. *
     *
     * @param Tag tag
     * @return String tagId
     * @throws Exception DuplicateKeyException
     */
    @Nonnull
    public String addTag(@Nonnull Tag tag) throws Exception {
        if (!tag.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new InvalidException("Your recipe is invalid.");
        }
        DBCursor tags = collection.find(new BasicDBObject("_id", tag.getTagId()));
        if (tags != null && tags.size() != 0) {
            throw new DuplicateKeyException("You have created the tag.");
        }
        DBObject recipeObject =
                new BasicDBObject("_id", tag.getTagId())
                        .append("recipeId", tag.getRecipeId())
                        .append("name", tag.getName());
        collection.insert(recipeObject);
        tags.close();
        return tag.getName();
    }

    /**
     * Deleting a tag by recipeId. *
     *
     * @param String recipeId
     * @throws Exception NullKeyException
     */
    public void deleteTag(@Nonnull String recipeId) throws Exception {
        DBCursor tags = collection.find(new BasicDBObject("recipeId", recipeId));
        for (DBObject t : tags) {
            collection.findAndRemove(t);
        }
        tags.close();
    }
}
