package com.corbinelli.lorenzo.swimming.repository.mongo;

import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.corbinelli.lorenzo.swimming.model.Swimmer;
import com.corbinelli.lorenzo.swimming.repository.SwimmerRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class SwimmerMongoRepository implements SwimmerRepository {
	
	private MongoCollection<Document> swimmerCollection;

	public SwimmerMongoRepository(MongoClient client, String dbName, String collectionName) {
		swimmerCollection = client.getDatabase(dbName).getCollection(collectionName);
	}

	@Override
	public List<Swimmer> findAll() {
		return Collections.emptyList();
	}

	@Override
	public Swimmer findById(String id) {
		return null;
	}

	@Override
	public void save(Swimmer swimmer) {
		
	}

	@Override
	public void delete(String id) {
		
	}

}
