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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nyc.c4q.capstone.BottomNavFragment.DashBoardFragment;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserApartmentInfo;

import static android.content.ContentValues.TAG;

/**
 * Created by jervon.arnoldd on 3/15/18.
 */

public class TenantDataBaseHelper {

    private FirebaseDatabase database;
    private StorageReference storageReference;
    private String maintenanceData = "Maintenance";
    private String tenanetsData = "Tenanets";
    private String propertiesData = "Properties";
    private String userData = "user";

    private UserApartmentInfo user;
    private List<Tickets> messages;




    private List<String> listOfUrl = new ArrayList<>();

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
        messages = new ArrayList<>();
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
                Log.e("Error", databaseError.getMessage());
            }
        });
    }

    private void getMainInfo(String building_id, String apt) {
        Query query = database.getReference("Maintenance").child(building_id).child(apt);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Tickets>> data = new GenericTypeIndicator<List<Tickets>>() {
                };
                messages = dataSnapshot.getValue(data);
                DashBoardFragment.giveStuff(messages,user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    public void createNewTicket(Intent data, final Tickets ticket) {
//        final Uri uri = data.getData();
//        String buildingID = String.valueOf(user.getBuilding_id());
//        database.getReference().child(maintenanceData).child(buildingID).child(user.getAPT()).push()
//                .setValue(ticket, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError,DatabaseReference databaseReference) {
//                        if (databaseError == null) {
//                            String key = databaseReference.getKey();
//                                StorageReference storageReference = FirebaseStorage.getInstance()
//                                        .getReference(FirebaseAuth.getInstance().getUid())
//                                        .child(key)
//                                        .child(uri.getLastPathSegment());
//                                putImageInStorage(storageReference, uri, key, ticket);
//
//
//                        } else {
//                            Log.w(TAG, "Unable to write message to database.",
//                                    databaseError.toException());
//                        }
//                    }
//                });
//    }

//    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key, final Tickets ticket) {
//        storageReference.putFile(uri).addOnCompleteListener(
//                new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Tickets tickets = new Tickets(ticket.getTicket_number()
//                                    , ticket.getTime()
//                                    , ticket.getApt()
//                                    , ticket.getDescription()
//                                    , ticket.getDescription()
//                                    , task.getResult().getMetadata().getDownloadUrl().toString());
//                            messages.add(tickets);
//                            String buildingID = String.valueOf(user.getBuilding_id());
//                            database.getReference().child(maintenanceData)
//                                    .child(buildingID)
//                                    .child(user.getAPT())
//                                    .setValue(messages);
//                        } else {
//                            Log.w(TAG, "Image upload task was not successful.",
//                                    task.getException());
//                        }
//                    }
//                });
//    }

    public void upLoadPhoto(Intent data) {
        final Uri uri = data.getData();
        long time = Calendar.getInstance().getTimeInMillis();
        String key = String.valueOf(time);
        storageReference = FirebaseStorage.getInstance()
                .getReference(FirebaseAuth.getInstance().getUid())
                .child(key)
                .child(uri.getLastPathSegment());
        putImageInStorage(storageReference, uri, key);
    }

    private void putImageInStorage(StorageReference storageReference, Uri uri, final String key) {
        storageReference.putFile(uri).addOnCompleteListener(
                new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            listOfUrl.add(task.getResult().getMetadata().getDownloadUrl().toString());
                        } else {
                            Log.e(TAG, "Image upload task was not successful.", task.getException());
                        }
                    }
                });
    }

    public void createNewTicket(final Tickets ticket) {
        ticket.setImageURl(listOfUrl);
        if (messages == null) {
            messages = new ArrayList<>();
            messages.add(ticket);
        } else {
            messages.add(ticket);
        }
        String buildingID = String.valueOf(user.getBuilding_id());
        database.getReference().child(maintenanceData).child(buildingID).child(user.getAPT())
                .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    public void removePhotos(int postion) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference desertRef = storageRef.child(listOfUrl.get(postion));
    }


    public UserApartmentInfo getUser() {
        return user;
    }

    public List<Tickets> getMessages() {
        return messages;
    }

}
