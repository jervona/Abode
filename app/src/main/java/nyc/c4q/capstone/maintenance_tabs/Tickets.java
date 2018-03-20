package nyc.c4q.capstone.maintenance_tabs;

/**
 * Created by c4q on 3/19/18.
 */

public class Tickets {
    String APT;
    long time;
    String subject;
    String status;
    String number;

    public Tickets(String APT, long time, String subject, String status, String number) {
        this.APT = APT;
        this.time = time;
        this.subject = subject;
        this.status = status;
        this.number = number;
    }

    public Tickets() {
    }

}
