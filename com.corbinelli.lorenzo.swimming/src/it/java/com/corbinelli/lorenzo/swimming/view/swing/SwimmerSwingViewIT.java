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
import com.corbinelli.lorenzo.swimming.model.Swimmer;
import com.corbinelli.lorenzo.swimming.repository.mongo.SwimmerMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class SwimmerSwingViewIT extends AssertJSwingJUnitTestCase {
	
	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
	private MongoClient client;
	private FrameFixture window;
	private SwimmerSwingView swimmerView;
	private SwimmerMongoRepository swimmerRepository;
	private SwimmingController swimmingController;

	@Override
	protected void onSetUp() throws Exception {
		client = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getFirstMappedPort()));
		swimmerRepository = new SwimmerMongoRepository(client, "testDB", "testCollection");
		for (Swimmer swimmer : swimmerRepository.findAll()) {
			swimmerRepository.delete(swimmer.getId());
		}
		GuiActionRunner.execute(() -> {
			swimmerView = new SwimmerSwingView();
			swimmingController = new SwimmingController(swimmerView, swimmerRepository);
			swimmerView.setSwimmingController(swimmingController);
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
			.containsExactly(swimmer1.toString(), swimmer2.toString());
	}
	
	@Test @GUITest
	public void testAddButtonSuccess() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("nameTextBox").enterText("test");
		window.radioButton("rdBtnFemale").click();
		window.comboBox("strokes").selectItem(1);
		window.button(JButtonMatcher.withText("Add")).click();
		assertThat(window.list("swimmerList").contents())
			.containsExactly(new Swimmer("1", "test", "Female", "Backstroke").toString());
	}
}
