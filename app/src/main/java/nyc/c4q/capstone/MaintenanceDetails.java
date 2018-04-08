package nyc.c4q.capstone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaintenanceDetails extends Fragment {
    View rootView;
    @BindView(R.id.tenant_name)
    TextView tenantName;
    @BindView(R.id.tenants_apt)
    TextView tenantApt;
    @BindView(R.id.subject_title_view)
    TextView issueTitle;
    @BindView(R.id.radio_urgent)
    RadioButton urgentButton;
    @BindView(R.id.radio_moderate)
    RadioButton moderateButton;
    @BindView(R.id.user_description_view)
    TextView description;
    @BindView(R.id.image_request_rv)
    RecyclerView imageRV;

    public MaintenanceDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maintenance_details, container, false);
        return rootView;
    }

}
