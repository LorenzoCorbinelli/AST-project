package com.corbinelli.lorenzo.swimming.view.swing;


import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.annotation.GUITest;
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

public class SwimmerSwingViewIT extends AssertJSwingJUnitTestCase {
	
	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	@Inject
	private MongoClient client;
	private FrameFixture window;
	@Inject
	private SwimmerSwingView swimmerView;
	@Inject
	private SwimmerMongoRepository swimmerRepository;
	private SwimmingController swimmingController;

	@Override
	protected void onSetUp() throws Exception {
		Injector injector = Guice.createInjector(
				new SwimmingSwingMongoDefaultModule()
				.mongoHost(mongo.getHost())
				.mongoPort(mongo.getFirstMappedPort())
				.databaseName("testDB")
				.collectionName("testCollection"));
		GuiActionRunner.execute(() -> {
			injector.injectMembers(this);
			for (Swimmer swimmer : swimmerRepository.findAll()) {
				swimmerRepository.delete(swimmer.getId());
			}
			swimmingController = swimmerView.getSwimmingController();
			return swimmerView;
		});
		window = new FrameFixture(robot(), swimmerView);
		window.show();
	}
	
	@Override
	protected void onTearDown() throws Exception {
		client.close();
	}

	@Test @GUITest
	public void testAllSwimmers() {
		Swimmer swimmer1 = new Swimmer("1", "test1", "testGender", "testStroke");
		Swimmer swimmer2 = new Swimmer("2", "test2", "testGender", "testStroke");
		swimmerRepository.save(swimmer1);
		swimmerRepository.save(swimmer2);
		GuiActionRunner.execute(() -> swimmingController.allSwimmers());
		assertThat(window.list("swimmerList").contents())
			.containsExactly("1, test1, testGender, testStroke", "2, test2, testGender, testStroke");
	}
	
	@Test @GUITest
	public void testAddButtonSuccess() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("nameTextBox").enterText("test");
		window.button(JButtonMatcher.withText("Add")).click();
		assertThat(window.list("swimmerList").contents())
			.containsExactly("1, test, Male, Freestyle");
	}
	
	@Test @GUITest
	public void testAddButtonError() {
		swimmerRepository.save(new Swimmer("1", "existing", "Male", "Freestyle"));
		window.textBox("idTextBox").enterText("1");
		window.textBox("nameTextBox").enterText("test");
		window.button(JButtonMatcher.withText("Add")).click();
		assertThat(window.list("swimmerList").contents()).containsExactly("1, existing, Male, Freestyle");
		window.label("errorMessageLabel")
			.requireText("Already existing swimmer with id 1: 1, existing, Male, Freestyle");
	}
	
	@Test @GUITest
	public void testDeleteButtonSuccess() {
		GuiActionRunner.execute(() -> 
			swimmingController.newSwimmer(new Swimmer("1", "toRemove", "testGender", "testStroke")));
		window.list("swimmerList").selectItem(0);
		window.button(JButtonMatcher.withText("Remove Swimmer")).click();
		assertThat(window.list("swimmerList").contents()).isEmpty();
	}
	
	@Test @GUITest
	public void testDeleteButtonError() {
		Swimmer swimmer = new Swimmer("1", "notPresent", "testGender", "testStroke");
		GuiActionRunner.execute(() -> swimmerView.getListSwimmerModel().addElement(swimmer));
		window.list("swimmerList").selectItem(0);
		window.button(JButtonMatcher.withText("Remove Swimmer")).click();
		assertThat(window.list("swimmerList").contents()).isEmpty();
		window.label("errorMessageLabel").requireText("No existing swimmer with id 1: 1, notPresent, testGender, testStroke");
	}
}
