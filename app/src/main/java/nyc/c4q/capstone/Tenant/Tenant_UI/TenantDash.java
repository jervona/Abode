package nyc.c4q.capstone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.Tenant.tenant_resource_controller.DashAdapter;
import nyc.c4q.capstone.Tenant.tenant_resource_controller.DashRvModel;

/**
 * Created by jervon.arnoldd on 6/28/18.
 */

public class TenantDash extends ConstraintLayout {
    @BindView(R.id.dash_recycler_view)
    RecyclerView recyclerView;



    public TenantDash(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);


    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        recyclerView.setAdapter(new DashAdapter());
    }




    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

}
