package nyc.c4q.capstone.Tenant.tenant_resource_controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.capstone.R;

/**
 * Created by D on 3/31/18.
 */

public class DashAdapter extends RecyclerView.Adapter<DashHolder> {


    private List<DashRvModel> dash_rv_modelList;

    public DashAdapter(List<DashRvModel> dash_rv_modelList) {
        this.dash_rv_modelList = dash_rv_modelList;
    }

    @Override
    public DashHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dash_board_itemview,parent,false);
        return new DashHolder(childView);
    }

    @Override
    public void onBindViewHolder(DashHolder holder, int position) {
        DashRvModel dash_rv_model = dash_rv_modelList.get(position);
        holder.onBind(dash_rv_model);

    }

    @Override
    public int getItemCount() {
        return dash_rv_modelList.size();
    }
}
