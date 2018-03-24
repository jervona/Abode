package nyc.c4q.capstone.datamodels;

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
    private String imageUrl;

    public Tickets(String ticket_number, long time, String location, String apt, String description, String status, String imageUrl){
        this.ticket_number = ticket_number;
        this.time = time;
        this.location = location;
        this.apt = apt;
        this.description = description;
        this.status = status;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
