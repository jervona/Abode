package nyc.c4q.capstone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.adapter.ImageAdapter;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;


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
    @BindView((R.id.radio_priority))
    RadioGroup priorityOptions;
    @BindView(R.id.radio_urgent)
    RadioButton urgentOption;
    @BindView(R.id.radio_moderate)
    RadioButton moderateOption;
    @BindView(R.id.location_spinner)
    Spinner locationSpinner;
    @BindView(R.id.user_description_view)
    TextView userDescription;
    @BindView(R.id.image_request_rv)
    RecyclerView imageRV;

    Bundle bundle;
    int userPriority;
    int location;
    ActionBar actionBar;

    public MaintenanceDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maintenance_details, container, false);
        ButterKnife.bind(this, rootView);
        bundle = getArguments();
        imageRV.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        setHasOptionsMenu(true);
        setupActionBar();
        setPriorityOptions();
        setLocationSpinner();
        getMaintenanceDetails();
        return rootView;
    }

    public void setupActionBar() {
        try {
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception e) {
            Log.e("testibg,", e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getMaintenanceDetails() {
        if (bundle != null) {
            locationSpinner.setOnKeyListener(null);
            moderateOption.setKeyListener(null);
            urgentOption.setKeyListener(null);
            issueTitle.setKeyListener(null);
            userDescription.setKeyListener(null);

            String tixData = bundle.getString("Maintenance_data");
            Gson gson = new Gson();
            Tickets data = gson.fromJson(tixData, Tickets.class);
            tenantName.setText("Robyn Rivera");
            tenantApt.setText(data.getApt());
            issueTitle.setText(data.getTitle());
            userDescription.setText(data.getDescription());
            locationSpinner.setSelection(data.getLocation());

            switch (data.getPriority()) {
                case 1:
                    urgentOption.setChecked(true);
                    break;
                case 2:
                    moderateOption.setChecked(true);
                    break;
            }

            if (data.getImageURl() != null) {
                ArrayList<String> storage = new ArrayList<>();
                storage.addAll(data.getImageURl());
                Log.e("data", storage.size() + "");
                imageRV.setAdapter(new ImageAdapter(storage));
            }

        }

    }

    public void setPriorityOptions(){
        priorityOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                switch (checked){
                    case R.id.radio_urgent:
                        userPriority = 1;
                        break;
                    case R.id.radio_moderate:
                        userPriority = 2;
                        break;
                }
            }
        });

    }

    public void setLocationSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.location_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onDestroy() {
        actionBar.setDisplayHomeAsUpEnabled(false);
        super.onDestroy();
    }

}
