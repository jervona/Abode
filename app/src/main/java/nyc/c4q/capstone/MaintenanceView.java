package nyc.c4q.capstone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import nyc.c4q.capstone.Tenant.tenant_maintenance.SubmittedAdapter;

/**
 * Created by jervon.arnoldd on 6/28/18.
 */

public class MaintenanceView extends LinearLayout {

    @BindView(R.id.maintenance_rv)
    RecyclerView maintenanceRV;

    SubmittedAdapter adapter;
    String TAG = "MaintenanceView";
    @Inject
    TenentDataBaseHelper test;

    private CompositeDisposable disposables = new CompositeDisposable();


    public MaintenanceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ((BaseApp) context.getApplicationContext()).myComponent.inject(this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        adapter = new SubmittedAdapter();
        maintenanceRV.setAdapter(adapter);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        test.maintenanceValueListener()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tickets -> {
                    Log.e("Tickets List Size :", tickets.size() + "");
                    Collections.reverse(tickets);
                    adapter.updateTicketListItems(tickets);
                }, throwable -> Log.e(TAG, "Error " + throwable.getMessage()));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}


