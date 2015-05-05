package de.tubs.cs.isf.spl.jorg.soft_features;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
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
public class Alarm extends Feature implements Runnable {

    private LocalDateTime time;
    private final Duration sleep;

    public Alarm(final LocalDateTime time) {
        this(time, Duration.ofMinutes(5));
    }

    public Alarm(final LocalDateTime time, final Duration sleep) {
        if (LocalDateTime.now().isAfter(time)) {
            throw new IllegalArgumentException("Event is already over!");
        }
        this.sleep = sleep;
        this.time = time;
    }

    @Override
    public void action() {
        run();
    }

    @Override
    public String menuKey() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean play() {
		Sequencer sequencer = null;
		try {
			sequencer = MidiSystem.getSequencer();
            Sequence sequence = MidiSystem.getSequence(new File("media/sunshine.mid"));

            sequencer.open();
            sequencer.setSequence(sequence);

            sequencer.start();

            Locale.setDefault(Locale.ENGLISH);
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

    @Override
    public String title() {
        return "Alarm";
    }
}
