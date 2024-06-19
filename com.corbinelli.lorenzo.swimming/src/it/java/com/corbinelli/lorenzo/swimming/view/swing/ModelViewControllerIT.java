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
import com.corbinelli.lorenzo.swimming.guice.SwimmingSwingMongoDefaultModule;
import com.corbinelli.lorenzo.swimming.model.Swimmer;
import com.corbinelli.lorenzo.swimming.repository.mongo.SwimmerMongoRepository;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mongodb.MongoClient;

public class ModelViewControllerIT extends AssertJSwingJUnitTestCase {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	@Inject
	private MongoClient client;
	private FrameFixture window;
	private SwimmingController swimmingController;
	@Inject
	private SwimmerMongoRepository swimmerRepository;
	
	@Override
	protected void onSetUp() throws Exception {
		Injector injector = Guice.createInjector(
				new SwimmingSwingMongoDefaultModule()
				.mongoHost(mongo.getHost())
				.mongoPort(mongo.getFirstMappedPort())
				.databaseName("testDB")
				.collectionName("testCollection"));
		injector.injectMembers(this);
		for (Swimmer swimmer : swimmerRepository.findAll()) {
			swimmerRepository.delete(swimmer.getId());
		}
		window = new FrameFixture(robot(), GuiActionRunner.execute(() -> {
			SwimmerSwingView swimmerSwingView = injector.getInstance(SwimmerSwingView.class);
			swimmingController = swimmerSwingView.getSwimmingController();
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
	
	@Test
	public void testDeleteSwimmer() {
		swimmerRepository.save(new Swimmer("10", "existing", "testGender", "testStroke"));
		GuiActionRunner.execute(() -> swimmingController.allSwimmers());
		window.list("swimmerList").selectItem(0);
		window.button(JButtonMatcher.withText("Remove Swimmer")).click();
		assertThat(swimmerRepository.findById("10")).isNull();
	}
}
