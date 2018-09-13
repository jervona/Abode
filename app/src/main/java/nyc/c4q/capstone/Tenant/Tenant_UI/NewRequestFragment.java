package nyc.c4q.capstone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.gson.Gson;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import nyc.c4q.capstone.adapters.ImageAdapter;
import nyc.c4q.capstone.datamodels.Tickets;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRequestFragment extends Fragment {

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
    @BindView(R.id.submit_request_button)
    Button submit;
    @Inject
    TenentDataBaseHelper helper;

    View rootView;
    ImageAdapter adapter;

    AHBottomNavigation bottom;
    ActionBar actionBar;
    int userPriority;
    int location;

    private static final int REQUEST_IMAGE = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    String TAG = "MaintenanceFragment";

    List<Bitmap> bitmapList = new ArrayList<>();
    Bundle bundle;

    public NewRequestFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_new_request, container, false);
        ButterKnife.bind(this, rootView);
        ((BaseApp) rootView.getContext().getApplicationContext()).myComponent.inject(this);
        bundle = getArguments();
        if (bundle != null) {
            getTixRequestData();
        } else {
            adapter = new ImageAdapter(bitmapList);
        }

        setupActionBar();
        bottom = getActivity().findViewById(R.id.bottom_navigation);
        bottom.setVisibility(View.GONE);
        radioGroup();

        return rootView;
    }

    void radioGroup() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.location_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        priorityOptions.setOnCheckedChangeListener((radioGroup, checked) -> {
            switch (checked) {
                case R.id.radio_urgent:
                    userPriority = 1;
                    break;
                case R.id.radio_moderate:
                    userPriority = 2;
                    break;
            }
        });

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

    public void setupActionBar() {
        setHasOptionsMenu(true);
        try {
            assert getActivity() != null;
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception e) {
            Log.e(TAG,"ActionBar Error, "+ e.getMessage());
        }
    }

    public void getTixRequestData() {
        locationSpinner.setOnKeyListener(null);
        moderateOption.setKeyListener(null);
        urgentoption.setKeyListener(null);
        subjectTitle.setKeyListener(null);
        userDescription.setKeyListener(null);
        submit.setVisibility(View.GONE);

        String tixData = bundle.getString("Tix_data");
        Gson gson = new Gson();
        Tickets data = gson.fromJson(tixData, Tickets.class);
        subjectTitle.setText(data.getTitle());
        userDescription.setText(data.getDescription());
        locationSpinner.setSelection(data.getLocation());

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

    @OnClick(R.id.add_photo_button)
    void optionDialog() {
        DialogPlus dialog = DialogPlus.newDialog(rootView.getContext())
                .setContentHolder(new ViewHolder(R.layout.option_dialog))
                .setOnClickListener((dialog1, view) -> {
                    switch (view.getId()) {
                        case R.id.camera:
                            Log.e("Camera", "ckicked");
                            dispatchTakePictureIntent();
                            break;
                        case R.id.library:
                            Log.e("library", "ckicked");
                            getPicture();
                            break;
                    }
                })
                .create();
        dialog.show();
    }

    public void dispatchTakePictureIntent() {
        Log.e(TAG, "dispatchTakePictureIntent()");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(rootView.getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void getPicture() {
        Log.e(TAG, "getPicture");
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE);
    }

    @OnClick(R.id.submit_request_button)
    public void newTicketWithPhones() {
        long time = Calendar.getInstance().getTimeInMillis();
        Tickets ticket = new Tickets("1"
                , time
                , location
                , helper.getUser().getAPT()
                , subjectTitle.getText().toString()
                , userDescription.getText().toString()
                , "Pending"
                , userPriority);

        Observable.fromIterable(bitmapList)
                .flatMap(bitmap -> helper.uploadImageToCloud(bitmap))
                .flatMap(taskSnapshot -> Observable.just(taskSnapshot.getMetadata().getDownloadUrl().toString()))
                .toList()
                .map(strings -> {
                    ticket.setImageURl(strings);
                    return helper.createNewTicket(ticket);
                })
                .subscribe(completableObservable -> {

                },throwable -> {

                });
    }

//    private ObservableSource<? extends R> uploadImageToCloud() {
//
//    }

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
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = rootView.getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bitmapList.add(selectedImage);
                Log.e(TAG, "lib" + selectedImage.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmapList.add(imageBitmap);
        }
        adapter.updatedList(bitmapList);
    }

    @Override
    public void onDestroy() {
        actionBar.setDisplayHomeAsUpEnabled(false);
        bottom.setVisibility(View.VISIBLE);
        super.onDestroy();
    }
}