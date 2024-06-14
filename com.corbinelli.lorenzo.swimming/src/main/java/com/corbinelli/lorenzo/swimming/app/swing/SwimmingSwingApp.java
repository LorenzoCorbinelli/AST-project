package com.corbinelli.lorenzo.swimming.app.swing;

import java.awt.EventQueue;

import com.corbinelli.lorenzo.swimming.controller.SwimmingController;
import com.corbinelli.lorenzo.swimming.repository.mongo.SwimmerMongoRepository;
import com.corbinelli.lorenzo.swimming.view.swing.SwimmerSwingView;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class SwimmingSwingApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				String mongoHost = "localhost";
				int mongoPort = 27017;
				if(args.length > 0)
					mongoHost = args[0];
				if(args.length > 1)
					mongoPort = Integer.parseInt(args[1]);
				SwimmerMongoRepository swimmerRepository = 
						new SwimmerMongoRepository(
								new MongoClient(new ServerAddress(mongoHost, mongoPort)),
								"swimming", "swimmer");
				SwimmerSwingView swimmerView = new SwimmerSwingView();
				SwimmingController swimmingController = new SwimmingController(swimmerView, swimmerRepository);
				swimmerView.setSwimmingController(swimmingController);
				swimmerView.setVisible(true);
				swimmingController.allSwimmers();
			} catch(Exception e) {
				e.printStackTrace();
			}
		});
	}
}
