// #condition ShareViaEmail
package de.tubs.cs.isf.spl.jorg.calendar.exports.share;

import static de.tubs.cs.isf.spl.jorg.App.app;

/**
 * @author rose
 * @date 26.05.15.
 */
public class EmailSharer extends Sharer {

	protected EmailSharer(final String name) {
		super(name);
	}

	@Override
	protected String script() {
		return "<script type=\"text/javascript\">\n" + "\tfunction " + name + "() {\n"
						+ "\t\tvar url = \"mailto:?Subject=Meetings for '" + app().currentUser()
						+ "'&Body=\" + document.URL" + "\n" + "\t\twindow.open(url);\n" + "\t}\n" + "</script>";
	}
}
