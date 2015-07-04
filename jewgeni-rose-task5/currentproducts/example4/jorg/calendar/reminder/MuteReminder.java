package jorg.calendar.reminder; 

import jorg.calendar.Meeting; 
import java.time.Duration; 
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import javax.swing.JOptionPane; 

/**
 *
 * @author rose
 */
 

/**
 *
 * @author rose
 */
class  MuteReminder  extends Reminder {
	

    /**
     *
     * @param time   LDT of Meeting
     * @param before Duration to play before meeting begin
     */
    MuteReminder(final Meeting meeting, final Duration before) {
        super(meeting, before);
    }

	

    protected void play() {
        JOptionPane.showMessageDialog(null, "Your meeting '" + meeting.title() + "' is about to begin in " + before.
                                      toHours() + " hours", "Reminder", JOptionPane.OK_OPTION);
    }

	

    public void run() {
        LocalDateTime now;
        while (!shutdown) {
            now = LocalDateTime.now();
            if (now.equals(time) || now.isAfter(time)) {
                play();
                break;
            }
            try {
                Thread.sleep(Duration.ofMinutes(1).toMillis());    // sleep for a minute and check again
            } catch (InterruptedException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

	

    @Override
    protected synchronized void stop() {
        shutdown = true;
    }

	

    @Override
    public String toString() {
        return "Reminder set for " + time.format(DateTimeFormatter.ISO_LOCAL_DATE) + " at "
            + time.format(DateTimeFormatter.ISO_LOCAL_TIME) + " clock.";
    }


}
