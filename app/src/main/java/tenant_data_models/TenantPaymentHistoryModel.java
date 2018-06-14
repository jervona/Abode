package tenant_data_models;

/**
 * Created by D on 3/19/18.
 */

public class TenantPaymentHistoryModel {

    private String tenant_name;
    private String tenant_apt;
    private String month;
    private String amount_paid;
    private String conf;


    public TenantPaymentHistoryModel() {
    }

    public TenantPaymentHistoryModel(String month, String amount_paid, String conf) {
        this.month = month;
        this.amount_paid = amount_paid;
        this.conf=conf;
    }

    public TenantPaymentHistoryModel(String tenant_name, String tenant_apt, String month, String amount_paid, String conf) {
        this.tenant_name = tenant_name;
        this.tenant_apt = tenant_apt;
        this.month = month;
        this.amount_paid = amount_paid;
        this.conf = conf;
    }


    public String getTenant_name() {
        return tenant_name;
    }

    public String getTenant_apt() {
        return tenant_apt;
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



