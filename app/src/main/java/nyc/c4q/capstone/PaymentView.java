package nyc.c4q.capstone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.braintreepayments.api.dropin.DropInRequest;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import nyc.c4q.capstone.Tenant.tenant_pay_package.Tenant_Pay_Adapter;

/**
 * Created by jervon.arnoldd on 6/28/18.
 */

public class PaymentView extends LinearLayout{

    @BindView(R.id.payment_history_rv) RecyclerView paymentHistoryRv;

    @Inject TenentDataBaseHelper test;

    Tenant_Pay_Adapter adapter;
    String TAG = "PaymentView";
    Context context;

    private static final int REQUEST_CODE = 94;

    public PaymentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ((BaseApp) context.getApplicationContext()).myComponent.inject(this);
        this.context=context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        adapter = new Tenant_Pay_Adapter();
        paymentHistoryRv.setAdapter(adapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        test.paymentValueListener()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paymentHistoryModelList -> {
                    Log.e("Rent History List Size:", paymentHistoryModelList.size() + "");
                    Collections.reverse(paymentHistoryModelList);
                    adapter.updateTicketListItems(paymentHistoryModelList);
                }, throwable -> Log.e(TAG, "Error " + throwable.getMessage()));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
