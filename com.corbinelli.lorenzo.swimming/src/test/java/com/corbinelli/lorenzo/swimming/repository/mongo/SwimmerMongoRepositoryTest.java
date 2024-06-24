package com.corbinelli.lorenzo.swimming.repository.mongo;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
	
	@Test
	public void testFindByIdNotFound() {
		assertThat(swimmerRepository.findById("1")).isNull();
	}
	
	@Test
	public void testFindByIdFound() {
		addTestSwimmerToTheDB("1", "notToBeFound", "testGender", "testStroke");
		addTestSwimmerToTheDB("2", "toBeFound", "testGender", "testStroke");
		assertThat(swimmerRepository.findById("2"))
			.isEqualTo(new Swimmer("2", "toBeFound", "testGender", "testStroke"));
	}
	
	@Test
	public void testSave() {
		Swimmer swimmer = new Swimmer("1", "test", "testGender", "testStroke");
		swimmerRepository.save(swimmer);
		assertThat(readAllSwimmersFromTheDB()).containsExactly(swimmer);
	}
	
	@Test
	public void testDelete() {
		addTestSwimmerToTheDB("1", "test", "testGender", "testStroke");
		swimmerRepository.delete("1");
		assertThat(readAllSwimmersFromTheDB()).isEmpty();
	}

	private void addTestSwimmerToTheDB(String id, String name, String gender, String mainStroke) {
		swimmerCollection.insertOne(
				new Document()
				.append("id", id)
				.append("name", name)
				.append("gender", gender)
				.append("mainStroke", mainStroke));
	}
	
	private List<Swimmer> readAllSwimmersFromTheDB() {
		return StreamSupport.stream(swimmerCollection.find().spliterator(), false)
				.map(d -> new Swimmer(""+d.get("id"), ""+d.get("name"), ""+d.get("gender"), ""+d.get("mainStroke")))
				.collect(Collectors.toList());
	}
}
