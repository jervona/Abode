package nyc.c4q.capstone.BottomNavFragment;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ScrollView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.capstone.MainActivity;
import nyc.c4q.capstone.R;
import nyc.c4q.capstone.database.TenantDataBaseHelper;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.maintenance.NewRequestFragment;
import nyc.c4q.capstone.maintenance.SubmittedAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaintanceFragment extends Fragment {

    View rootView;

    @BindView(R.id.ticket_rv)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

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
    SubmittedAdapter adapter;
    TenantDataBaseHelper db;


    public MaintanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maintenance, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, rootView);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = TenantDataBaseHelper.getInstance(FirebaseDatabase.getInstance());
        adapter = new SubmittedAdapter(ticketsList);
        recyclerView.setAdapter(adapter);
        updateList();
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
                if (ticketsList != null) {
                    adapter.updateTicketListItems(ticketsList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
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
