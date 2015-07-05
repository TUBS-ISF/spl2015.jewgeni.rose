package jorg.calendar.exports.share; 

/**
 * @author rose
 */
public  class  FacebookSharer  extends Sharer {
	

    public FacebookSharer() {
        super("facebook");
    }

	

    protected String script() {
        return "<script type=\"text/javascript\">\n" +
                "\tfunction " + name + "() {\n" +
                "\t\tvar url = \"http://www.facebook.com/sharer.php?u=\" + document.URL;\n" +
                "\t\twindow.open(url);\n" +
                "\t}\n" +
                "</script>";
    }


}
