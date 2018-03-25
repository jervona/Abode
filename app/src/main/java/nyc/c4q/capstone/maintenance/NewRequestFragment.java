package nyc.c4q.capstone.maintenance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {
    View rootView;

    @BindView(R.id.tix_num_display)
    TextView tixNumDisplay;
    @BindView(R.id.request_date_display)
    TextView requestDateDisplay;
    @BindView(R.id.date_scheduled_display)
    TextView dateScheduledDisplay;
    @BindView(R.id.subject_title_view)
    EditText subjectTitle;
    @BindView(R.id.urgent_checkbox)
    CheckBox urgentCheckBox;
    @BindView(R.id.moderate_checkbox)
    CheckBox moderateCheckBox;
    @BindView(R.id.location_spinner)
    Spinner locationSpinner;
    @BindView(R.id.user_description_view)
    EditText userDescription;

    @BindView(R.id.repair_image)
    ImageView repairImage;

    TenantDataBaseHelper db;
    private ScrollView maintenanceScroll;
    private static final int REQUEST_IMAGE = 2;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    String TAG = "MaintenanceFragment";
    String location;

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
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = aptLocation.get(position);
                Log.e("i choose", aptLocation.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    @OnClick(R.id.submit_request_button)
    public void submitTicket() {
        long time = Calendar.getInstance().getTimeInMillis();
        Tickets tickets = new Tickets("1"
                , time
                , location
                , db.getUser().getAPT()
                , userDescription.getText().toString()
                , "Pending"
                , ""
        );
        db.createNewTicket(tickets);
        getActivity().onBackPressed();
    }

    @OnClick(R.id.add_photo_button)
    public void getPicture() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void getPictureFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        Log.e("Photo", imageUri.toString());
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            long time = Calendar.getInstance().getTimeInMillis();
            Tickets tickets = new Tickets("1"
                    , time
                    , location
                    , db.getUser().getAPT()
                    , userDescription.getText().toString()
                    , "Pending"
                    , "");
            db.createNewTicket(data, tickets);
        }
    }
}
