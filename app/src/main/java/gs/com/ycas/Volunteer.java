package gs.com.ycas;

import java.util.ArrayList;

public class Volunteer {

    public ArrayList<User> volunteersList;

    private static Volunteer INSTANCE = null;

    // other instance variables can be here

    private Volunteer() {};

    public static Volunteer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Volunteer();
        }
        return(INSTANCE);
    }

    public ArrayList<User> getVolunteers() {
        return this.volunteersList;
    }

    public void setVolunteers(ArrayList<User> volunteersList){
        this.volunteersList = volunteersList;
    }
}
