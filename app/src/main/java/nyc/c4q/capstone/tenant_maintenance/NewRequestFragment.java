package nyc.c4q.capstone.tenant_maintenance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nyc.c4q.capstone.adapter.ImageAdapter;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {
    View rootView;


    @BindView(R.id.subject_title_view)
    EditText subjectTitle;
    @BindView((R.id.radio_priority))
    RadioGroup priorityOptions;
    @BindView(R.id.radio_urgent)
    RadioButton urgentoption;
    @BindView(R.id.radio_moderate)
    RadioButton moderateOption;
    @BindView(R.id.location_spinner)
    Spinner locationSpinner;
    @BindView(R.id.user_description_view)
    EditText userDescription;
    @BindView(R.id.image_request_rv)
    RecyclerView rv;
    ActionBar actionBar;
    int userPriority;


    TenantDataBaseHelper db;
    private ScrollView maintenanceScroll;
    private static final int REQUEST_IMAGE = 2;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    String TAG = "MaintenanceFragment";
    String location;
    List<Intent> listOfPhotos;
    List<Uri> hello = new ArrayList<>();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bundle bundle;


    public NewRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_request, container, false);
        ButterKnife.bind(this, rootView);
        bundle = getArguments();
        setHasOptionsMenu(true);
        setupActionBar();



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.location_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        rv.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));

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

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getTixRequestData();

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

    @OnClick(R.id.submit_request_button)
    public void newTicketWithPhones() {
        long time = Calendar.getInstance().getTimeInMillis();
        Tickets tickets = new Tickets("1"
                , time
                , location
                , db.getUser().getAPT()
                , subjectTitle.getText().toString()
                , userDescription.getText().toString()
                , "Pending"
                , userPriority);
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

    public void getTixRequestData() {
        if (bundle != null) {
            String tixData = bundle.getString("Tix_data");
            Gson gson = new Gson();
            Tickets data = gson.fromJson(tixData, Tickets.class);
            subjectTitle.setText(data.getTitle());
            userDescription.setText(data.getDescription());

            switch (data.getPriority()) {
                case 1:
                    urgentoption.setChecked(true);
                    break;
                case 2:
                    moderateOption.setChecked(true);
                    break;
            }

            if (data.getImageURl() != null) {
                ArrayList<String> storage = new ArrayList<>();
                storage.addAll(data.getImageURl());
                Log.e("data", storage.size() + "");
                rv.setAdapter(new ImageAdapter(storage));
            }

        }

    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(rootView.getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            db.upLoadPhoto(data);
            Uri uri = data.getData();
            hello.add(uri);
            rv.setAdapter(new ImageAdapter(hello));
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            hello.add(uri);
            rv.setAdapter(new ImageAdapter(hello));
        }
    }

    @Override
    public void onDestroy() {
        actionBar.setDisplayHomeAsUpEnabled(false);
        super.onDestroy();
    }
}
