package nyc.c4q.capstone;

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
