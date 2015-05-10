package de.tubs.cs.isf.spl.jorg.app_features;

import static de.tubs.cs.isf.spl.jorg.App.app;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JOptionPane;

import de.tubs.cs.isf.spl.jorg.Feature;

/**
 *
 * @author rose
 */
public class AlarmFactory extends Feature {

    public AlarmFactory(final String key) {
        this(key, key);
    }

    public AlarmFactory(final String key, final String desc) {
        super(key, desc);
    }

    @Override
    public void action() {
        app().println("Setting up alarm ... ", key);
        app().print("Date [2015-04-30]: ");
        final String dateStr = app().readLine();

        app().print("Start [08:00]: ");
        final String beginStr = app().readLine();

        app().print("Sleep time [min]: ");
        final String mins = app().readLine();
        Duration duration = Duration.ofMinutes(5);
        if (mins != null && !mins.isEmpty()) {
            duration = Duration.ofMinutes(Long.parseLong(mins));
        }
        final LocalDateTime date = LocalDateTime.parse(dateStr + "T" + beginStr + ":00",
                                                       DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        new Alarm(date, duration).run();
    }
}

class Alarm implements Runnable {

    private LocalDateTime time;
    private final Duration sleep;

    Alarm(final LocalDateTime time, final Duration sleep) {
        if (LocalDateTime.now().isAfter(time)) {
            throw new IllegalArgumentException("Event is already over!");
        }
        this.sleep = sleep;
        this.time = time;
    }

    private boolean play() {
        Sequencer sequencer = null;
        try {
            sequencer = MidiSystem.getSequencer();
            Sequence sequence = MidiSystem.getSequence(new File("media/sunshine.mid"));

            sequencer.open();
            sequencer.setSequence(sequence);

            sequencer.start();

            if (JOptionPane.showConfirmDialog(null, "Sleep for another " + sleep.toMinutes() + " minutes?",
                                              "Alarm", JOptionPane.OK_OPTION) == JOptionPane.YES_OPTION) {
                return true;
            }
            sequencer.stop();
        } catch (final InvalidMidiDataException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (final IOException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (final MidiUnavailableException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (sequencer != null) {
                sequencer.close();
            }
        }
        return false;
    }

    public void run() {
        LocalDateTime now;
        while (true) {
            now = LocalDateTime.now();
            if (now.equals(time) || now.isAfter(time)) {
                if (!play()) {
                    break;
                }
                time = time.plus(sleep);
            }
            try {
                Thread.sleep(Duration.ofMinutes(1).toMillis());    // sleep for a minute and check again
            } catch (InterruptedException ex) {
                Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
