package nyc.c4q.capstone;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by jervon.arnoldd on 4/10/18.
 */

public class Notification {
    private static Notification single_instance = null;

    int num;
    String path;


    public Notification() {
    }

    public static Notification getInstance() {
        if (single_instance == null)
            single_instance = new Notification();

        return single_instance;
    }


    public int getNum() {
        return num;
    }

//    public void radioGroup() {
//
//            Intent intent = new Intent(rootView.getContext().getApplicationContext(), MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(rootView.getContext().getApplicationContext(), NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//            NotificationManager notificationManager = (NotificationManager) rootView.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(rootView.getContext().getApplicationContext(), NOTIFICATION_CHANNEL)
//                    .setSmallIcon(R.mipmap.ic_launcher_round)
//                    .setContentTitle("You've been notified! of a change")
//                    .setContentIntent(pendingIntent)
//                    .setDefaults(NotificationCompat.DEFAULT_ALL)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setContentText("is is now four");
//            assert notificationManager != null;
//            notificationManager.notify(NOTIFICATION_ID, builder.build());
//
//    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
