package edu.northeastern.cs5500.recipe.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.northeastern.cs5500.recipe.JavaMongoDBConnection;
import edu.northeastern.cs5500.recipe.model.Review;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * ReviewController used to build connection to MongoDB and have multiple method to add and edit
 * review information on MongoDB.
 */
@Singleton
@Slf4j
public class ReviewController implements Controller {

    private static DBCollection collection = JavaMongoDBConnection.getDB().getCollection("review");
    public static String id;

    @Inject
    ReviewController() {}

    /** register a collection and add it into MongoDB. * */
    @Override
    public void register() {}

    /**
     * get the Review by recipeId. *
     *
     * @param userId userId
     * @return ReviewComment
     */
    @Nullable
    public Review getReview(@Nonnull String reviewId) {
        // TODO: Should this be null or should this throw an exception?
        log.debug("ReviewController > getReview({})", reviewId);
        DBCursor reviews = collection.find(new BasicDBObject("reviewId", reviewId));
        if (reviews.size() != 0) {
            return new Review(reviews.one());
        }
        return null;
    }

    /**
     * @param recipeId
     * @return list of all review
     */
    @Nonnull
    public List<Review> getAllReviewsByRecipeId(String recipeId) {
        log.debug("ReviewController > getReviews()");
        DBCursor reviews = collection.find(new BasicDBObject("recipeId", recipeId));
        if (reviews.size() == 0) {
            return new ArrayList<>();
        }
        List<Review> results = new ArrayList<>();
        for (DBObject r : reviews) {
            results.add(new Review(r));
        }
        return results;
    }

    /** @return list of all review review */
    @Nonnull
    public List<Review> getAllReviews() {
        log.debug("ReviewController > getReview()");
        DBCursor reviews = collection.find();
        if (reviews.size() == 0) {
            return new ArrayList<>();
        }
        List<Review> results = new ArrayList<>();
        for (DBObject r : reviews) {
            results.add(new Review(r));
        }
        return results;
    }

    /**
     * @param review
     * @return review.getRecipeId()
     * @throws Exception DuplicateKeyException
     */
    /**
     * @param review
     * @return review.getRecipeId()
     * @throws Exception DuplicateKeyException
     */
    @Nonnull
    public static String addReview(@Nonnull Review review) throws Exception {
        if (!review.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new Exception("InvalidReviewException");
        }

        DBCursor reviews = collection.find(new BasicDBObject("reviewId", review.getreviewId()));

        if (reviews.size() != 0) {
            throw new Exception("DuplicateKeyException");
        }

        DBObject reviewObject =
                new BasicDBObject("userId", review.getUserId())
                        .append("date", review.getDate())
                        .append("content", review.getContent())
                        .append("recipeId", review.getRecipeId())
                        .append("rating", review.getRating())
                        .append("reviewId", review.getreviewId());

        System.out.println(reviewObject);
        collection.insert(reviewObject);
        String id = reviewObject.get("_id").toString();
        log.debug("ReviewController > addReview(...)");
        System.out.println(id);
        return id;
    }

    /*
     * public void updateReview(@Nonnull Review review) throws Exception {
     * log.debug("RecipeController > updateRecipe(...)"); String id = review.getRecipeId(); if (id
     * == null) { // TODO: replace with a real null key exception throw new
     * Exception("The key is null."); }
     *
     * if (!review.isValid()) { // TODO: replace with a real invalid object exception // probably
     * not one exception per object type though... throw new Exception("The Recipe is invalid."); }
     * DBCursor reviews = collection.find(new BasicDBObject("recipeId", review.getRecipeId())); if
     * (reviews.size() == 0) { throw new Exception("The key is not found"); } Iterator<DBObject>
     * cursor = collection.find().iterator(); try { while (cursor.hasNext()) {
     * System.out.println(((BasicDBObject) cursor.next()).toJson()); } } finally { ((DBCursor)
     * cursor).close(); } System.out.println(review.getContent()); System.out.println(collection);
     * DBObject reviewObject = new BasicDBObject("userId", review.getUserId()) .append("date",
     * review.getDate()) .append("content", review.getContent()) .append("recipeId",
     * review.getRecipeId()) .append("rating", review.getRating());
     * System.out.println(reviewObject); collection.save(reviewObject); }
     */

    public List<String> getPrimaryKey() throws Exception {
        DBCursor reviews = collection.find();

        if (reviews.size() == 0) {
            reviews.close();
            return new ArrayList<>();
        }
        List<String> results = new ArrayList<>();
        for (DBObject r : reviews) {
            results.add(r.get("_id").toString());
        }
        reviews.close();
        return results;
    }

    /**
     * @param recipeId
     * @throws Exception KeyNotFoundException
     */
    public void deleteReview(@Nonnull String id) throws Exception {
        log.debug("ReviewController > deleteReview(...)");
        DBCursor reviews = collection.find(new BasicDBObject("_id", new ObjectId(id)));
        if (reviews.size() == 0) {
            reviews.close();
            // TODO: replace with a real recipe not found exception
            throw new Exception("KeyNotFoundException");
        }
        collection.findAndRemove(reviews.one());
        reviews.close();
    }

    public void deleteReviewByRecipeId(@Nonnull String recipeId) throws Exception {
        log.debug("ReviewController > deleteReview(...)");
        DBCursor reviews = collection.find(new BasicDBObject("recipeId", recipeId));

        if (reviews.size() == 0) {
            reviews.close();
            return;
        }
        for (DBObject r : reviews) {
            collection.findAndRemove(r);
        }
        reviews.close();
    }
}
