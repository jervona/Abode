package nyc.c4q.capstone;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import durdinapps.rxfirebase2.RxFirebaseStorage;
import io.reactivex.Completable;
import io.reactivex.Observable;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserInfo;
import tenant_data_models.TenantPaymentHistoryModel;

/**
 * Created by jervon.arnoldd on 6/28/18.
 */

@Singleton
public class TenentDataBaseHelper {

    private FirebaseDatabase database;
    private UserInfo user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference maintenanceRef;
    private DatabaseReference paymentHistoryRef;

    private List<TenantPaymentHistoryModel> paymentsList;
    private List<Tickets> ticketsList;

    @Inject
    TenentDataBaseHelper(FirebaseDatabase database,FirebaseAuth firebaseAuth) {
        this.database = database;
        this.firebaseAuth=firebaseAuth;
    }

    void setUser(UserInfo user) {
        this.user = user;
        this.maintenanceRef =database.getReference().child("Maintenance").child(String.valueOf(user.getBuilding_id())).child(user.getAPT());
        this.paymentHistoryRef =database.getReference().child("Rent").child(String.valueOf(user.getBuilding_id())).child(user.getAPT());
    }

    UserInfo getUser() {
        return user;
    }

    Observable<List<Tickets>> maintenanceValueListener() {
        return RxFirebaseDatabase
                .observeValueEvent(maintenanceRef,DataSnapshotMapper.listOf(Tickets.class))
                .doOnNext(tickets -> ticketsList = tickets)
                .toObservable();
    }

    Observable<List<TenantPaymentHistoryModel>> paymentValueListener() {
        return RxFirebaseDatabase
                .observeValueEvent(paymentHistoryRef, DataSnapshotMapper.listOf(TenantPaymentHistoryModel.class))
                .doOnNext(paymentHistoryModelList -> paymentsList = paymentHistoryModelList)
                .toObservable();
    }

    Observable<Completable> upLoadRent(TenantPaymentHistoryModel rent) {
        if (paymentsList == null) {
            paymentsList = new ArrayList<>();
            paymentsList.add(rent);
        } else {
            paymentsList.add(rent);
        }
        return RxFirebaseDatabase.setValue(paymentHistoryRef,paymentsList)
                .toObservable();
    }

    Observable<Completable> createNewTicket(final Tickets ticket) {
        if (ticketsList == null) {
            ticketsList = new ArrayList<>();
            ticketsList.add(ticket);
        } else {
            ticketsList.add(ticket);
        }
        return  RxFirebaseDatabase.setValue(maintenanceRef,ticketsList)
                .toObservable();
    }

    Observable<UploadTask.TaskSnapshot> uploadImageToCloud(Bitmap data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        data.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageData = baos.toByteArray();
        long time = Calendar.getInstance().getTimeInMillis();
        String key = String.valueOf(time);
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference(firebaseAuth.getUid())
                .child(key);
        return RxFirebaseStorage.putBytes(storageReference, imageData)
                .toObservable()
                .doOnNext(taskSnapshot -> taskSnapshot.getMetadata().getDownloadUrl().toString());
    }
}
