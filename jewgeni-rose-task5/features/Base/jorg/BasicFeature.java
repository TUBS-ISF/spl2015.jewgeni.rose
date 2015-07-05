package jorg;

/**
 * @author rose
 */
public abstract class BasicFeature implements Feature {

    protected final String key, desc;
    
    protected BasicFeature(final String key, final String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String description() {
        return desc;
    }

    public String menuKey() {
        return key;
    }

    public abstract void action();

    protected void print(final Object obj) {
        App.print(obj, key);
    }

    protected void println(final Object obj) {
        App.println(obj, key);
    }

    protected void printErr(final Object obj) {
        App.printErr(obj, key);
    }

    protected String readLine(final String prompt) {
        return App.readLine(prompt, key);
    }

    protected String readLine() {
        return readLine("");
    }
}
