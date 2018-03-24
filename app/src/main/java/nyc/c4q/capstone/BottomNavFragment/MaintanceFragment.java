package nyc.c4q.capstone.BottomNavFragment;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import nyc.c4q.capstone.DataBaseTesting;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.maintenance.NewRequestFragment;
import nyc.c4q.capstone.maintenance.SubmittedAdapter;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaintanceFragment extends Fragment {

    View rootView;
    TextView pendingView;
    TextView scheduledView;
    TextView completedView;
    RecyclerView pendingRV;
    RecyclerView scheduledRV;
    RecyclerView completedRV;
    SubmittedAdapter adapter;
    LinearLayoutManager pendingLayoutManager;
    LinearLayoutManager scheduledLayoutManager;
    LinearLayoutManager completedLayoutManager;
    private ScrollView maintenanceScroll;
    private static final int REQUEST_IMAGE = 2;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    String TAG = "MaintenanceFragment";
    List<Tickets> ticketsList;
    FirebaseDatabase data = FirebaseDatabase.getInstance();

    private static final int NOTIFICATION_ID = 555;
    String NOTIFICATION_CHANNEL = "C4Q Notifications";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    TenantDataBaseHelper db;
    String id;
//    DataBaseTesting db;

    public MaintanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maintenance, container, false);
//        ButterKnife.bind(this,rootView);
        pendingView = rootView.findViewById(R.id.pending_view);
        scheduledView = rootView.findViewById(R.id.scheduled_view);
        completedView = rootView.findViewById(R.id.completed_view);
        maintenanceScroll = rootView.findViewById(R.id.maintenance_scroll);
        pendingRV = rootView.findViewById(R.id.pending_tix_rv);
        scheduledRV = rootView.findViewById(R.id.scheduled_tix_rv);
        completedRV = rootView.findViewById(R.id.completed_tix_rv);
        pendingLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        scheduledLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        completedLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        if (db.getMessages() == null) {
            ticketsList = new ArrayList<>();
        } else {
            ticketsList = db.getMessages();
        }

        adapter = new SubmittedAdapter(ticketsList);
        pendingRV.setLayoutManager(pendingLayoutManager);
        pendingRV.setAdapter(adapter);
        scheduledRV.setLayoutManager(scheduledLayoutManager);
//        scheduledRV.setAdapter(adapter);
        completedRV.setLayoutManager(completedLayoutManager);
//        completedRV.setAdapter(adapter);
        updateList();

//            ticketsList = db.getMessages();
////            StorageReference storageReference = storage.getReferenceFromUrl(ticketsList.get(1).getImageUrl());
//            Glide.with(this /* context */)
//                    .using(new FirebaseImageLoader())
//                    .load(storageReference)
//                    .into(imageView);
//        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Toast.makeText(getActivity(), "New Repair Request ", Toast.LENGTH_SHORT).show();
                NewRequestFragment requestFragment = new NewRequestFragment();
                FragmentManager fragManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragManager.beginTransaction();
                fragmentTransaction.replace(R.id.maintenance_frag, requestFragment).addToBackStack("maintenance frag");
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateList() {
        data.getReference().child("Maintenance").child(String.valueOf(db.getUser().getBuilding_id())).child(db.getUser().getAPT()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Tickets>> t = new GenericTypeIndicator<List<Tickets>>() {};
                ticketsList = dataSnapshot.getValue(t);
                sendNotification("hello");
                adapter.swap(ticketsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    public void sendNotification(String a) {
        Intent intent = new Intent(rootView.getContext().getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(rootView.getContext().getApplicationContext(), NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager) rootView.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(rootView.getContext().getApplicationContext(), NOTIFICATION_CHANNEL)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("You've been notified! of a change")
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText("is is now four");
        assert notificationManager != null;
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
