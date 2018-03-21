package nyc.c4q.capstone.datamodels;

import java.util.ArrayList;

/**
 * Created by jervon.arnoldd on 3/16/18.
 */

public class Tickets {

   String APT;
   long time;
   String subject;
   String status;

    public Tickets(String APT, long time, String subject, String status) {
        this.APT = APT;
        this.time = time;
        this.subject = subject;
        this.status = status;
    }

    public Tickets() {
    }
}
