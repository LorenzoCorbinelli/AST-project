package com.corbinelli.lorenzo.swimming.controller;

import static org.mockito.Mockito.verify;


import static java.util.Arrays.asList;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.MongoDBContainer;

import com.corbinelli.lorenzo.swimming.model.Swimmer;
import com.corbinelli.lorenzo.swimming.repository.SwimmerRepository;
import com.corbinelli.lorenzo.swimming.repository.mongo.SwimmerMongoRepository;
import com.corbinelli.lorenzo.swimming.view.SwimmerView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class SwimmingControllerIT {
	
	@Mock
	private SwimmerView swimmerView;
	private SwimmerRepository swimmerRepository;
	private SwimmingController swimmingController;
	private AutoCloseable closeable;
	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	private static final String DB_NAME = "swimming";
	private static final String COLLECTION_NAME = "swimmer";
	
	@Before
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		swimmerRepository = new SwimmerMongoRepository(
				new MongoClient(
						new ServerAddress(
								mongo.getHost(), 
								mongo.getFirstMappedPort())), 
				DB_NAME, COLLECTION_NAME);
		for (Swimmer swimmer : swimmerRepository.findAll()) {
			swimmerRepository.delete(swimmer.getId());
		}
		swimmingController = new SwimmingController(swimmerView, swimmerRepository);
	}
	
	@After
	public void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllSwimmers() {
		Swimmer swimmer = new Swimmer("1", "test", "testGender", "testStroke");
		swimmerRepository.save(swimmer);
		swimmingController.allSwimmers();
		verify(swimmerView).showAllSwimmers(asList(swimmer));
	}
	
	@Test
	public void testNewSwimmer() {
		Swimmer swimmer = new Swimmer("1", "test", "testGender", "testStroke");
		swimmingController.newSwimmer(swimmer);
		verify(swimmerView).swimmerAdded(swimmer);
	}
	
	@Test
	public void testDeleteSwimmer() {
		Swimmer swimmer = new Swimmer("1", "test", "testGender", "testStroke");
		swimmerRepository.save(swimmer);
		swimmingController.deleteSwimmer(swimmer);
		verify(swimmerView).swimmerRemoved(swimmer);
	}

}
