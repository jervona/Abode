package nyc.c4q.capstone.database;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.List;

import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserApartmentInfo;

import static android.content.ContentValues.TAG;

/**
 * Created by jervon.arnoldd on 3/15/18.
 */

public class TenantDataBaseHelper {

    private FirebaseDatabase database;
    private String maintenanceData = "Maintenance";
    private String tenanetsData = "Tenanets";
    private String propertiesData = "Properties";
    private String userData = "user";

    private UserApartmentInfo user;
    private List<Tickets> messages;

    private static TenantDataBaseHelper instance;


    private TenantDataBaseHelper(FirebaseDatabase database) {
        this.database = database;
    }

    public static TenantDataBaseHelper getInstance(FirebaseDatabase database) {
        if (instance == null) {
            instance = new TenantDataBaseHelper(database);
        }
        return instance;
    }


    public void getUserInfoFromDataBase(String uid) {
        Query query = database.getReference("user").child(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UserApartmentInfo.class);
                String id = String.valueOf(user.getBuilding_id());
                getMainInfo(id, user.getAPT());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });
    }

    private void getMainInfo(String building_id, String apt) {
        Query query = database.getReference("Maintenance").child(building_id).child(apt);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Tickets>> t = new GenericTypeIndicator<List<Tickets>>() {
                };
                messages = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void createNewTicket(Intent data, final Tickets ticket) {
        long time= Calendar.getInstance().getTimeInMillis();
        final Uri uri = data.getData();
        Log.e(TAG, "Uri: " + uri.toString());
        Tickets tickets = new Tickets("1"
                ,time
                , ticket.getApt()
                , ticket.getDescription()
                , ticket.getDescription()
                , "https://www.google.com/images/spin-32.gif");
        String buildingID = String.valueOf(user.getBuilding_id());
        database.getReference().child(maintenanceData).child(buildingID).child(user.getAPT()).push()
                .setValue(tickets, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError,
                                           DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            String key = databaseReference.getKey();
                            StorageReference storageReference = FirebaseStorage.getInstance()
                                            .getReference(FirebaseAuth.getInstance().getUid())
                                            .child(key)
                                            .child(uri.getLastPathSegment());
                            putImageInStorage(storageReference, uri, key,ticket);
                        } else {
                            Log.w(TAG, "Unable to write message to database.",
                                    databaseError.toException());
                        }
                    }
                });
    }

    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key, final Tickets ticket) {
        storageReference.putFile(uri).addOnCompleteListener(
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Tickets tickets = new Tickets(ticket.getTicket_number()
                                    , ticket.getTime()
                                    , ticket.getApt()
                                    , ticket.getDescription()
                                    , ticket.getDescription()
                                    , task.getResult().getMetadata().getDownloadUrl().toString());
                            messages.add(tickets);
                            String buildingID = String.valueOf(user.getBuilding_id());
                            database.getReference().child(maintenanceData)
                                    .child(buildingID)
                                    .child(user.getAPT())
                                    .setValue(messages);
                        } else {
                            Log.w(TAG, "Image upload task was not successful.",
                                    task.getException());
                        }
                    }
                });
    }

    public UserApartmentInfo getUser() {
        return user;
    }

    public List<Tickets> getMessages() {
        return messages;
    }
}
