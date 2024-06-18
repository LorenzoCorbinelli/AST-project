package com.corbinelli.lorenzo.swimming.bdd.steps;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class DatabaseSteps {
	
	static final String DB_NAME = "testDB";
	static final String COLLECTION_NAME = "testCollection";
	private MongoClient client;
	
	static final String SWIMMER_ID_1 = "1";
	static final String SWIMMER_NAME_1 = "test1";
	static final String SWIMMER_ID_2 = "2";
	static final String SWIMMER_NAME_2 = "test2";
	static final String SWIMMER_GENDER = "testGender";
	static final String SWIMMER_STROKE = "testStroke";

	@Before
	public void setUp() {
		client = new MongoClient();
		client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME).drop();
	}
	
	@After
	public void teardown() {
		client.close();
	}
	
	@Given("The database contains some swimmers")
	public void the_database_contains_some_swimmers() {
		addSwimmerToTheDB(SWIMMER_ID_1, SWIMMER_NAME_1, SWIMMER_GENDER, SWIMMER_STROKE);
		addSwimmerToTheDB(SWIMMER_ID_2, SWIMMER_NAME_2, SWIMMER_GENDER, SWIMMER_STROKE);
	}
	
	@Given("The swimmer is in the meantime removed from the database")
	public void the_swimmer_is_in_the_meantime_removed_from_the_database() {
		client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME)
			.deleteOne(Filters.eq("id", SWIMMER_ID_1));
	}
	
	private void addSwimmerToTheDB(String id, String name, String gender, String mainStroke) {
		client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME)
			.insertOne(new Document()
					.append("id", id)
					.append("name", name)
					.append("gender", gender)
					.append("mainStroke", mainStroke));
	}
}
