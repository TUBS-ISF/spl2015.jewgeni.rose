// #condition ShareViaTwitter
package de.tubs.cs.isf.spl.jorg.calendar.exports.share;

import static de.tubs.cs.isf.spl.jorg.App.app;

/**
 * @author rose
 * @date 26.05.15.
 */
public class TwitterSharer extends Sharer {

	protected TwitterSharer(final String name) {
		super(name);
	}

	@Override
	protected String script() {
		return "<script type=\"text/javascript\">\n" + "\tfunction " + name + "() {\n"
						+ "\t\tvar url = \"http://twitter.com/share?url=\" + document.URL + \"&text=Meetings for "
						+ app().currentUser() + "\"&hashtags=jOrganizer\n" + "\t\twindow.open(url);\n" + "\t}\n"
						+ "</script>";
	}
}
