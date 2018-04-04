package nyc.c4q.capstone.datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jervon.arnoldd on 3/16/18.
 */

public class Tickets {

    private String ticket_number;
    private long time;
    private String location;
    private String apt;
    private String description;
    private String status;
    List<String> imageURl;
    private String title;
    private int priority;


    public Tickets(String ticket_number, long time, String location, String apt, String title,String description, String status, int priority){
       this.title=title;
        this.ticket_number = ticket_number;
        this.time = time;
        this.location = location;
        this.apt = apt;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

    public Tickets() {
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public long getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getApt() {
        return apt;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getImageURl() {
        return imageURl;
    }

    public String getTitle() {
        return title;
    }

    public void setImageURl(List<String> imageURl) {
        this.imageURl = imageURl;
    }

    public int getPriority() {
        return priority;
    }
}
