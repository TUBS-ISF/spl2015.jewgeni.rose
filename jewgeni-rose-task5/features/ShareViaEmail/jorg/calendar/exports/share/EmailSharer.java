package jorg.calendar.exports.share;

import static jorg.App.app;

/**
 * @author rose
 */
public class EmailSharer extends Sharer {

    protected EmailSharer() {
        super("email");
    }

    @Override
    protected String script() {
        return "<script type=\"text/javascript\">\n" +
                "\tfunction " + name + "() {\n" +
                "\t\tvar url = \"mailto:?Subject=Meetings for '" + app().currentUser() + "'&Body=\" + document.URL"
                + "\n" +
                "\t\twindow.open(url);\n" +
                "\t}\n" +
                "</script>";
    }
}
