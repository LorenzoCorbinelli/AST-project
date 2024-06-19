package com.corbinelli.lorenzo.swimming.guice;

import com.corbinelli.lorenzo.swimming.controller.SwimmingController;
import com.corbinelli.lorenzo.swimming.repository.SwimmerRepository;
import com.corbinelli.lorenzo.swimming.repository.mongo.SwimmerMongoRepository;
import com.corbinelli.lorenzo.swimming.view.swing.SwimmerSwingView;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.mongodb.MongoClient;

public class SwimmingSwingMongoDefaultModule extends AbstractModule {
	
	private String mongoHost = "localhost";
	private int mongoPort = 27017;
	private String dbName = "swimming";
	private String collectionName = "swimmer";
	
	public SwimmingSwingMongoDefaultModule mongoHost(String mongoHost) {
		this.mongoHost = mongoHost;
		return this;
	}
	
	public SwimmingSwingMongoDefaultModule mongoPort(int mongoPort) {
		this.mongoPort = mongoPort;
		return this;
	}
	
	public SwimmingSwingMongoDefaultModule databaseName(String dbName) {
		this.dbName = dbName;
		return this;
	}
	
	public SwimmingSwingMongoDefaultModule collectionName(String collectionName) {
		this.collectionName = collectionName;
		return this;
	}
	
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(MongoHost.class).toInstance(mongoHost);
		bind(Integer.class).annotatedWith(MongoPort.class).toInstance(mongoPort);
		bind(String.class).annotatedWith(MongoDBName.class).toInstance(dbName);
		bind(String.class).annotatedWith(MongoCollectionName.class).toInstance(collectionName);
		
		bind(SwimmerRepository.class).to(SwimmerMongoRepository.class);
		
		install(new FactoryModuleBuilder()
				.implement(SwimmingController.class, SwimmingController.class)
				.build(SwimmingControllerFactory.class));
	}
	
	@Provides
	MongoClient mongoClient(@MongoHost String host, @MongoPort int port) {
		return new MongoClient(host, port);
	}
	
	@Provides
	SwimmerSwingView swimmerView(SwimmingControllerFactory swimmingControllerFactory) {
		SwimmerSwingView view = new SwimmerSwingView();
		view.setSwimmingController(swimmingControllerFactory.create(view));
		return view;
	}

}
