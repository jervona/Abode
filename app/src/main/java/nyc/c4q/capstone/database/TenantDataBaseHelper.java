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

import nyc.c4q.capstone.TenantBottomNavFragment.DashBoardFragment;
import nyc.c4q.capstone.MainActivity;
import tenant_data_models.TenantPaymentHistoryModel;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserInfo;

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

    private UserInfo user;
    private List<Tickets> ticketsList;
    private List<TenantPaymentHistoryModel> paymentsList;


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


    public void getUserInfoFromDataBase(String uid, final MainActivity.UserDBListener listener) {
        ticketsList = new ArrayList<>();
        Query query = database.getReference("user").child(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UserInfo.class);
                assert user != null;
                listener.delegateUser(user);

                if (user.getStatus().equals("Tenant")) {
                    String id = String.valueOf(user.getBuilding_id());
                    getMaintenance(id, user.getAPT());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });
    }

    private void getMaintenance(String building_id, String apt) {
        Query query = database.getReference("Maintenance").child(building_id).child(apt);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Tickets>> data = new GenericTypeIndicator<List<Tickets>>() {
                };
                //ticketsList = dataSnapshot.getValue(data);
                getPayments(user.getAPT());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPayments(String apt) {
        Query query = database.getReference("Rent").child(user.getAPT());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<TenantPaymentHistoryModel>> data = new GenericTypeIndicator<List<TenantPaymentHistoryModel>>() {
                };
                paymentsList = dataSnapshot.getValue(data);
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
//                            ticketsList.add(tickets);
//                            String buildingID = String.valueOf(user.getBuilding_id());
//                            database.getReference().child(maintenanceData)
//                                    .child(buildingID)
//                                    .child(user.getAPT())
//                                    .setValue(ticketsList);
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

    public void upLoadRent(TenantPaymentHistoryModel rent) {
        if (paymentsList == null) {
            paymentsList = new ArrayList<>();
            paymentsList.add(rent);
        } else {
            paymentsList.add(rent);
        }
        database.getReference().child("Rent").child(user.getAPT()).setValue(paymentsList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

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
        if (ticketsList == null) {
            ticketsList = new ArrayList<>();
            ticketsList.add(ticket);
        } else {
            ticketsList.add(ticket);
        }
        String buildingID = String.valueOf(user.getBuilding_id());
        database.getReference().child(maintenanceData).child(buildingID).child(user.getAPT())
                .setValue(ticketsList).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    public UserInfo getUser() {
        return user;
    }

    public List<Tickets> getTicketsList() {
        return ticketsList;
    }

    public List<TenantPaymentHistoryModel> getPayments() {
        return paymentsList;
    }


}
