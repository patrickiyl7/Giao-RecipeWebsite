package edu.northeastern.cs5500.recipe.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.northeastern.cs5500.recipe.JavaMongoDBConnection;
import edu.northeastern.cs5500.recipe.model.Comment;
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

@Singleton
@Slf4j
public class CommentController implements Controller {
    private static DBCollection collection = JavaMongoDBConnection.getDB().getCollection("comment");

    @Inject
    CommentController() {}

    @Override
    public void register() {
        log.info("CommentController > register");
        collection = JavaMongoDBConnection.getDB().getCollection("comment");
        // TODO: This should be in a database
        log.info("CommentController > register > adding default comment");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);

        final Comment defaultComment1 =
                new Comment("patrick1108", curDate, "I love fried chicken.");

        try {
            addComment(defaultComment1);
        } catch (Exception e) {
            log.error("CommentController > register > adding default comments > failure?");
            e.printStackTrace();
        }
    }

    @Nullable
    public Comment getComment(@Nonnull String userId) {
        // TODO: Should this be null or should this throw an exception?
        log.debug("CommentController > getComment({})", userId);
        DBCursor comments = collection.find(new BasicDBObject("userId", userId));
        if (comments.size() != 0) {
            return new Comment(comments.one());
        }
        return null;
    }

    @Nonnull
    public Collection<Comment> getComments() {
        log.debug("CommentController > getComment()");
        DBCursor comments = collection.find();
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        List<Comment> results = new ArrayList<>();
        for (DBObject c : comments) {
            results.add(new Comment(c));
        }
        return results;
    }

    @Nonnull
    public Collection<Comment> getCommentsByUserId(String userId) {
        log.debug("CommentController > getCommentsByUserId()");
        DBCursor comments = collection.find(new BasicDBObject("userId", userId));
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        List<Comment> results = new ArrayList<>();
        for (DBObject c : comments) {
            results.add(new Comment(c));
        }
        return results;
    }

    @Nonnull
    public String addComment(@Nonnull Comment comment) throws Exception {
        if (!comment.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new Exception("InvalidRecipeException");
        }
        DBCursor comments = collection.find(new BasicDBObject("userId", comment.getUserId()));
        if (comments.size() != 0) {
            throw new Exception("DuplicateKeyException");
        }
        DBObject commentObject =
                new BasicDBObject("userId", comment.getUserId())
                        .append("date", comment.getDate())
                        .append("content", comment.getContent());
        collection.insert(commentObject);

        log.debug("CommentController > addComment(...)");
        return comment.getUserId();
    }

    public void updateCommeet(@Nonnull Comment comment) throws Exception {
        log.debug("CommentController > updateComment(...)");
        String id = comment.getUserId();
        if (id == null) {
            // TODO: replace with a real null key exception
            throw new Exception("The key is null.");
        }

        if (!comment.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new Exception("The Comment is invalid.");
        }
        DBCursor comments = collection.find(new BasicDBObject("userId", comment.getUserId()));
        if (comments.size() == 0) {
            throw new Exception("The key is not found");
        }
        DBObject reviewObject =
                new BasicDBObject("userId", comment.getUserId())
                        .append("date", comment.getDate())
                        .append("content", comment.getContent());
        collection.save(reviewObject);
    }

    public void deleteComment(@Nonnull String userId) throws Exception {
        log.debug("CommentController > deleteComment(...)");
        DBCursor comments = collection.find(new BasicDBObject("userId", userId));
        if (comments.size() == 0) {
            // TODO: replace with a real recipe not found exception
            throw new Exception("KeyNotFoundException");
        }
        collection.findAndRemove(comments.one());
    }
}
