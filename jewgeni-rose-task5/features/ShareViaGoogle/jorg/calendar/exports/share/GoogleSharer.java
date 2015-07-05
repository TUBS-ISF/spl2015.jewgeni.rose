package jorg.calendar.exports.share;

/**
 * @author rose
 */
public class GoogleSharer extends Sharer {

    public GoogleSharer() {
        super("google");
    }

    @Override
    protected String script() {
        return "<script type=\"text/javascript\">\n" +
                "\tfunction " + name + "() {\n" +
                "\t\tvar url = \"https://plus.google.com/share?url=\" + document.URL;\n" +
                "\t\twindow.open(url);\n" +
                "\t}\n" +
                "</script>";
    }
}
