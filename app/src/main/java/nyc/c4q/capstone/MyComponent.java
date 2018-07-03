package nyc.c4q.capstone;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jervon.arnoldd on 7/2/18.
 */

@Singleton
@Component(modules = Stuff.class)
public interface MyComponent {

    void inject(MaintenanceView maintenanceView);

    void inject(MainActivity mainActivity);

    void inject(PaymentView paymentView);

    void inject(NewRequestFragment newRequestFragment);

    void inject(TenantActivity tenantActivity);
}
