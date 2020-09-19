package edu.northeastern.cs5500.recipe.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.northeastern.cs5500.recipe.JavaMongoDBConnection;
import edu.northeastern.cs5500.recipe.exception.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.exception.InvalidException;
import edu.northeastern.cs5500.recipe.exception.NullKeyException;
import edu.northeastern.cs5500.recipe.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * UserController used to build connection to MongoDB and have multiple method to add and edit user
 * information on MongoDB.
 */
@Singleton
@Slf4j
public class UserController implements Controller {
    private static DBCollection collection = JavaMongoDBConnection.getDB().getCollection("user");

    @Inject
    UserController() {}

    @Override
    public void register() {
        log.info("UserController > register");
        collection = JavaMongoDBConnection.getDB().getCollection("user");
        // TODO: This should be in a database
        log.info("UserController > register > adding default users");
        final User defaultUser1 =
                new User(
                        "patrick7709",
                        "patrick",
                        "1234567",
                        "yihaoli@gmail.com",
                        "I love fried chicken.",
                        "GUEST",
                        "");
        final User defaultUser2 =
                new User(
                        "yiwenzhang734",
                        "YiwenZhang",
                        "1234567",
                        "yiwenzhang@gmail.com",
                        "I love slava chicken.",
                        "GUEST",
                        "");

        try {
            addUser(defaultUser1);
            addUser(defaultUser2);
        } catch (Exception e) {
            log.error("UserController > register > adding default users > failure?");
            e.printStackTrace();
        }
    }

    @Nullable
    public User getUser(@Nonnull String id) {
        // TODO: Should this be null or should this throw an exception?
        log.debug("UserController > getUser({})", id);
        DBCursor users = collection.find(new BasicDBObject("userId", id));
        if (users.size() != 0) {
            User user = new User(users.one());
            users.close();
            return user;
        }
        users.close();
        return null;
    }

    @Nullable
    public User getUserByEmail(@Nonnull String emailAddress) {
        // TODO: Should this be null or should this throw an exception?
        // System.out.println("reached1");
        log.debug("UserController > getUser({})", emailAddress);
        DBCursor users = collection.find(new BasicDBObject("email", emailAddress));
        if (users.size() != 0) {
            // System.out.println("reached2");
            User user = new User(users.one());
            users.close();
            return user;
        }
        users.close();
        return null;
    }

    @Nullable
    public User getUserByUserName(@Nonnull String userName) {
        // TODO: Should this be null or should this throw an exception?
        // System.out.println("reached1");
        log.debug("UserController > getUser({})", userName);
        DBCursor users = collection.find(new BasicDBObject("username", userName));
        if (users.size() != 0) {
            // System.out.println("reached2");
            User user = new User(users.one());
            users.close();
            return user;
        }
        users.close();
        return null;
    }

    @Nonnull
    public Collection<User> getUsers() {
        log.debug("UserController > getUser()");
        DBCursor users = collection.find();
        if (users.size() == 0) {
            users.close();
            return new ArrayList<>();
        }
        List<User> results = new ArrayList<>();
        for (DBObject u : users) {
            results.add(new User(u));
        }
        users.close();
        return results;
    }

    @Nonnull
    public String addUser(@Nonnull User user) throws Exception {
        if (!user.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new InvalidException("InvalidRecipeException");
        }
        DBCursor users = collection.find(new BasicDBObject("userId", user.getUserId()));
        if (users.size() != 0) {
            users.close();
            throw new DuplicateKeyException("You have created the user with this User Id.");
        }
        DBCursor users1 = collection.find(new BasicDBObject("username", user.getUserName()));
        if (users1.size() != 0) {
            users1.close();
            users.close();
            throw new DuplicateKeyException("You have created the user with this User Name.");
        }
        DBObject userObject =
                new BasicDBObject("userId", user.getUserId())
                        .append("username", user.getUserName())
                        .append("passWord", user.getPassWord())
                        .append("email", user.getEmailAddress())
                        .append("slogan", user.getSlogan())
                        .append("status", user.getStatus())
                        .append("finalImage", user.getfinalImage());
        collection.insert(userObject);

        log.debug("UserController > addUser(...)");
        users.close();
        users1.close();
        return user.getUserId();
    }

    public void updateUser(@Nonnull User user) throws Exception {
        log.debug("UserController > updateUser(...)");
        String id = user.getUserId();
        if (id == null) {
            // TODO: replace with a real null key exception
            throw new NullKeyException("NullKeyException");
        }

        if (!user.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new InvalidException("InvalidRecipeException");
        }
        DBCursor users = collection.find(new BasicDBObject("userId", user.getUserId()));
        if (users.size() == 0) {
            // TODO: replace with a real recipe not found exception
            users.close();
            throw new NullKeyException("KeyNotFoundException");
        }
        DBObject userObject =
                new BasicDBObject("userId", user.getUserId())
                        .append("username", user.getUserName())
                        .append("passWord", user.getPassWord())
                        .append("email", user.getEmailAddress())
                        .append("slogan", user.getSlogan())
                        .append("status", user.getStatus())
                        .append("finalImage", user.getfinalImage());
        collection.insert(userObject);
        users.close();
    }

    public void deleteUser(@Nonnull String id) throws Exception {
        log.debug("UserController > deleteUser(...)");
        DBCursor users = collection.find(new BasicDBObject("userId", id));
        if (users.size() == 0) {
            // TODO: replace with a real recipe not found exception
            throw new NullKeyException("KeyNotFoundException");
        }
        collection.findAndRemove(users.one());
        users.close();
    }
}
