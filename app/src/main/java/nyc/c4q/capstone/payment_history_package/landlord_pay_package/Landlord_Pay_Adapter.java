package nyc.c4q.capstone.payment_history_package.landlord_pay_package;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.capstone.R;
import nyc.c4q.capstone.datamodels.LandlordPay;

/**
 * Created by D on 3/24/18.
 */

public class Landlord_Pay_Adapter extends RecyclerView.Adapter<Landlord_Pay_Holder> {

    List<LandlordPay> landlordPayList;

    public Landlord_Pay_Adapter(List<LandlordPay> landlordPayList) {
        this.landlordPayList = landlordPayList;
    }

    @Override
    public Landlord_Pay_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.landlord_pay_itemview,parent,false);
        return new Landlord_Pay_Holder(view);
    }

    @Override
    public void onBindViewHolder(Landlord_Pay_Holder holder, int position) {

        LandlordPay landlordPay = landlordPayList.get(position);
        holder.onBind(landlordPay);
    }

    @Override
    public int getItemCount() {
        return landlordPayList.size();
    }
}
