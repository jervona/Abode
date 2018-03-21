package nyc.c4q.capstone.datamodels;

/**
 * Created by jervon.arnoldd on 3/16/18.
 */

public class UserApartmentInfo {
    String id;
    long building_id;
    String apt;
    String first_name;
    String last_name;
    String phone_number;
    String email_address;


    public UserApartmentInfo() {
    }

    public UserApartmentInfo(String id, long building_id, String APT, String first_name, String last_name, String phone_number, String email_address) {
        this.id = id;
        this.building_id = building_id;
        this.apt = APT;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email_address = email_address;
    }


    public String getId() {
        return id;
    }

    public long getBuilding_id() {
        return building_id;
    }

    public String getAPT() {
        return apt;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail_address() {
        return email_address;
    }
}
