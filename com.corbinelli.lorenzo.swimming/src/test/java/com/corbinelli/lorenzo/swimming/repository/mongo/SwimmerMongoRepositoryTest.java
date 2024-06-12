package com.corbinelli.lorenzo.swimming.repository.mongo;


import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.corbinelli.lorenzo.swimming.model.Swimmer;
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
	
	@Test
	public void testFindAllWithSomeSwimmersInTheDB() {
		addTestSwimmerToTheDB("1", "test1", "testGender", "testStroke");
		addTestSwimmerToTheDB("2", "test2", "testGender", "testStroke");
		assertThat(swimmerRepository.findAll()).containsExactly(
				new Swimmer("1", "test1", "testGender", "testStroke"),
				new Swimmer("2", "test2", "testGender", "testStroke"));
	}
	
	private void addTestSwimmerToTheDB(String id, String name, String gender, String mainStroke) {
		swimmerCollection.insertOne(
				new Document()
					.append("id", id)
					.append("name", name)
					.append("gender", gender)
					.append("mainStroke", mainStroke)
				);
	}
	
	@Test
	public void testFindByIdNotFound() {
		assertThat(swimmerRepository.findById("1")).isNull();
	}

}
