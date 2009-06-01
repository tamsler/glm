package org.thomasamsler.glm.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This entry point will be used when no Gears plugin is detected
 */
public class NoGears {
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel
				.add(new HTML(
						"<font color=\"red\">ERROR: This browser does not support Gears. "
								+ " Please <a href=\"http://gears.google.com/\">install Gears</a> "
								+ "and reload the application.</font>"));
	}
}