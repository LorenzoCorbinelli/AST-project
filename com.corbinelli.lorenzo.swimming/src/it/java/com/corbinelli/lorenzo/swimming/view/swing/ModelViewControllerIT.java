package com.corbinelli.lorenzo.swimming.view.swing;


import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.corbinelli.lorenzo.swimming.controller.SwimmingController;
import com.corbinelli.lorenzo.swimming.model.Swimmer;
import com.corbinelli.lorenzo.swimming.repository.mongo.SwimmerMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class ModelViewControllerIT extends AssertJSwingJUnitTestCase {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	private MongoClient client;
	private FrameFixture window;
	private SwimmingController swimmingController;
	private SwimmerMongoRepository swimmerRepository;
	
	@Override
	protected void onSetUp() throws Exception {
		client = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		swimmerRepository = new SwimmerMongoRepository(client, "testDB", "testCollection");
		for (Swimmer swimmer : swimmerRepository.findAll()) {
			swimmerRepository.delete(swimmer.getId());
		}
		window = new FrameFixture(robot(), GuiActionRunner.execute(() -> {
			SwimmerSwingView swimmerSwingView = new SwimmerSwingView();
			swimmingController = new SwimmingController(swimmerSwingView, swimmerRepository);
			swimmerSwingView.setSwimmingController(swimmingController);
			return swimmerSwingView;
		}));
		window.show();
	}

	@Override
	protected void onTearDown() throws Exception {
		client.close();
	}
	
	@Test
	public void testAddSwimmer() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("nameTextBox").enterText("test");
		window.button(JButtonMatcher.withText("Add")).click();
		assertThat(swimmerRepository.findById("1")).isEqualTo(new Swimmer("1", "test", "Male", "Freestyle"));
	}
}
