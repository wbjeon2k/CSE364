package kr.twww.mrs.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;



public class MongoTest {
    private Integer PORT = 27017;
    private String DATABASE_NAME = "movielink-test";
    private String COLLECTION_NAME = "movie-link-test";
    String keyword;
    String keyword1;
    String keyword2;

    public MongoTest() {
        PORT = 27017;
        DATABASE_NAME = "movielink-test";
        COLLECTION_NAME = "movie-link-test";
    }

    public void mockmethod(){
        MongoClient mongoClient = new MongoClient("localhost", PORT);

        MongoDatabase db = mongoClient.getDatabase(DATABASE_NAME);

        MongoCollection<Document> documentMongoCollection = db.getCollection(COLLECTION_NAME);

        BasicDBObject query = new BasicDBObject();

        query.put("title", keyword); //titledp keyword
        query.put("text", Pattern.compile(keyword));

        Document myDoc = documentMongoCollection.find().first();
        System.out.println(myDoc.toJson());

        /*
        List<BasicDBObject> coll = new ArrayList<BasicDBObject>();

        // 키워드 1 (like '%keyworkd1%')

        coll.add(new BasicDBObject("title", Pattern.compile(keyword1)));

        MongoCursor<Document> cursor = documentMongoCollection.find(query).iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                String text = document.get("text").toString();
            }
        } finally {
            cursor.close();
        }
        */

    }

}
