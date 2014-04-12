import com.mongodb.*;
import com.mongodb.DB;
import java.util.Set;


public class Demo {

    private void doStuff() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("cjug");

        Set<String> collectionNames = db.getCollectionNames();

        for (String collectionName : collectionNames) {
            System.out.println(collectionName);
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

}