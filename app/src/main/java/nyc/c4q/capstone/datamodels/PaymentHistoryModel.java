package nyc.c4q.capstone.datamodels;

/**
 * Created by D on 3/19/18.
 */

public class PaymentHistoryModel {

    private String month;
    private String amount_paid;
    private String conf;


    public PaymentHistoryModel() {
    }

    public PaymentHistoryModel(String month, String amount_paid, String conf) {
        this.month = month;
        this.amount_paid = amount_paid;
        this.conf=conf;
    }

    public String getMonth() {
        return month;
    }


    public String getAmount_paid() {
        return amount_paid;
    }

    public String getConf(){
        return conf;
    }


}



