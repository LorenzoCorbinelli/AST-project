package com.corbinelli.lorenzo.swimming.view.swing;


import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.corbinelli.lorenzo.swimming.controller.SwimmingController;
import com.corbinelli.lorenzo.swimming.model.Swimmer;

@RunWith(GUITestRunner.class)
public class SwimmerSwingViewTest extends AssertJSwingJUnitTestCase {
	
	private FrameFixture window;
	private SwimmerSwingView swimmerSwingView;
	@Mock
	private SwimmingController swimmingController;
	private AutoCloseable closeable;

	@Override
	protected void onSetUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		GuiActionRunner.execute(() -> {
			swimmerSwingView = new SwimmerSwingView();
			swimmerSwingView.setSwimmingController(swimmingController);
			return swimmerSwingView;
		});
		window = new FrameFixture(robot(), swimmerSwingView);
		window.show();
	}
	
	@Override
	protected void onTearDown() throws Exception {
		closeable.close();
	}

	@Test @GUITest
	public void testControlsInitialState() {
		window.label(JLabelMatcher.withText("id"));
		window.textBox("idTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("name"));
		window.textBox("nameTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("gender"));
		window.radioButton("rdBtnMale").requireEnabled();
		window.radioButton("rdBtnMale").requireSelected();
		window.radioButton("rdBtnFemale").requireEnabled();
		window.label(JLabelMatcher.withText("main stroke"));
		window.comboBox("strokes").requireEnabled();
		assertThat(window.comboBox("strokes").contents())
			.containsExactly("Freestyle", "Backstroke", "Breaststroke", "Butterfly", "Mixed");
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
		window.list("swimmerList");
		window.button(JButtonMatcher.withText("Remove Swimmer")).requireDisabled();
		window.label("errorMessageLabel").requireText(" ");
	}
	
	@Test
	public void testWhenIdAndNameAreNotEmptyThenTheAddButtonShouldBeEnabled() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("nameTextBox").enterText("test");
		window.button(JButtonMatcher.withText("Add")).requireEnabled();
	}
	
	@Test
	public void testWhenIdOrNameAreBlankThenTheAddButtonShouldBeDisabled() {
		JTextComponentFixture idTextBox = window.textBox("idTextBox");
		JTextComponentFixture nameTextBox = window.textBox("nameTextBox");
		idTextBox.enterText(" ");
		nameTextBox.enterText("test");
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
		idTextBox.setText("");
		nameTextBox.setText("");
		idTextBox.enterText("1");
		nameTextBox.enterText(" ");
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
	}
	
	@Test
	public void testRemoveSwimmerShouldBeEnabledOnlyWhenASwimmerIsSelected() {
		GuiActionRunner.execute(() -> 
			swimmerSwingView.getListSwimmerModel()
				.addElement(new Swimmer("1", "test", "testGender", "testStroke")));
		
		window.list("swimmerList").selectItem(0);
		JButtonFixture removeButton = window.button(JButtonMatcher.withText("Remove Swimmer"));
		removeButton.requireEnabled();
		window.list("swimmerList").clearSelection();
		removeButton.requireDisabled();
	}
	
	@Test
	public void testShowAllswimmersShouldAddSwimmersToTheList() {
		Swimmer swimmer1 = new Swimmer("1", "test1", "testGender", "testStroke");
		Swimmer swimmer2 = new Swimmer("2", "test2", "testGender", "testStroke");
		GuiActionRunner.execute(() -> swimmerSwingView.showAllSwimmers(asList(swimmer1, swimmer2)));
		String[] contents = window.list("swimmerList").contents();
		assertThat(contents).containsExactly("1, test1, testGender, testStroke", "2, test2, testGender, testStroke");
	}
	
	@Test
	public void testSwimmerAddedShouldAddTheSwimmerToTheListAndResetTheErrorLabel() {
		GuiActionRunner.execute(() -> 
			swimmerSwingView.swimmerAdded(new Swimmer("1", "test", "testGender", "testStroke")));
		String[] contents = window.list("swimmerList").contents();
		assertThat(contents).containsExactly("1, test, testGender, testStroke");
		window.label("errorMessageLabel").requireText(" ");
	}
	
	@Test
	public void testSwimmerShowErrorShouldSetTheMessageInTheErrorLabel() {
		Swimmer swimmer = new Swimmer("1", "test", "testGender", "testStroke");
		GuiActionRunner.execute(() -> swimmerSwingView.showError("error message", swimmer));
		window.label("errorMessageLabel").requireText("error message: 1, test, testGender, testStroke");
	}
	
	@Test
	public void testShowErrorSwimmerNotFound() {
		Swimmer swimmer1 = new Swimmer("1", "test1", "testGender", "testStroke");
		Swimmer swimmer2 = new Swimmer("2", "test2", "testGender", "testStroke");
		GuiActionRunner.execute(() -> {
			swimmerSwingView.getListSwimmerModel().addElement(swimmer1);
			swimmerSwingView.getListSwimmerModel().addElement(swimmer2);
		});
		GuiActionRunner.execute(() -> swimmerSwingView.showErrorSwimmerNotFound("error message", swimmer1));
		window.label("errorMessageLabel").requireText("error message: 1, test1, testGender, testStroke");
		assertThat(window.list("swimmerList").contents()).containsExactly("2, test2, testGender, testStroke");
	}
	
	@Test
	public void testSwimmerRemovedShouldRemoveTheSwimmerFromTheListAndResetTheErrorLabel() {
		Swimmer swimmer1 = new Swimmer("1", "test1", "testGender", "testStroke");
		Swimmer swimmer2 = new Swimmer("2", "test2", "testGender", "testStroke");
		GuiActionRunner.execute(() -> {
			swimmerSwingView.getListSwimmerModel().addElement(swimmer1);
			swimmerSwingView.getListSwimmerModel().addElement(swimmer2);
		});
		GuiActionRunner.execute(() -> 
			swimmerSwingView.swimmerRemoved(new Swimmer("2", "test2", "testGender", "testStroke")));
		String[] contents = window.list("swimmerList").contents();
		assertThat(contents).containsExactly("1, test1, testGender, testStroke");
		window.label("errorMessageLabel").requireText(" ");
	}
	
	@Test
	public void testAddButtonShouldCallTheNewSwimmerMethodOfTheController() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("nameTextBox").enterText("test");
		window.radioButton("rdBtnFemale").click();
		window.comboBox("strokes").selectItem(1);
		window.button(JButtonMatcher.withText("Add")).click();
		verify(swimmingController).newSwimmer(new Swimmer("1", "test", "Female", "Backstroke"));
	}
	
	@Test
	public void testRemoveSwimmerButtonShouldCallTheDeleteSwimmerMethodOfTheController() {
		Swimmer swimmer1 = new Swimmer("1", "test1", "testGender", "testStroke");
		Swimmer swimmer2 = new Swimmer("2", "test2", "testGender", "testStroke");
		GuiActionRunner.execute(() -> {
			swimmerSwingView.getListSwimmerModel().addElement(swimmer1);
			swimmerSwingView.getListSwimmerModel().addElement(swimmer2);
		});
		window.list("swimmerList").selectItem(1);
		window.button(JButtonMatcher.withText("Remove Swimmer")).click();
		verify(swimmingController).deleteSwimmer(swimmer2);
	}
}
