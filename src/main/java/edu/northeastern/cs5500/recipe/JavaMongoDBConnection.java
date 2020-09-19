package edu.northeastern.cs5500.recipe;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Arrays;

public class JavaMongoDBConnection {
    private static DB database;
    private static final String DATABASE_NAME = "heroku_s0bpsb6x";

    public static DB getDB() {
        if (database == null) {
            MongoCredential credential =
                    MongoCredential.createCredential(
                            "penglu2", "heroku_s0bpsb6x", "12345skye".toCharArray());
            MongoClient mongoClient =
                    new MongoClient(
                            new ServerAddress("ds061395.mlab.com", 61395),
                            Arrays.asList(credential));

            database = mongoClient.getDB(DATABASE_NAME);
        }
        return database;
    }
}
