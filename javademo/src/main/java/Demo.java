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

        DBCursor cursor = collection.find();
        println("Before delete count: " + cursor.count());

        BasicDBObject query = new BasicDBObject("age",
                new BasicDBObject("$lt", 20));

        cursor = collection.find(query);
        println("People under 20 (" + cursor.count() + "): ");
        try {
            while (cursor.hasNext()) {
                DBObject person = cursor.next();
                println("deleting" + person);
                collection.remove(person);
            }
        } finally {
            cursor.close();
        }

        cursor = collection.find();
        println("After delete count: " + cursor.count());
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