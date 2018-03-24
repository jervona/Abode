package nyc.c4q.capstone.maintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {
    View rootView;
    @BindView(R.id.tix_num_view)
    TextView tixNumView;
    @BindView(R.id.tix_num_display)
    TextView tixNumDisplay;
    @BindView(R.id.request_date_view)
    TextView requestDateView;
    @BindView(R.id.request_date_display)
    TextView requestDateDisplay;
    @BindView(R.id.date_scheduled_view)
    TextView dateScheduledView;
    @BindView(R.id.date_scheduled_display)
    TextView dateScheduledDisplay;
    @BindView(R.id.subject_title_view)
    EditText subjectTitle;
    @BindView(R.id.priority_view)
    TextView priorityType;
    @BindView(R.id.urgent_checkbox)
    CheckBox urgentCheckBox;
    @BindView(R.id.moderate_checkbox)
    CheckBox moderateCheckBox;
    @BindView(R.id.repair_location_view)
    TextView repairLocation;
    @BindView(R.id.location_spinner)
    Spinner locationSpinner;
    @BindView(R.id.repair_description_view)
    TextView description;
    @BindView(R.id.user_description_view)
    EditText userDescription;
    @BindView(R.id.repair_image)
    ImageView repairImage;
    @BindView(R.id.add_photo_button)
    Button addPhotoBtn;
    @BindView(R.id.submit_request_button)
    Button submitRepairBtn;

    public NewRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new_request, container, false);
        ButterKnife.bind(this, rootView);

        final List<String> aptLocation = new ArrayList<>();
        aptLocation.add("Bathroom");
        aptLocation.add("Kitchen");
        aptLocation.add("Living Room");
        aptLocation.add("Bedroom");
        aptLocation.add("Other");

        ArrayAdapter<String> aptLocationAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, aptLocation);
        aptLocationAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        locationSpinner.setAdapter(aptLocationAdapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("i choose", aptLocation.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

}
