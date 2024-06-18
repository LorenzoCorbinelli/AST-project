package com.corbinelli.lorenzo.swimming.app.swing;

import java.awt.EventQueue;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.corbinelli.lorenzo.swimming.controller.SwimmingController;
import com.corbinelli.lorenzo.swimming.repository.mongo.SwimmerMongoRepository;
import com.corbinelli.lorenzo.swimming.view.swing.SwimmerSwingView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true)
public class SwimmingSwingApp implements Callable<Void>{
	
	@Option(names = {"--mongo-host"}, description = "MongoDB host address")
	private String mongoHost = "localhost";
	
	@Option(names = {"--mongo-port"}, description = "MongoDB host port")
	private int mongoPort = 27017;
	
	@Option(names = {"--db-name"}, description = "Database name")
	private String databaseName = "swimming";
	
	@Option(names = {"--db-collection"}, description = "Collection name")
	private String collectionName = "swimmer";

	public static void main(String[] args) {
		new CommandLine(new SwimmingSwingApp()).execute(args);
	}
	
	@Override
	public Void call() throws Exception {
		EventQueue.invokeLater(() -> {
			try {
				SwimmerMongoRepository swimmerRepository = 
						new SwimmerMongoRepository(
								new MongoClient(new ServerAddress(mongoHost, mongoPort)), 
								databaseName, collectionName);
				SwimmerSwingView swimmerSwingView = new SwimmerSwingView();
				SwimmingController swimmingController = new SwimmingController(swimmerSwingView, swimmerRepository);
				swimmerSwingView.setSwimmingController(swimmingController);
				swimmerSwingView.setVisible(true);
				swimmingController.allSwimmers();
			} catch(Exception e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Exception", e);
			}
		});
		return null;
	}
}
