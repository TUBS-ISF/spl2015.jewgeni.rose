package jorg; 

import jorg.calendar.Calendar; 
import java.util.Objects; 

/**
 *
 * @author rose
 */
public  class  User {
	

    private final String name;

	
    private final Calendar cal;

	

    public User(final String name, final Calendar cal) {
        this.name = name;
        this.cal = cal;
    }

	

    public Calendar getCalendar() {
        return cal;
    }

	

    @Override
    public int hashCode() {
        return name.hashCode();
    }

	

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.name, other.name);
    }

	

    public String name() {
        return name;
    }

	

    @Override
    public String toString() {
        return name();
    }


}
