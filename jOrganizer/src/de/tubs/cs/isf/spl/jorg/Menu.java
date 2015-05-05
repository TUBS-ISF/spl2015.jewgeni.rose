package de.tubs.cs.isf.spl.jorg;

import static de.tubs.cs.isf.spl.jorg.App.CONFIG;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rose
 */
public class Menu {

    private final List<Feature> features;
    private final String menuString;

    public Menu() {
        features = new ArrayList<Feature>();
        for (final String key : CONFIG.stringPropertyNames()) {

        }
        final StringBuilder sb = new StringBuilder();
        sb.append("jOrganizer-Menü:");
        for (final Feature f : features) {
            sb.append("\t[").append(f.menuKey()).append("] - ").append(f.description()).append("\n");
        }
        menuString = sb.toString();
    }

    @Override
    public String toString() {
        return menuString;
    }
}
