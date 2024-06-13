package com.corbinelli.lorenzo.swimming.view.swing;


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
	}
}
