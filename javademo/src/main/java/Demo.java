import com.mongodb.*;

import java.util.Random;

public class Demo {

    private void doStuff() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("cjug");

        DBCollection collection = db.getCollection("people");
        Random statusRandom = new Random();
        Random ageRandom = new Random();

        for(int i=0; i<20;i++) {
            BasicDBObject person = new BasicDBObject("name", "Jonny").
                    append("status", statusRandom.nextBoolean() ? "Awesome" : "Almost Awesome").
                    append("age", ageRandom.nextInt(60));
            collection.insert(person);
        }

        BasicDBObject query = new BasicDBObject("age",
                new BasicDBObject("$gt", 50));

        DBCursor cursor = collection.find(query);
        println("People over 50 (" + cursor.count() + "): ");
        try {
            while(cursor.hasNext()) {
                println(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();

        try {
            demo.doStuff();
        }catch(Throwable t) {
            t.printStackTrace();
        }

    }

    private void println(Object s) {
        System.out.println(s.toString());
    }
}