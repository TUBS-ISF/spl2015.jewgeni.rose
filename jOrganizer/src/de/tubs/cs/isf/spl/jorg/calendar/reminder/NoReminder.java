package de.tubs.cs.isf.spl.jorg.calendar.reminder;

/**
 *
 * @author rose
 */
public class NoReminder extends Reminder {

    public NoReminder() {
        super(null);
    }

    @Override
    public void action() {
        // nothing to do
    }

    @Override
    public String menuKey() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String title() {
        return "NoReminder";
    }

    @Override
    public String toString() {
        return "Keine Erinnerug eingerichtet.";
    }
}
