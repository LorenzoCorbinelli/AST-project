package com.corbinelli.lorenzo.swimming.repository.mongo;


import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class SwimmerMongoRepositoryTest {
	
	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	private MongoClient client;
	private SwimmerMongoRepository swimmerRepository;
	private MongoCollection<Document> swimmerCollection;
	private static final String DB_NAME = "swimming";
	private static final String COLLECTION_NAME = "swimmer";
	
	@Before
	public void setup() {
		client = new MongoClient(
					new ServerAddress(
						mongo.getHost(),
						mongo.getFirstMappedPort()));
		swimmerRepository = new SwimmerMongoRepository(client, DB_NAME, COLLECTION_NAME);
		MongoDatabase database = client.getDatabase(DB_NAME);
		database.drop();
		swimmerCollection = database.getCollection(COLLECTION_NAME);
	}

	@After
	public void tearDown() {
		client.close();
	}
	
	@Test
	public void testFindAllWithEmptyDB() {
		assertThat(swimmerRepository.findAll()).isEmpty();
	}

}