package de.tubs.cs.isf.spl.jorg;

/**
 *
 * @author rose
 */
public abstract class Feature {

	public String description() {
        return title();
    }

	public abstract String menuKey();

	public abstract String title();

	public abstract void action();
}
