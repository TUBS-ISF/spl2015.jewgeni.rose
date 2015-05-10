package de.tubs.cs.isf.spl.jorg;

/**
 *
 * @author rose
 */
public abstract class Feature {

    protected final String key, desc;

    protected Feature(final String key) {
        this.desc = getClass().getName();
        this.key = key;
    }

    protected Feature(final String key, final String desc) {
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
}
