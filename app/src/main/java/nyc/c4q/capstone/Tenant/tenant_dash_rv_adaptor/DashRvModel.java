package nyc.c4q.capstone.Tenant.tenant_resource_controller;

/**
 * Created by D on 3/31/18.
 */

public class DashRvModel {

    private int resource_icon;
    private String resource_name;
    private String url;


    public DashRvModel(int resource_icon, String resource_name, String url) {
        this.resource_icon = resource_icon;
        this.resource_name = resource_name;
        this.url = url;
    }

    String getUrl() {
        return url;
    }

    int getResource_icon() {
        return resource_icon;
    }

    String getResource_name() {
        return resource_name;
    }
}
