import com.mongodb.*;

import java.util.Random;

public class Demo {

    private void doStuff() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("cjug");
        Random statusRandom = new Random();
        Random ageRandom = new Random();

        DBCollection collection = db.getCollection("people");
        for (int i = 0; i < 20; i++) {
            BasicDBObject person = new BasicDBObject("name", "Jonny").
                    append("status", statusRandom.nextBoolean() ? "Awesome" : "Almost Awesome").
                    append("age", ageRandom.nextInt(60));
            collection.insert(person);
        }

        BasicDBObject query = new BasicDBObject("age",
                new BasicDBObject("$lt", 20));

        DBCursor cursor = collection.find(query);
        try {
            println("People under 20 before update: " + cursor.count());
        } finally {
            cursor.close();
        }

        // Bulk update
        BasicDBObject update = new BasicDBObject("$set",
                new BasicDBObject("age", 20));
        collection.updateMulti(query, update);

        cursor = collection.find(query);
        try {
            println("After update count: " + cursor.count());
        }finally {
            cursor.close();
        }

    }

    public static void main(String[] args) {
        Demo demo = new Demo();

        try {
            demo.doStuff();
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private void println(Object s) {
        System.out.println(s.toString());
    }
}