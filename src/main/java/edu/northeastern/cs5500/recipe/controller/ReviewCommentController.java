package edu.northeastern.cs5500.recipe.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.northeastern.cs5500.recipe.JavaMongoDBConnection;
import edu.northeastern.cs5500.recipe.model.ReviewComment;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * ReviewCommentController used to build connection to MongoDB and have multiple method to add and
 * edit ReviewComment information on MongoDB.
 */
@Singleton
@Slf4j
public class ReviewCommentController implements Controller {
    private static DBCollection collection =
            JavaMongoDBConnection.getDB().getCollection("reviewComment");

    @Inject
    ReviewCommentController() {}

    /** register a collection and add it into MongoDB. * */
    @Override
    public void register() {
        log.info("ReviewCommentController > register");
        log.info("ReviewCommentController > register > adding default reviewComment");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        // TODO: This should be in a database
        final ReviewComment defaultReviewComment =
                new ReviewComment(
                        "PengluYang",
                        curDate,
                        "No, Disgusting!",
                        "YiwenZhang_slava_chiken/YiwenZhang");

        try {
            addReviewComment(defaultReviewComment);

        } catch (Exception e) {
            log.error(
                    "ReviewCommentController > register > adding default ReviewComment > failure?");
            e.printStackTrace();
        }
    }

    /**
     * get the ReviewComment by userId. *
     *
     * @param userId userId
     * @return ReviewComment
     */
    @Nullable
    public ReviewComment getReviewComment(@Nonnull String reviewId) {
        // TODO: Should this be null or should this throw an exception?
        log.debug("ReviewCommentController > getReviewComment({})", reviewId);
        DBCursor reviewComments = collection.find(new BasicDBObject("_id", reviewId));
        if (reviewComments.size() != 0) {
            ReviewComment reviewComment = new ReviewComment(reviewComments.one());
            reviewComments.close();
            return reviewComment;
        }
        reviewComments.close();
        return null;
    }

    /** @return list of all review comments. */
    @Nonnull
    public List<ReviewComment> getAllReviewComments() {
        log.debug("ReviewCommentController > getAllReviewComments()");
        DBCursor reviewComments = collection.find();
        if (reviewComments.size() == 0) {
            reviewComments.close();
            return new ArrayList<>();
        }
        List<ReviewComment> results = new ArrayList<>();
        for (DBObject r : reviewComments) {
            results.add(new ReviewComment(r));
        }
        reviewComments.close();
        return results;
    }

    /**
     * Search reviewcooment by userId
     *
     * @param userId
     * @return list of review comments.
     */
    @Nonnull
    public Collection<ReviewComment> getReviewsByUserId(String reviewId) {
        log.debug("ReviewCommentController > getReviewComment()");
        DBCursor reviewComments = collection.find(new BasicDBObject("_id", reviewId));
        if (reviewComments.size() == 0) {
            return new ArrayList<>();
        }
        List<ReviewComment> results = new ArrayList<>();
        for (DBObject r : reviewComments) {
            results.add(new ReviewComment(r));
        }
        return results;
    }

    /**
     * @param reviewComment
     * @return reviewComment.getUserId()
     * @throws Exception DuplicateKeyException
     */
    @Nonnull
    public String addReviewComment(@Nonnull ReviewComment reviewComment) throws Exception {
        if (!reviewComment.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new Exception("InvalidReviewException");
        }

        DBCursor reviewComments =
                collection.find(new BasicDBObject("reviewId", reviewComment.getReviewId()));
        if (reviewComments.size() != 0) {
            throw new Exception("DuplicateKeyException");
        }
        System.out.println(reviewComment);
        DBObject reviewCommentObject =
                new BasicDBObject("userId", reviewComment.getUserId())
                        .append("date", reviewComment.getDate())
                        .append("content", reviewComment.getContent())
                        .append("_id", reviewComment.getReviewId());

        collection.insert(reviewCommentObject);
        System.out.println(reviewCommentObject);

        log.debug("ReviewCommentController > addReviewComment(...)");
        return reviewComment.getReviewId();
    }

    /*
     * public void updateReviewCommeet(@Nonnull ReviewComment reviewComment) throws Exception {
     * log.debug("RecipeController > updateRecipe(...)"); String id = reviewComment.getUserId(); if
     * (id == null) { // TODO: replace with a real null key exception throw new
     * Exception("The key is null."); }
     *
     * if (!reviewComment.isValid()) { // TODO: replace with a real invalid object exception //
     * probably not one exception per object type though... throw new
     * Exception("The Recipe is invalid."); } DBCursor reviewComments = collection.find(new
     * BasicDBObject("userId", reviewComment.getUserId())); if (reviewComments.size() == 0) { throw
     * new Exception("The key is not found"); } DBObject reviewObject = new BasicDBObject("userId",
     * reviewComment.getUserId()) .append("date", reviewComment.getDate()) .append("content",
     * reviewComment.getContent()); collection.save(reviewObject); }
     */
    /**
     * @param userId
     * @throws Exception KeyNotFoundException
     */
    public void deleteReviewComment(@Nonnull String reviewId) throws Exception {
        log.debug("ReviewCommentController > deleteReviewComment(...)");
        DBCursor reviewComments = collection.find(new BasicDBObject("_id", reviewId));
        if (reviewComments.size() == 0) {
            // TODO: replace with a real recipe not found exception
            throw new Exception("KeyNotFoundException");
        }
        collection.findAndRemove(reviewComments.one());
    }
}
