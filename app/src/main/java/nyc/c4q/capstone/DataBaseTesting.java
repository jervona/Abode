package nyc.c4q.capstone;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserApartmentInfo;

import static android.content.ContentValues.TAG;

/**
 * Created by jervon.arnoldd on 3/15/18.
 */

public class DataBaseTesting {

      private FirebaseDatabase database;

      private UserApartmentInfo user;
      private List<Tickets> messages;

      private static DataBaseTesting instance;


    private DataBaseTesting(FirebaseDatabase database) {
        this.database = database;
    }

    public static DataBaseTesting getInstance(FirebaseDatabase database) {
        if (instance == null) {
            instance = new DataBaseTesting(database);
        }
        return instance;
    }


    void getUserInfoFromDataBase(String uid) {
        Query query = database.getReference("user").child(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               user =  dataSnapshot.getValue(UserApartmentInfo.class);
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
                Log.d(TAG, "onDataChange: 42");
                GenericTypeIndicator<List<Tickets>> t = new GenericTypeIndicator<List<Tickets>>() {
                };
                messages = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
