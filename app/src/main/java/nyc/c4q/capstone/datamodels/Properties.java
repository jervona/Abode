package nyc.c4q.capstone.datamodels;

/**
 * Created by jervon.arnoldd on 3/16/18.
 */

public class Properties {

    long ID;
    String Building;
    String Address;
    int numberOfUnits;
    long netRent;


    public Properties() {
    }

    public Properties(long ID, String building, String address, int numberOfUnits, long netRent) {
        this.ID = ID;
        Building = building;
        Address = address;
        this.numberOfUnits = numberOfUnits;
        this.netRent = netRent;
    }
}
