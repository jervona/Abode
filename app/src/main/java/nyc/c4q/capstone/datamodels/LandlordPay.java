package nyc.c4q.capstone.datamodels;

import java.util.Date;

/**
 * Created by D on 3/24/18.
 */

public class LandlordPay {

    private String tenantName;
    private String addressLine;
    private String addressLine2;
    private String amount;
    private String confirmation;
    private String date;

    public LandlordPay(String tenantName, String addressLine, String addressLine2, String amount, String confirmation, String date) {
        this.tenantName = tenantName;
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.amount = amount;
        this.confirmation = confirmation;
        this.date = date;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAmount() {
        return amount;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public String getDate() {
        return date;
    }

}
