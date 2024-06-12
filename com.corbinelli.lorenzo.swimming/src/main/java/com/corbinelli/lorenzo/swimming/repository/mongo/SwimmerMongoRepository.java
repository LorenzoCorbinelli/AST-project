package com.corbinelli.lorenzo.swimming.repository.mongo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.corbinelli.lorenzo.swimming.model.Swimmer;
import com.corbinelli.lorenzo.swimming.repository.SwimmerRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class SwimmerMongoRepository implements SwimmerRepository {
	
	private MongoCollection<Document> swimmerCollection;

	public SwimmerMongoRepository(MongoClient client, String dbName, String collectionName) {
		swimmerCollection = client.getDatabase(dbName).getCollection(collectionName);
	}

	@Override
	public List<Swimmer> findAll() {
		return StreamSupport
				.stream(swimmerCollection.find().spliterator(), false)
				.map(this::fromDocumentToSwimmer)
				.collect(Collectors.toList());
	}

	private Swimmer fromDocumentToSwimmer(Document d) {
		return new Swimmer(""+d.get("id"), ""+d.get("name"), ""+d.get("gender"), ""+d.get("mainStroke"));
	}

	@Override
	public Swimmer findById(String id) {
		Document document = swimmerCollection.find(Filters.eq("id", id)).first();
		if(document != null)
			return fromDocumentToSwimmer(document);
		return null;
	}

	@Override
	public void save(Swimmer swimmer) {
		swimmerCollection.insertOne(
				new Document()
					.append("id", swimmer.getId())
					.append("name", swimmer.getName())
					.append("gender", swimmer.getGender())
					.append("mainStroke", swimmer.getMainStroke()));
	}

	@Override
	public void delete(String id) {
		
	}

}
