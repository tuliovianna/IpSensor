package com.example.IpSensor;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.Date;

/**
 *
 * @author tulio
 */
public class MongoConnection {
        
    //
    private String host;
    
    //
    private  int port;
    
    //
    private String database; 

    //
    private Mongo mongo;

    //
    private DB db;
    
    //
    private DBCollection tablePingWrapper;
    
    //
    private BasicDBObject documentPingWrapper; 

    /**
     * Constructor.
     */
    public MongoConnection() throws UnknownHostException, MongoException {
       this.host = "localhost";
       this.port = 27017;       
       this.mongo = new Mongo(this.host, this.port); 
       this.db = mongo.getDB("test");
       this.tablePingWrapper = db.getCollection("pingWrapper"); 
    }

    /**
    * Inserts a capture PingWrapper.
    */   
    public void insertPingWrapper(PingWrapper p) {
        BasicDBObject document = new BasicDBObject();
        String cat = "";
        String result = "";
        document.put("timestamp",new Date());
        document.put("url",p.getUrl()); 
        document.put("hops", p.getIps());       
        this.tablePingWrapper.insert(document); 
    }
    
    public void listAllPingWrapper() {
        DBCursor cursor = tablePingWrapper.find(); 
        
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
    
    public void findPingWrapper(String url) {
        DBCollection table = db.getCollection("pingWrapper");

	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put("url", url);
	DBCursor cursor = table.find(searchQuery);

	while (cursor.hasNext()) {
            System.out.println(cursor.next());
	}
    }
    
    
}
