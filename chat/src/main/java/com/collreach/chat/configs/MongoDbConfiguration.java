//package com.collreach.chat.configs;
//
//import com.mongodb.MongoClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//
//import java.net.UnknownHostException;
//
//@Configuration
//public class MongoDbConfiguration {
//
//    public @Bean
//    MongoDatabaseFactory getMongoDbFactory() throws UnknownHostException {
//        return new MongoDatabaseFactory(new MongoClient("localhost", 27017), "mongotest");
//    }
//
//    public @Bean(name = "mongoTemplate")
//    MongoTemplate getMongoTemplate() throws UnknownHostException {
//
//        MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory());
//        return mongoTemplate;
//    }
//}