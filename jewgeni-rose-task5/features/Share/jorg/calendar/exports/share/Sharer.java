package jorg.calendar.exports.share;

/**
 * @author rose
 */
public abstract class Sharer {

    protected final String name;

    public Sharer(final String name) {
        this.name = name;
    }

    public String content() {
        return script() + "\n" + link();
    }

    protected abstract String script();

    private String link() {
        return "<!-- " + name + " -->\n" +
                "<a href=\"javascript:void(0)\" onclick=\"" + name + "();\">" +
                "<img src=\"http://www.simplesharebuttons.com/images/somacro/" + name + ".png\" " +
                "alt=\"" + name + "\" /></a>";
    }
}
