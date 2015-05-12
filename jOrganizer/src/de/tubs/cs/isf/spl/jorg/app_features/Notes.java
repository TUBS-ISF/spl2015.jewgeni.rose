package de.tubs.cs.isf.spl.jorg.app_features;

import de.tubs.cs.isf.spl.jorg.App;
import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.PROMPT_CLEAR;
import static de.tubs.cs.isf.spl.jorg.App.app;
import de.tubs.cs.isf.spl.jorg.Feature;
import java.util.Stack;

/**
 *
 * @author rose
 */
public class Notes extends Feature {

    private static final String ADD_NOTE = "add";
    private static final String VIEW_LAST_NOTE = "show";
    // works only with history enabled
    private static final String HISTORY = "history";
    private static final String REMOVE_NOTE = "remove";
    private static final String LIST_ALL_NOTES = "list";

    private final Stack<Note> notes;
    private final String menuString;
    private final boolean historyMode;

    public Notes(final String key) {
        this(key, key);
    }

    public Notes(final String key, final String desc) {
        super(key, desc);
        notes = new Stack<Note>();
        this.historyMode = App.CONFIG.getProperty(HISTORY) != null;

        final StringBuilder sb = new StringBuilder();
        sb.append(App.PROMPT_BOLD + "notes menu:\n" + App.PROMPT_NORMAL);
        sb.append(String.format("%10s - exits notes menu\n", "[" + EXIT + "]"));
        sb.append(String.format("%10s - add a new note\n", "[" + ADD_NOTE + "]"));
        sb.append(String.format("%10s - view last note\n", "[" + VIEW_LAST_NOTE + "]"));

        if (historyMode) {
            sb.append(String.format("%10s - remove a note\n", "[" + REMOVE_NOTE + "]"));
            sb.append(String.format("%10s - list all notes\n", "[" + LIST_ALL_NOTES + "]"));
        }
        menuString = sb.toString();
    }

    public void add(final String title, final String desc) {
        final Note note = new Note(title, desc);

        if (historyMode) {
            notes.clear();
        } else if (notes.contains(note)) {
            notes.remove(note);
        }
        notes.push(note);
    }

    public boolean remove(final String title) {
        return notes.remove(new Note(title, null));
    }

    public void view() {
        if (!notes.empty()) {
            app().println(notes.peek(), key);
        }
    }

    public void list() {
        final StringBuilder sb = new StringBuilder();
        for (final Note note : notes) {
            sb.append(note.toString()).append("\n")
                .append("-----------------------\n");
        }
        app().println(sb, key);
    }

    @Override
    public void action() {
        String input;
        while (true) {
            app().println(PROMPT_CLEAR);
            app().println(menuString, key);
            input = app().readLine();

            if (EXIT.equals(input)) {
                break;
            }

            if (ADD_NOTE.equals(input)) {
                app().print("Title: ", key);
                final String title = app().readLine();

                app().print("Note: ", key);
                final String note = app().readLine();

                add(title, note);
            } else if (VIEW_LAST_NOTE.equals(input)) {
                view();
            }
            if (historyMode) {
                if (REMOVE_NOTE.equals(input)) {
                    app().print("Title: ", key);
                    final String title = app().readLine();

                    if (remove(title)) {
                        app().println("Note successfully removed", key);
                    } else {
                        app().println("There is no such note as '" + title + "'!", key);
                    }
                } else if (LIST_ALL_NOTES.equals(input)) {
                    list();
                }
            }
            app().printErr("Invalid option", key);
        }
    }

    public static class Note {

        final String note, title;

        public Note(final String title, final String note) {
            this.title = title;
            this.note = note;
        }

        @Override
        public int hashCode() {
            return title.hashCode();
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Note other = (Note) obj;
            return !((this.title == null) ? (other.title != null) : !this.title.equals(other.title));
        }

        @Override
        public String toString() {
            return App.PROMPT_BOLD + title + App.PROMPT_NORMAL + ":\n  " + note();
        }

        private String note() {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < note.length(); i++) {
                if (i % 40 == 0) {
                    sb.append("\n  ");
                }
                sb.append(note.charAt(i));
            }
            return sb.toString();
        }
    }
}
