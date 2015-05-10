package de.tubs.cs.isf.spl.jorg.app_features;

import de.tubs.cs.isf.spl.jorg.Feature;

/**
 *
 * @author rose
 */
public final class Quit extends Feature {

    public Quit(final String key) {
        this(key, key);
    }

    public Quit(final String key, final String desc) {
        super(key, desc);
    }

    @Override
    public void action() {
        System.exit(0);
    }
}
