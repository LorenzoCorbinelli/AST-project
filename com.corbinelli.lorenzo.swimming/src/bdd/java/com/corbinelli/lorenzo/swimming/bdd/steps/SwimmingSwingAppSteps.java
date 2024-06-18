package com.corbinelli.lorenzo.swimming.bdd.steps;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SwimmingSwingAppSteps {
	
	private static final String DB_NAME = "testDB";
	private static final String COLLECTION_NAME = "testCollection";
	private MongoClient client;
	private FrameFixture window;
	
	private static final String SWIMMER_ID_1 = "1";
	private static final String SWIMMER_NAME_1 = "test1";
	private static final String SWIMMER_ID_2 = "2";
	private static final String SWIMMER_NAME_2 = "test2";
	private static final String SWIMMER_GENDER = "testGender";
	private static final String SWIMMER_STROKE = "testStroke";
	
	@Before
	public void setUp() {
		client = new MongoClient();
		client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME).drop();
	}
	
	@After
	public void teardown() {
		client.close();
		if(window != null)
			window.cleanUp();
	}

	@Given("The database contains some swimmers")
	public void the_database_contains_some_swimmers() {
		addSwimmerToTheDB(SWIMMER_ID_1, SWIMMER_NAME_1, SWIMMER_GENDER, SWIMMER_STROKE);
		addSwimmerToTheDB(SWIMMER_ID_2, SWIMMER_NAME_2, SWIMMER_GENDER, SWIMMER_STROKE);
	}
	
	@Given("The Swimmer View is shown")
	public void the_swimmer_view_is_shown() {
		application("com.corbinelli.lorenzo.swimming.app.swing.SwimmingSwingApp")
			.withArgs(
					"--db-name=" + DB_NAME,
					"--db-collection=" + COLLECTION_NAME
					).start();
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame frame) {
				return "Swimming App".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(BasicRobot.robotWithCurrentAwtHierarchy());
	}
	@Given("The user provides swimmer data")
	public void the_user_provides_swimmer_data() {
		window.textBox("idTextBox").enterText("20");
		window.textBox("nameTextBox").enterText("new swimmer");
	}
	@When("The user clicks the {string} button")
	public void the_user_clicks_the_button(String button) {
		window.button(JButtonMatcher.withText(button)).click();
	}
	@Then("The list contains the new swimmer")
	public void the_list_contains_the_new_swimmer() {
		assertThat(window.list("swimmerList").contents())
			.anySatisfy(e -> assertThat(e).contains("20", "new swimmer", "Male", "Freestyle"));
	}
	
	@Given("The suer provides swimmer data with an existing id")
	public void the_suer_provides_swimmer_data_with_an_existing_id() {
		window.textBox("idTextBox").enterText(SWIMMER_ID_1);
		window.textBox("nameTextBox").enterText("existing swimmer");
	}
	
	@Then("An error is shown containing the name of the existing swimmer")
	public void an_error_is_shown_containing_the_name_of_the_existing_swimmer() {
		assertThat(window.label("errorMessageLabel").text()).contains(SWIMMER_NAME_1);
	}
	
	@Given("The user selects a swimmer from the list")
	public void the_user_selects_a_swimmer_from_the_list() {
		window.list("swimmerList").selectItem(".*" + SWIMMER_NAME_1 + ".*");
	}

	@Then("The swimmer is removed from the list")
	public void the_swimmer_is_removed_from_the_list() {
		assertThat(window.list("swimmerList").contents())
			.noneMatch(e -> e.contains(SWIMMER_NAME_1));
	}
	
	@Given("The swimmer is in the meantime removed from the database")
	public void the_swimmer_is_in_the_meantime_removed_from_the_database() {
		client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME)
			.deleteOne(Filters.eq("id", SWIMMER_ID_1));
	}

	@Then("An error is shown containing the name of the selected swimmer")
	public void an_error_is_shown_containing_the_name_of_the_selected_swimmer() {
		assertThat(window.label("errorMessageLabel").text()).contains(SWIMMER_NAME_1);
	}
	
	private void addSwimmerToTheDB(String id, String name, String gender, String mainStroke) {
		client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME)
			.insertOne(new Document()
					.append("id", id)
					.append("name", name)
					.append("gender", gender)
					.append("mainStroke", mainStroke));
	}
}
