package jorg.calendar.exports.share;

/**
 * @author rose
 */
public class LinkedInSharer extends Sharer {

    protected LinkedInSharer() {
        super("linkedin");
    }

    @Override
    protected String script() {
        return "<script type=\"text/javascript\">\n" +
                "\tfunction " + name + "() {\n" +
                "\t\tvar url = \"http://www.linkedin.com/shareArticle?mini=true&url=\" + document.URL;\n" +
                "\t\twindow.open(url);\n" +
                "\t}\n" +
                "</script>";
    }
}
