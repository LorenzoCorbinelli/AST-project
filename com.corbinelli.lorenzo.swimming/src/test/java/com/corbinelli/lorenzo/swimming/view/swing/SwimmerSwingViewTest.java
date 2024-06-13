package com.corbinelli.lorenzo.swimming.view.swing;


import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GUITestRunner.class)
public class SwimmerSwingViewTest extends AssertJSwingJUnitTestCase {
	
	private FrameFixture window;
	private SwimmerSwingView swimmerSwingView;

	@Override
	protected void onSetUp() throws Exception {
		GuiActionRunner.execute(() -> {
			swimmerSwingView = new SwimmerSwingView();
			return swimmerSwingView;
		});
		window = new FrameFixture(robot(), swimmerSwingView);
		window.show();
	}

	@Test
	public void testControlsInitialState() {
		window.label(JLabelMatcher.withText("id"));
		window.textBox("idTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("name"));
		window.textBox("nameTextBox").requireEnabled();
		window.label(JLabelMatcher.withText("gender"));
		window.radioButton("rdBtnMale").requireEnabled();
		window.radioButton("rdBtnFemale").requireEnabled();
		window.label(JLabelMatcher.withText("main stroke"));
		window.comboBox("strokes").requireEnabled();
		assertThat(window.comboBox("strokes").contents())
			.containsExactly("Freestyle", "Backstroke", "Breaststroke", "Butterfly", "Mixed");
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
	}
}
