package nyc.c4q.capstone.dash_controller;

/**
 * Created by D on 3/31/18.
 */

public class Dash_Rv_Model {

    private int resource_icon;
    private String resource_name;

    public Dash_Rv_Model(int resource_icon, String resource_name) {
        this.resource_icon = resource_icon;
        this.resource_name = resource_name;
    }

    public int getResource_icon() {
        return resource_icon;
    }

    public String getResource_name() {
        return resource_name;
    }
}
