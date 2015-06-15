// #condition ShareViaFacebook
package de.tubs.cs.isf.spl.jorg.calendar.exports.share;

/**
 * @author rose
 * @date 25.05.15.
 */
public class FacebookSharer extends Sharer {

	protected FacebookSharer(final String name) {
		super(name);
	}

	@Override
	protected String script() {
		return "<script type=\"text/javascript\">\n" + "\tfunction " + name + "() {\n"
						+ "\t\tvar url = \"http://www.facebook.com/sharer.php?u=\" + document.URL;\n"
						+ "\t\twindow.open(url);\n" + "\t}\n" + "</script>";
	}
}
