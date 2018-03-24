package nyc.c4q.capstone.datamodels;

import java.util.ArrayList;

/**
 * Created by jervon.arnoldd on 3/16/18.
 */

public class Tickets {

   String apt;
   long time;
   String subject;
   String status;

    public Tickets() {
    }

    public Tickets(String apt, long time, String subject, String status) {
        this.apt = apt;
        this.time = time;
        this.subject = subject;
        this.status = status;
    }

    public String getapt() {
        return apt;
    }

    public long getTime() {
        return time;
    }

    public String getSubject() {
        return subject;
    }

    public String getStatus() {
        return status;
    }
}
