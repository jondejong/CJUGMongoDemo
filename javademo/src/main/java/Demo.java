import com.mongodb.*;

public class Demo {

    private void doStuff() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        // This is not needed as it's the default
        mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        DB db = mongoClient.getDB("cjug");

        DBCollection collection = db.getCollection("people");
        collection.createIndex(new BasicDBObject("name", 1));

        BasicDBObject query = new BasicDBObject("name", "Jonny");

        DBCursor cursor = collection.find(query);
        println("People named Jonny (" + cursor.count() + "): ");
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