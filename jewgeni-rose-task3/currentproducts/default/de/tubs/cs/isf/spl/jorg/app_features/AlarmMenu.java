// #condition ALARM
package de.tubs.cs.isf.spl.jorg.app_features;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.timer.Timer;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.tubs.cs.isf.spl.jorg.Feature;

/**
 * @author rose
 */
public class AlarmMenu extends Feature {

	private final LocalTime DEFAULT_TIME = LocalTime.of(8, 0);
	private final LocalDate DEFAULT_DATE = LocalDate.now().plusDays(1);
	private final Duration DEFAULT_SLEEP_TIME = Duration.ofMinutes(5);

	public AlarmMenu(final String key, final String desc) {
		super(key, desc);
	}

	@Override
	public void action() {
		println("Setting up alarm ... \n");
		final String dateStr = readLine("Date [2015-04-30]: ");
		final LocalDate date;
		if (!dateStr.isEmpty()) {
			date = LocalDate.parse(dateStr);
		} else {
			date = DEFAULT_DATE;
		}

		final String beginStr = readLine("Start [08:00]: ");
		final LocalTime time;
		if (!beginStr.isEmpty()) {
			time = LocalTime.parse(beginStr);
		} else {
			time = DEFAULT_TIME;
		}

		final String mins = readLine("Sleep time [min]: ");
		Duration duration = DEFAULT_SLEEP_TIME;
		if (!mins.isEmpty()) {
			duration = Duration.ofMinutes(Long.parseLong(mins));
		}
		final LocalDateTime dateTime = date.atTime(time);

		if (LocalDateTime.now().isAfter(dateTime)) {
			printErr("Event is already over!");
		} else {
			final Alarm alarm = new Alarm(dateTime, duration);
			println("Set up alarm at '" + alarm + "'");
			SwingUtilities.invokeLater(alarm);
		}
	}
}

class Alarm implements Runnable {

	private LocalDateTime time;
	private final Duration sleep;

	Alarm(final LocalDateTime time, final Duration sleep) {
		this.sleep = sleep;
		this.time = time;
	}

	// #ifdef AlarmWithSound
	private boolean sound() {
		Sequencer sequencer = null;
		try {
			sequencer = MidiSystem.getSequencer();
			Sequence sequence = MidiSystem.getSequence(getClass().getClassLoader().getResource("media/sunshine.mid")
							.openStream());
			sequencer.open();
			sequencer.setSequence(sequence);

			sequencer.start();

			if (mute()) {
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

	// #endif

	private boolean mute() {
		return JOptionPane.showConfirmDialog(null, "Sleep for another " + sleep.toMinutes() + " minutes?", "Alarm",
						JOptionPane.OK_OPTION) == JOptionPane.YES_OPTION;
	}

	public void run() {
		LocalDateTime now;
		while (true) {
			now = LocalDateTime.now();
			if (now.equals(time) || now.isAfter(time)) {

				// #if AlarmWithSound
				if (!sound()) {
				// #else
//@				if (!mute()) {
				// #endif
					break;
				}
				time = time.plus(sleep);
			}
			try {
				Thread.sleep(Timer.ONE_MINUTE);
			} catch (InterruptedException ex) {
				Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public String toString() {
		return String.format("%1$te. %1$tB %1$tY, %1$tR o'clock", time);
	}
}
