package com.corbinelli.lorenzo.swimming.bdd.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static com.corbinelli.lorenzo.swimming.bdd.steps.DatabaseSteps.DB_NAME;
import static com.corbinelli.lorenzo.swimming.bdd.steps.DatabaseSteps.COLLECTION_NAME;
import static com.corbinelli.lorenzo.swimming.bdd.steps.DatabaseSteps.SWIMMER_ID_1;
import static com.corbinelli.lorenzo.swimming.bdd.steps.DatabaseSteps.SWIMMER_NAME_1;

import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SwimmingSwingViewSteps {
	
	private FrameFixture window;
	
	@After
	public void teardown() {
		if(window != null)
			window.cleanUp();
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
	
	@Then("An error is shown containing the name of the selected swimmer")
	public void an_error_is_shown_containing_the_name_of_the_selected_swimmer() {
		assertThat(window.label("errorMessageLabel").text()).contains(SWIMMER_NAME_1);
	}
	
}
