package nyc.c4q.capstone.splash;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.capstone.datamodels.Properties;
import nyc.c4q.capstone.datamodels.Tickets;
import nyc.c4q.capstone.datamodels.UserApartmentInfo;

/**
 * Created by jervon.arnoldd on 3/17/18.
 */

public class JunkCode {


//    public UserApartmentInfo(String id, String building_id, String APT, String first_name, String last_name, String phone_number, String email_address)

//    UserApartmentInfo userApartmentInfo = new UserApartmentInfo(firebaseUser.getUid(),1521310103,"1K", "Jervon", "Arnold", "917-562-2823", "userApartmentInfo@hotmail.com");
//        UserApartmentInfo userApartmentInfo2 = new UserApartmentInfo("2K", "Amy", "Denis", "917-562-2823", "userApartmentInfo@hotmail.com");
//        UserApartmentInfo userApartmentInfo3 = new UserApartmentInfo("3K", "Darnell", "Denis", "917-562-2823", "userApartmentInfo@hotmail.com");
//        UserApartmentInfo userApartmentInfo4 = new UserApartmentInfo("4K", "Victoria", "Denis", "917-562-2823", "userApartmentInfo@hotmail.com");
//        UserApartmentInfo userApartmentInfo5 = new UserApartmentInfo("5K", "C4Q", "Denis", "917-562-2823", "userApartmentInfo@hotmail.com");
//        UserApartmentInfo userApartmentInfo6 = new UserApartmentInfo("6K", "John", "Denis", "917-562-2823", "userApartmentInfo@hotmail.com");

//    Properties properties0 = new Properties(unixTime + 1, "Low Tower", "413 Herzl Street", 45, 10000);
//    Properties properties1 = new Properties(unixTime + 2, "High Tower", "100 Herzl Street", 47, 10000);
//    Properties properties2 = new Properties(unixTime + 3, "Med Tower", "4500 Herzl Street", 45, 10000);
//    Properties properties3 = new Properties(unixTime + 4, "C4Q Tower", "700 Herzl Street", 45, 10000);
//    Properties properties4 = new Properties(unixTime + 6, "Arnold Tower", "413 Herzl Street", 45, 10000);
//
//
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference tenanets = database.getReference(tenanetsData);
//    DatabaseReference maintenance = database.getReference(maintenanceData);
//    DatabaseReference users = database.getReference("user");
//
//        users.child(userApartmentInfo.getId()).setValue(userApartmentInfo);
//


//        DatabaseReference prop = database.getReference(propertiesData);
//        prop.child(String.valueOf(properties0.ID)).setValue(properties0);
//        prop.child(String.valueOf(properties1.ID)).setValue(properties1);
//        prop.child(String.valueOf(properties2.ID)).setValue(properties2);
//        prop.child(String.valueOf(properties3.ID)).setValue(properties3);
//        prop.child(String.valueOf(properties4.ID)).setValue(properties4);

//
//    Tickets ticketone = new Tickets("5Y", unixTime, "This is just a test to tell you the boiler is broken", "Pending");
//    Tickets ticketTwo = new Tickets("7M", unixTime, "This is just a test to tell you the light is broken", "Pending");
//    Tickets ticketthree = new Tickets("9W", unixTime, "This is just a test to tell you the Door Bell is broken", "Pending");
//
//    String primaryKey = String.valueOf(1521250539);
//
//    List<Tickets> ticketsList = new ArrayList<>();
//        ticketsList.add(ticketone);
//        ticketsList.add(ticketTwo);
//        ticketsList.add(ticketthree);


//        maintenance.child("1521310101").child(ticketthree.APT).setValue(ticketsList);
//
//        tenanets.child("1521310103").child(userApartmentInfo.APT).setValue(userApartmentInfo);
//        tenanets.child("1521310103").child(userApartmentInfo2.APT).setValue(userApartmentInfo2);
//        tenanets.child("1521310103").child(userApartmentInfo3.APT).setValue(userApartmentInfo3);
//        tenanets.child("1521310103").child(userApartmentInfo4.APT).setValue(userApartmentInfo4);
//        tenanets.child("1521310103").child(userApartmentInfo5.APT).setValue(userApartmentInfo5);
//        tenanets.child("1521310103").child(userApartmentInfo6.APT).setValue(userApartmentInfo6);


    //Returns a List
//        Query query = database.getReference(maintenanceData).child("1521250539");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                Tickets one = dataSnapshot.getValue(Tickets.class);
//                GenericTypeIndicator<List<Tickets>> t = new GenericTypeIndicator<List<Tickets>>() {};
//                List<Tickets> messages = dataSnapshot.getValue(t);
//
//                assert messages != null;
//                for (int i = 0; i < messages.size(); i++) {
//                    if (messages.get(i).APT.equals("1K")){
//                        Log.e("Found 1k",messages.get(i).APT);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//       yoo.toString();
//       Log.e("yoo",query.toString());
//        tenanets.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                String value = dataSnapshot.getValue(String.class);
////               List<UserApartmentInfo> hello= dataSnapshot.getValue(Teees);
////
////                UserApartmentInfo value2 = dataSnapshot.getValue(UserApartmentInfo.class);
////                Log.e(TAG, "Value is: " + value2.first_name);
////                Log.e(TAG, "Value is: " + value2.last_name);
////                Log.e(TAG, "Value is: " + value2.phone_number);
////                Log.e(TAG, "Value is: " + value2.email_address);
////                sendNotification(value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.e(TAG, "Failed to read value.", error.toException());
//            }
//        });
}
