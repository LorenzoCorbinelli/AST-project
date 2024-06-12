package com.corbinelli.lorenzo.swimming.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
		return StreamSupport
				.stream(swimmerCollection.find().spliterator(), false)
				.map(d -> new Swimmer(""+d.get("id"), ""+d.get("name"), ""+d.get("gender"), ""+d.get("mainStroke")))
				.collect(Collectors.toList());
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
